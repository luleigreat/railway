package com.railway.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlUtil {
	private static ExecuteSql exeSql_ = null;
	static{
		exeSql_ = new ExecuteSql();
		try {
			exeSql_.initParam();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkTableExists(String tableName){
		String sql = "SHOW TABLES LIKE '%" + tableName + "%'";
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			list = exeSql_.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0;
	}
	
	public static List<List<String>> executeSql(String sql) throws Exception{
		return exeSql_.executeSql(sql);
	}
}
