package com.im.service.common;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.im.service.exception.NullException;
import com.im.service.util.report.HtmlReport;

public class Config {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(Config.class);
   
	public static HashMap<ServiceGroup, String> CONF_FILE_LIST = new HashMap <ServiceGroup, String> ();
	public static HashMap<ServiceGroup, String> TEST_DATA_DIR_LIST = new HashMap <ServiceGroup, String> ();
	public static HashMap<String, ServiceGroup> TEST_TO_BUSINESS_MAP = new HashMap <String, ServiceGroup> ();
	public static HashMap<ServiceGroup, String> REQUEST_BODY_DIR_LIST = new HashMap <ServiceGroup, String> ();
	public static HashMap<ServiceGroup, String> CONTRACT_DIR_LIST = new HashMap <ServiceGroup, String> ();
	
	public static String TEST_HTML_REPORT_DIR;
	
	public static boolean ELASTIC_STACK_LOGGING = false;
	
	public static String getConfig (ServiceGroup serviceGroup) {
    	try {
    		if (! CONF_FILE_LIST.containsKey(serviceGroup))
    			throw new NullException("Invalid application :"+serviceGroup);
    	} catch (Exception e) {
    		throw new NullException(e.getMessage());
    	}
    	
    	return CONF_FILE_LIST.get(serviceGroup);
    }
    
	public static String getTestDataDir(ServiceGroup serviceGroup) {
		try {
			if (! Config.TEST_DATA_DIR_LIST.containsKey(serviceGroup))
				throw new NullException("Invalid application :" + serviceGroup);
		} catch (Exception e) {
			throw new NullException(e.getMessage());
		}

		return Config.TEST_DATA_DIR_LIST.get(serviceGroup);
	}
	
	public static String getRequestBodyDir(ServiceGroup serviceGroup) {
		return Config.REQUEST_BODY_DIR_LIST.get(serviceGroup);
	}
	
	public static String getContractDir(ServiceGroup serviceGroup) {
		return Config.CONTRACT_DIR_LIST.get(serviceGroup);
	}
	
	/*
	public static Application getTestBusinessMap(String testName) {
		try {
			if (! Config.TEST_TO_BUSINESS_MAP.containsKey(testName))
				throw new NullException("Invalid test name :" + testName);
		} catch (Exception e) {
			throw new NullException(e.getMessage());
		}

		return Config.TEST_TO_BUSINESS_MAP.get(testName);
	}
	*/
	public static void htmlReportInit(String reportDir) {
		Config.TEST_HTML_REPORT_DIR = reportDir;
		HtmlReport.init();	
	}

	public static void generateHtmlReport() {
		HtmlReport.createHtmlReport();		
	}
}