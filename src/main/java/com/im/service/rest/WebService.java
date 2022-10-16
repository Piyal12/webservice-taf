package com.im.service.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.im.service.common.Config;
import com.im.service.exception.FileException;
import com.im.service.exception.NullException;
import com.im.service.session.Session;
import com.im.service.util.excel.Excel;
import com.im.service.util.listener.HtmlReportListener;
import com.im.service.util.listener.TestListener;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;


public class WebService {
	
	private static final Logger LOG = LoggerFactory.getLogger(WebService.class);

	private ThreadLocal <Session> session = new ThreadLocal<Session>();
	private ThreadLocal <Integer> status = new ThreadLocal<Integer>();
	private ThreadLocal <Response> response = new ThreadLocal<Response>();	
	private ThreadLocal <HashMap <String,String>> testData = new ThreadLocal <HashMap <String,String>>();
	private ThreadLocal <HashMap <String,String>> addlTestData = new ThreadLocal <HashMap <String,String>>();
	private ThreadLocal <HashMap<String,Object>> headers = new ThreadLocal <HashMap<String,Object>>();
	private ThreadLocal <String> customRequestBody = new  ThreadLocal <String> ();
	private ThreadLocal <String> customToken = new  ThreadLocal <String> ();
	private ThreadLocal <Boolean> headerToken = new ThreadLocal<Boolean>();
	private ThreadLocal <Boolean> isRequestBody = new ThreadLocal<Boolean>();
	private ThreadLocal <Boolean> isOTP = new ThreadLocal<Boolean> ();
	private ThreadLocal <Boolean> isMultiPart = new ThreadLocal<Boolean> ();

	private ThreadLocal <HashMap<String,Object>> multipart = new ThreadLocal <HashMap<String,Object>>();
	private ThreadLocal <ArrayList <Object>> multipartFile = new ThreadLocal <ArrayList <Object>> ();
	private ThreadLocal <HashMap<String,Object>> formParam = new ThreadLocal <HashMap<String,Object>>();


	public WebService(Session session) {
		this.setSession(session);
		this.headerToken.set(true);
		this.setCustomRequestBody(null);
		this.setIsRequestBody(true);
		this.setOTP(false);
		this.setMultiPart(false);

	}
	
	public WebService(Session session, boolean HeaderToken) {
		this.setSession(session);
		this.headerToken.set(HeaderToken);
	}
	
	public Session getSession() {
		return session.get();
	}

	public void setSession(Session session) {
		this.session.set(session);
	}
	
	public int getStatus() {
		return status.get();
	}

	public void setStatus(int status) {
		this.status.set(status);
	}

	public Response getResponse() {
		return response.get();
	}

	public void setResponse(Response response) {
		this.response.set(response);
	}
	
	public HashMap<String, String> getTestData() {
		return testData.get();
	}

	public void setTestData(String testName) {
		this.testData.set(Excel.getTestData(this.getSession(), testName));
	}
	
	public void setTestData(HashMap <String, String> testData) {
		this.testData.set(testData);
	}
	
	public HashMap <String,String> getAddlTestData() {
		return addlTestData.get();
	}

	public void setAddlTestData(HashMap <String,String> addlTestData) {
		this.addlTestData.set(addlTestData);
	}
	
	public boolean getHeaderToken() {
		return headerToken.get();
	}

	public void setHeaderToken(boolean headerToken) {
		this.headerToken.set(headerToken);
	}
	
	public HashMap<String, Object> getAddlHeaders() {
		return headers.get();
	}

	public void setAddlHeaders(HashMap<String, Object> headers) {
		this.headers.set(headers);
	}
	
	public String getCustomRequestBody() {
		return customRequestBody.get();
	}

	public void setCustomRequestBody(String customRequestBody) {
		if (customRequestBody != null)
			customRequestBody = customRequestBody.replaceAll("\\s+", " ");
		this.customRequestBody.set(customRequestBody);
	}	
	
	public void setCustomRequestBodyFromFile (String name) {
		if (Config.getRequestBodyDir(this.getSession().getServiceGroup()) != null) {
			String fileName = Config.getRequestBodyDir(this.getSession().getServiceGroup())+name+Session.JSON_FILE_EXTN;
			try {
				File f = new File (fileName);
				if (f.exists()) {
					String content = new String(Files.readAllBytes(Paths.get(fileName)));
					this.customRequestBody.set(content);
				}
			} catch (IOException e) {
				throw new FileException ("Invalid File Name: "+ fileName);
			}
		}
	}

	public String getParameterized(String str, Object... param) {
        return String.format(str, param);
    }
	
	public void setCustomToken(String token) {
		this.customToken.set(token);
	}
	
	public String getCustomToken() {
		return this.customToken.get();
	}
	
	public String getAuthToken() {
		if (this.customToken.get() != null)
			return this.customToken.get();
		return this.getSession().getToken();
	}
	
