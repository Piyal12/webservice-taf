package com.im.service.util.ws;

import java.util.HashMap;

import com.im.service.common.Environment;
import com.im.service.common.ServiceGroup;
import com.im.service.rest.WebService;
import com.im.service.session.Session;
import com.im.service.session.SessionProperties;


public class Ws {

	
	public static WebService get (ServiceGroup sGroup, String sName, Environment env) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.get();
		return rest;
	}
	
	public static WebService get (ServiceGroup sGroup, String sName, Environment env, String token) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.get();
		return rest;
	}
	
	public static WebService get (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.get();
		return rest;
	}
	
	public static WebService get (ServiceGroup sGroup, String sName, Environment env, String token, HashMap<String, String> data) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.get();
		return rest;
	}
	
	public static WebService get (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String token, boolean authFlag) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setHeaderToken(authFlag);
		rest.get();
		return rest;
	}
	
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, String custReqBody, int custReqBodyMode ) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, boolean authFlag) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setHeaderToken(authFlag);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, String token, String custReqBody, int custReqBodyMode ) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String token) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, boolean authFlag, String custReqBody) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setCustomRequestBody(custReqBody);
		rest.setHeaderToken(authFlag);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, boolean authFlag, String custReqBody, int custReqBodyMode) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);
		rest.setHeaderToken(authFlag);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String token, boolean authFlag) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setHeaderToken(authFlag);
		rest.post();
		return rest;
	}

	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String token, String custReqBody) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setCustomRequestBody(custReqBody);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String token, String custReqBody, int custReqBodyMode) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String token, boolean authFlag, String custReqBody) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setCustomRequestBody(custReqBody);
		rest.setHeaderToken(authFlag);
		rest.post();
		return rest;
	}
	
	public static WebService post (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String token, boolean authFlag, String custReqBody, int custReqBodyMode) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);
		rest.setHeaderToken(authFlag);
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);
		rest.post();
		return rest;
	}
	
	public static WebService delete (ServiceGroup sGroup, String sName, Environment env) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.delete();
		return rest;
	}
	
	public static WebService delete (ServiceGroup sGroup, String sName, Environment env, String token) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.delete();
		return rest;
	}
	
	public static WebService delete (ServiceGroup sGroup, String sName, Environment env, String token, HashMap<String, String> data) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.delete();
		return rest;
	}
	
	public static WebService delete (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.delete();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String token) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String token, HashMap<String, String> data) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String custReqBody, int custReqBodyMode ) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String token, String custReqBody, int custReqBodyMode ) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String token, HashMap<String, String> data, String custReqBody) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setCustomRequestBody(custReqBody);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String token, HashMap<String, String> data, String custReqBody, int custReqBodyMode) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);		
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String token, HashMap<String, String> data, boolean authFlag, String custReqBody) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setCustomRequestBody(custReqBody);
		rest.setHeaderToken(authFlag);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, String token, HashMap<String, String> data, boolean authFlag, String custReqBody, int custReqBodyMode) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).token(token).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setHeaderToken(authFlag);
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);		
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String custReqBody) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setCustomRequestBody(custReqBody);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, String custReqBody, int custReqBodyMode) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);		
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, boolean authFlag, String custReqBody) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setCustomRequestBody(custReqBody);
		rest.setHeaderToken(authFlag);
		rest.put();
		return rest;
	}
	
	public static WebService put (ServiceGroup sGroup, String sName, Environment env, HashMap<String, String> data, boolean authFlag, String custReqBody, int custReqBodyMode) {
		Session session = new Session(SessionProperties.newProperties(sGroup).environment(env).serviceName(sName).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setHeaderToken(authFlag);
		if (custReqBodyMode == 1)
			rest.setCustomRequestBody(custReqBody);
		if (custReqBodyMode == 2)
			rest.setCustomRequestBodyFromFile(custReqBody);	
		rest.put();
		return rest;
	}

}
