package common;

import com.im.service.common.ServiceGroup;
import com.im.service.common.Config;

public class TestConfig {
	
	private static String CONF_DIR = System.getProperty("user.dir")+"/src/test/resources/config/";
	private static String TEST_DATA_DIR = System.getProperty("user.dir")+"/src/test/resources/testdata/";
	private static String REQUEST_BODY_DIR = System.getProperty("user.dir")+"/src/test/resources/postbody/";
	private static String TEST_HTML_REPORT_DIR = System.getProperty("user.dir")+"/target/htmlreport/";
	private static String CONTRACT_DIR = System.getProperty("user.dir")+"/src/test/resources/contract/";
	
	public static String COMMANDLINE_ENV = "WSTAF";

	public static void init () {
		
		/*
		 * Add the login credentials for Authentication token
		 */
		
		Config.CONF_FILE_LIST.put(ServiceGroup.CREDENTIAL, CONF_DIR+"credential.ini");

		
		/*
		 * ServiceName to the <ServiceGroup>.ini config file mapping
		 */
		Config.CONF_FILE_LIST.put(ServiceGroup.LOGIN, CONF_DIR+"login.ini");
		Config.CONF_FILE_LIST.put(ServiceGroup.USER, CONF_DIR+"user.ini");


		/*
		 *  ServiceName to Test Data directory mapping
		 */
		Config.TEST_DATA_DIR_LIST.put(ServiceGroup.LOGIN, TEST_DATA_DIR+"login/");
		Config.TEST_DATA_DIR_LIST.put(ServiceGroup.USER, TEST_DATA_DIR+"user/");
		
		/*
		 *  ServiceName to Request Body directory mapping (directory with json files)
		 */
		Config.REQUEST_BODY_DIR_LIST.put(ServiceGroup.LOGIN, REQUEST_BODY_DIR+"login/");
		Config.REQUEST_BODY_DIR_LIST.put(ServiceGroup.USER, REQUEST_BODY_DIR+"user/");

		
		/*
		 *  ServiceName to Contract Body directory mapping (directory with json files)
		 */
		
		Config.CONTRACT_DIR_LIST.put(ServiceGroup.SAMPLE, CONTRACT_DIR+"user/");
		
		
		/*
		 *  Initialize HTML Report
		 */
		
		Config.htmlReportInit(TestConfig.TEST_HTML_REPORT_DIR);
		
		
		/*
		 *  Enable / Diable Elastic Stack logging;
		 */
		
		//Config.ELASTIC_STACK_LOGGING = false;
	}
	
	public static void conclude() {
		Config.generateHtmlReport();
	}
	
	
}
