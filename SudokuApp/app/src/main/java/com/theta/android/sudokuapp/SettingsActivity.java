package com.theta.android.sudokuapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Pair;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Switch colorMode = (Switch) findViewById(R.id.colormode);
        colorMode.setChecked(readColorMode(this));

        colorMode.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                setColorMode(colorMode.isChecked());
             }
        });


                RadioGroup diffGroup = (RadioGroup) findViewById(R.id.difficulty);
        switch(readDifficulty(this))
        {
            case 0:
                diffGroup.check(R.id.easyBut);
                break;
            case 1:
                diffGroup.check(R.id.mediumBut);
                break;
            case 2:
                diffGroup.check(R.id.hardBut);
                break;
        }

        diffGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.easyBut:
                        setDifficulty(0);
                        break;
                    case R.id.mediumBut:
                        setDifficulty(1);
                        break;
                    case R.id.hardBut:
                        setDifficulty(2);
                        break;
                }
            }
        });

        //word_bank button
        Button btn_word = (Button) findViewById(R.id.button_word);
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

        //word_pair button
        Button btn_pair = (Button) findViewById(R.id.button_pair);
        //set the button to do stuff on click
        btn_pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPairButton();
            }
            public void openPairButton() {
                Intent intent_pair = new Intent(SettingsActivity.this, WordPairActivity.class);
                startActivity(intent_pair);
            }
        });

    }

    private void setDifficulty(int level) {
        SharedPreferences prefs = this.getSharedPreferences("Difficulty", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("value", level);
        editor.commit();
    }

    public static int readDifficulty(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Difficulty", Context.MODE_PRIVATE);
        return prefs.getInt("value", 0);
    }


    private void setColorMode(Boolean value) {
        SharedPreferences prefs = this.getSharedPreferences("ColorMode", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("value", value);
        editor.commit();
    }

    public static Boolean readColorMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ColorMode", Context.MODE_PRIVATE);
        return prefs.getBoolean("value", false);
    }

}