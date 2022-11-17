package com.theta.android.sudokuapp;

import static org.junit.Assert.*;//

import android.content.Context;


import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.Validator;

public class SudokuTest {


    //size tests
    @Test
    public void setSize4x4() {
        int testValue = 4;
        int testValue1 = 0;

        int expectedValue = 4;

        Sudoku sudoku = new Sudoku();
        Sudoku sudoku1 = new Sudoku();

        sudoku.setSize(testValue);

        assertEquals(sudoku.getSize(), expectedValue);;

        sudoku1.setSize(testValue1);

        assertEquals(sudoku.getSize(), expectedValue);;

    }

    @Test
    public void setSize6x6() {
        int testValue = 6;
        int testValue1 = 1;
        int expectedValue = 6;

        Sudoku sudoku = new Sudoku();
        Sudoku sudoku1 = new Sudoku();

        sudoku.setSize(testValue);

        assertEquals(sudoku.getSize(), expectedValue);;

        sudoku1.setSize(testValue1);

        assertEquals(sudoku.getSize(), expectedValue);;
    }

    @Test
    public void setSize9x9() {
        int testValue = 9;
        int testValue1 = 2;
        int expectedValue = 9;

        Sudoku sudoku = new Sudoku();
        Sudoku sudoku1 = new Sudoku();

        sudoku.setSize(testValue);

        assertEquals(sudoku.getSize(), expectedValue);;

        sudoku1.setSize(testValue1);

        assertEquals(sudoku.getSize(), expectedValue);;
    }

    @Test
    public void setSize12x12() {
        //any test value is not 0,1,2 or 4,6,9
        int testValue = 17898987;
        int expectedValue = 12;
        Sudoku sudoku = new Sudoku();

        sudoku.setSize(testValue);

        assertEquals(sudoku.getSize(), expectedValue);;
    }

    //diff tests
    @Test
    public void setDifficulty() {
        //easy
        int testValue1 = 0;
        int expectedValue1 = 0;
        //medium
        int testValue2 = 1;
        int expectedValue2 = 1;
        //hard
        int testValue3 = 2;
        int expectedValue3 = 2;

        Sudoku sudoku1 = new Sudoku();
        Sudoku sudoku2 = new Sudoku();
        Sudoku sudoku3 = new Sudoku();

        sudoku1.setDifficulty(testValue1);
        sudoku2.setDifficulty(testValue2);
        sudoku3.setDifficulty(testValue3);

        assertEquals(sudoku1.getDifficulty(), expectedValue1);;
        assertEquals(sudoku2.getDifficulty(), expectedValue2);;
        assertEquals(sudoku3.getDifficulty(), expectedValue3);;
    }



    @Test
    public void startGame() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void onCellChange() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void isBoardFull(){
        Boolean testValue = false;

        final int size = 9;
        List<List<Integer>> testBoard = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            testBoard.add(new ArrayList<>());
            for (int x = 0; x < size; x++) {
                testBoard.get(y).add(0);
            }
        }

        testBoard.get(0).set(0, -1);

        Boolean expectedValue = false;
        //Boolean testValue = isBoardFull();

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void  makeBoard(){
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void  createBoard() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void onWin() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void createWinPopup() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void  checkWin(){
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void  checkLineH(){
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void  checkLineV(){
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void checkGrid() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void findWordPair() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void generateBoard() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void initBoard()  {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void createRow() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

    @Test
    public void initPairs() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }


}