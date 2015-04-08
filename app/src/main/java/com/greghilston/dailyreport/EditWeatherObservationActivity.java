package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditWeatherObservationActivity extends Activity {
    Button submitButton;
    Button cancelButton;
    Button removeButton;
    EditText timeEditText;
    EditText currentlyEditText;
    EditText temperatureEditText;
    EditText humidityEditText;
    EditText pressureEditText;
    Observation observation; // Observation to be edited by the user
    Weather weather;
    int index;
    public static final int RESULT_DELETE_WEATHER_OBSERVATION   = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_weather_observation);

        submitButton = (Button) findViewById(R.id.button2);
        cancelButton = (Button) findViewById(R.id.button);
        removeButton = (Button) findViewById(R.id.button3);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        currentlyEditText = (EditText) findViewById(R.id.currentlyEditText);
        temperatureEditText = (EditText) findViewById(R.id.temperatureEditText);
        humidityEditText = (EditText) findViewById(R.id.humidityEditText);
        pressureEditText = (EditText) findViewById(R.id.pressureEditText);

        observation = (Observation) getIntent().getSerializableExtra("observation");
        index = (int) getIntent().getSerializableExtra("index");
        weather = (Weather) observation;

        timeEditText.setText(weather.getTime());
        currentlyEditText.setText(weather.getCurrently());
        temperatureEditText.setText(Float.toString(weather.getTemperature()));
        humidityEditText.setText(Float.toString(weather.getHumidity()));
        pressureEditText.setText(Float.toString(weather.getPressure()));

        /**
         * Cancels the creation of the Weather Observation, returning to the MainActivity
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("Weather Text Observation Cancel Button Clicked!");

                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        /**
         * Returns the text entered by the user so a Weather Observation can be created
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("Weather Text Observation Submit Button Clicked!");

                Intent returnIntent = new Intent();
                returnIntent.putExtra("index", index);
                returnIntent.putExtra("time", timeEditText.getText().toString());
                returnIntent.putExtra("currently", currentlyEditText.getText().toString());
                returnIntent.putExtra("temperature", temperatureEditText.getText().toString());
                returnIntent.putExtra("humidity", humidityEditText.getText().toString());
                returnIntent.putExtra("pressure", pressureEditText.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        /**
         * Removes the current index
         */
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("Remove Weather Observation Button Clicked!");

                Intent returnIntent = new Intent();
                returnIntent.putExtra("index", index);
                setResult(RESULT_DELETE_WEATHER_OBSERVATION, returnIntent);
                finish();
            }
        });
    }
}


