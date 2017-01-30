package se.gurey.smock.api.serviceroutes;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Singleton;

import se.gurey.smock.api.db.RouteMap;
import se.gurey.smock.api.entity.WebserviceEndpoint;
import se.gurey.smock.api.entity.rule.ContainsRule;
import se.gurey.smock.api.entity.rule.EndpointRule;
import se.gurey.smock.api.entity.rule.EndpointRuleTypeAdapter;
import se.gurey.smock.api.path.PathMatcher;
import spark.Request;
import spark.Response;

@Singleton
public class ServiceRoutes {

    static Logger log = LoggerFactory.getLogger(ServiceRoutes.class);

    private RouteMap routes;
    
    @Inject
    private ServiceRouteHandler routeHandler;

    @Inject
    private PathMatcher pathMatcher;

    @Inject
    private Gson gson;

    @Inject
    public ServiceRoutes(RouteMap routes) {
        this.routes = routes;
    }

    public void init() {
        initServiceHandler();
        initSample();
        initSmockApi();
    }
    
    public void initServiceHandler() {
        before((req, res) -> {
            WebserviceEndpoint endpoint = this.getEndpoint(req, res);
            if (endpoint != null) {
                res.header("Content-Type", endpoint.getType());
                String response = routeHandler.handleServiceRequest(endpoint, req, res);
                if(response != null){
                    halt(200, response);
                } else {
                    halt(404);
                }
            }
        });
    }
    
    public WebserviceEndpoint getEndpoint(Request req, Response res){
        WebserviceEndpoint endpoint = routes.get(req.uri());
        if(endpoint == null){
            endpoint = routes.keySet().stream()
                    .filter(uri -> pathMatcher.pathMatches(uri, req.uri()))
                    .findFirst()
                    .map(result -> routes.get(result))
                    .orElse(null);
        }
        return endpoint;
    }

    public void initSample() {
        after((req, res) -> res.header("Content-Type", "application/json"));
        get("/sample/webend", (req, res) -> new WebserviceEndpoint("/service"), gson::toJson);
        get("/sample/rule", (req, res) -> new ContainsRule("contains",  new HashMap<String, String>(), false), gson::toJson);
    }

    public void initSmockApi() {
        after("/smock/api/*", (req, res) -> res.header("Content-Type", "application/json"));

        get("/smock/api/routes", (req, res) -> routes.values().stream().collect(Collectors.toList()), gson::toJson);

        post("/smock/api/routes", (req, res) -> {
            String body = req.body();
            WebserviceEndpoint postEnd = gson.fromJson(body, WebserviceEndpoint.class);
            routes.put(postEnd.getPath(), postEnd);
            return "{\"status\":\"OK\"}";
        });

        post("/smock/api/routes/:routeid/rules", (req, res) -> {
            log.info("Setting new rule: " + req.body());
            WebserviceEndpoint route = this.getRouteById(req.params("routeid"));
            EndpointRule rule = EndpointRuleTypeAdapter.getEndpointRule(req);
            if(route != null){
                route.getRules().add(rule);
            }
            return "TADA!";
        });

        get("/smock/api/routes/:routeid/rules", (req, res) -> {
            return this.getRouteById(req.params("routeid")).getRules();
        }, gson::toJson);
        
        
    }

    
    private WebserviceEndpoint getRouteById(String id) {
        return routes.values().stream().filter(wse -> wse.getId().equals(id)).findFirst().orElse(null);
    }
}
