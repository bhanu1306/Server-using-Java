package com.bhanu.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConn {
	private static Connection con=null;
 static{
	 try {
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/android", "root", "bhanu");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("Check the jar file for connection");
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("Database Server is not accessible or cannot make connection with the database ");
		e.printStackTrace();
	}
	 
 }
 private MySQLConn()
 {}
 protected static Connection getConnection()
 {
	 return con;
 }
 protected static void closeConnection() throws SQLException
 {
	 try{
	 con.close();
	 }catch(Exception e)
	 {
		 System.out.println("e");
		 e.printStackTrace();
	 }
	 finally{
		 con.close();
	 }
 }
 
}
