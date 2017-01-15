package se.gurey.smock.api.serviceroutes;

import se.gurey.smock.api.entity.WebserviceEndpoint;
import se.gurey.smock.api.entity.rule.EndpointRule;
import spark.Request;
import spark.Response;

public class ServiceRouteHandler {
    
    public String handleServiceRequest(WebserviceEndpoint endpoint, Request req, Response res) {
        System.out.println(endpoint.getRules().size() + " rule(s) found for " + endpoint.getPath());
        EndpointRule rule = endpoint.getRules().stream().filter(r -> r.applyRule(req, res)).findFirst().orElse(null);
        if(rule != null){
            System.out.println("RULE EXISTS: Returning " + rule.getResponse());
            return rule.getResponse();
        } else {
            EndpointRule defaultRule = endpoint.getRules().stream().filter(r -> r.isDefault()).findFirst().orElse(null);
            if(defaultRule != null){
                return defaultRule.getResponse();
            }
            System.out.println("NO default rule found for route: " + endpoint.getPath());
        }
        return null;
    }

}
