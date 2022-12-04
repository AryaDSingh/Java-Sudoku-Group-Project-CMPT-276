package com.theta.android.sudokuapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuCellTest {


    @Test
    public void setText() {
        SudokuCell cell = new SudokuCell();

        String testValue = "1";
        cell.setText(testValue);
        String expectedValue = "1";

        String result = cell.getText();

        assertEquals(expectedValue,result);
    }

    /**
     * testing with empty string
     */
    @Test
    public void getFirst1() {
        SudokuCell cell = new SudokuCell();

        String testValue = "";
        cell.setText(testValue);
        String expectedValue = "";

        String result = cell.getFirst2();

        assertEquals(expectedValue,result);
    }

    /**
     * testing with length 1 string
     */
    @Test
    public void getFirst2() {
        SudokuCell cell = new SudokuCell();

        String testValue = "b";
        cell.setText(testValue);
        String expectedValue = "b";

        String result = cell.getFirst2();

        assertEquals(expectedValue,result);
    }

    /**
     * testing with length 2 string
     */
    @Test
    public void getFirst3() {
        SudokuCell cell = new SudokuCell();

        String testValue = "ba";
        cell.setText(testValue);
        String expectedValue = "ba";

        String result = cell.getFirst2();

        assertEquals(expectedValue,result);
    }

    /**
     * testing with normal input
     */
    @Test
    public void getFirst4() {
        SudokuCell cell = new SudokuCell();

        String testValue = "123456abcde";
        cell.setText(testValue);
        String expectedValue = "12";

        String result = cell.getFirst2();

        assertEquals(expectedValue,result);
    }


}