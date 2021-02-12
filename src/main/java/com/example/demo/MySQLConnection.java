/*
 * @ Author: JVH Consulting
 * @   Date: Jan 1, 2021
 * @Abstract:
 *           This ABSTRACT Class will be used to obtain a JDBC Connection for MySQL DB 
 *           and returning it to the caller to use. This Class has been modified
 *           to use it in the AWS (Amazon Web Services Cloud) & its BeanStalk Service 
 *           to connect to a MySQL DB created using the Configuration DB or RDB parameters of
 *           AWS BeanStalk Infrastructure as a Platform. (PaaS) versus IaaS or SaaS
 * @Methods:
 * @ connectToMySQL() - Uses the MySQL Driver class to establish a connection to the DB
 * @ disconectMySQL() - Closes the connection after it has been used.   
 */

package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MySQLConnection {
	
	private Connection conn;
	
	/*
	 * Note the use of the System Environment variables to get the MySQL required connection parameters to connect to its DB.
	 * These MySQL DB values were set by AWS BeanStalk as Environment Variables in the AWS environment Spb302-env where this application runs
	 */
	public Connection connectToMySQL() throws Exception {
		
		/* This could be used with Amazon Web Services (AWS) since they put this info into the Environment Variables
		Class.forName("com.mysql.jdbc.Driver");
		String dbName = System.getenv("RDS_DB_NAME");
		String userName = System.getenv("RDS_USERNAME");
		String password = System.getenv("RDS_PASSWORD");
		String hostname = System.getenv("RDS_HOSTNAME");
		String port = System.getenv("RDS_PORT");
		String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?" + "user=" + userName + "&password=" + password;
		System.out.println("Getting remote connection with connection string from environment variables");
		
		Connection con = DriverManager.getConnection(jdbcUrl);
		System.out.println("Remote connection successful.");
		return con;
		*/
		String dbName="JVHDATABASE";// use HELLO_JAVA to change DB in MySQl container show databases; show tables; show columns from JOBSTABLE;
		String userName="JAIME";
		String password = "1234";
		String hostname="access"; //USE WITH DOCKER BUT ENSURE THE VALUE IS SET VIA --network-alias mysql (and using --network mynet (or the net name))
		//String hostname="localhost";
		//String hostname="mysql-service";
		String port ="3306";
		
		//String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?" + "user=" + userName + "&password=" + password;
		String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?" + "user=" + userName + "&password=" + password;
		try {
			// Load-Register-Setup MySQL JDBC Driver
			Class.forName("com.mysql.jdbc.Driver"); // MySQL JDBC Driver Class
			System.out.println("JDBC Url: "+ jdbcUrl);
			System.out.println("Getting remote connection with connection string from environment variables");
			conn = DriverManager.getConnection(jdbcUrl);
			System.out.println("CONNECTION ATTEMPT USING DS ---->>>>>>>>>>>>>>");
		} // End Try
		
		catch (SQLException e) {
			System.out.println("Value of jdbcUrl: " + jdbcUrl);
			System.out.println("SQL Exception caught: " + e.getMessage());}
		
		return conn;
	
	} // End connect
	
	
	public Connection connectToMySQL(String jdbcUrl) throws Exception {
		try {
			// Load-Register-Setup MySQL JDBC Driver
			Class.forName("com.mysql.jdbc.Driver"); // MySQL JDBC Driver Class
			
			System.out.println("JDBC Url: "+ jdbcUrl);
			System.out.println("Getting remote connection with connection string from environment variables");
			conn = DriverManager.getConnection(jdbcUrl);
			System.out.println("CONNECTION ATTEMPT USING DS ---->>>>>>>>>>>>>>");
		} // End Try
		
		catch (SQLException e) {
			System.out.println("Value of jdbcUrl: " + jdbcUrl);
			System.out.println("SQL Exception caught: " + e.getMessage());}
		
		return conn;
	}	
	
	
	public void disconnectMySQL() {
		try {conn.close();} 
		catch (Exception e) {System.out.println("SQL Exception caught: " + e.getMessage());}
	} 	

}
