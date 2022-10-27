package com.theta.android.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gameBoard = findViewById(R.id.gameBoard);
        gameBoard.setAdapter(new CellAdapter(this));
    }
}