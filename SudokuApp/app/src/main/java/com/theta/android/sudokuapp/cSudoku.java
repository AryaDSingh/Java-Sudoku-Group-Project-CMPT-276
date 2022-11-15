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
        int sizeId = SettingsActivity.readSize(context);
        int difficulty = SettingsActivity.readDifficulty(context);
        List<String> pairLines = cWordBank.getMainPairs(context);
        if (pairLines.size() != sudoku.getSize()) {
            pairLines = HelpFunc.readFile(context, R.raw.words);
        }

        sudoku.setSize(sizeId);
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

}