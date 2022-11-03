package com.theta.android.sudokuapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Button btn_word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        //word_bank button
        btn_word = (Button) findViewById(R.id.button_word);
        //set the button to do stuff on click
        btn_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWordButton();

            }
            public void openWordButton() {
                Intent intent_word = new Intent(SettingsActivity.this, WordBankActivity.class);
                startActivity(intent_word);
            }
        });









    }
}