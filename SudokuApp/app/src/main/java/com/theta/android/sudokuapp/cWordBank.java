package com.theta.android.sudokuapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class cWordBank {
    private LinearLayout layout;
    private Context context;
    private String dir = " ";
    private final String rootDir = " ";
    private List<cWordFile> fileList;

    public void removeFile(cWordFile f) {
        fileList.remove(f);
    }

    public static List<String> getMainPairs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        String dir = prefs.getString(".mainPairDir", "");
        List<String> pairLines = new ArrayList<>();

        int numPairs = prefs.getInt(dir+"  .numpairs", 0);
        for (int i = 0; i < numPairs; i++) {
            String first= prefs.getString(dir+"  ."+i+".first", "");
            String second= prefs.getString(dir+"  ."+i+".second", "");
            pairLines.add(first+","+second);
        }
        return pairLines;
    }


    public cWordBank(Context context, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        getFiles();
    }

    public void setMainPairs(cWordFile file) {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(".mainPairDir", dir+file.getText());
        editor.commit();

        for (cWordFile f: fileList) {
            f.setCheckBox(false);
        }
        file.setCheckBox(true);
    }

    public void addFile(String name, Boolean isFile, Boolean isChecked) {
        name = HelpFunc.cleanString(name);
        for (cWordFile f: fileList) {
            String text = f.getText();
            if (text.equals(name)) {
                return;
            }
        }
        fileList.add(new cWordFile(context, name, layout, this, isFile));
        if (isChecked) {
            fileList.get(fileList.size()-1).setCheckBox(true);
        }
    }

    public void openFile(cWordFile file) {
        Intent intent = new Intent(context, WordPairActivity.class);
        intent.putExtra("fileDir", dir+file.getText());
        context.startActivity(intent);
    }

    public void changeDir(cWordFile file) {
        saveFiles();
        if (file == null) {
            if (dir.equals(rootDir)) {return;}

            int i = dir.lastIndexOf(" ", dir.length()-2);
            dir = dir.substring(0, i+1);

        }
        else {
            dir = dir+file.getText()+" ";
        }
        int size = fileList.size() + 2;
        for (cWordFile f: fileList) {
            layout.removeView(f.getView());
        }
        getFiles();
    }

    private void getFiles() {
        fileList = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        String[] fileNames = prefs.getString(dir+"  .files", " ").split(" ");
        String mainPairDir =  prefs.getString(".mainPairDir", "");
        for (String s: fileNames) {
            Boolean isChecked = mainPairDir.equals(dir+s);
            Boolean isFile = prefs.getBoolean(dir+s+"  .isfile", false);
            addFile(s, isFile, isChecked);
        }

    }

    public void saveFiles() {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String files = "";

        for (cWordFile f: fileList) {
            files += f.getText() + " ";
        }
        files = files.trim();
        if (files.length() == 0) {
            files = " ";
        }

        editor.putString(dir+"  .files", files);

        for (cWordFile file: fileList) {
            String s = file.getText();
            Boolean isFile = file.isFile();
            editor.putBoolean(dir+s+"  .isfile", isFile);
        }
        editor.commit();

    }


}
