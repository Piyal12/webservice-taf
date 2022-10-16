package com.im.service.util.listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.im.service.util.report.HtmlReport;

public class HtmlReportListener implements ITestListener  {

	private static ThreadLocal <String> api = new ThreadLocal <String> ();
	private static ThreadLocal <String> postBody = new ThreadLocal <String> ();
	private static ThreadLocal <String> response = new ThreadLocal <String> ();
	private static ThreadLocal <String> statusCode = new ThreadLocal <String> ();
	private static ThreadLocal <String> responseTime = new ThreadLocal <String> ();
	
	public static String getAPI() {
		return HtmlReportListener.api.get();
	}

	public static void setAPI(String api) {
		HtmlReportListener.api.set(api);
	}
	
	public static String getPostBody() {
		return HtmlReportListener.postBody.get();
	}

	public static void setPostBody(String postBody) {
		HtmlReportListener.postBody.set(postBody);
	}
	
	public static String getResponse() {
		return HtmlReportListener.response.get();
	}

	public static void setResponse(String response) {
		HtmlReportListener.response.set(response);
	}
	
	public static String getResponseTime() {
		return HtmlReportListener.responseTime.get();
	}

	public static void setResponseTime(long responseTime) {
		HtmlReportListener.responseTime.set(String.valueOf(responseTime));
	}
	
	public static String getStatusCode() {
		return HtmlReportListener.statusCode.get();
	}

	public static void setStatusCode(int statusCode) {
		HtmlReportListener.statusCode.set(String.valueOf(statusCode));
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		this.addToHTMLReport(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		this.addToHTMLReport(result);
	}
	
	private String getTestStatus (int status) {
		if (status == 1)
			return "Pass";
		if (status == 2)
			return "Skip";
		return "Fail";	
		
	}
	
	public void addToHTMLReport(ITestResult result) {
		
		HashMap <String, String> testOut = new HashMap <String, String> ();
		
		testOut.put("servicename", result.getMethod().getMethodName().split("_")[0]);
		testOut.put("testname", result.getMethod().getMethodName());
		testOut.put("teststatus", this.getTestStatus(result.getStatus()));
		testOut.put("api", "");
		if (HtmlReportListener.getAPI() != null)
	    	testOut.put("api", HtmlReportListener.getAPI());
		testOut.put("payload", "");
		if (HtmlReportListener.getPostBody() != null)
			testOut.put("payload", StringEscapeUtils.escapeJson(HtmlReportListener.getPostBody()));
		testOut.put("responsestatuscode", "");
		if (HtmlReportListener.getStatusCode() != null)
			testOut.put("responsestatuscode", HtmlReportListener.getStatusCode());
		testOut.put("responsetime", "");
		if (HtmlReportListener.getResponseTime() != null)
			testOut.put("responsetime", HtmlReportListener.getResponseTime());
		testOut.put("responsebody", "");
		if (HtmlReportListener.getResponse() != null)
	    	testOut.put("responsebody", StringEscapeUtils.escapeJson(HtmlReportListener.getResponse()));	
		
		try {
			JSONObject jsonObject = new JSONObject(testOut);
			Files.write(Paths.get(HtmlReport.TEST_OUT_FILE), (jsonObject.toString()+",").getBytes(), StandardOpenOption.APPEND);
	   } catch (IOException e) {
			e.printStackTrace();
		}
	}

}

