 package com.greghilston.dailyreport;

 /***
 * Represents a text observation being made by the user
 */
public class Text extends Observation {
     private String text = "";

     /**
      * Used when making a live obersation for a report, as the time is gotten for you
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
      * @param time the time the text observation was made
      * @param text the observation itself
      * @param note any note
      */
     public Text(String time, String text, String note) {
         this.time = time;
         this.text = text;
         this.note = note;
     }

     /**
      * @return text for this text observation
      */
     public String getText() {
         return text;
     }
 }
