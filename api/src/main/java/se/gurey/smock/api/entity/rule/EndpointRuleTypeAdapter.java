package se.gurey.smock.api.entity.rule;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.jayway.jsonpath.JsonPath;
import spark.Request;

public class EndpointRuleTypeAdapter {

    private static Gson gson = new Gson();

    public static EndpointRule getEndpointRule(Request req){
        String body = req.body();
        System.out.println(body);
        String ruleType = JsonPath.parse(body).read("ruleType").toString().toLowerCase();
        if(RuleType.JSON.toString().toLowerCase().equals(ruleType)){
            return gson.fromJson(body, JsonRule.class);
        } else if (RuleType.CONTAINS.toString().toLowerCase().equals(ruleType)){
            return gson.fromJson(body, ContainsRule.class);
        }
        throw new IllegalStateException("An endpoint rule should have a ruleType set");
    }

}