	public boolean getIsRequestBody() {
		return isRequestBody.get();
	}

	public void setIsRequestBody(boolean flag) {
		this.isRequestBody.set(flag);
	}
	
	public boolean isOTP() {
		return isOTP.get();
	}

	public void setOTP (boolean flag) {
		this.isOTP.set(flag);
	}
	
	public boolean isMultiPart() {
		return isMultiPart.get();
	}

	public void setMultiPart(boolean flag) {
		this.isMultiPart.set(flag);
	}

	public HashMap <String, Object> getFormParam() {
		return this.formParam.get();
	}

	public void setFormParam(HashMap<String, Object> formdata) {
		this.formParam.set(formdata);
	}

	public HashMap <String, Object> getMultiPart() {
		return this.multipart.get();
	}

	public void setMultiPart(HashMap <String, Object> multipart) {
		this.multipart.set(multipart);
	}

	public ArrayList <Object> getMultiPartFile() {
		return multipartFile.get();
	}

	public void setMultiPart(ArrayList <Object> multipartFile) {
		this.multipartFile.set(multipartFile);
	}

	public String getParameterized(String str, ArrayList <?> alist) {
        return String.format(str, alist.toArray());
    }
	
	public String getParameterize(String template, HashMap<String, String> data) {
		
		if (data == null)
			return template; 
		
		if (template==null)
			throw new NullException("RequestBody is null");
		
		for (String key : data.keySet()) {
			Pattern pattern =  Pattern.compile("\\$"+key, Pattern.CASE_INSENSITIVE);
			if(pattern.matcher(template).find()) {
				
				String targetString = data.get(key);
				if (data.get(key) != null) {
					if (data.get(key).equalsIgnoreCase("[token]"))
						targetString = this.getAuthToken();
					if (data.get(key).equalsIgnoreCase("[null]")) 
						targetString = "null";
					if (data.get(key).equalsIgnoreCase("[blank]"))
						targetString = "";
					
					if (! data.get(key).equalsIgnoreCase("[null]")) 
						template = template.replaceAll("(?i)" + "\\$"+key, targetString );
					else {
						Pattern pattern1 =  Pattern.compile("\"\\$"+key+"\"", Pattern.CASE_INSENSITIVE);
						if(pattern1.matcher(template).find())
							template = template.replaceAll("(?i)" + "\"\\$"+key+"\"", targetString );
						else
							template = template.replaceAll("(?i)" + "\\$"+key, targetString );
					}
				}
			}
		}
		
		return template;
	}

	
	public RequestSpecification getAdditionalHeaders (RequestSpecification request) {
		 if (this.getAddlHeaders() != null)
	        	for (String key:this.getAddlHeaders().keySet())
	        		request.header(key, this.getAddlHeaders().get(key));
		 return request;
	}

	
	private RequestSpecification getMultiPartData(RequestSpecification request) {

		
		 if (this.getFormParam() != null)
	        	for (String key:this.getFormParam().keySet())
	        		request.formParam(key, this.getFormParam().get(key));
		
		 if (this.getMultiPart() != null)
	        	for (String key:this.getMultiPart().keySet())
	        		request.multiPart(key, this.getMultiPart().get(key));
		 
		 if (this.getMultiPartFile() != null)
	        	for (int i=0; i<this.getMultiPartFile().size(); i++)
	        		request.multiPart((MultiPartSpecification) this.getMultiPartFile().get(i));
		 
		return request;
	}

	
	public void post () {
		RestAssured.baseURI = this.getSession().getUrl();
        RequestSpecification request = RestAssured.given();

        request.contentType("application/json");
        request.header("cache-control","no-cache,no-cache");
        if (this.getHeaderToken())
        	request.header("Authorization",this.getAuthToken());
        
        request = this.getAdditionalHeaders(request);
        
        if(this.isMultiPart()) {
        	request = this.getMultiPartData(request);
        }
       
        String api = this.getParameterize(this.getSession().getAPI(),this.getTestData());
        api = this.getParameterize(api,this.getAddlTestData());
        LOG.debug("Service:"+RestAssured.baseURI+api);
       
        String requestBody = null;
        if (this.getIsRequestBody()) {
        	if (this.getCustomRequestBody() == null) { 
        		requestBody = this.getParameterize(this.getSession().getReqBody(), this.getTestData());
        		requestBody = this.getParameterize(requestBody, this.getAddlTestData());
             } else {
             	//requestBody = this.getCustomRequestBody();
            	 requestBody = this.getParameterize(this.getCustomRequestBody(), this.getTestData());
            	 requestBody = this.getParameterize(requestBody, this.getAddlTestData());
             }
             
             LOG.debug("Post Body:"+requestBody.replaceAll("\\s+", " "));
             
             request.body(requestBody);
        } else 
        	LOG.debug("Post Body:"+ null);
       
        Response response = request.post(api);
        this.setStatus(response.getStatusCode());
        this.setResponse(response);
        LOG.debug("Response:"+response.getBody().asString());
        this.toTestListener(api,requestBody);
	}
	
