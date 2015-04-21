package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Represents a daily report a user would create for a specific project
 */
public class Report {
    protected Project project; // The project this report belongs to
    protected String companyName; // Name of company that this report is for
    protected String projectName; // Name of project that this company is running
    protected String date = new SimpleDateFormat("MM_dd_yyyy").format(new Date());
    protected ArrayList<Person> people = new ArrayList<>(); // People that work for manager
    protected ArrayList<Company> companies = new ArrayList<>();
    protected ArrayList<Equipment> equipment = new ArrayList<>();
    protected ArrayList<Observation> observations = new ArrayList<Observation>();
    public static final int RESULT_CANCELED    = 0;
    public static final int RESULT_OK           = -1;
    private String individualReportFolderPath = DocumentMaster.reportDirPhone.getPath() + File.separator + getFileName();
    private String xmlFilePath;
    private int pictureCount = 0; // How many pictures have been taken for this report (also used for naming pictures)

    /**
     * Creates a report. Used when created a report from an XML file
     */
    public Report() {
        observations.add(new Text("Arrived on site"));
    }

    /**
     * Creates a report, given a project. Used when user makes a new report
     *
     * @param project
     */
    public Report(Project project) {
        this(); // Calls the above constructor, ensuring our first observation is made
        this.project = project;
        companyName = project.getCompanyName();
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
    public void addObservation(Observation o) {
        observations.add(o);
    }

    /**
     * Removes the Observation form the timeline at inde xi
     * @param index index of Observation to remove
     * @return removed observation
     */
    public Observation removeObservation(int index) {
        return observations.remove(index);
    }

    /**
     * Prints a friendly overview of the report to Standard Out
     */
    public void printReport() {
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
            else if(this.observations.get(i) instanceof Weather) {
                System.out.println("\t" + ((Weather) this.observations.get(i)).getCurrently());
                System.out.println("\t" + ((Weather) this.observations.get(i)).getTemperature());
                System.out.println("\t" + ((Weather) this.observations.get(i)).getHumidity());
                System.out.println("\t" + ((Weather) this.observations.get(i)).getPressure());
            }
            else if(this.observations.get(i) instanceof Picture) {
                System.out.println("\t" + ((Picture) this.observations.get(i)).getPictureName());
                System.out.println("\t" + ((Picture) this.observations.get(i)).getPicturePath());
            }

            System.out.println("\t" + this.observations.get(i).getNote());
        }

        System.out.println("\n");
    }

    /**
     * Transcribes the Report's timeLine to the GUI
     * TODO: See if this is the correct thing to do, or the correct place to do it
     */
    public void reportToGui(LinearLayout ll) {
        System.out.println("reportToGui");

        Collections.sort(observations, new Comparator<Observation>() {
            public int compare(Observation o1, Observation o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        ll.removeAllViews();

        for(int i = 0; i < getObservationsCount(); i++) {
            Observation o = getObservations().get(i);

            if(o instanceof Text) {
                TextView textView = new TextView(MainActivity.context);

                final int finalI = i;
                final Observation finalO = o;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nextScreen = new Intent(MainActivity.context, EditTextObservationActivity.class);
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   // The SingleTop - makes it act Modal (from : http://mono-for-android.1047100.n5.nabble.com/OnActivityResult-not-being-called-td4802915.html)
                        nextScreen.putExtra("observation", finalO);
                        nextScreen.putExtra("index", finalI);
                        ((Activity)MainActivity.context).startActivityForResult(nextScreen, 2);
                    }
                });

                textView.setText(o.getTime() + ": " + ((Text) o).getText() + "\n");
                ll.addView(textView);
            }
            else if(o instanceof Weather) {
                TextView textView = new TextView(MainActivity.context);

                final int finalI = i;
                final Observation finalO = o;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nextScreen = new Intent(MainActivity.context, EditWeatherObservationActivity.class);
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   // The SingleTop - makes it act Modal (from : http://mono-for-android.1047100.n5.nabble.com/OnActivityResult-not-being-called-td4802915.html)
                        nextScreen.putExtra("observation", finalO);
                        nextScreen.putExtra("index", finalI);
                        ((Activity)MainActivity.context).startActivityForResult(nextScreen, 3);
                    }
                });

                textView.setText(o.getTime() + ": " + ((Weather) o).toString() + "\n");
                ll.addView(textView);
            }
            else if(o instanceof Picture) { // TODO
                TextView textView = new TextView(MainActivity.context);
                textView.setText(o.getTime() + ": " + ((Picture) o).getPictureName() + "\n");
                ll.addView(textView);

                ImageView imageView = new ImageView(MainActivity.context);
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 2; // Fixes OutOfMemory Exceptions for working with Bitmaps
                Bitmap bmp = BitmapFactory.decodeFile(((Picture) o).getPicturePath(), options);
                imageView.setImageBitmap(bmp);
                ll.addView(imageView);
            }
        }
    }

    /**
     * @return  this report's project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @return  the date this report was made
     */
	public String getDate() {
		return date;
	}

    /**
     * @return filename (convention is just the report's date)
     */
    public String getFileName() {
        return getDate();
    }

    /**
     * @return  the headcount
     */
	public int getHeadCount() {
		return people.size();
	}

    /**
     * @return  the company count
     */
    public int getCompanyCount() {
        return companies.size();
    }

    /**
     * @return  the equipment count
     */
    public int getEquipmentCount() {
        return equipment.size();
    }

    /**
     * @return  the observation count
     */
    public int getObservationsCount() {
        return observations.size();
    }

    /**
     * @return  list of companies for this report
     */
	public ArrayList<Company> getCompanies() {
		return companies;
	}

    /**
     * @return  list of observations for this report (also being called the "timeline")
     */
    public ArrayList<Observation> getObservations() {
        return observations;
    }

    /**
     * @return  list of people for this report
     */
	public ArrayList<Person> getPeople() {
		return people;
	}

    /**
     * @return  list of equipment for this report
     */
	public ArrayList<Equipment> getEquipment() {
		return equipment;
	}

    /**
     * @return  the company name for the report
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName  the company name for this report
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return  the project name for this report
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName  the name of the project for this report
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @param date  the date for this report
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @param project  the project for this report
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @param individualReportFolderPath  path for this reports folder
     */
    public void setIndividualReportFolderPath(String individualReportFolderPath) {
        System.out.println("setIndividualReportFolderPath: " + individualReportFolderPath);

        this.individualReportFolderPath = individualReportFolderPath;
        setXmlFilePath(individualReportFolderPath + File.separator + getFileName() + ".xml");
    }

    /**
     * @return  path for this reports folder
     */
    public String getIndividualReportFolderPath() {
        return individualReportFolderPath;
    }

    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public String getXmlFilePath() {
        return this.xmlFilePath;
    }

    public int getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(int pictureCount) {
        this.pictureCount = pictureCount;
    }
}
