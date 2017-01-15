package se.gurey.smock.api.entity;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

import se.gurey.smock.api.entity.rule.EndpointRule;

public class WebserviceEndpoint {

    private String path;
    private String id;
    private List<EndpointRule> rules;
    private boolean isDefault;
    private String responseType;

    public WebserviceEndpoint(String path, boolean isDefault, String reponseType) {
        this.path = path;
        this.isDefault = isDefault;
        this.responseType = reponseType;
        this.rules = new ArrayList<>();
        this.id = Hashing.md5().hashString(path, Charset.forName("UTF-8")).toString().substring(0, 10);
    }

    public WebserviceEndpoint(String path) {
        this(path, false, "application/json");
    }

    public WebserviceEndpoint() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getType() {
        return responseType;
    }

    public void setType(String type) {
        this.responseType = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EndpointRule> getRules() {
        return rules;
    }

    public void setRules(List<EndpointRule> rules) {
        this.rules = rules;
    }

}
