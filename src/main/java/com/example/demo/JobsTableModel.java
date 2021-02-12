/*
 * @ Author: JVH Consulting

 * @   Date: Jan 1, 2021
 * @Abstract:
 *           This Class extends (inherits) from MySQLConnection & implements interface MySQLOperations  
 *           and returning it to the caller to use. This Class has been modified
 *           to use it in the AWS (Amazon Web Services Cloud) & its BeanStalk Service 
 *           to connect to a MySQL DB created using the Configuration DB or RDB parameters of
 *           AWS BeanStalk Infrastructure as a Platform. (PaaS) versus IaaS or SaaS
 *           
 *           Use the class instantiating it and using its methods (CRUD) to modify the JobsTable in MySQL
 *           The instantiation can be done in the CONTROLLER Class which receives all routing requests
 *           When you receive form related to adding Jobs then use the received data (in Model container)
 *           to set a javaBean representing a Jobs record in JOBSTABLE ENTITY and use it to connect to MySQL & CRUD the record
 *           NOTE: With Java Spring Boot you can use JPA operations. In the future Update these process to use JPA
 *           or Java Persistent API to CRUD the JobsTable in MYSQL BUT using JPA
 *           
 * @Methods:
 * @ connectToMySQL() - Uses the MySQL Driver class to establish a connection to the DB
 * @ disconectMySQL() - Closes the connection after it has been used.   
 */

package com.example.demo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


public class JobsTableModel extends MySQLConnection implements MySQLOperations {
	
	/*
	 * 	Connection & SQL Related Variables	
	 */
		
	private final String JobsBeanInsert  = "INSERT INTO JOBSTABLE VALUES( ?, ?, ?, ?)"; // TOTAL = 4  
										   
	private final String JobsBeanRead    = "SELECT TITLE, EMPLOYER, DESCRIPTION "
											  + "FROM JOBSTABLE "
											  + " WHERE ID = ? ";
	
	private final String JobsBeanReadAll = "SELECT"
			                             + " ID, "
			                             + "TITLE, "
			                             + "EMPLOYER, "
			                             + "DESCRIPTION "
			                             + "FROM JOBSTABLE ";
			  
	
	//MODIFY WITH DDS & FIELDS TO UPDATE
	private final String JobsBeanUpdt = "UPDATE JOBSTABLE SET TITLE = ?, EMPLOYER = ?, DESCRIPTION = ? "
											  + " WHERE ID = ? ";

	private final String JobsBeanCreateTable = "CREATE TABLE JOBSTABLE "
			                                 + "(ID INTEGER, "
			                                 + "TITLE VARCHAR(50), "
			                                 + "EMPLOYER VARCHAR(30), "
			                                 + "DESCRIPTION VARCHAR(100) )"; // TOTAL = 4 
	
	/**
	 * addDB method - INSERTS RECORD INTO MYSQL DB TABLE
	 */

	public void addDB(JobsTableEntity javaBean) throws Exception {
		
		System.out.println("Value f record to INSERT:INTO JOBSTABLE ID: "+javaBean.getId()+":"+javaBean.getEmployer()+":"+javaBean.getTitle() );
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("From Method:addDB:About to call connectMySQL()");
		conn  = connectToMySQL();//Method inherited from SuperClass MySQLConnection
		System.out.println("From Method:addDB:AFTER call to connectMySQL() Made");
		synchronized(this) {
		pstmt = conn.prepareStatement(JobsBeanInsert);
		pstmt.setLong(1, javaBean.getId());
		pstmt.setString(2, javaBean.getTitle());
		pstmt.setString(3, javaBean.getEmployer());
		pstmt.setString(4, javaBean.getDescription());
		
				
		try{
			pstmt.executeUpdate();//INSERT RECORD IN DB2400
			System.out.println("Record inserted into JOBSTABLE: ");
		}
		catch (Exception e) {
			System.out.println("Error on insert to JobsTable " + e.getMessage());
		}
		}//End Synch
		
		disconnectMySQL();
	}
	
	
	/*
	 * Read a record from Table Method
	 */
		public JobsTableEntity readDB(JobsTableEntity javaBean) throws Exception {
		
		System.out.println("Value for record to READ:FROM JOBSTABLE ID: "+javaBean.getId()+":"+javaBean.getEmployer()+":"+javaBean.getTitle() );
		Connection conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("From Method:readDB:About to call connectMySQL()");
		conn = connectToMySQL();
		System.out.println("After invoking method:readDB to call connectMySQL() Made");
			
		//MUST UPDATE ACCORDINGLY	
		synchronized(this) {
			
		pstmt = conn.prepareStatement(JobsBeanRead);
		pstmt.clearParameters();
		System.out.println("Id value received by invocation to readDB method in JobsTableModel: " + javaBean.getId() );
		
		pstmt.setInt(1, javaBean.getId());   //Key Field 1
		
		
		try{
			rs = pstmt.executeQuery();
			//Populate javaBean with JobsTable get results
			while(rs.next()){
				javaBean.setTitle(rs.getString(1));//Field 1 value in SELECT
				javaBean.setEmployer(rs.getString(2));//Field 2 value
				javaBean.setDescription(rs.getString(3));//Field 3 value
				System.out.println("INSIDE rs.next() loop of readDB method " );
				System.out.println("Value for record READ:FROM JOBSTABLE ID: "+javaBean.getId()+":"+javaBean.getEmployer()+":"+javaBean.getTitle() );
				int num_records_read =+ 1;
				System.out.println("No of records read: " + num_records_read); 
			}
		}
		catch (Exception e) {System.out.println("Error on READ to JobsTable readDB operation JobsTableBean ENTITY - " + e.getMessage());	}

		}//End Synch
		
		disconnectMySQL();
		
		return javaBean;
					
	}//End READ Method		
	
