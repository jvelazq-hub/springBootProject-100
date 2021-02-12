/*
 /*
 * @ Author: JVH Consulting
 * @   Date: Jan 1, 2021
 * @Abstract:
 *           This Interface defines the available (CRUD) operations using JDBC 
 *           when connected to the AWS provided MySQL DB. Since this is an interface
 *           no method implementation is provided. The implementation must be supplied by the 
 *           class using the interface.
 */

package com.example.demo;

public interface MySQLOperations  {
	
    //Allowed MySQL Operations Create-Read-Update-Delete (CRUD) 
	public void addDB(JobsTableEntity javaBean) throws Exception;
	public JobsTableEntity readDB(JobsTableEntity javaBean) throws Exception;// Mod this method so it returns a javabean with the results of get (now it returns void-null)
	public void updtDB(JobsTableEntity javaBean) throws Exception;
	public void delDB(JobsTableEntity javaBean) throws Exception;

}
