package com.greghilston.dailyreport;

import java.util.ArrayList;

/**
 * Represents the user's account
 */
public class Account {
	private String name;
	private String company;
	private ArrayList<Project> projects;

    public Account(String name, String company) {
        this.name = name;
        this.company = company;
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	public ArrayList getProject() {
		return projects;
	}

    public void addProject(Project p) {
        projects.add(p);
    }

    public void removeProject(Project p) {
        projects.remove(p);
    }
}
