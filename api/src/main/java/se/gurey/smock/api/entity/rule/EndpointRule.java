package se.gurey.smock.api.entity.rule;

import spark.Request;
import spark.Response;

import java.util.HashMap;

public abstract class EndpointRule {

    private HashMap<String, String> conditions;
    private String response;
    private RuleType ruleType;
    private boolean isDefault;
    
    public EndpointRule(String response, HashMap<String, String> conditions, boolean isDefault, RuleType ruleType) {
        this.setResponse(response);
        this.setRuleType(ruleType);
        this.isDefault = isDefault;
        this.conditions = conditions;
    }

    abstract public boolean applyRule(Request req, Response res);

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setConditions(HashMap<String, String> conditions) {this.conditions = conditions;}

    public HashMap<String, String> getConditions() {
        return conditions;
    }
}
