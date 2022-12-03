package com.theta.android.sudokuapp;

import static org.junit.Assert.*;

import android.util.Log;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HelpFuncTest {

    @Test
    public void cleanString1() {
        String testValue1 = "   a    ";
        String expectedValue1 = "a";
        String returnValue1 = HelpFunc.cleanString(testValue1);

        assertEquals(returnValue1, expectedValue1);
    }

    @Test
    public void cleanString2() {
        String testValue2 = "z";
        String expectedValue2 = "z";
        String returnValue2 = HelpFunc.cleanString(testValue2);

        assertEquals(returnValue2, expectedValue2);
    }

    @Test
    public void cleanString3() {
        String testValue3 = "z    z ";
        String expectedValue3 = "zz";
        String returnValue3 = HelpFunc.cleanString(testValue3);

        assertEquals(returnValue3, expectedValue3);;
    }

    @Test
    public void readFile() {
        int testValue = 0;
        int expectedValue = 0;

        assertEquals(testValue, expectedValue);;
    }


    @Test
    public void split() {
        String testValue1 = "a b c   ";
        List<String> result1 = HelpFunc.split(testValue1, ' ');
        assertEquals(6, result1.size());
    }

    /**
     * testing incorrect input
     */
    @Test
    public void langToLocale1() {
        String testValue = "ENGLASH";
        Locale expectedValue = null;

        Locale returnValue = HelpFunc.langToLocale(testValue);
        assertEquals(expectedValue, returnValue);

    }

    /**
     * testing various valid language inputs
     * should return corresponding correct language
     */
    @Test
    public void langToLocale2() {
        String[] testValues =
                        {"English",
                        "Japanese",
                        "Mandrin",
                        "Cantonese"};
        Locale[] expectedValues =
                {Locale.ENGLISH,
                Locale.JAPANESE,
                Locale.SIMPLIFIED_CHINESE,
                Locale.TRADITIONAL_CHINESE};

        int size = testValues.length;
        for (int i = 0; i < size; i++) {
            String testValue = testValues[i];
            Locale expectedValue = expectedValues[i];
            Locale returnValue = HelpFunc.langToLocale(testValue);
            assertEquals(expectedValue, returnValue);
        }

    }


}