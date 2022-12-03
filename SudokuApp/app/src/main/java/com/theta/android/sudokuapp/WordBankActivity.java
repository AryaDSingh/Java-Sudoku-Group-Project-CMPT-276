package com.theta.android.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * the wordbank activity class
 */
public class WordBankActivity extends AppCompatActivity {
    private cWordBank fileSystem;

    /**
     * creates word bank buttons and initializes wordbank controller
     * @param savedInstanceState the SavedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_bank);

        this.fileSystem = new cWordBank(this, findViewById(R.id.wordBankRoot));

        Button fileBut = findViewById(R.id.fileBut);
        fileBut.setOnClickListener(view -> createPrompt(fileSystem, true));

        Button FolderBut = findViewById(R.id.folderBut);
        FolderBut.setOnClickListener(view -> createPrompt(fileSystem, false));

        Button returnBut = findViewById(R.id.returnBut);
        returnBut.setOnClickListener(view -> fileSystem.changeDir(null));

    }

    /**
     * saves files on stop event
     */
    @Override
    protected void onStop() {
        super.onStop();
        this.fileSystem.saveFiles();
    }

    /**
     * creates a prompt to name a folder/file
     * @param fileSystem the current wordbank controller
     * @param isFile True if is a file, false if is a folder
     */
    private void createPrompt(cWordBank fileSystem, Boolean isFile) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View prompt = inflater.inflate(R.layout.prompt, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(prompt);

        final TextView title = prompt.findViewById(R.id.title);
        title.setText("Enter " + (isFile? "file": "folder") + " name");
        final EditText edit = prompt.findViewById(R.id.editText);

        alert.setCancelable(false);
        alert.setPositiveButton("Save", (dialogInterface, i) -> fileSystem.addFile(edit.getText().toString(), isFile, false, true));
        alert.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        alert.create();
        alert.show();
    }

}