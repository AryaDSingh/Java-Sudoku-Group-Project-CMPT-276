package com.theta.android.sudokuapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class for settings activity
 * sudoku settings can be read from here
 */
public class SettingsActivity extends AppCompatActivity {

    private Switch darkModeBut;
    private Switch pairSwitch;
    private Switch translateSwitch;
    private Switch voiceSwitch;
    private Spinner languageSelect;

    private List<Integer> diffIds = new ArrayList<Integer>(Arrays.asList(R.id.easyBut, R.id.mediumBut, R.id.hardBut));
    private RadioGroup diffGroup;

    private List<Integer> sizeIds = new ArrayList<Integer>(Arrays.asList(R.id.size4, R.id.size6, R.id.size9, R.id.size12));
    private RadioGroup sizeGroup;

    /**
     * saves settings on pause event
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveSettings();
    }

    /**
     * initializes the correct values to each setting option
     * and creates an click listener for wordbank button
     *
     * @param savedInstanceState the SavedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        diffGroup = (RadioGroup) findViewById(R.id.difficulty);
        darkModeBut = (Switch) findViewById(R.id.colormode);
        pairSwitch = (Switch) findViewById(R.id.practicePairs);
        sizeGroup = (RadioGroup) findViewById(R.id.sudokuSize);
        translateSwitch = (Switch) findViewById(R.id.translate);
        voiceSwitch = (Switch) findViewById(R.id.voiceMode);
        languageSelect = (Spinner) findViewById(R.id.language);

        List<String> languages = Arrays.asList("English", "Japanese", "Mandrin", "Cantonese", "French", "German", "Italian", "Korean");

        languageSelect.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages));
        languageSelect.setSelection(languages.indexOf(readLanguage(this)));
        diffGroup.check(diffIds.get(readDifficulty(this)));
        darkModeBut.setChecked(readColorMode(this));
        pairSwitch.setChecked(readPracticeMode(this));
        sizeGroup.check(sizeIds.get(readSize(this)));
        translateSwitch.setChecked(readTranslateMode(this));
        voiceSwitch.setChecked(readVoiceMode(this));

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
    }

    /**
     * saves the current settings
     */
    private void saveSettings() {
        SharedPreferences prefs = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int difficulty = diffIds.indexOf(diffGroup.getCheckedRadioButtonId());
        int boardSize = sizeIds.indexOf(sizeGroup.getCheckedRadioButtonId());

        Boolean darkMode = darkModeBut.isChecked();
        Boolean practiceMode = pairSwitch.isChecked();
        Boolean translateMode = translateSwitch.isChecked();
        Boolean voiceMode = voiceSwitch.isChecked();
        String language = HelpFunc.cleanString(languageSelect.getSelectedItem().toString());

        editor.putInt("difficulty", difficulty);
        editor.putBoolean("darkmode", darkMode);
        editor.putBoolean("practicemode", practiceMode);
        editor.putInt("boardsize", boardSize);
        editor.putBoolean("translatemode", translateMode);
        editor.putBoolean("voicemode", voiceMode);
        editor.putString("language", language);
        editor.commit();

        cSudoku.deleteSave(this);
    }

    ////////////////////////////////////
    //Getters for various settings below
    ////////////////////////////////////

    public static int readDifficulty(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return prefs.getInt("difficulty", 0);
    }

    public static Boolean readColorMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return prefs.getBoolean("darkmode", false);
    }

    public static int readSize(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return prefs.getInt("boardsize", 2);
    }

    public static Boolean readPracticeMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return prefs.getBoolean("practicemode", false);
    }

    public static Boolean readTranslateMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return prefs.getBoolean("translatemode", false);
    }

    public static Boolean readVoiceMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return prefs.getBoolean("voicemode", false);
    }

    public static String readLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return prefs.getString("language", "English");
    }

}