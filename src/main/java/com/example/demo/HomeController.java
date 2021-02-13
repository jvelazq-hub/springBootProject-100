package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

//import javax.validation.Valid;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	int key = 100;
	
	//@Autowired
	//JobRepository jobRepository;
	
	/*
	 * switch on "/" or default route: http://localhost:8080/
	 * set the container (model) to contain a vector of jobs 
	 * then route or dispatch form: "list.html" to display the 
	 * jobs vector 
	 */
	
	@RequestMapping("/")
	public String listJobs(Model model) {
		
		//FOR TESTING ONLY - COMMENT OUT AND LET CONNECTIONS BE DONE 
		//BY THE METHODS INVOKED IN THE /PROCESS ROUTE OR MAP 
		//System.out.println("About to call the getRenoteConnection() method");
		//Connection conn = this.getRemoteConnection();
		//System.out.println("After call to the getRenoteConnection() method");
		
		//model.addAttribute("jobs", jobRepository.findAll()); I BELIEVE USED WITH JPA WHICH IS NO MORE
		
				
		/*
		 *  Use the findAllJobs() Method of JobsTableModel --> to create vector of ALL jobs in MySQL & return it to HomeController 
		 *  Define a Vector which will contain the output of the findAllJobs() method (a vector of type JobsTableEntity) 
		 *  Instantiate the JobSTableEntity class since the method will return a vector with the JobsTableEntity class jobs as its elements
		 * Invoke the findAllJobs method() which returns a vector of type JobsTableEntity of ALL job records in MySQL JOBSTABLE
		 * FIRST: Instantiate the Table Model (jobsTableModel) so the findAllJobs method can be invoked
		 * SECOND: add the new Vector attribute to the model (container) prior to dispatching to the view 
		 */
		 
		Vector <JobsTableEntity> jobVector = new Vector<JobsTableEntity>();//ESTA ES LA NUEVA MANERA DE REFERIRSE A LOS VECTORES!!!!!! SIMILAR A SETS Y LISTS
		JobsTableModel jobsTableModel = new JobsTableModel();
		
		try {
			
			jobVector = jobsTableModel.findAllJobs();// equivalent instruction model.addAttribute("jobsV", jobsTableModel.findAllJobs());
			model.addAttribute("jobsV", jobVector);//So I can pass it to the view via the model!!!! In the view reference the object using "jobsV" 
		    System.out.println("From routing step / (initial) invoked the findAllJobs method and added records to th model ");	
		    } 
			
			catch(Exception e) { System.out.println("Exception from invocation of findAllJobs() from within the / Request Mapping in Controller");
		    System.out.println("Exception from invocation of findAllJobs() due to null connectivity to MySQL");
		 }
		
		
		return "list";
		
	}
	 
	/*
	 * Switch on "/add" (the link--> <href="/add" in list.html) then set the container (model) to contain 
	 * a single EMPTY job class which has been instantiated (although not yet populated). The model will be forwarded
	 * to the view ("jobform.html") so the job instance can be populated prior to inserting into h2 DB
	 */
	@GetMapping("/add")
	public String jobForm(Model model) {
		model.addAttribute("job", new Job());
		return "jobform";
		
	}
	
	/*
	 * Switch on "/process" (POST request from jobform.html) then use the @Valid annotation in conjunction with the BindingResult obj to verify
	 * if entry errors exist. If they do return to "jobform.html", otherwise use method (.save) in the jobrepository instance to save the Job Entity 
	 * into the h2 in memory DB. 
	 */
	@PostMapping("/process")
	//public String processForm(@Valid Job job, BindingResult result) {
	public String processForm(Job job, BindingResult result) {
		if (result.hasErrors()) {
			return "jobform";
		}
		System.out.println("For EMPLOYER= "  + job.getEmployer() + " DESCRIPTION= " + job.getDescription());
		
		/*
		 * Instantiate the Table Model (JobsTableBean) & Create TABLE JOBSTABLE
		 */
		 
		JobsTableModel jobsTableModel = new JobsTableModel();
		
		//Since Table already created SKIP/COMMENT this section 
		//try {
		//	jobsTableBean.connectToMySQL();
		//	//For Create Table First Time
		//	jobsTableBean.createMySQLTable();
		//  jobsTableBean.disconnectMySQL(); 
		//}
		//catch (Exception e) {
		//	System.out.println("Exception on Connection from Controller process mapping:" + e.getMessage());
		//}
		
		/*
		 * After TABLE created do the INSERT operation to create job records in table
		 *
		 */
		
		JobsTableEntity jobBean = new JobsTableEntity();
		//Set values to INSERT
		key += 5;
		jobBean.setId(key);
		jobBean.setTitle(job.getTitle());
		jobBean.setEmployer(job.getEmployer());
		jobBean.setDescription(job.getDescription());
		
		try { jobsTableModel.addDB(jobBean);	
			  jobsTableModel.disconnectMySQL(); 	
		}
		catch (Exception e) {System.out.println("Exception on addDB from Controller process mapping:" + e.getMessage());} 
		
		
		
		/*
		 * Fetch record just added to verify it was inserted
		 */
		
		try { jobsTableModel.readDB(jobBean);
		
			System.out.println("For Job ID= " + job.getId() + " TITLE= "  + job.getTitle() + " EMPLOYER= "  + job.getEmployer() + " DESCRIPTION= " + job.getDescription());
		
		}
		catch (Exception e) {System.out.println("Exception on readDB from Controller process mapping:" + e.getMessage());} 
		
		
		//Disconnect from DB once Operation Finished --> no Java Leaks Garbage_Collector
		try {jobsTableModel.disconnectMySQL();}
		catch (Exception e) {System.out.println("Exception on disconnectMySQL() from Controller process mapping:" + e.getMessage());} 
		
		//jobRepository.save(job); 
		return "redirect:/";
	}
	
	/*
	 * getRemoteConnection() Method for TESTING - UNCOMMENT
	 */
	private static Connection getRemoteConnection() {
        
		//if (System.getenv("RDS_HOSTNAME") != null) {
		try {
		
		Class.forName("com.mysql.jdbc.Driver");
		String dbName = System.getenv("RDS_DB_NAME");
		String userName = System.getenv("RDS_USERNAME");//jvhdba
		String password = System.getenv("RDS_PASSWORD");//passw0rd
		String hostname = System.getenv("RDS_HOSTNAME");
		String port = System.getenv("RDS_PORT");
		String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?" + "user=" + userName + "&password=" + password;
		System.out.println("Getting remote connection with connection string from environment variables");
		
		Connection con = DriverManager.getConnection(jdbcUrl);
		System.out.println("Remote connection successful.");
		return con;
		}
		catch (ClassNotFoundException e) { System.out.println(e.toString());}
		catch (Exception e) { System.out.println(e.toString());}
		//}
		return null;
		}
	
	/*
	 * Method added as example of rendering an existing pdf file in the resource directory (same as application.property)
	
	@RequestMapping("/")
	public ResponseEntity<InputStreamResource> getDemoPDF() throws IOException {
		
		String Pdf="Kubernetes.pdf";//May retrieve name as a result of a Callable Statement to OS400 BUT: what about the location of the file???
		System.out.println("inside the getDemoPDF method");
        
        ClassPathResource pdf = new ClassPathResource(Pdf);
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        
        headers.add("content-disposition", "inline;filename=Test.pdf");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(pdf.contentLength())
                .body(new InputStreamResource(pdf.getInputStream()));
    }
	*/
	
}
