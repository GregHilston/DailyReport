package com.greghilston.dailyreport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class EquipmentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headcount_list);

        // Equiptment Checkboxes
        final CheckBox steamRollerCheckBox = (CheckBox) findViewById(R.id.steamRoller);
        final CheckBox cherryPickerCheckBox = (CheckBox) findViewById(R.id.cherryPicker);
        final CheckBox bullDozerCheckBox = (CheckBox) findViewById(R.id.bullDozer);
        final CheckBox hammerCheckBox = (CheckBox) findViewById(R.id.hammer);
        final CheckBox nailCheckBox = (CheckBox) findViewById(R.id.nail);
        final CheckBox anotherNailCheckBox = (CheckBox) findViewById(R.id.anotherNail);
        final CheckBox aGreatAttitudeCheckBox = (CheckBox) findViewById(R.id.aGreatAttitude);

        // Our submit button
        final Button submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int equipmentCounter = 0;

                if (steamRollerCheckBox.isChecked()) {
                    equipmentCounter++;
                }
                if (cherryPickerCheckBox.isChecked()) {
                    equipmentCounter++;
                }
                if (bullDozerCheckBox.isChecked()) {
                    equipmentCounter++;
                }
                if (hammerCheckBox.isChecked()) {
                    equipmentCounter++;
                }
                if (nailCheckBox.isChecked()) {
                    equipmentCounter++;
                }
                if (anotherNailCheckBox.isChecked()) {
                    equipmentCounter++;
                }
                if (aGreatAttitudeCheckBox.isChecked()) {
                    equipmentCounter++;
                }
            }
        });
    }
}
