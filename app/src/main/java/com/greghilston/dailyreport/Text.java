package com.greghilston.dailyreport;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a text observation being made by the user
 */
public class Text extends Observation {
    private String text = "";

    /**
     * Used when making a live obersation for a report, as the date is gotten for you
     *
     * @param s
     */
    public Text(String s) {
        super();
        text = s;
    }

    /**
     * Used when creating a report from an XML document
     *
     * @param dateAndTime the date and time the text observation was made
     * @param text        the observation itself
     * @param note        any note
     */
    public Text(String dateAndTime, String text, String note) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        try {
            this.date = formatter.parse(dateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.text = text;
        this.note = note;
    }

    /**
     * @return text for this text observation
     */
    public String getText() {
        return text;
    }

    /**
     * sets the Text's text
     */
    public void setText(String text) {
        this.text = text;
    }
}
