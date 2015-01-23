package com.greghilston.dailyreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a daily report a user would create for a specific project
 */
public class Report {
	private String date;
	private int headCount;
	private ArrayList<Company> companies;
	private ArrayList<Person> people;
	private ArrayList<Equipment> equipment;
	private Timeline timeline;

    public Report(int headCount) {
        date = new SimpleDateFormat("MM_dd_yyyy").format(new Date());
        this.headCount = headCount;
        this.timeline = new Timeline();
    }

	public String getDateTime() {
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
