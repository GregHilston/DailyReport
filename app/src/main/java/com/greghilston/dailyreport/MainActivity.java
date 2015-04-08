package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIO;
import com.greghilston.dailyreport.ForecastIOLibrary.src.com.arcusweather.forecastio.ForecastIOResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class MainActivity extends Activity {
    Project project = new Project("Construction", "ACME");
    final Report r = new Report(project);
    public LinearLayout linearLayout;
    public Context context = this;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.timeLine);
        textView = new TextView(getApplicationContext());

        r.reportToGui(linearLayout, this);

        final Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent camScreen = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(camScreen);
            }
        });

        /**
         * For testing purposes, should just create an observation for the current weather of Durham
         */
        final Button weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Changing this to open up txt text screen
                String API_KEY = "cbbd1fc614026e05d5429175cdfb0d10";
                Double Lat = 43.1339;
                Double Lang = 70.9264;

                //Set the API key, Lat, and Lang
                ForecastIO forecast = new ForecastIO(API_KEY,Lat, Lang);

                //Set the request parameters
                //ability to set the units, exclude blocks, extend options and user agent for the request. This is not required.
                HashMap<String, String> requestParam = new HashMap<String, String>();
                requestParam.put("units", "us");
                requestParam.put("userAgent", "Custom User Agent 1.0");
                forecast.setRequestParams(requestParam);
                forecast.makeRequest();

                String responseString = forecast.getResponseString();
                ForecastIOResponse FIOR = new ForecastIOResponse(responseString);

                //Retreive the current weather conditions
                String currently = FIOR.getCurrently().getValue("summary");
                float temperature = Float.parseFloat(FIOR.getCurrently().getValue("temperature"));
                float humid = Float.parseFloat(FIOR.getCurrently().getValue("humidity"));
                float pressure = Float.parseFloat(FIOR.getCurrently().getValue("pressure"));

                //Humidity is given in a float that ranges from 0-1 inclusive
                //Change to a percentage out of 100
                float relativeHumid = Float.parseFloat(Float.toString(humid));
                relativeHumid = relativeHumid*100;

                // TODO: Change what is returned? Or let the user choose
                //Add the weather observation
                String weatherResult = "Currently: " + currently + "\n"
                                    +"Temperature: " + temperature +"°F"+ "\n"
                                    +"Humidity: " + relativeHumid + "%" + "\n"
                                    +"Pressure: " + pressure + " millibar" + "\n";
                r.addObservation(new Weather(currently, temperature, relativeHumid, pressure));
                r.reportToGui(linearLayout, context);
            }
        });

        final Button noteButton = (Button) findViewById(R.id.noteButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), TextObservationActivity.class);
                startActivityForResult(nextScreen, 1);
            }
        });
    }

    /**
     * Opens an intent for emailing
     * @param filePath  file attached in the email intent
     */
    public void emailFile(String filePath) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "Grehgh@gmail.com");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Sent usng Daily Report App");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Daily Report File");
        emailIntent.setType("application/image");

        Uri uri = Uri.parse("file://" + filePath);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(emailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if (id == R.id.create_xml) {
            System.out.println("Generating a XML Document");
            String filePath = DocumentMaster.getInstance().createXml(r, getApplicationContext().getExternalFilesDir(null));
            System.out.println("filePath: " + filePath);

            emailFile(filePath);
        }
        else if (id == R.id.create_csv) {
            System.out.println("Generating a CSV Document");
            String filePath = DocumentMaster.getInstance().createCsv(r, getApplicationContext().getExternalFilesDir(null));
            System.out.println("filePath: " + filePath);

            emailFile(filePath);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");

        if (requestCode == 1) {
            System.out.println("\t requestCode: 1");
            System.out.println("\t resultCode: " + resultCode);

            if(resultCode == RESULT_OK) {
                String result =  data.getStringExtra("result");

                if(result != "") { // Do not make an observation for an empty string
                    System.out.println("\t\tText Observation Returned: " + result);
                    r.addObservation(new Text(result));
                    r.reportToGui((LinearLayout) findViewById(R.id.timeLine), this);
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                System.out.println("\t\tText Observation: Cancelled!");
            }
        }
        else if (requestCode == 2) {
            System.out.println("\t requestCode: 2");
            System.out.println("\t resultCode: " + resultCode);

            if(resultCode == RESULT_OK) {
                System.out.println("\t\tEdit Text Observation: changes made!");

                int index = data.getIntExtra("index", 0);
                String time = data.getStringExtra("time");
                String t =  data.getStringExtra("text");

                System.out.print(time);
                System.out.print(t);

                Text text = (Text) r.getObservations().remove(index);
                text.setTime(time);
                text.setText(t);
                r.getObservations().add(index, text);
            }
            else if (resultCode == RESULT_CANCELED) {
                System.out.println("\t\tEdit Text Observation: Cancelled!");
            }
            else if (resultCode == EditTextObservationActivity.RESULT_DELETE_TEXT_OBSERVATION){
                System.out.println("Removing Text Observation");
                r.getObservations().remove(data.getIntExtra("index", 0));
            }
            else if (resultCode == EditWeatherObservationActivity.RESULT_DELETE_WEATHER_OBSERVATION){
                System.out.println("Removing Weather Observation");
                r.getObservations().remove(data.getIntExtra("index", 0));
            }
        }
        else if (requestCode == 3) {
            System.out.println("\t requestCode: 3");
            System.out.println("\t resultCode: " + resultCode);

            if(resultCode == RESULT_OK) {
                System.out.println("\t\tWeather Observation: changes made!");

                int index = data.getIntExtra("index", 0);
                String time = data.getStringExtra("time");
                String currently = data.getStringExtra("currently");
                String temperature = data.getStringExtra("temperature");
                String humidity = data.getStringExtra("humidity");
                String pressure = data.getStringExtra("pressure");

                Weather weather = (Weather) r.getObservations().remove(index);

                weather.setTime(time);
                weather.setCurrently(currently);
                weather.setTemperature(temperature);
                weather.setHumidity(humidity);
                weather.setPressure(pressure);

                r.getObservations().add(index, weather);
            }
            else if (resultCode == RESULT_CANCELED) {
                System.out.println("\t\tEdit Text Observation: Cancelled!");
            }
        }

        r.reportToGui(linearLayout, context);
    }
}
