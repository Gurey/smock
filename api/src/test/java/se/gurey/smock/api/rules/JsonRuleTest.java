package se.gurey.smock.api.rules;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import se.gurey.smock.api.entity.rule.JsonRule;
import se.gurey.smock.api.entity.rule.RuleType;
import spark.Request;
import spark.Response;

public class JsonRuleTest {

	private Gson g = new Gson();
	private JsonRule rule = new JsonRule("WORKING!", new HashMap<>() ,false);
	private String json;
	private Request req;
	private Response res;
	
	@Before
	public void before() {
		TestJson j = new TestJson();
		json = g.toJson(j);
		req = mock(Request.class);
		res = mock(Response.class);
	}
	
	@Test
	public void readJson(){
		when(res.body()).thenReturn(json);
		rule.getConditions().put("name", "myname");
		assertTrue(rule.applyRule(req, res));
	}
	
	@Test
	public void readJsonList(){
		when(res.body()).thenReturn(json);
		rule.getConditions().put("items[0].listname", "mylistname");
		assertTrue(rule.applyRule(req, res));
	}
	
	@Test
	public void readJsonListToManyInList(){
		TestJson j = new TestJson();
		j.items.add(new TestJsonListItem());
		when(res.body()).thenReturn(g.toJson(j));
		rule.getConditions().put("items[0].listname", "mylistname");
		assertTrue(rule.applyRule(req, res));
	}
	
	@Test
	public void readJsonTwoRules() {
		when(res.body()).thenReturn(json);
		rule.getConditions().put("items[0].listamount", "1");
		rule.getConditions().put("items[0].listname", "mylistname");
		assertTrue(rule.applyRule(req, res));
	}
	
	@Test
	public void readJsonWithBadPath() {
		when(res.body()).thenReturn(json);
		rule.getConditions().put("fail", "wewillnevergethere");
		assertFalse(rule.applyRule(req, res));
	}
	
	@Test
	public void gsonCanSerializeJsonRule(){
		rule.getConditions().put("name", "myname");
		String j = g.toJson(rule);
		g.fromJson(j, JsonRule.class);
	}
	
}


class TestJson {
	
	public String name;
	public List<TestJsonListItem> items;
	
	public TestJson() {
		this.name = "myname";
		this.items = new ArrayList<>();
		this.items.add(new TestJsonListItem());
	}
	
}

class TestJsonListItem {
	public String listname;
	public int listamount;
	
	public TestJsonListItem() {
		this.listname = "mylistname";
		this.listamount = 1;
	}
}