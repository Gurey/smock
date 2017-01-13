package se.gurey.smock.api.db;

import java.util.HashMap;

import javax.inject.Singleton;

import se.gurey.smock.api.entity.WebserviceEndpoint;

@Singleton
public class RouteMap extends HashMap<String, WebserviceEndpoint> {

	private static final long serialVersionUID = 1783124408287066296L;
	
	public RouteMap() {}

}
