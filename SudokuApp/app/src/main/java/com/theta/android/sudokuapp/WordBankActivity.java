package com.theta.android.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class WordBankActivity extends AppCompatActivity {
    private Button button;
    private EditText native1;
    private EditText foreign1;
    private EditText native2;
    private EditText foreign2;
    private EditText native3;
    private EditText foreign3;
    private EditText native4;
    private EditText foreign4;
    private EditText native5;
    private EditText foreign5;
    private EditText native6;
    private EditText foreign6;
    private EditText native7;
    private EditText foreign7;
    private EditText native8;
    private EditText foreign8;
    private EditText native9;
    private EditText foreign9;


    private final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_bank);
        button = (Button)findViewById(R.id.submitBtn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                firstPair();
                secondPair();
                thirdPair();
                fourthPair();
                fifthPair();
                sixthPair();
                seventhPair();
                eighthPair();
                ninthPair();
//                BufferedReader reader = HelpFunc.readFile(context, R.raw.words);
//                String line = "";
//                String lines = "";
//                try {
//                    while ( (line = reader.readLine()) != null ) {
//                        String[] items = line.split(",");
//                        lines= lines + items[0] + "," + items[1] + ";;";
//                         // display a toast message
//                    }
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(getApplicationContext(), lines, Toast.LENGTH_LONG).show();
            }
            });
    }
    public void firstPair(){
        native1 = (EditText) findViewById(R.id.text1);
        String entry1 = native1.getText().toString();
        foreign1 = (EditText) findViewById(R.id.text11);
        String entry11 = foreign1.getText().toString();
        String content = entry1 + "," + entry11;
        File path = getApplicationContext().getFilesDir();
        try{
            FileOutputStream writer = new FileOutputStream("/Users/minkaungkhant/cmpt276-1227-d2-theta/SudokuApp/app/src/main/res/raw");
            writer.write(content.getBytes());
            char ch[] = content.toCharArray();
            for (int i = 0; i < ch.length; i++) {

                // we will write the string by writing each
                // character one by one to file
                writer.write(ch[i]);
                 Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void secondPair(){
        native2 = (EditText) findViewById(R.id.text2);
        String entry2 = native2.getText().toString();
        foreign2 = (EditText) findViewById(R.id.text12);
        String entry12 = foreign2.getText().toString();
        File path = context.getFilesDir();
        File file = new File(path, "words.csv");
        String content = entry2 + "," + entry12;

        // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void thirdPair(){
        native3 = (EditText) findViewById(R.id.text3);
        String entry3 = native3.getText().toString();
        foreign3 = (EditText) findViewById(R.id.text13);
        String entry13 = foreign3.getText().toString();
        File path = context.getFilesDir();
        File file = new File(path, "words.csv");
        String content = entry3 + "," + entry13;

        // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fourthPair(){
        native4 = (EditText) findViewById(R.id.text4);
        String entry4 = native4.getText().toString();
        foreign4 = (EditText) findViewById(R.id.text14);
        String entry14 = foreign4.getText().toString();
        File path = context.getFilesDir();
        File file = new File(path, "words.csv");
        String content = entry4 + "," + entry14;

        // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fifthPair(){
        native5 = (EditText) findViewById(R.id.text5);
        String entry5 = native5.getText().toString();
        foreign5 = (EditText) findViewById(R.id.text15);
        String entry15 = foreign5.getText().toString();
        File path = context.getFilesDir();
        File file = new File(path, "words.csv");
        String content = entry5 + "," + entry15;

        // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sixthPair(){
        native6 = (EditText) findViewById(R.id.text6);
        String entry6 = native6.getText().toString();
        foreign6 = (EditText) findViewById(R.id.text16);
        String entry16 = foreign6.getText().toString();
        File path = context.getFilesDir();
        File file = new File(path, "words.csv");
        String content = entry6 + "," + entry16;

        // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void seventhPair(){
        native7 = (EditText) findViewById(R.id.text7);
        String entry7 = native7.getText().toString();
        foreign7 = (EditText) findViewById(R.id.text17);
        String entry17 = foreign7.getText().toString();
        File path = context.getFilesDir();
        File file = new File(path, "words.csv");
        String content = entry7 + "," + entry17;

        // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void eighthPair(){
        native8 = (EditText) findViewById(R.id.text8);
        String entry8 = native8.getText().toString();
        foreign8 = (EditText) findViewById(R.id.text18);
        String entry18 = foreign8.getText().toString();
        File path = context.getFilesDir();
        File file = new File(path, "words.csv");
        String content = entry8 + "," + entry18;

        // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ninthPair(){
            native9 = (EditText) findViewById(R.id.text9);
            String entry9 = native9.getText().toString();
            foreign9 = (EditText) findViewById(R.id.text19);
            String entry19 = foreign9.getText().toString();
            File path = context.getFilesDir();
            File file = new File(path, "words.csv");
            String content = entry9 + "," + entry19;

            // Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();  // display a toast message
            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);
                stream.write(content.getBytes());
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}