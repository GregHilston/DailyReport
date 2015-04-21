package com.greghilston.dailyreport;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditWeatherObservationActivity extends FragmentActivity {
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
                if(MainActivity.debugeMode) {
                    System.out.println("Weather Text Observation Cancel Button Clicked!");
                }

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
                if(MainActivity.debugeMode) {
                    System.out.println("Weather Text Observation Submit Button Clicked!");
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("index", index);
                returnIntent.putExtra("date", timeEditText.getText().toString());
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
                if(MainActivity.debugeMode) {
                    System.out.println("Remove Weather Observation Button Clicked!");
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("index", index);
                setResult(RESULT_DELETE_WEATHER_OBSERVATION, returnIntent);
                finish();
            }
        });


    }

    //TODO: This is not the "Correct" way to do this but it is working
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

            TextView textView;
        TimePickerFragment(TextView textView){
            this.textView=textView;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            //DateFormat.is24HourFormat(getActivity())
            return new TimePickerDialog(getActivity(), this, hour, minute,true);
        }


        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            textView.setText(hourOfDay + ":" + minute);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment(timeEditText);
        newFragment.show(getFragmentManager(), "timePicker");
    }

}


