package com.im.service.datagenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import com.im.service.common.DataConstraint;
import com.im.service.common.DataType;
import com.im.service.common.ServiceGroup;
import com.im.service.exception.NullException;
import com.im.service.util.excel.Excel;
import com.im.service.util.other.Strings;

public class DataGenerator {
	
	Collection<Object[]> data = new ArrayList<Object[]>();

	public final String UNASSIGNED = "[unassigned]";

	private String[] attribute;
	private String[] value;
	private HashMap<String, String> actualTestData = new HashMap<String, String>();
	private HashMap<String, String> testData = new HashMap<String, String>();
	private List<String> includeAttribute = null;
	private List<String> excludeAttribute = null;
			
	private boolean includeAttrFlag = false;
	private boolean excludeAttrFlag = false;
	
	private boolean dependsOnEnvironment = false;
	private int colStartIndex = Excel.COL_START_INDEX;
	
	private int randomStringLength = Strings.getLen();
	private DataType dataType;
	private DataConstraint dataConstraint;
	
	private String attributeName;

	private ServiceGroup serviceGroup;
	private String serviceName;
	
	private String testGroup;
	private String testName;
	
	private String environment = null;

	private DataFormatter df = new DataFormatter();

	
	public DataGenerator(DataType dataType, DataConstraint dataConstraint) {
		this.dataType = dataType;
		this.dataConstraint = dataConstraint;
	}
	
	public ServiceGroup getServiceGroup() {
		return this.serviceGroup;
	}

