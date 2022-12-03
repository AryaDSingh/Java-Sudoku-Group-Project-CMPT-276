package com.theta.android.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * File controller class
 */
public class cWordFile {
    private Context context;
    private Button but;
    private Button delBut;
    private CheckBox checkBut;
    private LinearLayout butParent;
    private LinearLayout layout;
    private cWordBank parent;
    private Boolean isFile;
    private String text;

    /**
     * sets if the file is checked and clickable
     * @param bool file is checked and not clickable if true
     *             file is unchecked and clickable if false
     */
    public void setCheckBox(Boolean bool) {
        checkBut.setChecked(bool);
        checkBut.setClickable(!bool);
    }

    public View getView() {
        return butParent;
    }

    public Boolean isFile() {
        return isFile;
    }

    public Boolean isDel() {return delBut!=null;}

    /**
     * initializes file
     * @param context current context
     * @param text the name of the file
     * @param layout the layout display the file in
     * @param parent the Wordbank that created this file
     * @param isFile true if is a file, false if is a folder
     * @param isDel true if is deletable
     */
    public cWordFile(Context context, String text, LinearLayout layout, cWordBank parent, Boolean isFile, Boolean isDel) {
        this.context = context;
        this.parent = parent;
        this.isFile = isFile;
        this.text = text;
        this.layout = layout;
        createBut(layout, isDel);

        createListener();
    }

    public String getText() {
        return text;
    }

    /**
     * creates the file button
     * @param parent the linearlayout to put the button in
     * @param isDel True if the file should be deletable
     */
    private void createBut(LinearLayout parent, Boolean isDel) {
        LayoutInflater inflater = LayoutInflater.from(context);
        butParent = (LinearLayout)inflater.inflate(R.layout.wordbankbutton, parent, false);
        checkBut = (CheckBox) butParent.getChildAt(0);
        but = (Button) butParent.getChildAt(1);
        delBut = (Button) butParent.getChildAt(2);
        if (!isDel) {
            butParent.removeView(delBut);
            delBut = null;
        }

        if (!isFile) {
            butParent.removeViewAt(0);
        }
        but.setText((isFile? "\uD83D\uDCC4": "\uD83D\uDCC1") + text);
        parent.addView(butParent);
    }

    /**
     * creates listener for the file button, check box, and delete button
     */
    private void createListener() {

        but.setOnClickListener(view -> {
            if (isFile) {
                parent.openFile(this);
            }
            else {
                parent.changeDir(this);
            }
        });

        if (delBut != null) {
            delBut.setOnClickListener(view -> {
                parent.removeFile(this);
            });
        }

        if (isFile) {
            checkBut.setOnClickListener(view -> {
                parent.setMainPairs(this);
            });
        }
    }
}
