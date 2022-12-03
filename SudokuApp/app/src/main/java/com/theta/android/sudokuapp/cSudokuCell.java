package com.theta.android.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Cell Controller class
 */
public class cSudokuCell {
    private final Button but;
    private final Context context;
    final private SudokuCell cell;
    final private cSudoku parent;
    private Boolean translate;
    private Boolean voiceMode;
    private Boolean enabled = true;
    private TextToSpeech tts;

    /**
     * initializes the cell
     * @param context current context
     * @param parent cSudoku this cell belongs to
     * @param butParent layout that contains the button
     * @param translate translates text if true
     * @param voiceMode uses comprehension mode if true
     */
    public cSudokuCell(Context context, cSudoku parent, LinearLayout butParent, Boolean translate, Boolean voiceMode) {
        this.translate = translate;
        this.voiceMode = voiceMode;
        this.parent = parent;
        this.context = context;
        this.cell = new SudokuCell();

        if (voiceMode = true) {
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    tts.setLanguage(HelpFunc.langToLocale(SettingsActivity.readLanguage(context)));
                }
            });
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        but = (Button) inflater.inflate(R.layout.cell, butParent, false);
        butParent.addView(but);

        createListener();
    }

    public void setEnabled(boolean bool) {
        this.enabled = bool;
    }

    public View getView() {
        return but;
    }

    public String getText() {
        return cell.getText();
    }

    public void setText(String text) {
        setText(text, false);
    }

    /**
     * changes the text of the current cell
     * @param text string to change the text to
     * @param callChange calls parent changeEvent if true
     */
    private void setText(String text, Boolean callChange) {
        if (translate) {
            text = parent.translate(text);
        }
        cell.setText(text);
        if (voiceMode && !callChange && !text.equals("")) {
            but.setText(Integer.toString(parent.getPairIndex(text)+1));
        }
        else {
            but.setText(cell.getFirst2());
        }

        if (callChange) {
            parent.onCellChange(this);
        }
    }

    /**
     * creates a listener for button clicks
     * used for comprehension mode
     * and for changing text in a cell
     */
    private void createListener() {
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enabled){
                    createPrompt();
                }
                else if (voiceMode) {
                    voiceText();
                }
            }
        });
    }

    /**
     * Text to speach the current word
     */
    private void voiceText() {
        tts.speak(getText(), TextToSpeech.QUEUE_FLUSH, null, "");
    }

    /**
     * create a prompt to enter a word for the current cell
     */
    private void createPrompt() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View prompt = inflater.inflate(R.layout.prompt, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(prompt);

        final TextView title = prompt.findViewById(R.id.title);
        title.setText("Current word: " + this.getText());
        final EditText edit = prompt.findViewById(R.id.editText);

        alert.setCancelable(false);
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setText(edit.getText().toString(), true);
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
