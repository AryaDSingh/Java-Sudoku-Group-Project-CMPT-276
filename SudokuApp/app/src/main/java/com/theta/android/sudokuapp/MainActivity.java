package com.theta.android.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creates sudoku board
        Log.d("SUDOKU", "ran5");
        Sudoku game = new Sudoku(this, findViewById(R.id.gameBoard));

    }
}