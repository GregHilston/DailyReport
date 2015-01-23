 package com.greghilston.dailyreport;

 /***
 * Represents a text observation being made by the user
 */
public class Text extends Observation {
     private String text = "";

     public Text(String s) {
         super();
         text = s;
     }

     @Override
     public String toString() {
         return text + "\n" + "Note: " + note;
     }

     public String getText() {
         return text;
     }
 }
