package com.im.service.session;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.im.service.common.ServiceGroup;
import com.im.service.common.Config;
import com.im.service.common.Environment;
import com.im.service.exception.ConfigFileException;
import com.im.service.exception.FileException;

public class Session {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(Session.class);

	private ThreadLocal <ServiceGroup> serviceGroup = new ThreadLocal<ServiceGroup>();
	private ThreadLocal <Environment> environment = new ThreadLocal<Environment>();
	private ThreadLocal <String> serviceName = new ThreadLocal<String>();
	private ThreadLocal <String> token = new ThreadLocal<String>();
	private ThreadLocal <String> url = new ThreadLocal<String>();
	private ThreadLocal <String> apiToCall = new ThreadLocal<String>();
	private ThreadLocal <String> reqBody = new ThreadLocal<String>();
	private ThreadLocal <String> query = new ThreadLocal<String>();
	private ThreadLocal <String> dbServer = new ThreadLocal<String>();
	private ThreadLocal <String> dbPort = new ThreadLocal<String>();
	private ThreadLocal <String> dbInstance = new ThreadLocal<String>();
	private ThreadLocal <String> dbName = new ThreadLocal<String>();
	private ThreadLocal <String> dbUserName = new ThreadLocal<String>();
	private ThreadLocal <String> dbPassword = new ThreadLocal<String>();
	
	private static final String SEC_APP ="app";
	private static final String SEC_API ="api";
	private static final String SEC_REQ_BODY ="reqbody";
	private static final String SEC_SQL ="sql";
	
	private static final String EVN_TOKEN = "token";
	private static final String ENV_URL = "url";
	private static final String ENV_DB_SERVER = "dbServer";
	private static final String ENV_DB_PORT = "dbPort";
	private static final String ENV_DB_INSTANCE = "dbInstance";
	private static final String ENV_DB_NAME = "dbName";
	private static final String ENV_DB_USER_NAME = "dbUserName";
	private static final String ENV_DB_PASSWORD = "dbPassword";
	
	public static final String JSON_FILE_EXTN = ".json";

	
	private Ini ini;
	private Section app;
	private Section env;
	private Section api;
	private Section rBody;
	private Section sql;
	

	public Session (SessionProperties prop) {
		this.serviceGroup.set(prop.getServiceGroup());
		this.environment.set(prop.getEnvironment());
		this.serviceName.set(prop.getServiceName());
		this.token.set(prop.getToken());

		try {
			ini = new Ini();
			ini.load(new FileReader(Config.getConfig(this.getServiceGroup())));
		} catch (Exception e) {
			throw new ConfigFileException (e.getMessage());
		} 
		
		app = ini.get(Session.SEC_APP);
		env = app.getChild(this.getEnvironment().toString().toLowerCase());
		api = ini.get(Session.SEC_API);
		sql = ini.get(Session.SEC_SQL);
		rBody = ini.get(Session.SEC_REQ_BODY);
	
		dbServer.set(env.get(Session.ENV_DB_SERVER));
		dbPort.set(env.get(Session.ENV_DB_PORT));
		dbInstance.set(env.get(Session.ENV_DB_INSTANCE));
		dbName.set(env.get(Session.ENV_DB_NAME));
		dbUserName.set(env.get(Session.ENV_DB_USER_NAME));
		dbPassword.set(env.get(Session.ENV_DB_PASSWORD));
		
		url.set(env.get(Session.ENV_URL));
		
		try {
			apiToCall.set(api.get(this.getServiceName()));
		} catch (Exception e) {
			throw new ConfigFileException ("Section: api not found in the "+Config.getConfig(this.getServiceGroup())+ " file");
		}
		
		try {
			reqBody.set(rBody.get(this.getServiceName()));
		} catch (Exception e) {
			throw new ConfigFileException ("Section: reqBody not found in the "+Config.getConfig(this.getServiceGroup())+" file");
		} 
		
		if (Config.getRequestBodyDir(this.getServiceGroup()) != null) {
			String fileName = Config.getRequestBodyDir(this.getServiceGroup())+this.getServiceName()+Session.JSON_FILE_EXTN;
			try {
				File f = new File (fileName);
				if (f.exists()) {
					String content = new String(Files.readAllBytes(Paths.get(fileName)));
					//if (content != null)
					//	content = content.replaceAll("\\s+", " ");
					reqBody.set(content);
				}
			} catch (IOException e) {
				throw new FileException ("Invalid File Name: "+ fileName);
			}
		}
		
		
		try {
			query.set(sql.get(this.getServiceName()));
		} catch (Exception e) {
			throw new ConfigFileException ("Section: sql not found in the "+Config.getConfig(this.getServiceGroup())+ " file");
		}
		
		//if(env.get(Session.EVN_TOKEN) != null)
		//	if(env.get(Session.EVN_TOKEN).trim().length() > 0)
		//		token.set(env.get(Session.EVN_TOKEN));
		
		if (token.get() == null)
			if(env.get(Session.EVN_TOKEN) != null)
				if(env.get(Session.EVN_TOKEN).trim().length() > 0)
					token.set(env.get(Session.EVN_TOKEN));
		
	}
	
	public ServiceGroup getServiceGroup() {
		return serviceGroup.get();
	}
	
	public Environment getEnvironment() {
		return environment.get();
	}

	public String getServiceName() {
		return serviceName.get();
	}

	public String getToken() {
		return token.get();
	}

	public String getUrl() {
		return url.get();
	}

	public String getAPI() {
		return apiToCall.get();
	}
	
	public String getReqBody() {
		return reqBody.get();
	}

	public String getQuery() {
		return query.get();
	}

	public String getDbServer() {
		return dbServer.get();
	}

	public String getDbPort() {
		return dbPort.get();
	}

	public String getDbInstance() {
		return dbInstance.get();
	}

	public String getDbName() {
		return dbName.get();
	}

	public String getDbUserName() {
		return dbUserName.get();
	}

	public String getDbPassword() {
		return dbPassword.get();
	}	
	
	public String getSchemaFile() {
		return Config.getContractDir(this.getServiceGroup())+this.getServiceName()+Session.JSON_FILE_EXTN;
	}
	
}
