package com.greghilston.dailyreport;

/**
 * A class that has a notes field. Extended by many observations
 */
public class Notable {
    String note = "";

    String getNote() {
        return note;
    }

    void setNote(String note) {
        this.note = note;
    }
}
