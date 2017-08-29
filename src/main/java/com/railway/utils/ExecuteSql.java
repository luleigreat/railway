package com.railway.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ExecuteSql {
    private String driver;
    private String url;
    private String user;
    private String pass;
    Connection conn;
    Statement stmt;
    List<Statement> listStmt = new ArrayList<Statement>();
    ResultSet rs;
    boolean isInTrans = false;
    
    public void initParam() throws IOException{
    	InputStream is = ExecuteSql.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties props = new Properties();

		props.load(is);
        driver = props.getProperty("driver");
        url = props.getProperty("url");
        user = props.getProperty("username");
        pass = props.getProperty("password");  
    }
     
    public List<List<String>> executeSql(String sql) throws Exception{
    	List<List<String>> list = new ArrayList<List<String>>();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            stmt = conn.createStatement();
            boolean hasResultSet = stmt.execute(sql);
            if (hasResultSet) {
                rs = stmt.getResultSet();
                java.sql.ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                 
                while (rs.next()) {
                	List<String> listTmp = new ArrayList<String>();
                    for (int i = 0; i < columnCount; i++) {
                        System.out.print(rs.getString(i+1) + "\t");
                        listTmp.add(rs.getString(i+1));
                    }
                    System.out.println();
                    list.add(listTmp);
                }
            }
            else {
                System.out.println("改SQL语句影响的记录有" + stmt.getUpdateCount() + "条");
            }
        } 
        finally
        {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public void beginTrans() throws SQLException, ClassNotFoundException{
        Class.forName(driver);
        conn = DriverManager.getConnection(url,user,pass);
        conn.setAutoCommit(false); //JDBC中默认是true，我们改成false，然后在后面手动提交
        listStmt.clear();
    	isInTrans = true;
    }
    
    public void executeTrans(String sql) throws SQLException{
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        listStmt.add(stmt);
        System.out.println("改SQL语句影响的记录有" + stmt.getUpdateCount() + "条");
    }
    
    public boolean endTrans(boolean commit){
    	isInTrans = false;
    	try 
        {
        	if(commit){
        		conn.commit();
        	}else{
        		conn.rollback();
        	}
        	
            for(Statement stmt:listStmt){
            	if(stmt != null)
            		stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
            return true;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
         
    	ExecuteSql ed = new ExecuteSql();
        ed.initParam();
     
//        ed.executeSql("drop table if exists school"); //(insertSql);    
//        ed.executeSql("create table school(id int, name varchar(50), addr varchar(50))");       
//        ed.executeSql("insert into school values(1, 'No1', 'BeiJing')");    
//        ed.executeSql("select * from school");  
        
        ed.beginTrans();
        ed.executeTrans("create table school2(id int, name varchar(50), addr varchar(50))");
        ed.executeTrans("insert into school2 values(1, 'No1', 'BeiJing')");  
        ed.executeTrans("insert into school2 values(2, 'No1', 'BeiJing')");  
        ed.endTrans(false);
    }
     
 
}