package com.theta.android.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class cSudoku {
    private final String TAG = "sudoku";

    private final Sudoku sudoku;
    private final LinearLayout board;
    private List<List<cSudokuCell>> cells;
    private Context context;

    public cSudoku(Context context, ViewGroup board) {
        this.context = context;
        this.sudoku = new Sudoku();
        this.board = (LinearLayout) board;

        startGame(false);
    }

    private void startGame(Boolean isReplay) {
        int difficulty = SettingsActivity.readDifficulty(context);
        List<String> pairLines = cWordBank.getMainPairs(context);
        if (pairLines.size() != sudoku.getSize()) {
            pairLines = HelpFunc.readFile(context, R.raw.words);
        }

        sudoku.setDifficulty(difficulty);
        sudoku.initPairs(pairLines);
        sudoku.generateBoard();
        if (!isReplay) {
            initBoard();
        }
        generateBoard();
        sudoku.startGame();
    }

    private void generateBoard() {
        final int size = sudoku.getSize();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cSudokuCell cell = cells.get(y).get(x);
                String word = sudoku.getWordAt(y, x);
                cell.setEnabled(word.length() == 0);
                cell.setText(word);
            }
        }
    }

    private void initBoard() {
        final int size = sudoku.getSize();
        cells = new ArrayList<List<cSudokuCell>>(size);

        for (int y = 0; y < size; y++) {
            LinearLayout row = createRow(size);
            for (int x = 0; x < size; x++) {
                cSudokuCell cell = new cSudokuCell(context, this, row);
                cells.get(y).add(cell);
            }
        }
    }

    private LinearLayout createRow(final int size) {
        LinearLayout row = new LinearLayout(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        p.weight = 1;
        row.setLayoutParams(p);
        board.addView(row);
        cells.add(new ArrayList<>(size));
        return row;
    }

    public void onCellChange(cSudokuCell cell) {
        if (sudoku.onCellChange(findIndex(cell), cell.getText())) {
            int score = sudoku.getScore();
            int winTime = sudoku.getTime();
            int moves = sudoku.getMoves();
            createWinPopup(score, winTime, moves);
            StatisticActivity.addStats(context, 1, score, winTime, moves);
        }
    }

    private void createWinPopup(int score, int time, int moves) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View winScreen = inflater.inflate(R.layout.win_screen, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(winScreen);

        final TextView scoreText = (TextView) winScreen.findViewById(R.id.gameScore);
        final TextView timeText = (TextView) winScreen.findViewById(R.id.gameTime);
        final TextView movesText = (TextView) winScreen.findViewById(R.id.gameMoves);
        scoreText.setText(scoreText.getText().toString() + Integer.toString(score));
        timeText.setText(timeText.getText().toString() + Integer.toString(time));
        movesText.setText(movesText.getText().toString() + Integer.toString(moves));

        alert.setCancelable(false);
        alert.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                startGame(true);
            }
        });
        alert.create();
        alert.show();
    }

    private Pair<Integer, Integer> findIndex(cSudokuCell cell) {
        for (int y = 0; y < cells.size(); y++) {
            int x = cells.get(y).indexOf(cell);
            if (x != -1) {
                return new Pair<>(y, x);
            }
        }
        return null;
    }




    /*


    public cSudoku(Context context, ViewGroup board) {
        this.board = (LinearLayout) board;
        this.context = context;


        initBoard(context);
        startGame();
    }

    private void startGame() {
        this.pairs = new ArrayList<>(size);
        this.startTime = Calendar.getInstance().getTimeInMillis();
        this.moves = 0;
        this.cellsFull = 0;

        initPairs(context);
        generateBoard();
    }


    public void onCellChange(SudokuCell cell, int change) {
        cellsFull += change;
        moves++;
        if (cellsFull == size*size) {
            if (checkWin()) {
                onWin();
            }
        }

    }

    private Boolean isBoardFull(List<List<Integer>> boardLayout) {
        for (List<Integer> i: boardLayout) {
            if (i.contains(-1)) {
                return false;
            }
        }
        return true;
    }

    private Boolean makeBoard(List<List<Integer>> boardLayout, List<Integer> vals ) {
        int y=0,x=0;
        for (int i = 0; i < size*size; i++) {
            y = i/size;
            x = i%size;
            if (boardLayout.get(y).get(x) == -1) {
                Collections.shuffle(vals);
                int yGrid = (y/3)*3;
                int xGrid = (x/3)*3;

                for (int val: vals) {
                    Boolean valid = true;
                    for (int j = 0; j < size; j++) { // check grid & col
                        if ((boardLayout.get(j).get(x) == val) || ((boardLayout.get(yGrid+(j/3)).get(xGrid+(j%3))) == val)) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid && !boardLayout.get(y).contains(val)) { //check row
                        boardLayout.get(y).set(x, val);
                        if (isBoardFull(boardLayout)) {
                            return true;
                        }
                        else if (makeBoard(boardLayout, vals)) {
                            return true;
                        }
                    }
                }
                break;
            }
        }

        boardLayout.get(y).set(x, -1);
        return false;
    }

    private List<List<Integer>> createBoard() {
        List<List<Integer>> boardLayout = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            boardLayout.add(new ArrayList<>());
            for (int x = 0; x < size; x++) {
                boardLayout.get(y).add(-1);
            }
        }

        List<Integer> vals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vals.add(i);
        }

        makeBoard(boardLayout, vals);

        if (testing) {
            boardLayout.get(0).set(0, -1);
        }
        else {
            for (int i = 0; i < size*(difficulty+3); i++) {
                int x = new Random().nextInt(size);
                int y = new Random().nextInt(size);

                boardLayout.get(y).set(x, -1);
            }
        }



        return boardLayout;
    }



    private void onWin() {
        //time to complete game in seconds
        int winTime = (int) ((Calendar.getInstance().getTimeInMillis() - startTime) / 1000);
        int score = 10000 - (int) Math.sqrt(winTime*moves);
        //this.moves is number of moves made to beat the game

        StatisticActivity.addStats(context, 1, score, winTime, this.moves);

        createWinPopup(score, winTime, moves);
    }




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

    private void generateBoard() {
        List<List<Integer>> boardLayout = createBoard();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int pairIndex = boardLayout.get(y).get(x);

                String firstWord;
                if (pairIndex != -1) {
                    firstWord = pairs.get(pairIndex).first;
                    cellsFull++;
                }
                else {
                    firstWord = "";
                }

                cells.get(y).get(x).setText(firstWord, false);
            }
        }
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



*/

}
