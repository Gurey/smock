package se.gurey.smock.api.entity.rule;

import spark.Request;
import spark.Response;

public class ContainsRule extends EndpointRule{

    private String containsThis; 
    
    public ContainsRule(String containsThis, String response, boolean isDefault) {
        super(response, isDefault, RuleType.CONTAINS);
        this.containsThis = containsThis;
    }

    @Override
    public boolean applyRule(Request req, Response res) {
        return req.body().contains(containsThis);
    }

}
