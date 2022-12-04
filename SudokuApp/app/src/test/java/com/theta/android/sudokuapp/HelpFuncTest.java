package com.theta.android.sudokuapp;

import static org.junit.Assert.*;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
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

    /**
     * test multiple 0 occurances of character
     */

    @Test
    public void split1() {
        String testValue1 = "abcdef";
        Character splitAt =  ' ';
        List<String> expectedValue = Arrays.asList("abcdef");

        List<String> result = HelpFunc.split(testValue1, splitAt);
        assertEquals(expectedValue, result);
    }


    /**
     * test multiple occurances of character
     */

    @Test
    public void split2() {
        String testValue1 = "a b c   ";
        Character splitAt =  ' ';
        List<String> expectedValue = Arrays.asList("a","b", "c", "", "", "");

        List<String> result = HelpFunc.split(testValue1, splitAt);
        assertEquals(expectedValue, result);
    }

    /**
     * test regular simple input
     */
    @Test
    public void split3() {
        String testValue1 = "a b";
        Character splitAt =  ' ';
        List<String> expectedValue = Arrays.asList("a","b");

        List<String> result = HelpFunc.split(testValue1, splitAt);
        assertEquals(expectedValue, result);
    }

    /**
     * test empty string
     */
    @Test
    public void split4() {
        String testValue1 = "";
        Character splitAt =  ' ';
        List<String> expectedValue = new ArrayList<>();

        List<String> result = HelpFunc.split(testValue1, splitAt);
        assertEquals(expectedValue, result);
    }

    /**
     * test string that is the same as the character
     */
    @Test
    public void split5() {
        String testValue1 = " ";
        Character splitAt =  ' ';
        List<String> expectedValue = Arrays.asList("","");

        List<String> result = HelpFunc.split(testValue1, splitAt);
        assertEquals(expectedValue, result);
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
     * testing null input
     */
    @Test
    public void langToLocale2() {
        String testValue = null;
        Locale expectedValue = null;

        Locale returnValue = HelpFunc.langToLocale(testValue);
        assertEquals(expectedValue, returnValue);

    }

    /**
     * testing various valid language inputs
     * should return corresponding correct language
     */
    @Test
    public void langToLocale3() {
        String[] testValues =
                {"English",
                "Japanese",
                "Mandrin",
                "Cantonese",
                "French",
                "German",
                "Italian",
                "Korean"};
        Locale[] expectedValues =
                {Locale.ENGLISH,
                Locale.JAPANESE,
                Locale.SIMPLIFIED_CHINESE,
                Locale.TRADITIONAL_CHINESE,
                Locale.FRENCH,
                Locale.GERMAN,
                Locale.ITALIAN,
                Locale.KOREAN};

        int size = testValues.length;
        for (int i = 0; i < size; i++) {
            String testValue = testValues[i];
            Locale expectedValue = expectedValues[i];
            Locale returnValue = HelpFunc.langToLocale(testValue);
            assertEquals(expectedValue, returnValue);
        }

    }


}