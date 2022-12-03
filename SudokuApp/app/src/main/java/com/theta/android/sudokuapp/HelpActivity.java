package com.theta.android.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * activity class for the help menu
 *
 * displays the instructions from layout
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_button);
        setLayout();

    }
    //dark mode
    private void setLayout() {
        if (SettingsActivity.readColorMode(this)) {
            LinearLayout header = findViewById(R.id.root);
            int color = getResources().getColor(R.color.dark);
            header.setBackgroundColor(color);
        }
    }


}