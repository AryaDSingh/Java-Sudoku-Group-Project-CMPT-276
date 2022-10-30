package com.theta.android.sudokuapp;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    private final String TAG = "sudoku";
    private final int size = 9; //side length size
    private List<List<SudokuCell>> cells;
    private final LinearLayout board;
    private final List<Pair<String, String>> pairs = new ArrayList<>(size);

    public Sudoku(Context context, ViewGroup board) {
        this.board = (LinearLayout) board;

        initPairs(context);
        initBoard(context);
        //fillBoard(context);
    }


    private void initBoard(Context context) {
        BufferedReader reader = readFile(context, R.raw.boardlayout1);
        String line = "";
        int y = 0;
        cells = new ArrayList<List<SudokuCell>>(size);
        try {
            while ( (line = reader.readLine()) != null && y < size) {
                String[] items = line.split(",");
                LinearLayout row = createRow(context);
                for (int x = 0; x < items.length; x++) {
                    items[x] = items[x].replace(" ", "");
                    int pairIndex = Integer.parseInt(items[x])-1;
                    String firstWord = pairIndex != -1 ? pairs.get(pairIndex).first: " ";
                    SudokuCell cell = new SudokuCell(context, firstWord);
                    row.addView(cell.getView());
                    cells.get(y).add(cell);
                }
                y++;
            }
        }
        catch (IOException e) {
            Log.e(TAG, "could not init board layout");
            e.printStackTrace();
        }
    }

    private LinearLayout createRow(Context context) {
        LinearLayout row = new LinearLayout(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        p.weight = 1;
        row.setLayoutParams(p);
        board.addView(row);
        cells.add(new ArrayList<SudokuCell>(size));
        return row;
    }

    private void fillBoard(Context context) {
        cells = new ArrayList<List<SudokuCell>>(size);
        for (int x = 0; x < size; x++) {
            LinearLayout row = createRow(context);

            for (int y = 0; y < size; y++) {
                SudokuCell cell = new SudokuCell(context, Integer.toString(x * 10 + y));
                row.addView(cell.getView());
                cells.get(x).add(cell);
            }
        }
    }

    private void initPairs(Context context) {
        BufferedReader reader = readFile(context, R.raw.words);

        String line = "";
        try {
            while ( (line = reader.readLine()) != null ) {
                String[] items = line.split(",");
                if (items.length == 2) {
                    pairs.add(new Pair<>(items[0], items[1]));
                }
            }
        }
        catch (IOException e) {
            Log.e(TAG, "could not init pairs");
            e.printStackTrace();
        }

    }

    private BufferedReader readFile(Context context, int file) {
        InputStream is = context.getResources().openRawResource(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        return reader;
    }

}
