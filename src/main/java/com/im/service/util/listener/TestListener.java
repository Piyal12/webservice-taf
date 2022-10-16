package com.im.service.util.listener;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.im.service.util.elasticstack.Entry;
import com.im.service.util.elasticstack.ElasticStackLog;
import com.im.service.util.report.ExtentReport;

public class TestListener implements ITestListener {

	private ExtentReports extentReport;
	private ThreadLocal<ExtentTest> methodTest = new ThreadLocal<ExtentTest>();
    private ThreadLocal<ExtentTest> dataProviderTest = new ThreadLocal<>();

    
    private static ThreadLocal <String> api = new ThreadLocal <String> ();
	private static ThreadLocal <String> postBody = new ThreadLocal <String> ();
	private static ThreadLocal <String> response = new ThreadLocal <String> ();
	private static ThreadLocal <String> statusCode = new ThreadLocal <String> ();
	private static ThreadLocal <String> responseTime = new ThreadLocal <String> ();
	private static ThreadLocal <String> serviceGroup = new ThreadLocal <String> ();
	private static ThreadLocal <String> serviceName = new ThreadLocal <String> ();
	private static ThreadLocal <String> testEnvironment = new ThreadLocal <String> ();

	
	public static String getTestEnvironment() {
		return testEnvironment.get();
	}

	public static void setTestEnvironment(String testEnvironment) {
		TestListener.testEnvironment.set(testEnvironment);
	}
	
	public static String getServiceGroup() {
		return serviceGroup.get();
	}

	public static void setServiceGroup(String serviceGroup) {
		TestListener.serviceGroup.set(serviceGroup);
	}

	public static String getServiceName() {
		return serviceName.get();
	}

	public static void setServiceName(String serviceName) {
		TestListener.serviceName.set(serviceName);
	}
	
	public static String getAPI() {
		return TestListener.api.get();
	}

	public static void setAPI(String api) {
		TestListener.api.set(api);
	}
	
	public static String getPostBody() {
		return TestListener.postBody.get();
	}

	public static void setPostBody(String postBody) {
		TestListener.postBody.set(postBody);
	}
	
	public static String getResponse() {
		return TestListener.response.get();
	}

	public static void setResponse(String response) {
		TestListener.response.set(response);
	}
	
	public static String getResponseTime() {
		return TestListener.responseTime.get();
	}

	public static void setResponseTime(long responseTime) {
		TestListener.responseTime.set(String.valueOf(responseTime));
	}
	
	public static String getStatusCode() {
		return TestListener.statusCode.get();
	}

	public static void setStatusCode(int statusCode) {
		TestListener.statusCode.set(String.valueOf(statusCode));
	}
    
    
	
	public void onTestStart(ITestResult result) {
		//this.extentTest.set(extentReport.createTest(result.getMethod().getMethodName()));
		//System.out.println("TestStart");
		
		//String methodName = result.getMethod().getMethodName();
		//String methodDesc = result.getMethod().getDescription();
		
		String name = result.getMethod().getDescription() == null || result.getMethod().getDescription().isEmpty() ? result.getMethod().getMethodName() : result.getMethod().getDescription();
					

        if (result.getParameters().length>0) {
            if (this.methodTest.get() != null && this.methodTest.get().getModel().getName().equals(name)) { } 
            else {
                this.createTest(result);
            }
            String paramName = Arrays.asList(result.getParameters()).toString();
            ExtentTest paramTest = this.methodTest.get().createNode(paramName);
            this.dataProviderTest.set(paramTest);
        } else {
        	this.createTest(result);
        }
	}

