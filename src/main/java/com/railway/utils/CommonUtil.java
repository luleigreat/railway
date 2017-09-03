package com.railway.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

public class CommonUtil {
	static String jsonSql_;
	static String jsonData_;
	static {
		jsonSql_ = readFile("sql.json"); 
		jsonData_ = readFile("data.json");
		System.out.println("CommonUtil:static data read");
	}
	
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
	
	public static JSONObject getTableSql(int tableId){
		JSONObject json = new JSONObject(jsonSql_);
		JSONObject retObj = json.getJSONObject("table"+tableId);
		return retObj;
	}
	
	public static JSONObject getTableCoordinate(int tableId){
		JSONObject json = new JSONObject(jsonData_);
		JSONObject retObj = json.getJSONObject("table"+tableId);
		return retObj;
	}
	
	public static String readFile(String fileName){
		String content = "";
		try {
			InputStream is = CommonUtil.class.getClassLoader().getResourceAsStream(fileName);

			// 一次读一个字节
	       // FileInputStream in = new FileInputStream(fileName);
	        //创建一个长度为1024的竹筒  
	        byte[] bbuf = new byte[1024];  
	        //用于保存实际读取的字节数  
	        int hasRead = 0;  
	        //使用循环来重复“取水”的过程  
	        while((hasRead = is.read(bbuf))>0)  
	        {  
	            //取出"竹筒"中(字节),将字节数组转成字符串输入  
	            content += new String(bbuf,0,hasRead);  
	        }  
	        
	        is.close();

	     } catch (IOException e) {
	        e.printStackTrace();
	     }
		 return content;
	}
	
	public static int getYear(){
		int year = 2017;
		if(new Date().getYear() >= year){
			year = new Date().getYear();
		}
		return year;
	}
	
	public static String getInfoTableName(int tableId){
		int year = getYear();
		return "t_" + year + "_" + tableId;
	}
	
	public static String getDataTableName(String userName,int tableId){
		int year = getYear();
		return "t_" + userName + "_" + year + "_" + tableId;
	}
	
	public static Position parseCoord(String sPos){
		String[] posArray = sPos.split(",");
		int row = Integer.parseInt(posArray[0]) - 1;
		int column = Integer.parseInt(posArray[1]) - 1;
		return new Position(row,column);
	}
	
	public static String formatInsertSqlInfo(String sql,List<String> list){
		sql += "(";
		for(String desc:list){
			sql += "'" + desc + "'";
			sql += ",";
		}
		sql = sql.substring(0, sql.length() -1);
		sql += ");";
		return sql;
	}
	public static String formatInsertSqlData(String sql,List<Integer> list){
		sql += "(";
		for(Integer desc:list){
			sql += desc;
			sql += ",";
		}
		sql = sql.substring(0, sql.length() -1);
		sql += ");";
		return sql;
	}
}
