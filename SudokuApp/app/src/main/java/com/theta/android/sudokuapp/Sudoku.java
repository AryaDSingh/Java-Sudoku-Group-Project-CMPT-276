package com.theta.android.sudokuapp;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    private final int size = 9*9;
    private final int subSize = 9; //subgrid size
    //private final List<List<SudokuCell>> cells;
    private final LinearLayout board;

    public Sudoku(Context context, ViewGroup board) {
        this.board = (LinearLayout) board;

        fillBoard(context);
    }

    private void fillBoard(Context context) {

        for (int x = 0; x < subSize; x++) {
            LinearLayout row = new LinearLayout(context);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
            p.weight = 1;
            row.setLayoutParams(p);
            board.addView(row);
            for (int y = 0; y < subSize; y++) {
                SudokuCell cell = new SudokuCell(context, Integer.toString(x*10+y));
                row.addView(cell.getView());
            }
        }
    }

}
