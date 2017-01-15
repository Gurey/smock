package se.gurey.smock.api.entity.rule;

import spark.Request;
import spark.Response;

public abstract class EndpointRule {

    private String response;
    private RuleType ruleType;
    private boolean isDefault;
    
    public EndpointRule(String response, boolean isDefault, RuleType ruleType) {
        this.setResponse(response);
        this.setRuleType(ruleType);
        this.isDefault = isDefault;
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

}
