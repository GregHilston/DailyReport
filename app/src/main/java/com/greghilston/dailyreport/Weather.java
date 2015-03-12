 package com.greghilston.dailyreport;

/***
 * Represents a weather observation being made by the user
 */
public class Weather extends Observation {
    private float temp = 0.0f;
    private float humidity = 0.0f;
    private Type type;

    public enum Type {
        Sunny, Rainy, Cloudy
    }

    public Weather(float temp, float humidity, Type type) {
        super();
        this.temp = temp;
        this.humidity = humidity;
        this.type = type;
    }

    /**
     * @return the humidity as a percentage of 100
     */
    public float getHumidity() {
        return (1 - humidity) * 100;
    }

    /**
     * @return  temperature
     */
    public float getTemp() {
        return temp;
    }

    /**
     * @return type (enum)
     */
    public Type getType() {
        return type;
    }
}
