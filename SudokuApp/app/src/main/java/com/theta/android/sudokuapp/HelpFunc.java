package com.theta.android.sudokuapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class HelpFunc {
    private HelpFunc() {
    }

    public static String cleanString(String s) {
        return s.replaceAll("[\u200B-\u200D\uFEFF]", "").trim();
    }

    public static BufferedReader readFile(Context context, int file) {
        InputStream is = context.getResources().openRawResource(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        return reader;
    }
}
