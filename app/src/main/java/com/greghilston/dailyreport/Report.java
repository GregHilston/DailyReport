package com.greghilston.dailyreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a daily report a user would create for a specific project
 */
public class Report {
    private Project project; // The project this report belongs to
	private String date;
	private int headCount;
	private ArrayList<Company> companies = new ArrayList<>();
	private ArrayList<Person> people = new ArrayList<>();
	private ArrayList<Equipment> equipment = new ArrayList<>();
	private Timeline timeline;

    public Report(Project project, int headCount) {
        date = new SimpleDateFormat("MM_dd_yyyy").format(new Date());
        this.project = project;
        this.headCount = headCount;
        this.timeline = new Timeline(this);
    }

    /**
     * Generates a report
     */
    public void generateReport() {
        DocumentMaster.getInstance().createXml(this);
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

    public Project getProject() {
        return project;
    }

	public String getDate() {
		return date;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public ArrayList<Company> getCompanies() {
		return companies;
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
}
