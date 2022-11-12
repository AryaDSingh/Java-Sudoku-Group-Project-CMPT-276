package com.theta.android.sudokuapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class cWordBank {
    private LinearLayout layout;
    private Context context;
    private String dir = "~/";
    private final String rootDir = "~/";
    private List<cWordFile> fileList;

    public void removeFile(cWordFile f) {
        fileList.remove(f);
    }

    public cWordBank(Context context, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        getFiles();
    }

    public void addFile(String name, Boolean isFile) {
        fileList.add(new cWordFile(context, name, layout, this, isFile));
    }

    public void openFile(cWordFile file) {
        int i = fileList.indexOf(file);
        Intent intent = new Intent(context, WordPairActivity.class);
        intent.putExtra("fileDir", dir+i);
        context.startActivity(intent);
    }

    public void changeDir(cWordFile file) {
        saveFiles();
        if (file == null) {
            if (dir.equals(rootDir)) {return;}

            int i = dir.lastIndexOf("/", dir.length()-2);
            dir = dir.substring(0, i+1);

        }
        else {
            int index = fileList.indexOf(file);
            dir = dir+index+"/";
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
        
        int numFiles = prefs.getInt(dir+"#", 0);
        for (int i = 0; i < numFiles; i++) {
            String name = prefs.getString(dir+i+".name", "");
            Boolean isFile = prefs.getBoolean(dir+i+".isfile", false);
            addFile(name, isFile);
        }

    }

    public void saveFiles() {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int numFiles = fileList.size();
        editor.putInt(dir+"#", numFiles);
        for (int i = 0; i < numFiles; i++) {
            cWordFile file = fileList.get(i);
            String name = file.getText();
            Boolean isFile = file.isFile();
            editor.putString(dir+i+".name", name);
            editor.putBoolean(dir+i+".isfile", isFile);
        }
        editor.commit();

    }


}
