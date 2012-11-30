package org.smarthome.configuration.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.smarthome.shared.configuration.DBConfiguration;
import org.smarthome.shared.json.JsonProcessor;

@Path("/")
public class ConfiguartionService {
	
	private static final String DBCONFIG_RELPATH = "/WEB-INF/config/mysql.prop";
	
	private static final String DBXMLCONFIG_RELPATH = "/WEB-INF/config/dbconfig.xml";
	
	private final String dbXmlConfigPath;
	
	@Context
	ServletContext context;
	
	public ConfiguartionService(@Context ServletContext context) {
		dbXmlConfigPath = context.getRealPath(DBXMLCONFIG_RELPATH);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dbconfigold")
	public Response getDbConfigurationOld() {
		DBConfiguration dbConfig = null;
		Response response;
		try {
			URL dbConfigUrl = context.getResource(DBCONFIG_RELPATH);
			Properties dbProperties = new Properties();
			dbProperties.load(dbConfigUrl.openStream());
			dbConfig = new DBConfiguration(dbProperties);
			response = Response.ok(dbConfig).build();
		} catch (MalformedURLException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Invalid DB configuration URL.").build();
		} catch (IOException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to read DB configuration.").build();
		}
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dbconfig")
	public Response getDbConfiguration() {
		DBConfiguration dbConfig = null;
		Response response;
		try {
			Properties dbProperties = new Properties();
			dbProperties.loadFromXML(new FileInputStream(dbXmlConfigPath));

			dbConfig = new DBConfiguration(dbProperties);
			response = Response.ok(dbConfig).build();
		} catch (MalformedURLException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Invalid DB configuration URL.").build();
		} catch (IOException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to read DB configuration.").build();
		}
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dbconfig/update")
	public Response updateDbConfiguration(@FormParam("dbconfig") String dbConfigJson) {
		DBConfiguration dbConfig = null;
		Response response;
		try {
			dbConfig = JsonProcessor.getInstance().fromJson(dbConfigJson, DBConfiguration.class);

			FileOutputStream outputStream = new FileOutputStream(dbXmlConfigPath);
			dbConfig.getProperties().storeToXML(outputStream, "Database Configuration");
			
			response = Response.ok(dbConfig).build();
		} catch (MalformedURLException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Invalid DB configuration URL.").build();
		} catch (IOException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to store DB configuration.").build();
		}
		return response;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/datetimeformat")
	public Response getDateTimeFormat() {
		return Response.ok("yyyy-MM-dd hh:mm:ss").build();
	}
}
