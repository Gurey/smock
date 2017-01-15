package se.gurey.smock.api;

import se.gurey.smock.api.path.PathMatcher;
import static org.junit.Assert.*;

import org.junit.Test;

public class PathMatcherTest {
    
    private PathMatcher pm = new PathMatcher();
    
    
    @Test
    public void testHappyDayTest() {
        assertTrue(pm.pathMatches("some/path/to", "/some/path/to"));
    }
    
    @Test
    public void testEndingWithSlash() {
        assertTrue(pm.pathMatches("some/path/to", "/some/path/to/"));
    }
    
    @Test
    public void testCleanUri() {
        assertEquals("some/path", pm.cleanUri("/some/path/"));
    }
    
    @Test
    public void testSparkPath() {
        assertTrue(pm.pathMatches("/api/company/:id", "/api/company/123"));
    }
    
    @Test
    public void testSparkPathWithLongParam() {
        assertTrue(pm.pathMatches("/api/company/:id", "/api/company/123456789123"));
    }
    
    @Test
    public void testNonMatchingLongerPath() {
        assertFalse(pm.pathMatches("/api/company/:id", "/api/company/123123/blabla"));
    }
    
    @Test
    public void testPathAfterParam() {
        assertTrue(pm.pathMatches("/api/company/:id/bla", "/api/company/123123/bla"));
    }
    
    @Test
    public void testNonMathcing() {
        assertFalse(pm.pathMatches("/fail/this/:id", "/api/company/123123/blabla"));
    }
    
    @Test
    public void testSparkUrlLongerThanIncommingUri() {
        assertFalse(pm.pathMatches("/api/path/:id", "/api/path/"));
    }
    
    @Test
    public void testUriWithQueryParam() {
        assertTrue(pm.pathMatches("/path/to/service", "path/to/service?query=true"));
    }
    
    @Test
    public void testShortUri() {
        assertTrue(pm.pathMatches("/", "/"));
    }
    
    @Test
    public void getParsedUri(){
        assertEquals("path/to/:my/:heart", pm.getMatchedPath("path/to/:my/:heart", "/path/to/12/13"));
    }
    
    @Test
    public void testMatchesWithCapitalLetters(){
        assertTrue(pm.pathMatches("/PATH/MATCH", "path/match"));
    }
    
}
