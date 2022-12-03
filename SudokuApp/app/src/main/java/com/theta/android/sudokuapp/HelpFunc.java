package com.theta.android.sudokuapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * various helper functions used in various classes
 */
public final class HelpFunc {
    private HelpFunc() {
    }

    /**
     * cleans a string of whitespaces and invisible characters
     * @param s string to be cleaned
     * @return cleaned string without whitespaces
     */
    public static String cleanString(String s) {
        return s.replaceAll("[\u200B-\u200D\uFEFF]", "").trim().replace(" ","");
    }

    /**
     * modified version of the string.split()
     * Splits even if there are no characters adjacent to splitting character
     *
     * @param s string to split
     * @param splitAt characters to split at
     * @return string list splitted at the specified character
     */
    public static List<String> split(String s, char splitAt) {
        List<String> splitList= new ArrayList<>();
        if (s.equals("")) {return splitList;}

        int prevSplit = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == splitAt) {
                splitList.add(s.substring(prevSplit, i));
                prevSplit = i+1;
            }
        }
        splitList.add(s.substring(prevSplit, s.length()));
        return splitList;
    }

    /**
     * Converts the name of a language(String) to the correct Locale value
     * @param lang The language string to be converted
     * @return The corresponding Locale language
     */
    public static Locale langToLocale(String lang) {
        switch (lang) {
            case "English":
                return Locale.ENGLISH;
            case "Japanese":
                return Locale.JAPANESE;
            case "Mandrin":
                return Locale.SIMPLIFIED_CHINESE;
            case "Cantonese":
                return Locale.TRADITIONAL_CHINESE;
            default:
                return null;
        }
    }

}

