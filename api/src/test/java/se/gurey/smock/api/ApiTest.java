package se.gurey.smock.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.restassured.specification.RequestSpecification;
import se.gurey.smock.api.db.RouteMap;
import se.gurey.smock.api.entity.WebserviceEndpoint;
import se.gurey.smock.api.entity.rule.ContainsRule;
import se.gurey.smock.api.entity.rule.EndpointRule;
import se.gurey.smock.api.entity.rule.JsonRule;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class ApiTest {
    
   private static String SMOCK_API_ROUTES = "/smock/api/routes";
    
   private static String BASE_URL = "http://localhost:8088";
   private static RouteMap routeMap;
   private static final Gson g = new Gson();
   
   @BeforeClass
   public static void beforeCLass() {
       Injector injector = Guice.createInjector();
       routeMap = injector.getInstance(RouteMap.class);
       new Thread(() -> ApiInit.init(8088, injector)).start();
       awaitInitialization();
   }
   
   @AfterClass
   public static void afterClass() {
       System.out.println("Stopping server...");
       stop();
       System.out.println("Server stopped");
   }
   
   @After
   public void after(){
       routeMap.clear();
   }
   
   @Test
   public void testInsertService(){
       req()
           .get("/sample/webend")
       .then()
           .assertThat()
           .body("path", equalTo("/service"));
   }
   
   @Test
   public void testPostNewService() {
       req()
           .body(new WebserviceEndpoint("/service1"))
           .post(SMOCK_API_ROUTES)
       .then()
           .statusCode(200);
       
       req()
           .get(SMOCK_API_ROUTES)
       .then()
           .body("[0].path", equalTo("/service1"));
       
       setDefaultRepsonse();
       
       req()
           .get("/service1")
       .then()
           .body(equalTo("default response"));
   }
   
   @Test
   public void testPostServiceWithParam(){
       req()
       .body(new WebserviceEndpoint("/service/:id"))
       .post(SMOCK_API_ROUTES)
   .then()
       .statusCode(200);
   
   req()
       .get(SMOCK_API_ROUTES)
   .then()
       .body("[0].path", equalTo("/service/:id"));
   
   setDefaultRepsonse();
   
   req()
       .get("/service/12")
   .then()
       .body(equalTo("default response"));
   }
   
   @Test
   public void testSetServiceRule() {
       req()
           .body(new WebserviceEndpoint("/ruleend"))
           .post(SMOCK_API_ROUTES)
       .then()
           .statusCode(200);
       
       String ruleUri = SMOCK_API_ROUTES + "/" + getWebServiceEndpoint().getId() + "/rules";
       HashMap<String, String> conds = new HashMap<String, String>();
       conds.put("containsrule", null);
       req()
           .body(new ContainsRule("containsrule", conds, false))
           .post(ruleUri)
       .then()
           .statusCode(200);
       
       req()
           .body("containsrule")
           .get("/ruleend")
       .then()
           .body(equalTo("containsrule"));
   }

    @Test
    public void testGetServiceRule() {
        req()
            .body(new WebserviceEndpoint("/ruleend"))
            .post(SMOCK_API_ROUTES)
            .then()
            .statusCode(200);

        String ruleUri = SMOCK_API_ROUTES + "/" + getWebServiceEndpoint().getId() + "/rules";
        HashMap<String, String> containsConds = new HashMap<>();
        containsConds.put("contains", "contains");
        req()
            .body(new ContainsRule("containsrule", containsConds, false))
            .post(ruleUri)
            .then()
            .statusCode(200);

        HashMap<String, String> jsonConds = new HashMap<>();
        jsonConds.put("list[0]", "gurkan");
        jsonConds.put("list[1]", "ghol");
        req()
            .body(new JsonRule("jsonrules", jsonConds, false))
            .post(ruleUri)
            .then()
            .statusCode(200);

        req()
            .body("contains")
            .get("/ruleend")
            .then()
            .body(equalTo("containsrule"));

        String jsonPayload = g.toJson(new TestObject());
        req()
            .body(jsonPayload)
            .get("/ruleend")
            .then()
            .body(equalTo("jsonrules"));

        TestObject failJson = new TestObject();
        failJson.list.set(1, "failfailfail");
        jsonPayload = g.toJson(failJson);
        req()
            .body(jsonPayload)
            .get("/ruleend")
            .then()
            .statusCode(404);
    }
   
   public WebserviceEndpoint getWebServiceEndpoint() {
       return routeMap.values().stream().findFirst().get();
   }
   
   public void setDefaultRepsonse(){
       getWebServiceEndpoint().getRules().add(new ContainsRule("default response", new HashMap<>(), true));
   }
   
   private RequestSpecification req() {
       return given().baseUri(BASE_URL);
   }

   class TestObject{
       public List list = Arrays.asList("gurkan", "ghol");
   }
}
