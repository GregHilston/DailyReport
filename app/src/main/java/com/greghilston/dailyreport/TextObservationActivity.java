package com.greghilston.dailyreport;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TextObservationActivity extends Activity {
    Button submitButton;
    Button cancelButton;
    Button voiceButton;
    EditText text; // Text entered by the user for this TextObservation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_observation);

        submitButton = (Button) findViewById(R.id.button2);
        cancelButton = (Button) findViewById(R.id.button);
        voiceButton = (Button) findViewById(R.id.button3);
        text = (EditText) findViewById(R.id.edtxtInput);

        /**
         * Cancels the creation of the TextObservation, returning to the MainActivity
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("Text Observation Cancel Button Clicked!");

                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        /**
         * Returns the text entered by the user so a TextObservation can be created
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("Text Observation Submit Button Clicked!");

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", text.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        /**
         * Creates a voice observation using google voice
         */
        voiceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("Voice Observation Submit Button Clicked!");

                Intent voiceIntent = new Intent();

                //Insert voice integration here.


                //voiceIntent.putExtra("result", text.getText().toString());
                //setResult(RESULT_OK, voiceIntent);
                finish();
            }
        });



    }
}


