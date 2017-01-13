package se.gurey.smock.api.entity;

import org.omg.CORBA.portable.ApplicationException;

public class WebserviceEndpoint {
	
	private String path;
	private String request;
	private String response;
	private boolean isDefault;
	private String type;
	
	public WebserviceEndpoint(String path, String req, String res, boolean isDefault, String type) {
		this.path = path;
		this.request = req;
		this.response = res;
		this.isDefault = isDefault;
		this.setType(type);
	}
	
	public WebserviceEndpoint(String path, String req, String res) {
		this(path, req, res, false, "application/json");
	}
	
	public WebserviceEndpoint() {}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
