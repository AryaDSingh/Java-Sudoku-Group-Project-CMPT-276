package com.theta.android.sudokuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.Scanner;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        // check win and read the txt file
        // add one in csv file
        // two parameters should be passed win and lose
        writeAndEdit(read(), 0, 0);
    }
    public void writeAndEdit(String args, int winScore, int loseScore) { // write the new value
        try {
            FileWriter myWriter = new FileWriter("res/raw/statsCount.txt");
            String win = "", loss = "", total = "";
            int i;
            for(i = 0; i < args.length();i++){
                if(args.charAt(i) != ','){
                    win += args.charAt(i);
                }
            }
            for(; i < args.length(); i++){
                if(args.charAt(i) != ','){
                    loss += args.charAt(i);
                }
            }
            for(; i < args.length(); i++){
                    total += args.charAt(i);
            }
            myWriter.write("Win:" + win + "Loss:"+ loss + "Total:" + total);
            myWriter.close();
            if(winScore == 1) {
                TextView setWin = findViewById(R.id.win);
                win = String.valueOf (Integer.parseInt(win) + 1);
                setWin.setText(win);
            }
            if(loseScore == 1) {
                TextView setLoss = findViewById(R.id.win);
                loss = String.valueOf(Integer.parseInt(loss) + 1);
                setLoss.setText(loss);
            }
            TextView setTotal = findViewById(R.id.win);
            total = String.valueOf(Integer.parseInt(total) + 1);
            setTotal.setText(total);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public String read() { // gets the win, loss and total matches in the file
        String getNum = "";
        try {
            File myObj = new File("res/raw/statsCount.txt");
            Scanner myReader = new Scanner(myObj);
            String fileContent = "";
            fileContent += myReader.nextLine();

            for (int i = 0; i < fileContent.length(); i++) {
                if (fileContent.charAt(i) >= '0' || fileContent.charAt(i) <= '9') {
                        getNum += fileContent.charAt(i);
                        if(fileContent.charAt(i + 1) < '0' && fileContent.charAt(i + 1) > '9'){
                            getNum += ',';
                        }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return getNum;
    }
}




