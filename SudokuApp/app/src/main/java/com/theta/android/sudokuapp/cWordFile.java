package com.theta.android.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class cWordFile {
    private Context context;
    private Button but;
    private Button delBut;
    private LinearLayout butParent;
    private LinearLayout layout;
    private cWordBank parent;
    private Boolean isFile;
    private String text;

    public View getView() {
        return but;
    }

    public Boolean isFile() {
        return isFile;
    }

    public cWordFile(Context context, String text, LinearLayout layout, cWordBank parent, Boolean isFile) {
        this.context = context;
        this.parent = parent;
        this.isFile = isFile;
        this.text = text;
        this.layout = layout;
        createBut(text, layout);

        createListener();
    }

    public String getText() {
        return text;
    }

    private void createBut(String text, LinearLayout parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        butParent = (LinearLayout)inflater.inflate(R.layout.wordbankbutton, parent, false);
        but = (Button) butParent.getChildAt(1);
        delBut = (Button) butParent.getChildAt(2);

        if (!isFile) {
            butParent.removeViewAt(0);
        }
        but.setText((isFile? "\uD83D\uDCC4": "\uD83D\uDCC1") + text);
        parent.addView(butParent);
    }

    private void createListener() {

        but.setOnClickListener(view -> {
            if (isFile) {
                parent.openFile(this);
            }
            else {
                parent.changeDir(this);
            }
        });

        delBut.setOnClickListener(view -> {
            layout.removeView(butParent);
            parent.removeFile(this);
        });
    }
}
