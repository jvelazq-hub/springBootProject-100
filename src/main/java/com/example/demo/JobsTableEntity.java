package com.example.demo;



/*
 * Entity annotation indicates a class that will be persistent (stored)
 */

public class JobsTableEntity {
	
	
	private int id;	
	private String title;
	private String employer;
	private String description;
	
	/*
	 * Getters/Setters to read/write to MySQL DB 
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
