package test;

import com.im.service.common.Annotation;
import com.im.service.common.Environment;
import com.im.service.common.ServiceGroup;
import com.im.service.rest.WebService;
import com.im.service.session.Session;
import com.im.service.session.SessionProperties;
import common.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetAllUserListTest extends TestBase {
    private static final Logger LOG = LoggerFactory.getLogger(GetAllUserListTest.class);
    public static final String SERVICE_NAME = "getAllUserList";
    @Test
    @Annotation.TestRun(serviceGroup = ServiceGroup.USER,  serviceName = GetAllUserListTest.SERVICE_NAME)//, testName="order_valid_data_test", testGroup="p0")
    public void getAllUser_test () {
        Session session = new Session(SessionProperties.newProperties(ServiceGroup.USER).environment(TestBase.ENV).serviceName(GetAllUserListTest.SERVICE_NAME).token(TestBase.TOKEN).build());
        WebService rest = new WebService(session);
        LOG.info("API Before Parameterize:" + rest.getSession().getAPI());
        rest.get();
        LOG.info("Status:" + rest.getStatus());
        Assert.assertEquals(rest.getStatus(), 200, "The expected status is "+200+". But actual is "+rest.getStatus()+".");
        LOG.info(rest.getResponse().asPrettyString());
    }
}
