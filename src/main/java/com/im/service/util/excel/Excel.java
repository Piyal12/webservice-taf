package com.im.service.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.im.service.common.Config;
import com.im.service.exception.FileException;
import com.im.service.exception.NullException;
import com.im.service.session.Session;

@SuppressWarnings("unused")
public class Excel {
	
	private static final Logger LOG = LoggerFactory.getLogger(Excel.class);
	
	public static final String FILE_EXTN = ".xlsx";
	public static final String PRIORITY_ALL = "all";
	public static final int COL_START_INDEX = 3;
	public static final int COL_START_INDEX_WITH_ENV = 4;
	public static final String DEF_SHEET_NAME = "testdata";
	public static final String HEADER_TEST_ACTIVE = "TestActive";
	public static final String HEADER_TEST_ENVIRONMENT = "TestEnvironment";
	public static final String HEADER_TEST_NAME = "TestName";
	public static final String HEADER_TEST_GROUP = "TestGroup";
	public static final String HEADER_ERROR_MESSAGE = "ErrorMessage";
	public static final String HEADER_STATUS_CODE = "StatusCode";
	public static final String NULL = "[null]";
	public static final String BLANK = "[blank]";

	
    public static Workbook readExcelFile(String fileName) {
		File f = new File (fileName);
		try {
    		if (! f.exists())
    			throw new NullException("Invalid fileName :"+ fileName);
    	} catch (Exception e) {
    		throw new NullException(e.getMessage() + " Invalid fileName : "+fileName );
    	}
		
		try {
		FileInputStream inputStream = new FileInputStream(new File(fileName));
	    return WorkbookFactory.create(inputStream);
		} catch (Exception e) {
    		throw new FileException ("Invalid fileName :"+ fileName);
    	}
	}
    
    public static void closeWorkbook (Workbook workBook) {
		
		try {
			workBook.close();
		} catch (Exception e) {
			throw new NullException("Invalid work book cant close it");
    	}
	}
    
	
    public static Sheet getSheet (Workbook w, String sheetName) {
		boolean flag = false;
		for (int i=0; i<w.getNumberOfSheets(); i++) {
			if (w.getSheetName(i).equals(sheetName))
				flag = true;
		}	
		
		try {
    		if (! flag)
    			throw new NullException("Invalid sheetName. Expected is "+ sheetName);
    	} catch (Exception e) {
    		throw new NullException("Invalid sheetName. Expected is "+ sheetName);
    	}
		
		return w.getSheet(sheetName);
	}
    
	public static HashMap <String, Integer> getColumnHeaders (Sheet sheetName) {
		HashMap <String, Integer> data = new HashMap <String, Integer> (); 
		for (int i=0; i<sheetName.getRow(0).getLastCellNum(); i++)
			if(Excel.isHeaderValid(sheetName, 0, i))
					data.put(sheetName.getRow(0).getCell(i).toString().trim(),i);
		return data;
	}
	
	public static boolean isExistsColumnHeader (Sheet sheetName, String columnName) {
		for (int i=0; i<sheetName.getRow(0).getLastCellNum(); i++)
			if (sheetName.getRow(0).getCell(i).toString().trim().equalsIgnoreCase(columnName))
				return true;
		return false;
	}
	
	public static boolean isTestDataColumn(String columnName) {
		if (columnName.equalsIgnoreCase(Excel.HEADER_STATUS_CODE))
			return false;
		if (columnName.equalsIgnoreCase(Excel.HEADER_ERROR_MESSAGE))
			return false;
		
		return true;
	}
	
