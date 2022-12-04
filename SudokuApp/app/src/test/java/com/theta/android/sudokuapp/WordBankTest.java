package com.theta.android.sudokuapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordBankTest {

    /**
     * test going down a folder when the directory is equal to the root directory
     */
    @Test
    public void changeDirTest1() {
        WordBank wb = new WordBank();

        String rootDir = "";
        String dir = "";
        String testValue = "myFile";
        String expectedValue = "myFile";

        String returnValue = wb.changeDir(dir, testValue, rootDir);

        assertEquals(expectedValue, returnValue);
    }

    /**
     * test going up a folder when the directory is equal to the root directory
     */
    @Test
    public void changeDirTest2() {
        WordBank wb = new WordBank();

        String rootDir = "";
        String dir = "";
        String testValue = "";
        String expectedValue = "";

        String returnValue = wb.changeDir(dir, testValue, rootDir);

        assertEquals(expectedValue, returnValue);
    }

    /**
     * test going down a folder when the directory is not equal to the root directory
     */
    @Test
    public void changeDirTest3() {
        WordBank wb = new WordBank();

        String rootDir = "";
        String dir = "folder1 myfolder";
        String testValue = "myFile";
        String expectedValue = "folder1 myfolder myFile";

        String returnValue = wb.changeDir(dir, testValue, rootDir);

        assertEquals(expectedValue, returnValue);
    }

    /**
     * test going up a folder when the directory is not equal to the root directory
     */
    @Test
    public void changeDirTest4() {
        WordBank wb = new WordBank();

        String rootDir = "";
        String dir = "folder1 myfolder";
        String testValue = "";
        String expectedValue = "folder1";

        String returnValue = wb.changeDir(dir, testValue, rootDir);

        assertEquals(expectedValue, returnValue);
    }
}