	public void onTestSuccess(ITestResult result) {
		//this.extentTest.get().log(Status.PASS, "Test Pass");
		//System.out.println("TestSuccess");
		// {"Response", TestListener.getResponse() == null ? "-" : TestListener.getResponse()}
		this.getTest(result).log(Status.PASS, "Test Pass");
	    String[][] data = {{"API", TestListener.getAPI() == null ? "-" : TestListener.getAPI()}, 
	    		{"PostBody", TestListener.getPostBody() == null ? "-" : TestListener.getPostBody()},
	    		{"Status", TestListener.getStatusCode() == null ? "-" : TestListener.getStatusCode()},
	    		{"Time", TestListener.getResponseTime() == null ? "-" : TestListener.getResponseTime()}};
		this.getTest(result).info(MarkupHelper.createTable(data, "table-sm"));	
		
		this.elasticStack(result, Status.PASS.getName(), "api");

	}

	public void onTestFailure(ITestResult result) {
		//System.out.println("TestFailure");		
		this.getTest(result).fail(result.getThrowable());

	    String[][] data = {{"API", TestListener.getAPI() == null ? "-" : TestListener.getAPI()}, 
	    		{"PostBody", TestListener.getPostBody() == null ? "-" : TestListener.getPostBody()},
	    		{"Status", TestListener.getStatusCode() == null ? "-" : TestListener.getStatusCode()},
	    		{"Time", TestListener.getResponseTime() == null ? "-" : TestListener.getResponseTime()},
	    		{"Response", TestListener.getResponse() == null ? "-" : TestListener.getResponse()}};
		this.getTest(result).info(MarkupHelper.createTable(data, "table-sm"));
		
		this.elasticStack(result, Status.FAIL.getName(), "api");
	}

	public void onTestSkipped(ITestResult result) {
		//System.out.println("TestSkipped");
		this.getTest(result).skip(result.getThrowable());
		
		this.elasticStack(result, Status.SKIP.getName(), "api");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onTestFailedWithTimeout(ITestResult result) {

	}
	
	public void onStart(ITestContext context) {
		//System.out.println("OnStart");
		extentReport = ExtentReport.getExtentReport();
	}
	
	public void onFinish(ITestContext context) {
		//System.out.println("OnFinish");
		this.extentReport.flush();
	}
	
	
	private void createTest(ITestResult result) {
        //String methodName = result.getMethod().getMethodName();
        //String methodDesc = result.getMethod().getDescription();

		String name = result.getMethod().getDescription() == null || result.getMethod().getDescription().isEmpty() ? result.getMethod().getMethodName() : result.getMethod().getDescription();

        ExtentTest test = this.extentReport.createTest(name);
        this.methodTest.set(test);

        String[] groups = result.getMethod().getGroups();
        if (groups.length > 0) {
            Arrays.asList(groups).forEach(x -> this.methodTest.get().assignCategory(x));
        }
    }
	
	private ExtentTest getTest(ITestResult result) {
        ExtentTest extentTest = result.getParameters() != null && result.getParameters().length > 0 ? this.dataProviderTest.get() : this.methodTest.get();
        return extentTest;
    }
	
	private void elasticStack (ITestResult result, String testStatus, String testType) {

		Entry elk = new Entry ();
		elk.setTimeStamp(LocalDateTime.now().toString());
		elk.setTestType(testType);
		elk.setApi(TestListener.getAPI());
		elk.setRequestBody(TestListener.getPostBody());
		elk.setServiceGroup(TestListener.getServiceGroup());
		elk.setServiceName(TestListener.getServiceName());
		elk.setTestName(result.getMethod().getDescription());
		elk.setTestMethod(result.getMethod().getMethodName());
		elk.setTestStatus(testStatus);
		elk.setResponse(TestListener.getResponse());
		elk.setStatusCode(TestListener.getStatusCode());
		elk.setResponseTime(TestListener.getResponseTime());
		elk.setTestEnvironment(TestListener.getTestEnvironment());
		if (testStatus.equalsIgnoreCase(Status.FAIL.name()))
			elk.setErrorMessage(result.getThrowable().toString());
		ElasticStackLog.perform(elk);
	}
	
}