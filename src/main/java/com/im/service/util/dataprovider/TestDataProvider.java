package com.im.service.util.dataprovider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.im.service.common.Config;
import com.im.service.common.Constant;
import com.im.service.common.DataType;
import com.im.service.common.DataConstraint;
import com.im.service.common.ServiceGroup;
import com.im.service.common.Annotation.TestData;
import com.im.service.common.Annotation.TestRun;
import com.im.service.exception.NullException;
import com.im.service.datagenerator.DataGenerator;
import com.im.service.util.excel.Excel;
import com.im.service.util.other.Strings;

public class TestDataProvider {

	public final String UNASSIGNED = "[unassigned]";
	
	@DataProvider(name = "TestDataGenerator")
	public Iterator<Object[]> getTestDataGenerator(Method m, ITestContext testContext) throws Exception {

		Collection<Object[]> data = new ArrayList<Object[]>();

		String environment = null;
		if ( testContext.getAttribute(Constant.ENVIRONMENT) != null)
			environment = testContext.getAttribute(Constant.ENVIRONMENT).toString();
		
		// System.out.println("S:"+m.getDeclaringClass().getField("serviceName").get(m.getDeclaringClass().newInstance()));
		//System.out.println("E:"+environment);

		ServiceGroup serviceGroup = null;
		DataType dataType = DataType.UNASSIGNED;
		DataConstraint dataConstraint = DataConstraint.UNASSIGNED;

		String serviceName = null;
		String testGroup = this.UNASSIGNED;
		String testName = this.UNASSIGNED;
		String attributeName = this.UNASSIGNED;
		boolean dependsOnEnvironment = false;
		int randomStringLength = Strings.getLen();
	
		List<String> includeAttribute = null;
		List<String> excludeAttribute = null;
		
		if (m.getAnnotation(TestData.class) == null)
			if (m.getAnnotation(TestRun.class) == null)
				throw new NullException("TestRun annotation not provided");
		
		if (m.getAnnotation(TestRun.class) != null) {
			serviceGroup = m.getAnnotation(TestRun.class).serviceGroup();
			dataType = m.getAnnotation(TestRun.class).dataType();
			dataConstraint = m.getAnnotation(TestRun.class).dataConstraint();
			serviceName = m.getAnnotation(TestRun.class).serviceName();
			testGroup = m.getAnnotation(TestRun.class).testGroup();
			testName = m.getAnnotation(TestRun.class).testName();
			dependsOnEnvironment = m.getAnnotation(TestRun.class).dependsOnEnvironment();
			attributeName = m.getAnnotation(TestRun.class).attributeName();
			randomStringLength = m.getAnnotation(TestRun.class).randomStringLength();
			includeAttribute = Arrays.asList(m.getAnnotation(TestRun.class).includeAttributeNames());
			excludeAttribute = Arrays.asList(m.getAnnotation(TestRun.class).excludeAttributeNames());
		}
		
		DataGenerator dg = new DataGenerator (dataType, dataConstraint);
		
		dg.setIncludeAttribute(includeAttribute);
		dg.setIncludeAttrFlag();
		dg.setExcludeAttribute(excludeAttribute);
		dg.setExcludeAttrFlag();
		
		dg.setAttributeName(attributeName);
		dg.setRandomStringLength(randomStringLength);
		
		dg.setServiceGroup(serviceGroup);
		dg.setServiceName(serviceName);
		
		if (m.getAnnotation(TestData.class) != null) {
			dg.setAttribute(m.getAnnotation(TestData.class).attributeName());
			dg.setValue(m.getAnnotation(TestData.class).value());
			dg.checkAttributeAndValue();
			dg.setActualTestData();
			data =  dg.generateData();
			return data.iterator();
		}
		 
		if (m.getAnnotation(TestData.class) == null) {
			dg.checkSeviceGroup();
			dg.checkSeviceName();
		}
		
		dg.setTestGroup(testGroup);
		dg.setTestName(testName);
		dg.setDependsOnEnvironment(dependsOnEnvironment);
		dg.setEnvironement(environment);
		
		Workbook workBook = Excel.readExcelFile(Config.getTestDataDir(serviceGroup) + serviceName + Excel.FILE_EXTN);
		Sheet sheet = Excel.getSheet(workBook, Excel.DEF_SHEET_NAME);
		
		dg.setColStartIndex(sheet);
		data = dg.generateData(sheet);

		Excel.closeWorkbook(workBook);
		
		return data.iterator();
	}

}