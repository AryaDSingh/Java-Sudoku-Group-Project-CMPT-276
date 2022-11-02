package com.theta.android.sudokuapp;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Sudoku {
    private final String TAG = "sudoku";
    private final int size = 9; //side length size
    private final LinearLayout board;
    private final List<Pair<String, String>> pairs = new ArrayList<>(size);
    private List<List<SudokuCell>> cells;
    private int cellsFull = 0;
    private final Context context;
    private long startTime;

    /**
     * Event that is called when one of the cells in the sudoku grid changes text
     *
     * @param context context of main activity
     * @param board layout that will contain the sudoku board
     */
    public Sudoku(Context context, ViewGroup board) {
        this.board = (LinearLayout) board;
        this.context = context;
        this.startTime = Calendar.getInstance().getTimeInMillis();

        initPairs(context);
        initBoard(context);
        checkWin();
    }

    /**
     * Event that is called when one of the cells in the sudoku grid changes text
     *
     * @param cell cell that had its text changed
     * @return
     */
    public void onCellChange(SudokuCell cell, int change) {
        cellsFull += change;
        if (cellsFull == size*size) {
            if (checkWin()) {
                onWin();
            }
        }

    }

    private void onWin() {
        CharSequence text = "You Win!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //time to complete game in seconds
        long winTime = (Calendar.getInstance().getTimeInMillis() - startTime) / 1000;


    }

    /**
     * Checks board cells to see if player has completed the game
     *
     * @param
     * @return True if the player has won the game
     */
    private Boolean checkWin() {
        int gridSize = (int) Math.sqrt(size); //size should be a perfect square
        for (int i = 0; i < size; i++) {
            Boolean lineV = checkLineV(cells.get(0).get(i));
            Boolean lineH = checkLineH(cells.get(i).get(0));
            Boolean grid = checkGrid(cells.get((i/gridSize)*gridSize).get((i%gridSize)*gridSize));

            if (!(lineV && lineH && grid)){
                return false;
            }
        }
        return true;
    }

    private boolean checkLineH(SudokuCell cell) {
        Pair<Integer,Integer> index = findIndex(cell);
        List<Pair<String,String>> seen = new ArrayList<>();

        for (SudokuCell c: cells.get(index.first)) {
            Pair<String,String> pair = findWordPair(c);
            if (pair == null || seen.contains(pair)) {
                return false;
            }
            seen.add(pair);
        }
        return true;
    }

    private Boolean checkLineV(SudokuCell cell) {
        Pair<Integer,Integer> index = findIndex(cell);
        List<Pair<String,String>> seen = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            SudokuCell c = cells.get(i).get(index.second);
            Pair<String,String> pair = findWordPair(c);
            if (pair == null || seen.contains(pair)) {
                return false;
            }
            seen.add(pair);
        }
        return true;
    }

    private Boolean checkGrid(SudokuCell cell) {
        Pair<Integer,Integer> index = findIndex(cell);
        int gridSize = (int) Math.sqrt(size);
        index = new Pair<>((index.first/gridSize)*gridSize,(index.second/gridSize)*gridSize); //index of first cell in grid
        List<Pair<String, String>> seen = new ArrayList<>();

        for (int y = index.first; y < index.first + gridSize; y++) {
            for (int x = index.second; x < index.second + gridSize; x++) {

                SudokuCell c = cells.get(y).get(x);
                Pair<String, String> pair = findWordPair(c);
                if (pair == null || seen.contains(pair)) {
                    return false;
                }
                seen.add(pair);
            }
        }
        return true;
    }

    private Pair<String, String> findWordPair(SudokuCell cell) {
        String text = cell.getText();
        //Log.d("SUDOKU", "[" + text + "] " + Integer.toString(text.length()));
        for (Pair<String, String> pair: pairs) {
            if (text.length() > 0) {
                //Log.d("SUDOKU", "[" + pair.first + "] == [" + text + "]" + Boolean.toString(pair.first == text) + " " + Integer.toString((int) text.charAt(0)) + " " + Integer.toString((int) pair.first.charAt(0)) + " " + Integer.toString(pair.first.length()) + " " + Integer.toString(text.length()));
            }
            if (text.equals(pair.first) || text.equals(pair.second) ) {
                return pair;
            }
        }
        return null;
    }

    private Pair<Integer,Integer> findIndex(SudokuCell cell) {
        for (int i = 0; i < cells.size(); i++) {
            for(int j = 0; j < cells.get(i).size(); j++) {
                if (cells.get(i).get(j) == cell) {
                    Pair<Integer,Integer> index = new Pair<>(i, j);
                    return index;
                }
            }
        }
        return null;
    }

    private void initBoard(Context context) {
        BufferedReader reader = HelpFunc.readFile(context, R.raw.boardlayout2);
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
                    String firstWord;
                    if (pairIndex != -1) {
                        firstWord = pairs.get(pairIndex).first;
                        cellsFull++;
                    }
                    else {
                        firstWord = "";
                    }
                    SudokuCell cell = new SudokuCell(context, firstWord, this);
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

    private void initPairs(Context context) {
        BufferedReader reader = HelpFunc.readFile(context, R.raw.words);

        String line = "";
        try {
            while ( (line = reader.readLine()) != null ) {
                String[] items = line.split(",");
                if (items.length == 2) {
                    items[0] = HelpFunc.cleanString(items[0]);
                    items[1] = HelpFunc.cleanString(items[1]);
                    pairs.add(new Pair<>(items[0], items[1]));
                }
            }
        }
        catch (IOException e) {
            Log.e(TAG, "could not init pairs");
            e.printStackTrace();
        }

    }



}
