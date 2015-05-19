 package com.greghilston.dailyreport;

 import java.text.ParseException;
 import java.text.SimpleDateFormat;

 /***
 * Represents a weather observation being made by the user
 */
public class Weather extends Observation {
    private String currently = ""; // TODO: Change to enum?
    private float temperature = 0.0f;
    private float humidity = 0.0f;
    private float pressure = 0.0f;

     /**
      * Used when the user clicks the Weather Observation button
      *
      * @param currently
      * @param temperature
      * @param humidity
      * @param pressure
      */
     public Weather(String currently, float temperature, float humidity, float pressure) {
         super();

         this.currently = currently;
         this.temperature = temperature;
         this.humidity = humidity;
         this.pressure = pressure;
     }

     /**
      * Used when creating a Weather Observation from an XML file
      *
      * @param dateAndTime
      * @param currently
      * @param temperature
      * @param humidity
      * @param pressure
      * @param note
      */
    public Weather(String dateAndTime, String currently, float temperature, float humidity, float pressure, String note) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        try {
            this.date = formatter.parse(dateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.currently = currently;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.note = note;
    }

    /**
     * @return  the current weather
     */
    public String getCurrently() {
        return currently;
    }

    /**
     * @return  temperature
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * @return  the humidity as a percentage of 100
     */
    public float getHumidity() {
        return (1 - humidity) * 100;
    }

    /**
     * @return  pressure
     */
    public float getPressure() {
        return pressure;
    }

    /**
     * @param currently  what the weather is like currently
     */
    public void setCurrently(String currently) {
        this.currently = currently;
    }

    /**
     * @param temperature  temperature at the date of the observation
     */
    public void setTemperature(String temperature) {
        this.temperature = Float.valueOf(temperature);
    }

    /**
     * @param humidity  humidity at the date of the observation
     */
    public void setHumidity(String humidity) {
        this.humidity = Float.valueOf(humidity);
    }

    /**
     * @param pressure  pressure at the date of the observation
     */
    public void setPressure(String pressure) {
        this.pressure = Float.valueOf(pressure);
    }

    @Override
    public String toString() {
        return currently + "\nTemperature: " + temperature + "\nHumidity: " + humidity + "\nPressure: " + pressure;
    }
}
