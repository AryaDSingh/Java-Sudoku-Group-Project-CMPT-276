package com.theta.android.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

/**
 * activity for creating pairs in the word file
 */
public class WordPairActivity extends AppCompatActivity {
    private LinearLayout layout;
    private String dir;
    private Boolean locked;

    /**
     * sets the perameters of the wordpair file
     * and creates a click event for new pair button
     *
     * @param savedInstanceState the SavedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        setContentView(R.layout.activity_word_pair);

        this.layout = findViewById(R.id.root);
        this.dir = getIntent().getStringExtra("fileDir");
        this.locked = !getIntent().getBooleanExtra("isDel", true);
        getPairs();

        Button pairBut = findViewById(R.id.pairBut);
        if (locked) {
            pairBut.setEnabled(false);
        }
        else {
            pairBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makePair("", "");
                }
            });
        }
    }

    /**
     * saves pairs on stop
     */
    @Override
    protected void onStop() {
        super.onStop();
        savePairs();
    }

    /**
     * saves the current word pairs in the word file
     * using sharedpreferences
     */
    private void savePairs() {
        SharedPreferences prefs = getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int num = layout.getChildCount();
        int start = 2;
        editor.putInt(dir+"  .numpairs", num-start);
        for (int i = start; i < num; i++) {
            LinearLayout pair = (LinearLayout) layout.getChildAt(i);
            String first = HelpFunc.cleanString(((EditText)pair.getChildAt(0)).getText().toString());
            String second = HelpFunc.cleanString(((EditText)pair.getChildAt(1)).getText().toString());

            editor.putString(dir+"  ."+(i-start)+".first", first);
            editor.putString(dir+"  ."+(i-start)+".second", second);
        }

        editor.commit();
    }

    /**
     * gets the word pairs of the file from sharedpreferences
     */
    private void getPairs() {
        TextView title = findViewById(R.id.title);
        SharedPreferences prefs = getSharedPreferences("WordBank", Context.MODE_PRIVATE);

        int numPairs = prefs.getInt(dir+"  .numpairs", 0);
        Log.d("SUDOKU", " num: " +  numPairs + " from: [" + dir+"  .numpairs" + "]");
        for (int i = 0; i < numPairs; i++) {
            String first= prefs.getString(dir+"  ."+i+".first", "");
            String second= prefs.getString(dir+"  ."+i+".second", "");
            makePair(first, second);
        }
    }

    /**
     * creates a new word pair
     * @param first the word from the primary language
     * @param second the translated word from the secondary language
     */
    private void makePair(String first, String second) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout pairText = (LinearLayout) inflater.inflate(R.layout.pairs, layout, false);
        layout.addView(pairText);
        EditText firstText = (EditText)pairText.getChildAt(0);
        EditText secondText = (EditText)pairText.getChildAt(1);
        firstText.setText(first);
        secondText.setText(second);
        if (locked) {
            firstText.setEnabled(false);
            secondText.setEnabled(false);
        }
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