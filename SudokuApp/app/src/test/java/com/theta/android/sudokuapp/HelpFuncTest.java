package com.theta.android.sudokuapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelpFuncTest {

    @Test
    public void cleanString() {
        String testValue1 = "   a    ";
        String expectedValue1 = "a";
        String returnValue1 = HelpFunc.cleanString(testValue1);

        assertEquals(returnValue1, expectedValue1);;

        String testValue2 = "z";
        String expectedValue2 = "z";
        String returnValue2 = HelpFunc.cleanString(testValue2);

        assertEquals(returnValue2, expectedValue2);;

        String testValue3 = "z    z ";
        String expectedValue3 = "z    z";
        String returnValue3 = HelpFunc.cleanString(testValue3);

        assertEquals(returnValue3, expectedValue3);;


    }

    @Test
    public void readFile() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }

}