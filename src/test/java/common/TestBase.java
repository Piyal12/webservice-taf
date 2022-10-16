package common;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.im.service.common.Config;
import com.im.service.common.Constant;
import com.im.service.common.Environment;
import com.im.service.util.report.ExtentReport;

@SuppressWarnings("unused")
public class TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(TestBase.class);
	public static Environment ENV = Environment.QA;
	public static String TOKEN = null;

	
	@BeforeSuite(alwaysRun=true)
	@Parameters({"environment"})
	public void onBeforeSuite (@Optional String env, ITestContext testContext) {
		
		if (env != null)
			ENV = this.getEnviroment(env);
		
		String cmd_env = System.getProperty(TestConfig.COMMANDLINE_ENV);
		
		if (cmd_env != null)
			ENV = this.getEnviroment(cmd_env);

		LOG.info("Test Environment set to: " + ENV);
		
		testContext.setAttribute(Constant.ENVIRONMENT, ENV.toString());
		
		ExtentReport.setTester("Piyal Bhunia");
		ExtentReport.setEnvironment(ENV.toString());
		ExtentReport.setSuffixTimeStamp(false);

		
		TestConfig.init();
		
		if(TestBase.TOKEN == null)
			TestAuth.getCredentials();
	}
	
	@AfterSuite (alwaysRun=true)
	public void onAfterSuite () {
		TestConfig.conclude();
	}
	
	public Environment getEnviroment (String env) {
		if (env != null) {
			if (env.equalsIgnoreCase(Constant.EVN_PROD))
				return Environment.PROD;
			if (env.equalsIgnoreCase(Constant.EVN_STAGE))
				return Environment.STAGE;
			if (env.equalsIgnoreCase(Constant.EVN_QA))
				return Environment.QA;
			if (env.equalsIgnoreCase(Constant.EVN_DEV))
				return Environment.DEV;
		}
		return null;
	}
	
	
	public boolean isStatusCode (Object expected, Object actual) {
		if (expected.toString().equalsIgnoreCase(actual.toString()))
			return true;
		
		return false;
	}
	
	
}
