package com.excelDBDataComparision;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ListModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelData {
	
	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	
	
	public static Map<String, List<Object>> readExcel() throws IOException {
		Properties prop = new Properties();
		Map<String, List<Object>> listMap = new HashMap<String, List<Object>>();
		
		FileInputStream fispp = new FileInputStream(System.getProperty("user.dir") + "\\src\\com\\excelDBDataComparision\\config.properties");
		prop.load(fispp);
		
		File file = new File(prop.getProperty("excelPath"));		
		try {
			FileInputStream fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(prop.getProperty("sheetName"));
			Row r = sheet.getRow(0);
			int maxCell=  r.getLastCellNum();		      
			for(int i=0;i<maxCell;i++) {
				ArrayList<Object> list = new ArrayList<Object>();
				for(int j=1;j<=sheet.getLastRowNum();j++) {
					switch (sheet.getRow(j).getCell(i).getCellType()) {
					case STRING:
						list.add(sheet.getRow(j).getCell(i).getStringCellValue());
						break;
						
					case NUMERIC:
						list.add(sheet.getRow(j).getCell(i).getNumericCellValue());
						
					default:				
						break;
					}					
				}				
				listMap.put(sheet.getRow(0).getCell(i).getStringCellValue(),list);				
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			wb.close();			
		}
		 
		return listMap;
	}
}
