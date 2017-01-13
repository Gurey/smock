package se.gurey.smock.api.serviceroutes;

import static spark.Spark.*;

import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import se.gurey.smock.api.db.RouteMap;
import se.gurey.smock.api.entity.WebserviceEndpoint;

public class ServiceRoutes {
	
	static Logger log = LoggerFactory.getLogger(ServiceRoutes.class);
	
	private RouteMap routes;
	
	@Inject
	private Gson gson;
	
	@Inject
	public ServiceRoutes(RouteMap routes) {
		this.routes = routes;
	}

	public void init() {
		before((req, res) -> {
			WebserviceEndpoint endpoint = routes.get(req.uri());
			if(endpoint != null){
				res.header("Content-Type", endpoint.getType());
				halt(200, endpoint.getResponse());
			}
		});
		
		get("/sample", (req,res) -> new WebserviceEndpoint("/service", "req", "{\"res\":123}"), gson::toJson);
		
		
		after("/smock/api/*" ,(req, res) -> res.header("Content-Type", "application/json"));
		get("/smock/api/routes", (req, res) -> routes.values().stream().collect(Collectors.toList()),gson::toJson);

		post("/smock/api/routes", (req,res) -> {
			String body = req.body();
			WebserviceEndpoint postEnd = gson.fromJson(body, WebserviceEndpoint.class);
			routes.put(postEnd.getPath(), postEnd);
			return "{\"status\":\"OK\"}";
		});
		
	}
	
	
}
