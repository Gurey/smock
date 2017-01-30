package se.gurey.smock.api.entity.rule;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import spark.Request;
import spark.Response;

public class JsonRule extends EndpointRule {
	
	private HashMap<String, String> jsonPaths;

	public JsonRule(String response, boolean isDefault, RuleType ruleType) {
		super(response, isDefault, ruleType);
		this.jsonPaths = new HashMap<>();
	}

	@Override
	public boolean applyRule(Request req, Response res) {
		Set<Entry<String, String>> ents = this.getJsonPaths().entrySet();
		String json = res.body();
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
	
	public HashMap<String, String> getJsonPaths() {
		return jsonPaths;
	}

}
