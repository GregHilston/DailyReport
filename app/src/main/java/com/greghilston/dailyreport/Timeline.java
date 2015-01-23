 package com.greghilston.dailyreport;

 import java.util.ArrayList;

 /**
 * Represents an entire day of observations
 */
public class Timeline{
    private Report report; // The report this timeline belongs to
    private ArrayList<Observation> observations = new ArrayList<Observation>();

    public Timeline(Report report) {
        this.report = report;
        observations.add(new Text("Arrived on site"));
    }

     public ArrayList<Observation> getObservations() {
         return observations;
     }
}