	public void setServiceGroup(ServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
	
	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName (String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String[] getAttribute() {
		return attribute;
	}
	
	public void setAttribute(String[] attribute) {
		this.attribute = attribute;
	}
	
	public String[] getValue() {
		return value;
	}
	
	public void setValue(String[] value) {
		this.value = value;
	}
	
	public HashMap<String, String> getActualTestData() {
		return actualTestData;
	}
	
	public void setActualTestData() {
		if (this.getValue().length != 0)
			for (int i = 0; i < this.getAttribute().length; i++)
				this.actualTestData.put(this.getAttribute()[i], this.getValue()[i]);
		else 
			for (int i = 0; i < this.getAttribute().length; i++)
				this.actualTestData.put(this.getAttribute()[i], "");
	}
	
	public void checkAttributeAndValue () {
		if (this.getAttribute().length == 0)
			throw new NullException("Attribute count cannot be zero");
		
		if (this.getValue().length != 0)
			if (this.getAttribute().length != this.getValue().length)
				throw new NullException("Attribute and values count mismatch");
		
	}

	public HashMap<String, String> getTestData() {
		return testData;
	}

	public void setTestData(HashMap<String, String> testData) {
		this.testData = testData;
	}

	public List<String> getIncludeAttribute() {
		return includeAttribute;
	}

	public void setIncludeAttribute(List<String> includeAttribute) {
		this.includeAttribute = includeAttribute;
	}

	public List<String> getExcludeAttribute() {
		return excludeAttribute;
	}

	public void setExcludeAttribute(List<String> excludeAttribute) {
		this.excludeAttribute = excludeAttribute;
	}

	public boolean isIncludeAttrFlag() {
		return includeAttrFlag;
	}
	
	public void setIncludeAttrFlag() {
		this.includeAttrFlag = false;
		if (this.getIncludeAttribute() != null)
			if (! this.getIncludeAttribute().isEmpty())
				this.includeAttrFlag = true;
	}

	public boolean isExcludeAttrFlag() {
		return excludeAttrFlag;
	}
	
	public void setDependsOnEnvironment(boolean dependsOnEnvironment) {
		this.dependsOnEnvironment = dependsOnEnvironment;
	}
	
	public boolean isDependsOnEnvironment() {
		return dependsOnEnvironment;
	}

	public void setEnvironement(String environment) {
		this.environment = environment;
	}
	
	public String getEnvironement() {
		return this.environment;
	}
	
	public String getTestGroup() {
		return this.testGroup;
	}
	
	public void setTestGroup(String testGroup) {
		this.testGroup = testGroup;
	}
	
	public String getTestName() {
		return this.testName;
	}
	
	public void setTestName(String testName) {
		this.testName = testName;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public void setColStartIndex (Sheet sheet) {
		this.colStartIndex = Excel.COL_START_INDEX;
		
		if (Excel.isTestEnvironmentHeaderExists(sheet))
			this.colStartIndex = Excel.COL_START_INDEX_WITH_ENV;
		
		if (this.isDependsOnEnvironment())
			this.colStartIndex = Excel.COL_START_INDEX_WITH_ENV;
	}
	
	public void setExcludeAttrFlag() {
		this.excludeAttrFlag = false;	
		if (this.getExcludeAttribute() != null)
			if (! this.getExcludeAttribute().isEmpty())
				this.excludeAttrFlag = true;	
	}

	public int getRandomStringLength() {
		return randomStringLength;
	}

	public void setRandomStringLength(int randomStringLength) {
		if (randomStringLength != Strings.getLen())
			Strings.setLen(randomStringLength);
		this.randomStringLength = randomStringLength;
	}
	
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public void checkAttributeName() {
		if (this.getAttributeName().equalsIgnoreCase(this.UNASSIGNED))
			throw new NullException("attribute name is not mentioned");
	}
	
	public void checkAttributeNameExistInTestData() {
		if (! this.getActualTestData().containsKey(attributeName))
			throw new NullException("Invalid attribute name i.e., no such attribute name provided in test data via @TestData");
	}
	
	public void checkSeviceGroup() {
		if (this.getServiceGroup().equals(ServiceGroup.UNASSIGNED))
			throw new NullException("Service group is not mentioned");
	}
	
	public void checkSeviceName() {
		if (this.getServiceName().equalsIgnoreCase(this.UNASSIGNED))
			throw new NullException("Service name is not mentioned");
	}

	public Collection<Object[]> generateData () {
		
		if (dataType.equals(DataType.UNASSIGNED)) {
			data.add(new Object[] { this.getActualTestData()});
			return data;
		}
		
		if (dataConstraint.equals(DataConstraint.UNASSIGNED)) {
			data.add(new Object[] { this.getActualTestData()});
			return data;
		}
			
		if (dataConstraint.equals(DataConstraint.ALL))
			data.add(new Object[] { this.generateData_ALL (this.dataType)});

		if (dataConstraint.equals(DataConstraint.FOR_SPECIFIED))
			data.add(new Object[] { this.generateData_FOR_SPECIFIED (this.dataType)});
		
		if (dataConstraint.equals(DataConstraint.ONE_BY_ONE))
			this.generateData_ONE_BY_ONE (this.dataType);
			
		return data;

	}
	
	public Collection<Object[]> generateData (Sheet sheet) {
		
		boolean testDataFoundForTestEnvironment = false;
		for (int row = 1; row <= sheet.getLastRowNum(); row++) {
			HashMap<String, String> testData = new HashMap<String, String>();

			boolean flag = false;
			if (Excel.isTestNameActive(sheet, sheet.getRow(row))) {
				if(this.isDependsOnEnvironment()) {
					if (this.getEnvironement() == null)
						throw new NullException("Test environment not set in iTestContext");
					
					if (Excel.matchTestEnvironment(sheet, sheet.getRow(row), this.getEnvironement())) {
						if (!this.getTestGroup().equalsIgnoreCase(this.UNASSIGNED)) {
							if (Excel.matchTestGroup(sheet, sheet.getRow(row), this.getTestGroup())) {
								if (!this.getTestName().equalsIgnoreCase(this.UNASSIGNED)) {
									if (!Excel.isExistActiveTestName(sheet, this.getTestName()))
										throw new NullException("Test name not exists in the data file");
									if (Excel.matchTestName(sheet, sheet.getRow(row), this.getTestName())) {
										flag = true;
										testDataFoundForTestEnvironment = true;
									}
								} else 
									flag = true;
							}
						} else if (!this.getTestName().equalsIgnoreCase(this.UNASSIGNED)) {
							if (!Excel.isExistActiveTestName(sheet, this.getTestName()))
								throw new NullException("Test name not exists in the data file");
							if (Excel.matchTestName(sheet, sheet.getRow(row), this.getTestName()))
								flag = true;
						} else
							flag = true;
					} else
						flag = false;
				} else {
					if (!this.getTestGroup().equalsIgnoreCase(this.UNASSIGNED)) {
						if (Excel.matchTestGroup(sheet, sheet.getRow(row), this.getTestGroup())) {
							if (!this.getTestName().equalsIgnoreCase(this.UNASSIGNED)) {
								if (!Excel.isExistActiveTestName(sheet, this.getTestName()))
									throw new NullException("Test name not exists in the data file");
								if (Excel.matchTestName(sheet, sheet.getRow(row), this.getTestName()))
									flag = true;
							} else 
								flag = true;
						}
					} else if (!this.getTestName().equalsIgnoreCase(this.UNASSIGNED)) {
						if (!Excel.isExistActiveTestName(sheet, this.getTestName()))
							throw new NullException("Test name not exists in the data file");
						if (Excel.matchTestName(sheet, sheet.getRow(row), this.getTestName()))
							flag = true;
					} else
						flag = true;
				}
				
			}

			if (flag) {
				if (dataType.equals(DataType.UNASSIGNED)) {
					for (int col = colStartIndex; col < sheet.getRow(row).getLastCellNum(); col++) {
						if(Excel.isHeaderValid(sheet, row, col))
							testData.put(sheet.getRow(0).getCell(col).toString(), Excel.cleanData(df.formatCellValue(sheet.getRow(row).getCell(col))));
					}
					data.add(new Object[] { testData });
					return data;

				}
				
				if (dataConstraint.equals(DataConstraint.UNASSIGNED)) {
					for (int col = colStartIndex; col < sheet.getRow(row).getLastCellNum(); col++) {
						if(Excel.isHeaderValid(sheet, row, col))
							testData.put(sheet.getRow(0).getCell(col).toString(), Excel.cleanData(df.formatCellValue(sheet.getRow(row).getCell(col))));
					}
					data.add(new Object[] { testData });
					return data;
				}
					
				if (dataConstraint.equals(DataConstraint.ALL))
					data.add(new Object[] { this.generateData_ALL (this.dataType, sheet, row, colStartIndex)});

				if (dataConstraint.equals(DataConstraint.FOR_SPECIFIED))
					data.add(new Object[] { this.generateData_FOR_SPECIFIED (this.dataType, sheet, row, colStartIndex)});
				
				if (dataConstraint.equals(DataConstraint.ONE_BY_ONE))
					this.generateData_ONE_BY_ONE (this.dataType, sheet, row, colStartIndex);
					
			}

		}

		if (this.isDependsOnEnvironment())
			if (data.isEmpty())
				if (! testDataFoundForTestEnvironment)
				throw new NullException("Test data not found for the test environment set");
		
		return data;
	}

	private HashMap<String, String> generateData_ALL(DataType dataType) {

		for (String key : this.getActualTestData().keySet()) {
			if (this.isIncludeAttrFlag()) {
				if (! this.getIncludeAttribute().contains(key)) {
					this.testData.put(key, this.getActualTestData().get(key));
					continue;
				}
			}	
			
			if (this.isExcludeAttrFlag()) {
				if (this.getExcludeAttribute().contains(key)) {
					this.testData.put(key, this.getActualTestData().get(key));
					continue;
				}
			}
			
			this.testData.put(key, this.getGeneratedData(dataType));
		}
		
		return this.testData;
	}
	
	private HashMap<String, String> generateData_ALL(DataType dataType, Sheet sheet, int row, int colStartIndex) {

		for (int col = colStartIndex; col < sheet.getRow(row).getLastCellNum(); col++) {
			if(Excel.isHeaderValid(sheet, 0, col))
				if (Excel.isTestDataColumn(sheet.getRow(0).getCell(col).toString())) {
					
					if (includeAttrFlag) {
						if (! includeAttribute.contains(sheet.getRow(0).getCell(col).toString())) {
							testData.put(sheet.getRow(0).getCell(col).toString(), Excel.cleanData(df.formatCellValue(sheet.getRow(row).getCell(col))));
							continue;
						}
					}	
					if (excludeAttrFlag) {
						if (excludeAttribute.contains(sheet.getRow(0).getCell(col).toString())) {
							testData.put(sheet.getRow(0).getCell(col).toString(), Excel.cleanData(df.formatCellValue(sheet.getRow(row).getCell(col))));
							continue;
						}
					}
					
					this.testData.put(sheet.getRow(0).getCell(col).toString(), this.getGeneratedData(dataType));
					
				} else
					testData.put(sheet.getRow(0).getCell(col).toString(), Excel.cleanData(df.formatCellValue(sheet.getRow(row).getCell(col))));
		}
		
		return this.testData;
	}
	
	private void generateData_ONE_BY_ONE(DataType dataType) {

		for (String key : this.getActualTestData().keySet()) {
			this.testData = new HashMap<String, String>();
			this.testData.put(key, this.getActualTestData().get(key));
			
			if (this.isIncludeAttrFlag())
				if (! this.getIncludeAttribute().contains(key)) 
					continue;
			
			if (this.isExcludeAttrFlag())
				if (this.getExcludeAttribute().contains(key)) 
					continue;
			
			for (String key1 : this.getActualTestData().keySet()) {
				testData.put(key1, this.getActualTestData().get(key1));
				if (key.equalsIgnoreCase(key1)) {
					this.testData.put(key1, this.getGeneratedData(dataType));
					data.add(new Object[] {testData});
				}
			}
		}		
	}
	
	private void generateData_ONE_BY_ONE(DataType dataType, Sheet sheet, int row, int colStartIndex) {

		for (int col = colStartIndex; col < sheet.getRow(row).getLastCellNum(); col++) {
			
			if(Excel.isHeaderValid(sheet, 0, col)) {

				if (this.isIncludeAttrFlag())
					if (! this.getIncludeAttribute().contains(sheet.getRow(0).getCell(col).toString())) 
						continue;
				
				if (this.isExcludeAttrFlag())
					if (this.getExcludeAttribute().contains(sheet.getRow(0).getCell(col).toString())) 
						continue;
				
				testData = new HashMap<String, String>();
				for (int colx = colStartIndex; colx < sheet.getRow(row).getLastCellNum(); colx++) {
					if(Excel.isHeaderValid(sheet, 0, colx)) {
						testData.put(sheet.getRow(0).getCell(colx).toString(), Excel.cleanData(df.formatCellValue(sheet.getRow(row).getCell(colx))));
						if (col == colx) {
							if (Excel.isTestDataColumn(sheet.getRow(0).getCell(colx).toString())) {
								this.testData.put(sheet.getRow(0).getCell(colx).toString(), this.getGeneratedData(dataType));
								data.add(new Object[] { testData });
							}
						}
					}
				}
			}
		}
	}
	
	private HashMap<String, String> generateData_FOR_SPECIFIED (DataType dataType) {
		this.checkAttributeName();
		this.checkAttributeNameExistInTestData();
		
		for (String key : actualTestData.keySet()) {
			this.testData.put(key, actualTestData.get(key));
			if (key.equalsIgnoreCase(attributeName)) {
				this.testData.put(key, this.getGeneratedData(dataType));
			}
		}
		
		return this.testData;
	}
	
	private HashMap<String, String> generateData_FOR_SPECIFIED (DataType dataType, Sheet sheet, int row, int colStartIndex) {
		this.checkAttributeName();
		
		for (int col = colStartIndex; col < sheet.getRow(row).getLastCellNum(); col++) {
			if(Excel.isHeaderValid(sheet, 0, col)) {
				testData.put(sheet.getRow(0).getCell(col).toString(), Excel.cleanData(df.formatCellValue(sheet.getRow(row).getCell(col))));
				if (Excel.isTestDataColumn(sheet.getRow(0).getCell(col).toString())) 
					if (sheet.getRow(0).getCell(col).toString().equalsIgnoreCase(attributeName)) 
						this.testData.put(sheet.getRow(0).getCell(col).toString(), this.getGeneratedData(dataType));
			}
		}
		
		return this.testData;
	}
 
	private String getGeneratedData (DataType dataType) {
		
		if (dataType.equals(DataType.NULL))
			return Excel.NULL;
		if (dataType.equals(DataType.BLANK))
			return Excel.BLANK;
		if (dataType.equals(DataType.NUMERIC))
			return Strings.getRandomNumber();
		if (dataType.equals(DataType.ALPHABET))
			return Strings.getRandomString();
		if (dataType.equals(DataType.ALPHABET_UPPERCASE))
			return Strings.getRandomString().toUpperCase();
		if (dataType.equals(DataType.ALPHABET_LOWERCASE))
			return Strings.getRandomString().toLowerCase();
		if (dataType.equals(DataType.ALPHA_NUMERIC))
			return Strings.getRandomAlphaNumeric();
		if (dataType.equals(DataType.ALPHA_NUMERIC_UPPERCASE))
			return Strings.getRandomAlphaNumeric().toUpperCase();
		if (dataType.equals(DataType.ALPHA_NUMERIC_LOWERCASE))
			return Strings.getRandomAlphaNumeric().toLowerCase();
		if (dataType.equals(DataType.SPECIAL_CHARS))
			return Strings.getRandomSpecialChar();
		if (dataType.equals(DataType.EMAIL))
			return Strings.getRandomEmail();

		return Excel.NULL;

	}

}
