package com.excelDBDataComparision;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

public class DataValidation {

	public static void main(String[] args) {

		try {
			Map<String, List<Object>> dbMap = ReadDBData.readFilmDBData();
			Map<String, List<Object>> excelMap = ReadExcelData.readExcel();
			
			Assert.assertTrue(dbMap.entrySet().size() == excelMap.entrySet().size() && dbMap.keySet().equals(excelMap.keySet()));
			
			for(String name : dbMap.keySet()) {
				List<Object> dbList = dbMap.get(name);
				List<Object> excelList = excelMap.get(name);
				
				if(dbList.get(0).toString().contains("CHAR") || dbList.get(0).toString().contains("TEXT")) {
					dbList.remove(0);
					for(int i=0;i<=dbList.size()-1;i++) {
						Assert.assertTrue(dbList.get(i).toString().equals(excelList.get(i).toString()));
					}
					System.out.println(name + " --> DB column Matched with excel column");
				}
				
				if(dbList.get(0).toString().contains("INT")) {
					dbList.remove(0);
					for(int i=0;i<=dbList.size()-1;i++) {
						Assert.assertTrue(Integer.parseInt(dbList.get(i).toString()) == Integer.parseInt(excelList.get(i).toString()));
				}
					System.out.println(name + " --> DB column Matched with excel column");
			}
					if(dbList.get(0).toString().contains("DECIMAL")) {
						dbList.remove(0);
						for(int i=0;i<=dbList.size()-1;i++) {
							Assert.assertTrue(Double.parseDouble(dbList.get(i).toString()) == Double.parseDouble(excelList.get(i).toString()));
					}
						System.out.println(name + " --> DB column Matched with excel column");
				}	
			} 
		}
		catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}
}
