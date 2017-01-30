package se.gurey.smock.api.entity.rule;

import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ContainsRule extends EndpointRule{

    public ContainsRule(String response, HashMap<String, String> conditions, boolean isDefault) {
        super(response, conditions, isDefault, RuleType.CONTAINS);
    }

    @Override
    public boolean applyRule(Request req, Response res) {
        final String body = req.body();
        Set<Map.Entry<String, String>> conds = getConditions().entrySet();
        return conds.stream()
                .filter(cond -> body.contains(cond.getKey()))
                .collect(Collectors.toList())
                .size() == conds.size();
    }

}
