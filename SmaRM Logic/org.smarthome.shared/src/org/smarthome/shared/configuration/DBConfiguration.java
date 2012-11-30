package org.smarthome.shared.configuration;

import java.io.Serializable;
import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class DBConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private static final String PROPERTY_URL = "db.url";
	@XmlTransient
	private static final String PROPERTY_USER = "db.user";
	@XmlTransient
	private static final String PROPERTY_PASSWD = "db.passwd";
	
	private String url;
	private String user;
	private String passwd;
	
	public DBConfiguration() {
	}
	
	public DBConfiguration(Properties dbProperties) {
		url = dbProperties.getProperty(PROPERTY_URL);
		user = dbProperties.getProperty(PROPERTY_USER);
		passwd = dbProperties.getProperty(PROPERTY_PASSWD);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_URL, url);
		properties.setProperty(PROPERTY_USER, user);
		properties.setProperty(PROPERTY_PASSWD, passwd);
		return properties;
	}
	
	@Override
	public String toString() {
		return "URL: " + url + "\nUser: " + user;
	}
}
