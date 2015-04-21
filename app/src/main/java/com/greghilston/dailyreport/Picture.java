 package com.greghilston.dailyreport;

 import java.text.ParseException;
 import java.text.SimpleDateFormat;

 /***
 * Represents a picture observation being made by the user
 */
public class Picture extends Observation {
     String picturePath = "";
     String pictureName = "";

     public Picture(String pictureName, String picturePath) {
         super();
         this.pictureName = pictureName;
         this.picturePath = picturePath;
     }

     /**
      * Used when creating a Weather Observation from an XML file
      * @param time
      * @param picName
      * @param picPath
      * @param note
      */
     public Picture(String time, String picName, String picPath, String note) {
         super();

         SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

         try {
             this.date = formatter.parse(time);
         } catch (ParseException e) {
             e.printStackTrace();
         }

         this.pictureName = picName;
         this.picturePath = picPath;
         this.note = note;
     }

     public String getPictureName() {
         return pictureName;
     }

     public void setPictureName(String pictureName) {
         this.pictureName = pictureName;
     }

     public String getPicturePath() {
         return picturePath;
     }

     public void setPicturePath(String picturePath) {
         this.picturePath = picturePath;
     }
 }
