 package com.greghilston.dailyreport;

/***
 * Represents a weather observation being made by the user
 */
public class Weather extends Observation {
    private float temp = 0.0f;
    private float humidity = 0.0f;
    private Type type;

    public Weather(float temp, float humidity, Type type) {
        super();
        this.temp = temp;
        this.humidity = humidity;
        this.type = type;
    }

    public enum Type {
        Sunny, Rainy, Cloudy
    }

    /**
     * Get humidity as a percentage of 100
     *
     * @return the humidity
     */
    public float getHumidity() {
        return (1 - humidity) * 100;
    }

    public float getTemp() {
        return temp;
    }

    public Type getType() {
        return type;
    }
}