	public static boolean isExistActiveTestName (Sheet sheet, Row row, String testName) {
		HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
		if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)) != null)
			if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)) != null)
				if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)).toString().equalsIgnoreCase(testName))
					if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)).toString().equalsIgnoreCase("Y")) 
						return true;
		
		return false;
	}
	
	public static boolean isExistActiveTestName (Sheet sheet, String testName) {

		boolean flag = false;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
			if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)) != null)
				if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)) != null)
					if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)).toString().equalsIgnoreCase(testName))
						if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)).toString().equalsIgnoreCase("Y")) 
							flag = true;
		}
		
		return flag;
	}
	
	public static boolean isExistActiveTestNameForTestEnvironment (Sheet sheet, String testName, String testEnvironment) {

		boolean flag = false;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
			if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)) != null)
				if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)) != null)
					if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ENVIRONMENT)) != null)
						if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)).toString().equalsIgnoreCase(testName))
							if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ENVIRONMENT)).toString().equalsIgnoreCase(testEnvironment))
								if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)).toString().equalsIgnoreCase("Y")) 
									flag = true;
		}
		
		return flag;
	}
	
	public static boolean isTestNameActive (Sheet sheet, Row row) {
		HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
		if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)) != null)
			if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ACTIVE)).toString().equalsIgnoreCase("Y")) 
						return true;
		return false;
	}
	
	public static boolean isTestEnvironmentHeaderExists (Sheet sheet) {
		HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
		if (colIdByName.containsKey(Excel.HEADER_TEST_ACTIVE))
			return true;
		return false;
	}
	
	public static boolean matchTestGroup (Sheet sheet, Row row, String group) {
		
		if (group.equalsIgnoreCase(Excel.PRIORITY_ALL))
			return true;
		
		HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
		if (row.getCell(colIdByName.get(Excel.HEADER_TEST_GROUP)) != null)
			if (row.getCell(colIdByName.get(Excel.HEADER_TEST_GROUP)).toString().equalsIgnoreCase(group)) 
						return true;
		return false;
	}
	
	public static boolean matchTestName (Sheet sheet, Row row, String testName) {
		
		HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
		if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)) != null)
			if (row.getCell(colIdByName.get(Excel.HEADER_TEST_NAME)).toString().equalsIgnoreCase(testName)) 
						return true;
		return false;
	}
	
	public static boolean matchTestEnvironment (Sheet sheet, Row row, String testEnvironment) {
		
		HashMap<String, Integer> colIdByName = getColumnHeaders(sheet);	
		if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ENVIRONMENT)) != null)
			if (row.getCell(colIdByName.get(Excel.HEADER_TEST_ENVIRONMENT)).toString().equalsIgnoreCase(testEnvironment)) 
						return true;
		return false;
	}
	
	public static boolean isHeaderValid (Sheet sheet, int row, int col) {
		if(sheet.getRow(row).getCell(col) != null)
			if(sheet.getRow(row).getCell(col).toString().trim().length() > 0)
				return true;
		return false;
	}

	public static HashMap<String, String> getTestData(Session session, String testName) {
		Workbook workBook = Excel.readExcelFile(Config.getTestDataDir(session.getServiceGroup())+session.getServiceName()+Excel.FILE_EXTN);
		Sheet sheet = Excel.getSheet(workBook, Excel.DEF_SHEET_NAME);
		HashMap<String, String> data = new HashMap<String, String>();
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			if (Excel.isExistActiveTestName (sheet, sheet.getRow(i), testName)) {
				for (int j = Excel.COL_START_INDEX; j < sheet.getRow(i).getLastCellNum(); j++) 
						data.put(sheet.getRow(0).getCell(j).toString(), Excel.cleanData(sheet.getRow(i).getCell(j)));
				break;
			}
		}

		Excel.closeWorkbook(workBook);
		
		try {
    		if (! (data.size() > 0))
    			throw new NullException("No test data or No active test data found for testName :"+ testName);
    	} catch (Exception e) {
    		throw new NullException(e.getMessage());
    	}

		if (data.containsKey(""))
			data.remove("");
		
		return data;
	}
	
	public static String cleanData (Object val) {
		if (val == null)
			return "";
		
		String cleanValue = val.toString();
		if (val.toString().endsWith(".0")) {
			cleanValue = val.toString().subSequence(0, val.toString().lastIndexOf(".0")).toString();
		}
		
		return cleanValue;
		
	}

}