	public void put () {
		RestAssured.baseURI = this.getSession().getUrl();
        RequestSpecification request = RestAssured.given();

        request.contentType("application/json");
        request.header("cache-control","no-cache,no-cache");
        if (this.getHeaderToken())
        	request.header("Authorization",this.getAuthToken());
        
        request = this.getAdditionalHeaders(request);
       
        String api = this.getParameterize(this.getSession().getAPI(),this.getTestData());
        api = this.getParameterize(api,this.getAddlTestData());
        LOG.debug("Service:"+RestAssured.baseURI+api);
       
        String requestBody = null;
        if (this.getIsRequestBody()) {
        	if (this.getCustomRequestBody() == null) {
        		requestBody = this.getParameterize(this.getSession().getReqBody(), this.getTestData());
        		requestBody = this.getParameterize(requestBody, this.getAddlTestData());
             } else {
              	//RequestBody = this.getCustomRequestBody();
            	 requestBody = this.getParameterize(this.getCustomRequestBody(), this.getTestData());
            	 requestBody = this.getParameterize(requestBody, this.getAddlTestData());
              }
             
             LOG.debug("Post Body:"+requestBody.replaceAll("\\s+", " "));
             
             request.body(requestBody);
        } else 
        	LOG.debug("Post Body:"+ null);
        
        Response response = request.put(api);
        this.setStatus(response.getStatusCode());
        this.setResponse(response);
        LOG.debug("Response:"+response.getBody().asString());
        this.toTestListener(api,requestBody);
	}

	public void get () {
		RestAssured.baseURI = this.getSession().getUrl();
        RequestSpecification request = RestAssured.given();

        request.contentType("application/json");
        request.header("cache-control","no-cache,no-cache");
        if (this.getHeaderToken())
        	request.header("Authorization", this.getAuthToken());
        
        request = this.getAdditionalHeaders(request);
        
        String api = this.getParameterize(this.getSession().getAPI(),this.getTestData());
        LOG.debug("Service:"+RestAssured.baseURI+api);
 
        Response response = request.get(api);
        this.setStatus(response.getStatusCode());
        this.setResponse(response); 
        LOG.debug("Response:"+response.getBody().asString());
        this.toTestListener(api, null);
	}
	
	public void delete () {
		RestAssured.baseURI = this.getSession().getUrl();
        RequestSpecification request = RestAssured.given();

        request.contentType("application/json");
        request.header("cache-control","no-cache,no-cache");
        if (this.getHeaderToken())
        	request.header("Authorization", this.getAuthToken());
        
        request = this.getAdditionalHeaders(request);
        
        String api = this.getParameterize(this.getSession().getAPI(),this.getTestData());
        LOG.debug("Service:"+RestAssured.baseURI+api);
 
        Response response = request.delete(api);
        this.setStatus(response.getStatusCode());
        this.setResponse(response); 
        LOG.debug("Response:"+response.getBody().asString());
        this.toTestListener(api, null);
	}

	public void patch () {
		RestAssured.baseURI = this.getSession().getUrl();
        RequestSpecification request = RestAssured.given();

        request.contentType("application/json");
        request.header("cache-control","no-cache,no-cache");
        if (this.getHeaderToken())
        	request.header("Authorization", this.getAuthToken());
        
        request = this.getAdditionalHeaders(request);
        
        String api = this.getParameterize(this.getSession().getAPI(),this.getTestData());
        LOG.debug("Service:"+RestAssured.baseURI+api);
 
        Response response = request.get(api);
        this.setStatus(response.getStatusCode());
        this.setResponse(response); 
        LOG.debug("Response:"+response.getBody().asString());
        this.toTestListener(api, null);
	}
	
	private void toTestListener(String api, String requestBody) {
		TestListener.setAPI(api);
		if(requestBody != null)
			TestListener.setPostBody(requestBody.replaceAll("\\s+", " "));
		TestListener.setResponse(this.getResponse().getBody().asString());
		TestListener.setStatusCode(this.getResponse().getStatusCode());
		TestListener.setResponseTime(this.getResponse().getTimeIn(TimeUnit.MILLISECONDS));
		TestListener.setServiceGroup(this.getSession().getServiceGroup().name());
		TestListener.setServiceName(this.getSession().getServiceName());
		TestListener.setTestEnvironment(this.getSession().getEnvironment().name());
		HtmlReportListener.setAPI(api);
		if(requestBody != null)
			HtmlReportListener.setPostBody(requestBody.replaceAll("\\s+", " "));
		HtmlReportListener.setResponse(this.getResponse().getBody().asString());
		HtmlReportListener.setStatusCode(this.getResponse().getStatusCode());
		HtmlReportListener.setResponseTime(this.getResponse().getTimeIn(TimeUnit.MILLISECONDS));
	}

}
