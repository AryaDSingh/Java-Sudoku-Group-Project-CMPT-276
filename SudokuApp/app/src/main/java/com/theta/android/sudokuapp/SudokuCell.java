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
import android.widget.Toast;

public class SudokuCell {
    private final Button but;
    private final Context context;
    private String text = "";
    private Sudoku parent;
    private int x;
    private int y;

    public SudokuCell(Context context, String word, Sudoku parent,int x, int y) {
        this.parent = parent;
        this.context =  context;
        LayoutInflater inflater = LayoutInflater.from(context);
        but = (Button) inflater.inflate(R.layout.cell, null);
        this.setText(word, false);
        this.x=x;
        this.y=y;

        styleCell();
        createListener(parent);
    }

    public View getView() {
        return but;
    }

    private void setText(String text, Boolean callChange) {
        text = HelpFunc.cleanString(text);
        int change = 0;
        if (this.text.length() == 0 && text.length() > 0) {
            change = 1;
        }
        else if (this.text.length() > 0 && text.length() == 0) {
            change = -1;
        }

        String first2 = text.length() > 2 ? text.substring(0, 2): text;
        this.text = text;
        but.setText(first2);

        if (callChange) {
            parent.onCellChange(this, change);
        }
    }

    public void setText(String text) {
        setText(text, true);
    }


    public String getText() {
        return text;
    }

    private void styleCell() { //I have no idea how to style stuff
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.weight = 1;
        p.setMargins(2, 2, 2, 2);
        but.setLayoutParams(p);
    }

    private void createListener(Sudoku parent) {
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPrompt(parent);
            }
        });
    }

    private void createPrompt(Sudoku parent) {
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
                if(parent.checkLineH(x,y) && parent.checkLineV(x,y) && parent.checkGrid(x,y))
                {
                    Toast.makeText(context,"I am passed horizontally",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context,"I am failed horizontally",Toast.LENGTH_LONG).show();
                    setText("");
                }

//                Toast.makeText(context,"I am at position x="+x+" and position y="+y,Toast.LENGTH_LONG).show();

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

    private void checkError(String toString) {
    }


}
