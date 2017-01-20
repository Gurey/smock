package se.gurey.smock.api.rules;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.gurey.smock.api.entity.rule.RuleType;
import se.gurey.smock.api.entity.rule.XmlRule;
import spark.Request;
import spark.Response;

public class XmlRuleTest {
    
    private static final String XML = readTestFile();
    
    private Request req;
    private Response res;
    
    @Before
    public void before() {
        req = mock(Request.class);
        res = mock(Response.class);
        when(req.body()).thenReturn(XML);
    }

    
    @Test
    public void getElement(){
        XmlRule rule = new XmlRule("Working!", false, RuleType.XML);
        rule.getPaths().put("//title", "Guava");
        assertTrue(rule.applyRule(req, res));
    }
    
    @Test
    public void getElementThatDontExist(){
        XmlRule rule = new XmlRule("Working!", false, RuleType.XML);
        rule.getPaths().put("//thisdonotexist", "will never get here");
        assertFalse(rule.applyRule(req, res));
    }
    
    private static String readTestFile() {
        final StringBuilder sb = new StringBuilder();
        try {
            Files.readAllLines(Paths.get("src/test/resources/test.xml"))
                .stream()
                .forEach(line -> sb.append(line).append("\n"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
    
}
