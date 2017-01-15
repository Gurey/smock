package se.gurey.smock.api.path;

public class PathMatcher {

    public boolean pathMatches(String serviceUri, String otherUri) {
        return getMatchedPath(serviceUri, otherUri) != null;
    }
    
    public String cleanUri(String uri) {
        if(uri.contains("?")) {
            uri = uri.substring(0, uri.indexOf('?'));
        }
        if(uri.startsWith("/") && uri.length() > 0) {
            uri = uri.substring(1, uri.length());
        }
        if(uri.endsWith("/") && uri.length() > 0){
            uri = uri.substring(0, uri.length() - 1);
        }
        
        return uri.toLowerCase();
    }
    
    public String getMatchedPath(String serviceUri, String otherUri){
        serviceUri = cleanUri(serviceUri);
        otherUri = cleanUri(otherUri);
        
        String[] uri1Split = serviceUri.split("/");
        String[] otherSplit = otherUri.split("/");
        
        if(uri1Split.length != otherSplit.length){
            return null;
        }
        
        String res = "";
        for (int i = 0; i < uri1Split.length; i++) {
            if(uri1Split[i].startsWith(":")){ // if a path contains a /some/:path
                res += uri1Split[i] + "/";
            } else if (!uri1Split[i].equals(otherSplit[i])){
                return null;
            } else {
                res += uri1Split[i] + "/";
            }
        }
        
        return cleanUri(res);
    }
    
}
