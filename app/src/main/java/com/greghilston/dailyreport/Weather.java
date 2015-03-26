 package com.greghilston.dailyreport;

/***
 * Represents a weather observation being made by the user
 */
public class Weather extends Observation {
    private String currently = ""; // TODO: Change to enum?
    private float temperature = 0.0f;
    private float humidity = 0.0f;
    private float pressure = 0.0f;

    public Weather(String currently, float temperature, float humidity, float pressure) {
        super();
        this.currently = currently;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
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

    @Override
    public String toString() {
        return currently + "\nTemperature: " + temperature + "\nHumidity: " + humidity + "\nPressure: " + pressure;
    }
}
