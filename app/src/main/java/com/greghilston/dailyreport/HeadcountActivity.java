package com.greghilston.dailyreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class HeadcountActivity extends Activity {
    // Static fields to determine if checkboxes should be checked
    static Boolean checkGrehgCheckBox = false;
    static Boolean checkEvanCheckBox = false;
    static Boolean checkTylerCheckBox = false;
    static Boolean checkJimCheckBox = false;
    static Boolean checkWalterCheckBox = false;
    static Boolean checkMikeCheckBox = false;
    static Boolean checkColletteCheckBox = false;
    static Boolean checkAcmeCheckBox = false;
    static Boolean checkResearchCheckBox = false;
    static Boolean checkDestructionCheckBox = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headcount_list);

        // Employee Checkboxes (Filled with example employees)
        final CheckBox grehgCheckBox = (CheckBox) findViewById(R.id.grehgCheckBox);
        grehgCheckBox.setChecked(checkGrehgCheckBox);

        final CheckBox evanCheckBox = (CheckBox) findViewById(R.id.evanCheckBox);
        evanCheckBox.setChecked(checkEvanCheckBox);

        final CheckBox tylerCheckBox = (CheckBox) findViewById(R.id.tylerCheckBox);
        tylerCheckBox.setChecked(checkTylerCheckBox);

        final CheckBox jimCheckBox = (CheckBox) findViewById(R.id.jimCheckBox);
        jimCheckBox.setChecked(checkJimCheckBox);

        final CheckBox walterCheckBox = (CheckBox) findViewById(R.id.walterCheckBox);
        walterCheckBox.setChecked(checkWalterCheckBox);

        final CheckBox mikeCheckBox = (CheckBox) findViewById(R.id.mikeCheckBox);
        mikeCheckBox.setChecked(checkMikeCheckBox);

        final CheckBox colletteCheckBox = (CheckBox) findViewById(R.id.colletteCheckBox);
        colletteCheckBox.setChecked(checkColletteCheckBox);

        // Company Checkboxes (Filled with example companies)
        final CheckBox acmeCheckBox = (CheckBox) findViewById(R.id.acmeCheckBox);
        acmeCheckBox.setChecked(checkAcmeCheckBox);

        final CheckBox researchCheckBox = (CheckBox) findViewById(R.id.researchCheckBox);
        researchCheckBox.setChecked(checkResearchCheckBox);

        final CheckBox destructionCheckBox = (CheckBox) findViewById(R.id.destructionCheckBox);
        destructionCheckBox.setChecked(checkDestructionCheckBox);

        // Our submit button
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int employeeCount = 0;
                Intent returnIntent = new Intent();

                if(MainActivity.debugMode) {
                    System.out.println("Headcount Submit Button Clicked!");
                }

                if (grehgCheckBox.isChecked()) {
                    returnIntent.putExtra("grehgCheckBoxSelected", true);
                    checkGrehgCheckBox = true;
                    employeeCount++;
                }
                else {
                    checkGrehgCheckBox = false;
                    returnIntent.putExtra("grehgCheckBoxSelected", false);
                }

                if (evanCheckBox.isChecked()) {
                    checkEvanCheckBox = true;
                    returnIntent.putExtra("evanCheckBoxSelected", true);
                    employeeCount++;
                }
                else {
                    checkEvanCheckBox = false;
                    returnIntent.putExtra("evanCheckBoxSelected", false);
                }

                if (tylerCheckBox.isChecked()) {
                    checkTylerCheckBox = true;
                    returnIntent.putExtra("tylerCheckBoxSelected", true);
                    employeeCount++;
                }
                else {
                    checkTylerCheckBox = false;
                    returnIntent.putExtra("tylerCheckBoxSelected", false);
                }

                if (jimCheckBox.isChecked()) {
                    checkJimCheckBox = true;
                    returnIntent.putExtra("jimCheckBoxSelected", true);
                    employeeCount++;
                }
                else {
                    checkJimCheckBox = false;
                    returnIntent.putExtra("jimCheckBoxSelected", false);
                }

                if (walterCheckBox.isChecked()) {
                    checkWalterCheckBox = true;
                    returnIntent.putExtra("walterCheckBoxSelected", true);
                    employeeCount++;
                }
                else {
                    checkWalterCheckBox = false;
                    returnIntent.putExtra("walterCheckBoxSelected", false);
                }

                if (mikeCheckBox.isChecked()) {
                    checkMikeCheckBox = true;
                    returnIntent.putExtra("mikeCheckBoxSelected", true);
                    employeeCount++;
                }
                else {
                    checkMikeCheckBox = false;
                    returnIntent.putExtra("mikeCheckBoxSelected", false);
                }

                if (colletteCheckBox.isChecked()) {
                    checkColletteCheckBox = true;
                    returnIntent.putExtra("colletteCheckBoxSelected", true);
                    employeeCount++;
                }
                else {
                    checkColletteCheckBox = false;
                    returnIntent.putExtra("colletteCheckBoxSelected", false);
                }

                if (acmeCheckBox.isChecked()) {
                    checkAcmeCheckBox = true;
                    returnIntent.putExtra("acmeCheckBoxSelected", true);
                    returnIntent.putExtra("acmeEmployeeCount", 0); // TODO: Change the 0 to the real number
                }
                else {
                    checkAcmeCheckBox = false;
                    returnIntent.putExtra("acmeCheckBoxSelected", false);
                }

                if (researchCheckBox.isChecked()) {
                    checkResearchCheckBox = true;
                    returnIntent.putExtra("researchCheckBoxSelected", true);
                    returnIntent.putExtra("researchEmployeeCount", 0); // TODO: Change the 0 to the real number
                }
                else {
                    checkResearchCheckBox = false;
                    returnIntent.putExtra("researchCheckBoxSelected", false);
                }

                if (destructionCheckBox.isChecked()) {
                    checkDestructionCheckBox = true;
                    returnIntent.putExtra("destructionCheckBoxSelected", true);
                    returnIntent.putExtra("destructionEmployeeCount", 0); // TODO: Change the 0 to the real number
                }
                else {
                    checkDestructionCheckBox = false;
                    returnIntent.putExtra("destructionCheckBoxSelected", false);
                }

                if(MainActivity.debugMode) {
                    System.out.println("Employee Count: " + employeeCount);
                }

                returnIntent.putExtra("employeeCount", employeeCount);

                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
