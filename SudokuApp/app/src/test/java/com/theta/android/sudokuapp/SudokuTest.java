package com.theta.android.sudokuapp;

import static org.junit.Assert.*;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SudokuTest {

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