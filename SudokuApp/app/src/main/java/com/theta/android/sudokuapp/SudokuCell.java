package com.theta.android.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SudokuCell {
    private final Button but;
    private final Context context;
    private String text;

    public SudokuCell(Context context, String num) {
        this.context =  context;
        LayoutInflater inflater = LayoutInflater.from(context);
        but = (Button) inflater.inflate(R.layout.cell, null);
        this.setText(num);

        styleCell();
        createListener();
    }

    public View getView() {
        return but;
    }

    private void setText(String text) {
        this.text = text;
        String first2 = text.length() > 2 ? text.substring(0, 2): text;
        but.setText(first2);
    }

    private String getText() {
        return text;
    }

    private void styleCell() { //I have no idea how to style stuff
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.weight = 1;
        p.setMargins(2, 2, 2, 2);
        but.setLayoutParams(p);
    }

    private void createListener() {
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPrompt();
            }
        });
    }

    private void createPrompt() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View prompt = inflater.inflate(R.layout.prompt, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(prompt);
        final TextView currWord = (TextView) prompt.findViewById(R.id.currentWord);
        currWord.setText(currWord.getText().toString() + this.getText());
        final EditText edit = (EditText) prompt.findViewById(R.id.editText);
        alert.setCancelable(false);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setText(edit.getText().toString());
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.create();
        alert.show();
    }



}
