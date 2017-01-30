package se.gurey.smock.api.rules;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.gurey.smock.api.entity.rule.ContainsRule;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ContainsRuleTest {

    private static String SHOULD_CONTAIN = "containsThis";
    
    private static ContainsRule rule;
    private Response res;
    private Request req;
    
    @BeforeClass
    public static void beforeClass() {
        HashMap<String, String> conds = new HashMap<>();
        conds.put(SHOULD_CONTAIN, null);
        rule = new ContainsRule(SHOULD_CONTAIN, conds, true);
    }
    
    @Before
    public void before() {
        res = mock(Response.class);
        req = mock(Request.class);
    }
    
    @Test
    public void testBodyContainsString(){
        when(req.body()).thenReturn("some random data" + SHOULD_CONTAIN + " ending some blabla");
        assertTrue(rule.applyRule(req, res));
    }
    
    @Test
    public void testBodyNotContainingCorrectData(){
        when(req.body()).thenReturn("nothing special");
        assertFalse(rule.applyRule(req, res));
    }
    
}
