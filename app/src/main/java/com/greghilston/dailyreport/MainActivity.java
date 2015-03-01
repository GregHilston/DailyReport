package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity.java's OnCreate() being called");

        // TODO: Fix this so we have the correct account and project listed
        Account account = new Account("Greg Hilston", "ACME Systems");
        Project project = new Project(account, "Construction");
        account.addProject(project);
        final Report r = new Report(project);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.timeLine);

        Text textObservation = new Text("Arrived on site");
        // GUI component
        TextView tv = new TextView(getApplicationContext());
        // tv.setId((int)System.currentTimeMillis()); // May need later to reference TextView
        tv.setText(textObservation.getText() + " : " + textObservation.getTime());

        linearLayout.addView(tv);

        final Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Create a new Camera Observation!");
            }
        });

        final Button weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Changing this to open up txt obs screen
                Intent nextScreen = new Intent(getApplicationContext(),txtObservationActivity.class);
                startActivity(nextScreen);

                //System.out.println("Create a new Weather Observation!");
            }
        });

        final Button noteButton = (Button) findViewById(R.id.noteButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Create a new Note Observation!");

                // TODO: Change text string to whatever "New Text Observation" activity returns
                // Java component
                Text textObservation = new Text("Saw a cat");

                // GUI component
                TextView tv = new TextView(getApplicationContext());
                // tv.setId((int)System.currentTimeMillis()); // May need later to reference TextView
                tv.setText(textObservation.getText() + " : " + textObservation.getTime());

                linearLayout.addView(tv);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Intent i = getIntent();
        String example = i.getStringExtra("extext");
        System.out.println("returned: " + example);
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

        return super.onOptionsItemSelected(item);
    }
}
