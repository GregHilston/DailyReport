package com.greghilston.dailyreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a daily report a user would create for a specific project
 */
public class Report {
    protected Project project; // The project this report belongs to
    protected String accountName; // Used when generating report objects, as no account/project obj is made
    protected String companyName; // Used when generating report objects, as no account/project obj is made
    protected String projectName; // Used when generating report objects, as no account/project obj is made
    protected String date = new SimpleDateFormat("MM_dd_yyyy").format(new Date());
    protected ArrayList<Person> people = new ArrayList<>(); // People that work for manager
    protected ArrayList<Company> companies = new ArrayList<>();
    protected ArrayList<Equipment> equipment = new ArrayList<>();
    protected ArrayList<Observation> observations = new ArrayList<>();

    /**
     * Creates a report. Used when created a report from an XML file
     */
    public Report() {
        // Left intentionally blank
    }

    /**
     * Creates a report, given a project. Used when user makes a new report
     *
     * @param project
     */
    public Report(Project project) {
        this.project = project;
        accountName = project.getAccount().getName();
        companyName = project.getAccount().getCompany();
        projectName = project.getProjectName();
    }

    /**
     * Adds a company to the list of companies for this report
     * @param c company to add
     */
    public void addCompany(Company c) {
        companies.add(c);
    }

    /**
     * Adds a person to the list of people for this report
     * @param p person to add
     */
    public void addPerson(Person p) {
        people.add(p);
    }

    /**
     * Adds a equipment to the list of equipment for this report
     * @param e equipment to add
     */
    public void addEquipment(Equipment e) {
        equipment.add(e);
    }

    /**
     * Adds an observation to the timeline for this report
     * @param o observation to add
     */
    public void addObservation(Text o) {
        observations.add(o);
    }

    /**
     * Prints a friendly overview of the report to Standard Out
     */
    public void printReport() {
        System.out.println("Account Name: " + this.accountName);
        System.out.println("Company Name: " + this.companyName);

        System.out.println("Project Name: " + this.projectName);
        System.out.println("Date: " + this.date);

        System.out.println("Head Count: " + this.people.size());
        System.out.println("Company Count: " + this.companies.size());
        System.out.println("Equipment Count: " + this.equipment.size());
        System.out.println("Observation Count: " + this.observations.size());

        System.out.println("People:");
        for(int i = 0; i < this.people.size(); i++) {
            System.out.println("\t" + this.people.get(i).getName());
            System.out.println("\t" + this.people.get(i).getHoursOnSite());
        }

        System.out.println("Companies:");
        for(int i = 0; i < this.companies.size(); i++) {
            System.out.println("\t" + this.companies.get(i).getName());
            System.out.println("\t" + this.companies.get(i).getQuantity());
        }

        System.out.println("Equipment:");
        for(int i = 0; i < this.equipment.size(); i++) {
            System.out.println("\t" + this.equipment.get(i).getName());
            System.out.println("\t" + this.equipment.get(i).getQuantity());
        }

        System.out.println("Observations:");
        for(int i = 0; i < this.observations.size(); i++) {
            System.out.println("\t" + this.observations.get(i).generateTime());

            if(this.observations.get(i) instanceof Text) {
                System.out.println("\t" + ((Text) this.observations.get(i)).getText());
            }

            System.out.println("\t" + this.observations.get(i).getNote());
        }

        System.out.println("\n");
    }

    public Project getProject() {
        return project;
    }

	public String getDate() {
		return date;
	}


    /**
     * File naming convention is just the report's date
     *
     * @return filename
     */
    public String getFileName() {
        return getDate();
    }

	public int getHeadCount() {
		return people.size();
	}

    public int getCompanyCount() {
        return companies.size();
    }

    public int getEquipmentCount() {
        return equipment.size();
    }

    public int getObservationsCount() {
        return observations.size();
    }

	public ArrayList<Company> getCompanies() {
		return companies;
	}

    public ArrayList<Observation> getObservations() {
        return observations;
    }

	public void setCompanies(ArrayList<Company> companies) {
		this.companies = companies;
	}

	public ArrayList<Person> getPeople() {
		return people;
	}

	public void setPeople(ArrayList<Person> people) {
		this.people = people;
	}

	public ArrayList<Equipment> getEquipment() {
		return equipment;
	}

	public void setEquipment(ArrayList<Equipment> equipment) {
		this.equipment = equipment;
	}

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
