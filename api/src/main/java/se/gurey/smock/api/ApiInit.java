package se.gurey.smock.api;

import static spark.Spark.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import se.gurey.smock.api.serviceroutes.ServiceRoutes;

public class ApiInit {
	
	private static Logger log = LoggerFactory.getLogger(ApiInit.class);

	public static void init(){
		before("/", (req, res) -> log.info(req.requestMethod() + " " + req.uri()));
		Injector injector = Guice.createInjector();
		injector.getInstance(ServiceRoutes.class).init();
		
	}
	
}
