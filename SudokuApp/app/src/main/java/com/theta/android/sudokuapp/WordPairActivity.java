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

public class WordPairActivity extends AppCompatActivity {
    private LinearLayout layout;
    private String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_pair);

        this.layout = findViewById(R.id.root);
        this.dir = getIntent().getStringExtra("fileDir");
        getPairs();


        Button pairBut = findViewById(R.id.pairBut);
        pairBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePair("", "");
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        savePairs();
    }


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
            Log.d("SUDOKU", "First & Second: " +first +" " + second);

            editor.putString(dir+"  ."+(i-start)+".first", first);
            editor.putString(dir+"  ."+(i-start)+".second", second);
        }

        editor.commit();
    }

    private void getPairs() {
        TextView title = findViewById(R.id.title);
        SharedPreferences prefs = getSharedPreferences("WordBank", Context.MODE_PRIVATE);

        int numPairs = prefs.getInt(dir+"  .numpairs", 0);
        for (int i = 0; i < numPairs; i++) {
            String first= prefs.getString(dir+"  ."+i+".first", "");
            String second= prefs.getString(dir+"  ."+i+".second", "");
            Log.d("SUDOKU", "First & Second: " +first +" " + second);
            makePair(first, second);
        }
    }

    private void makePair(String first, String second) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout pairText = (LinearLayout) inflater.inflate(R.layout.pairs, layout, false);
        layout.addView(pairText);
        ((EditText)pairText.getChildAt(0)).setText(first);
        ((EditText)pairText.getChildAt(1)).setText(second);
    }

}