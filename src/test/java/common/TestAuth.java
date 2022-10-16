package common;

import java.io.FileReader;
import java.util.HashMap;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.im.service.common.Config;
import com.im.service.common.ServiceGroup;
import com.im.service.exception.ConfigFileException;
import com.im.service.rest.WebService;
import com.im.service.session.Session;
import com.im.service.session.SessionProperties;

public class TestAuth {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TestAuth.class);
	
	private static final String APP ="app";
	private static final String ENV_LOGIN_ID = "loginId";
	private static final String ENV_LOGIN_TYPE = "loginType";
	private static final String ENV_LOGIN_MODE = "loginMode";
	private static final String ENV_VERIFY_MODE = "verifyMode";

	private static final String ENV_LOGIN_OTP = "loginOTP";
	@SuppressWarnings("unused")
	private static final String ENV_LOGIN_PASSWORD = "loginPassword";
	
	private static final String SERVICE_NAME = "loginOTP";
	private static final String SERVICE_NAME1 = "verifyOTP";
	
	public static void getCredentials() {
		Ini ini = new Ini();
		try {
			ini.load(new FileReader(Config.getConfig(ServiceGroup.CREDENTIAL)));
		} catch (Exception e) {
			throw new ConfigFileException (e.getMessage());
		}  
		
		Section app = ini.get(APP);
		Section env = app.getChild(TestBase.ENV.toString().toLowerCase());
		
		Session session = new Session(SessionProperties.newProperties(ServiceGroup.LOGIN).environment(TestBase.ENV).serviceName(TestAuth.SERVICE_NAME).build());
		WebService rest = new WebService(session);
		
		HashMap <String, String> data = new HashMap <String, String>();
    	data.put("loginId", env.get(TestAuth.ENV_LOGIN_ID));
    	data.put("loginType", env.get(TestAuth.ENV_LOGIN_TYPE));
    	data.put("loginMode", env.get(TestAuth.ENV_LOGIN_MODE));
		rest.setHeaderToken(false);

		rest.setTestData(data);
		rest.post();
		Assert.assertEquals(rest.getStatus(), 200, "The expected status is "+200+". But actual is "+rest.getStatus()+".");
		Assert.assertTrue(rest.getResponse().body().jsonPath().getBoolean("success") , "Login otp failed");
		
		session = new Session(SessionProperties.newProperties(ServiceGroup.LOGIN).environment(TestBase.ENV).serviceName(TestAuth.SERVICE_NAME1).build());
		rest = new WebService(session);
		
		data = new HashMap <String, String>();
    	data.put("loginId", env.get(TestAuth.ENV_LOGIN_ID));
    	data.put("loginType", env.get(TestAuth.ENV_LOGIN_TYPE));
    	data.put("mode", env.get(TestAuth.ENV_VERIFY_MODE));
    	data.put("otp", env.get(TestAuth.ENV_LOGIN_OTP));
		rest.setHeaderToken(false);

		rest.setTestData(data);
		rest.post();

		Assert.assertEquals(rest.getStatus(), 200, "The expected status is "+200+". But actual is "+rest.getStatus()+".");
		Assert.assertTrue(rest.getResponse().body().jsonPath().getBoolean("success") , "Login verification failed");
		
		TestBase.TOKEN = rest.getResponse().getHeader("Authorization");
		
	}

}
