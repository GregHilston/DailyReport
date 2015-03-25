package com.greghilston.dailyreport;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTextObservationActivity extends Activity {
    Button submitButton;
    Button cancelButton;
    EditText editText; // Text entered by the user for this TextObservation
    Observation observation; // Observation to be edited by the user
    Text text;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_observation);

        submitButton = (Button) findViewById(R.id.button2);
        cancelButton = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.edtxtInput);

        observation = (Observation) getIntent().getSerializableExtra("observation");
        index = (int) getIntent().getSerializableExtra("index");

        if(observation instanceof Text) {
            text = (Text) observation;
        }

        editText.setText(text.getText());

        /**
         * Cancels the creation of the TextObservation, returning to the MainActivity
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                System.out.println("Edit Text Observation Cancel Button Clicked!");

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
                System.out.println("Edit Text Observation Submit Button Clicked!");

                Intent returnIntent = new Intent();
                returnIntent.putExtra("text", text.getText().toString());
                returnIntent.putExtra("index", index);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}


