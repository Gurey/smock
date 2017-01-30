package se.gurey.smock.api.entity.rule;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import spark.Request;
import spark.Response;

public class JsonRule extends EndpointRule {
	

	public JsonRule(String response, HashMap<String, String> conditions, boolean isDefault) {
		super(response, conditions, isDefault, RuleType.JSON);
	}

	@Override
	public boolean applyRule(Request req, Response res) {
		Set<Entry<String, String>> ents = getConditions().entrySet();
		String json = req.body();
		for (Entry<String, String> entry : ents) {
			try {				
				String read = JsonPath.parse(json).read(entry.getKey()).toString();
				if(!read.equals(entry.getValue())){
					return false;
				}
			} catch (PathNotFoundException e) {
				return false;
			}
		}
		return true;
	}
	
}