	/*
	 * Update Table Method
	 */
	public void updtDB(JobsTableEntity javaBean) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("From Method:updtDB:About to call connectToDb400()");
		conn = connectToMySQL();
		System.out.println("From Method:updtDB:call connectToDb400() Made");
		synchronized(this) {
			pstmt = conn.prepareStatement(JobsBeanUpdt);
			pstmt.setDouble(4, javaBean.getId());//KeyField Field1 in Table
			
			pstmt.setString(2, javaBean.getTitle());//Field2 
			pstmt.setString(3, javaBean.getEmployer());//Field3
			pstmt.setString(4, javaBean.getDescription());//Field4
						
		try{
			pstmt.executeUpdate();//UPDATE RECORD IN DB2400
		}
		catch (Exception e) {
			System.out.println("Error on UPDATE to COTIRL30-Detail Record " + e.getMessage());
		}
		
		}//End Synch 
		
		disconnectMySQL();
	}
	
	/*
	 * Delete Method
	 */
	public void delDB(JobsTableEntity javaBean) throws Exception {}
	
	/*
	 * Create Table Method
	 */
	public void createMySQLTable() throws Exception {
		
		Connection conn = null;
		Statement stmt = null;
		conn = connectToMySQL();
		stmt = conn.createStatement();
		try {stmt.executeUpdate(JobsBeanCreateTable);}
		catch (Exception e) {System.out.println("Error on Create Table " + e.getMessage());}
		
		
	}
	
	/*
	 * findAllJobs() Method - Read the MySQL DB JOBSTABLE records and add return a Vector containing all job records
	 * the Vector will be used by the controller to populate the model(container) with the addModelAttribute method prior to sending to the view 
	 *  
	 */
	
	public Vector findAllJobs() throws Exception {
		
		Connection conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector <JobsTableEntity> jobVector = new Vector<JobsTableEntity>();//ESTA ES LA NUEVA MANERA DE DECLARAR UN VECTOR!!!!! SIMILAR A SETS Y LIST
		
		System.out.println("From Method:findAllJobs:About to call connectMySQL()");
		conn = connectToMySQL();
		System.out.println("After invoking connectMySql method: within the findAllJobs method");
		
		if (conn != null) {
			
			//MUST UPDATE ACCORDINGLY	
			synchronized(this) {
			
				pstmt = conn.prepareStatement(JobsBeanReadAll);
				pstmt.clearParameters();
				int num_records_read =0;
				try{
					rs = pstmt.executeQuery();
					//Populate javaBean with JobsTable get results
					while(rs.next()){
				
						JobsTableEntity job = new JobsTableEntity();//instance of Job class will contain data from job record read from JOBSTABLE 
                
						//Populate the job instance
						job.setId(rs.getInt("ID"));//Field 1 value in SELECT
						job.setTitle(rs.getString("TITLE"));//Field 2 value in SELECT
						job.setEmployer(rs.getString("EMPLOYER"));//Field 3 value in SELECT
						job.setDescription(rs.getString("DESCRIPTION"));//Field 4 value in SELECT
				
						//Now add the populated job record read from MySQL JOBSTABLE to the Jobs Vector 
						jobVector.add(job);
			
						System.out.println("INSIDE rs.next() loop of findAllJobs method " );
						System.out.println("Value for record READ:FROM JOBSTABLE ID: "+job.getId()+":"+job.getEmployer()+":"+job.getTitle() );
						num_records_read = num_records_read + 1;
						System.out.println("No of records read in rs.next loop of findAllJobs: " + num_records_read); 
					}
				}
				catch (Exception e) {System.out.println("Error on READ to JobsTable readDB operation JobsTableBean ENTITY - " + e.getMessage());	}

			}//End Synch
		
		}//End if not null
		
		disconnectMySQL();
		
		return jobVector;
					
	}//End READ Method		
	

}//End Class
