package test;

import com.im.service.common.*;
import com.im.service.rest.WebService;
import com.im.service.session.Session;
import com.im.service.session.SessionProperties;
import com.im.service.util.dataprovider.TestDataProvider;
import common.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;

public class CreateUserTest extends TestBase {
    private static final Logger LOG = LoggerFactory.getLogger(CreateUserTest.class);
    public static final String SERVICE_NAME = "createUsergorest";

    @Test(enabled=true, dataProviderClass= TestDataProvider.class, dataProvider="TestDataGenerator")
    @Annotation.TestRun(serviceGroup = ServiceGroup.USER,  serviceName = CreateUserTest.SERVICE_NAME, testName="createUsergorest_valid_data_test", testGroup="p0",attributeName = "email", dataConstraint = DataConstraint.FOR_SPECIFIED, dataType = DataType.NUMERIC)
    public void createUser_test (HashMap<String, String> data)  {
        Session session = new Session(SessionProperties.newProperties(ServiceGroup.USER).environment(TestBase.ENV).serviceName(CreateUserTest.SERVICE_NAME).build());
        WebService rest = new WebService(session);
        rest.setTestData(data);
        LOG.info("API Before Parameterize:" + rest.getSession().getAPI());
        LOG.info("API After Parameterize:" + rest.getParameterize(rest.getSession().getAPI(),rest.getTestData()));
        rest.post();
        LOG.info("Status:" + rest.getStatus());
        Assert.assertEquals(rest.getStatus(), 201, "The expected status is "+201+". But actual is "+rest.getStatus()+".");
        LOG.info(rest.getResponse().asPrettyString());
    }
}
