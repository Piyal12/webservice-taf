package test;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.im.service.common.ServiceGroup;
import com.im.service.common.Annotation.TestRun;
import com.im.service.rest.WebService;
import com.im.service.session.Session;
import com.im.service.session.SessionProperties;
import com.im.service.util.listener.TestListener;
import com.im.service.util.dataprovider.TestDataProvider;

import common.TestBase;

@Listeners(TestListener.class)
public class LoginTest extends TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(LoginTest.class);

	public static final String SERVICE_NAME = "loginOTP";
	public static final String SERVICE_NAME1 = "verifyOTP";


	@Test (enabled=true, dataProviderClass=TestDataProvider.class, dataProvider="TestDataGenerator")
	@TestRun (serviceGroup = ServiceGroup.LOGIN,  serviceName = LoginTest.SERVICE_NAME, testName="aut_login", testGroup="p0")
	public void login_otp_test (HashMap <String, String> data) throws Exception {
		Session session = new Session(SessionProperties.newProperties(ServiceGroup.LOGIN).environment(TestBase.ENV).serviceName(LoginTest.SERVICE_NAME).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setHeaderToken(false);
		rest.post();

		Assert.assertEquals(rest.getStatus(), 200, "The expected status is "+200+". But actual is "+rest.getStatus()+".");
		Assert.assertTrue(rest.getResponse().body().jsonPath().getBoolean("success") , "Login otp failed");
	}
	
	@Test (enabled=true, dataProviderClass=TestDataProvider.class, dataProvider="TestDataGenerator", dependsOnMethods= {"login_otp_test"} )
	@TestRun (serviceGroup = ServiceGroup.LOGIN,  serviceName = LoginTest.SERVICE_NAME1, testName="aut_login", testGroup="p0")
	public void verify_otp_test (HashMap <String, String> data) throws Exception {
		Session session = new Session(SessionProperties.newProperties(ServiceGroup.LOGIN).environment(TestBase.ENV).serviceName(LoginTest.SERVICE_NAME1).build());
		WebService rest = new WebService(session);
		rest.setTestData(data);	
		rest.setHeaderToken(false);
		rest.post();

		Assert.assertEquals(rest.getStatus(), 200, "The expected status is "+200+". But actual is "+rest.getStatus()+".");
		Assert.assertTrue(rest.getResponse().body().jsonPath().getBoolean("success") , "Login verification failed");
		
		LOG.info("Auth: "+rest.getResponse().getHeader("Authorization"));
	}
}
