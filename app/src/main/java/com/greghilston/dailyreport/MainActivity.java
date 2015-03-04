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
    Account account = new Account("Greg Hilston", "ACME Systems");
    Project project = new Project(account, "Construction");
    final Report r = new Report(project);
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.timeLine);
        account.addProject(project); // THIS WILL BREAK IF ONCREATE IS CALLED MORE THAN ONCE TODO: CHECK!

        Text textObservation = new Text("Arrived on site");
        r.addObservation(textObservation);
        // GUI component
        TextView tv = new TextView(getApplicationContext());
        // tv.setId((int)System.currentTimeMillis()); // May need later to reference TextView
        tv.setText(textObservation.getText() + " : " + textObservation.getTime());

        linearLayout.addView(tv);

        final Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent camScreen = new Intent(getApplicationContext(),cameraActivity.class);
                startActivity(camScreen);

            }
        });

        final Button weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Changing this to open up txt obs screen
                System.out.println("Weather Observation Button Pressed!");
            }
        });

        final Button noteButton = (Button) findViewById(R.id.noteButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), txtObservationActivity.class);
                startActivityForResult(nextScreen, 1);
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();  // Always call the superclass method first
//
//        Intent i = getIntent();
//        String example = i.getStringExtra("extext");
//
//        if(example == null) {
//            System.out.println("Text Observation Cancelled");
//        }
//        else {
//            System.out.println("Text Observation Returned: " + example);
//        }
//    }

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String result =  data.getStringExtra("result");

                if(result != "") { // Do not make an observation for an empty string
                    System.out.println("Text Observation Returned: " + result);
                    r.addObservation(new Text(result));
                    r.reportToGui((LinearLayout) findViewById(R.id.timeLine), new TextView(getApplicationContext()));
                }
            }
            if (resultCode == RESULT_CANCELED) {
                System.out.println("Text Observation Cancelled!");
            }
        }
    }
}
