package com.excelDBDataComparision;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ReadDBData {
	
	public static HashMap<String, List<Object>> readFilmDBData() throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream( System.getProperty("user.dir")+"\\src\\com\\excelDBDataComparision\\config.properties");
			prop.load(fis);
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
	
		Connection connect = DriverManager.getConnection(prop.getProperty("DBConnection"),prop.getProperty("userName"),prop.getProperty("password"));
		Statement smt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = smt.executeQuery(prop.getProperty("sqlQuery"));
		
		HashMap<String, List<Object>> listMap = new HashMap<String, List<Object>>();
		
		for(int i=1;i<=rs.getMetaData().getColumnCount();i++) {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(rs.getMetaData().getColumnTypeName(i));
			while (rs.next()) {
				if(rs.getMetaData().getColumnTypeName(i).contains("CHAR") || (rs.getMetaData().getColumnTypeName(i).contains("TEXT"))) {
					list.add(rs.getString(i));
				}
				else if(rs.getMetaData().getColumnTypeName(i).contains("INT")) {
					list.add(rs.getInt(i));
				}	
				else if(rs.getMetaData().getColumnTypeName(i).contains("DECIMAL")) {
					list.add(rs.getDouble(i));
				}
			}				
			rs.beforeFirst();			
			listMap.put(rs.getMetaData().getColumnName(i), list);			
		}
//		System.out.println(listMap);
		
		connect.close();
		return listMap;
	}	 
}
