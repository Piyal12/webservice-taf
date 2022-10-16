package com.im.service.util.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.im.service.util.other.Strings;

public class ExtentReport {
	
	private static ExtentReports report;
	
	private static String tester = "Automation Tester";
	private static String environment = "-";
	private static String docTitle = "WebServices Test Results";
	private static String reportName = "WebServices Test Automation";
	private static String reportPath = null;
	private static String reportFileName = null;
	private static boolean suffixTimeStamp = true;
	private static final String defREPORT_FILE_NAME = "extentreport";
	private static final String defREPORT_PATH = System.getProperty("user.dir")+"/target/extentreport/";	
	private static final String defREPORT_SUFFIX = ".html";

	public static ExtentReports getExtentReport () {
		
		if (ExtentReport.getReportPath() == null) 
			ExtentReport.setReportPath(ExtentReport.defREPORT_PATH);
		
		if (ExtentReport.getReportFileName() == null)
			ExtentReport.setReportFileName(ExtentReport.defREPORT_FILE_NAME);
		
		ExtentReport.setReportPath(ExtentReport.getReportPath()+ExtentReport.getReportFileName());
		
		if (ExtentReport.getSuffixTimeStamp())
			ExtentReport.setReportPath(ExtentReport.getReportPath()+"_"+Strings.getDateString());
		
		ExtentReport.setReportPath(ExtentReport.getReportPath()+ ExtentReport.defREPORT_SUFFIX);
			
		//System.getProperty("user.dir")+"/target/extentreport/extentreport_"+Strings.getDateString()+".html";
		
		ExtentSparkReporter sr = new ExtentSparkReporter(ExtentReport.getReportPath());
		sr.config().setDocumentTitle(ExtentReport.getDocTitle());
		sr.config().setReportName(ExtentReport.getReportName());
		sr.config().setTheme(Theme.STANDARD);
		
		report = new ExtentReports ();
		report.attachReporter(sr);
		report.setSystemInfo("Tester", ExtentReport.getTester());
		report.setSystemInfo("Environment", ExtentReport.getEnvironment());
		
		return report;
	}

	public static void setTester (String val) {
		ExtentReport.tester = val;
	}
	
	public static String getTester () {
		return ExtentReport.tester;
	}
	
	public static void setEnvironment (String val) {
		ExtentReport.environment = val;
	}
	
	public static String getEnvironment () {
		return ExtentReport.environment;
	}
	
	public static void setReportPath (String val) {
		ExtentReport.reportPath = val;
	}
	
	private static String getReportPath () {
		return ExtentReport.reportPath;
	}
	
	public static void setReportFileName (String val) {
		ExtentReport.reportFileName = val;
	}
	
	private static String getReportFileName () {
		return ExtentReport.reportFileName;
	}
	
	public static void setSuffixTimeStamp (boolean val) {
		ExtentReport.suffixTimeStamp = val;
	}
	
	private static boolean getSuffixTimeStamp () {
		return ExtentReport.suffixTimeStamp;
	}
	
	public static void setReportName (String val) {
		ExtentReport.reportName = val;
	}
	
	private static String getReportName () {
		return ExtentReport.reportName;
	}
	
	public static void setDocTitle (String val) {
		ExtentReport.docTitle = val;
	}
	
	private static String getDocTitle () {
		return ExtentReport.docTitle;
	}
	
}
