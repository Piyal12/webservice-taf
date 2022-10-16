package com.im.service.util.elasticstack;

public class Entry {
	
	private String timeStamp;
	private String testType;
	private String testEnvironment;
	private String serviceGroup;
	private String serviceName;
	private String testName;
	private String testMethod;
	private String testStatus;
	private String testData;
	private String api;
	private String apiType;
	private String requestBody;
	private String response;
	private String statusCode;
	private String responseTime;
	private String errorMessage;

	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public String getTestEnvironment() {
		return testEnvironment;
	}
	public void setTestEnvironment(String testEnvironment) {
		this.testEnvironment = testEnvironment;
	}
	public String getServiceGroup() {
		return serviceGroup;
	}
	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestMethod() {
		return testMethod;
	}
	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getApiType() {
		return apiType;
	}
	public void setApiType(String apiType) {
		this.apiType = apiType;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	public String getTestData() {
		return testData;
	}
	public void setTestData(String testData) {
		this.testData = testData;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	@Override
	public String toString() {
		return "Entry [timeStamp=" + timeStamp + ", testType=" + testType + ", testEnvironment=" + testEnvironment
				+ ", serviceGroup=" + serviceGroup + ", serviceName=" + serviceName + ", testName=" + testName
				+ ", testMethod=" + testMethod + ", testStatus=" + testStatus + ", testData=" + testData + ", api="
				+ api + ", apiType=" + apiType + ", requestBody=" + requestBody + ", response=" + response
				+ ", statusCode=" + statusCode + ", responseTime=" + responseTime + "]";
	}


	
}
