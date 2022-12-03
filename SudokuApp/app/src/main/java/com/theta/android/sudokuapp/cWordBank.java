package com.theta.android.sudokuapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Wordbank Controller class
 *
 * -This is a custom implementation of a file/folder system
 * using sharedpreferences that stores it in local storage.
 * -This does not use the android file system.
 * -Is similar to the "File Explorer" that can be found on windows computers
 * -This was created because using csv files and navigating android file systems
 * can be difficult for users. However, csv files are also support in our current app.
 *
 * Note:
 * one space (" ") means moving down a directory
 * two spaces and a dot ("  .") means accessing an attribute of that file/folder
 *
 * This was done so special characters (such as "\") are still usable in file/folder names.
 */
public class cWordBank {
    private final static String rootDir = ""; // root directory
    private final static String defaultDir = "Default_Pairs"; //default word pair directory
    private final static String practiceDir = "Practice"; //Practice folder directory
    private final static String mainPairDir = "  .mainPairDir"; //directory that points the the selected file

    private LinearLayout layout;
    private Context context;
    private String dir = ""; // the current directory
    private List<cWordFile> fileList;

    /**
     * loads root directory files
     * @param context current context
     * @param layout layout to display files in
     */
    public cWordBank(Context context, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        getFiles();

    }

    /**
     * gets file/folder name
     * @param prefs shardpreferences to access
     * @param filedir the path of the file/folder
     * @return name of the file/folder
     */
    private static String getFileName(SharedPreferences prefs, String filedir) {
        return prefs.getString(filedir+"  .name", null);
    }

    /**
     * gets if file/folder is deletable
     * @param prefs sharedpreferences to access
     * @param dir the directory to open the file/folder from
     * @param name the name of the file/folder
     * @return True if file/folder is deletable, else false
     */
    private Boolean getFileisDel(SharedPreferences prefs, String dir, String name) {
        return prefs.getBoolean((dir+" "+name).trim()+"  .isDel", true);
    }

    /**
     * gets if is a file or folder
     * @param prefs sharedpreferences to access
     * @param dir the directory to open the file/folder from
     * @param name the name of the file/folder
     * @return True if is a file, false if is a folder
     */
    private Boolean getFileisFile(SharedPreferences prefs, String dir, String name) {
        return prefs.getBoolean((dir+" "+name).trim()+"  .isFile", false);
    }

    /**
     * saves file/folder perameters to Shared preferences
     * @param editor sharedpreferences editor to save to
     * @param dir file/folder path
     * @param name name of the file/folder
     * @param isFile True if is a file, false if its a folder
     * @param isDel True if file/folder should be deletable
     */
    private static void setFilePerms(SharedPreferences.Editor editor, String dir, String name, boolean isFile, boolean isDel) {
        String fileDir = (dir+" "+name).trim();
        editor.putString(fileDir+"  .name", name);
        editor.putBoolean(fileDir+"  .isFile", isFile);
        editor.putBoolean(fileDir+"  .isDel", isDel);
        editor.commit();
    }

    /**
     * saves current files to directory
     * @param editor sharedprefences editor to save to
     * @param dir directory to save files to
     * @param files files to save (string is a csv format)
     */
    private static void setFilesInDir(SharedPreferences.Editor editor, String dir, String files) {
        editor.putString(dir+"  .files", files);
        editor.commit();
    }

    /**
     * returns files in the directory
     * @param prefs sharedprefernces to open
     * @param dir directory to retreive files from
     * @return returns a csv of files
     */
    private static List<String> getFilesInDir(SharedPreferences prefs, String dir) {
        return HelpFunc.split(prefs.getString(dir+"  .files", ""), ' ');
    }

    /**
     * saves the files in the current directory
     */
    public void saveFiles() {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String files = "";

        for (cWordFile f: fileList) {
            files += f.getText() + " ";
        }
        files = files.trim();

        setFilesInDir(editor, dir, files);

        for (cWordFile file: fileList) {
            String s = file.getText();
            Boolean isFile = file.isFile();
            Boolean isDel = file.isDel();
            setFilePerms(editor, dir, s, isFile, isDel);
        }
        editor.commit();

    }

    /**
     * gets the files in the current directory
     */
    private void getFiles() {
        fileList = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);

        List<String> fileNames = getFilesInDir(prefs, dir);
        String MainPairDir = prefs.getString(mainPairDir, rootDir);
        for (String s: fileNames) {
            Boolean isChecked = MainPairDir.equals((dir+" "+s).trim());
            Boolean isFile = getFileisFile(prefs, dir, s);
            Boolean isDel = getFileisDel(prefs, dir, s);
            addFile(s, isFile, isChecked, isDel);
        }

    }

    /**
     * creates the default files/folders for when a user first opens the app
     * @param prefs shared preferences to save to
     */
    private static void createDefaults(SharedPreferences prefs) { //needs improving
        String[] defaultList = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        SharedPreferences.Editor editor = prefs.edit();


        setFilePerms(editor, rootDir, practiceDir, false, false);
        setFilePerms(editor, rootDir, defaultDir, true, false);

        setFilesInDir(editor, rootDir, practiceDir+" "+defaultDir);

        editor.putInt(defaultDir+"  .numpairs", defaultList.length);
        for (int i = 0; i < defaultList.length; i++) {
            editor.putString(defaultDir+"  ."+i+".first", Integer.toString(i+1));
            editor.putString(defaultDir+"  ."+i+".second", defaultList[i]);
        }

        editor.commit();
    }

    /**
     * changes the directory
     * @param file name of the file/folder to change directory to,
     *             if null goes up a directory instead
     */
    public void changeDir(cWordFile file) {
        saveFiles();
        if (file == null) {
            if (dir.equals(rootDir)) {return;}

            int i = dir.lastIndexOf(" ");
            if (i == -1) {i = 0;}
            dir = dir.substring(0, i);

        }
        else {
            dir = (dir+" "+file.getText()).trim();

        }
        while (fileList.size() > 0) {
            removeFile(fileList.remove(0));
        }
        getFiles();
    }

    /**
     * removes a file from the current directory
     * @param f the file to be removed
     */
    public void removeFile(cWordFile f) {
        layout.removeView(f.getView());
        fileList.remove(f);
    }

    /**
     * opens a file in the current directory
     * @param file the file to open
     */
    public void openFile(cWordFile file) {
        Intent intent = new Intent(context, WordPairActivity.class);
        intent.putExtra("fileDir", (dir+" "+file.getText()).trim());
        intent.putExtra("isDel", file.isDel());
        context.startActivity(intent);
    }

    /**
     * creates a file/folder in the current directory
     * @param name string for the name of the file/folder
     * @param isFile True if is a file, false if is a folder
     * @param isChecked true if it is the currently selected word pair for the sudoku
     * @param isDel true if it can be deleted
     */
    public void addFile(String name, Boolean isFile, Boolean isChecked, Boolean isDel) {
        name = HelpFunc.cleanString(name);
        for (cWordFile f: fileList) {
            String text = f.getText();
            if (text.equals(name)) {
                return;
            }
        }
        fileList.add(new cWordFile(context, name, layout, this, isFile, isDel));
        if (isChecked) {
            fileList.get(fileList.size()-1).setCheckBox(true);
        }
    }

    /**
     * sets the word pair for the sudoku
     * @param file the file to be selected
     */
    public void setMainPairs(cWordFile file) {
        setMainPairs((dir+" "+file.getText()).trim());

        for (cWordFile f: fileList) {
            f.setCheckBox(false);
        }
        file.setCheckBox(true);
    }

    /**
     * sets the word pair for the sudoku
     * @param fileDir the directory of the file to be selected
     */
    private void setMainPairs(String fileDir) {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(rootDir+"  .mainPairDir", fileDir);
        editor.commit();

    }

    /**
     * gets the words from the currently selected word pair
     * @param context the current context
     * @return the list of words from the select word pair file
     */
    public static List<String> getMainPairs(Context context) {
        return getMainPairs(context, false);
    }

    /**
     * gets the words from the currently selected word pair
     * @param context the current context
     * @param ignorePractice true if practice pairs should be used
     * @return the list of words from the select word pair file
     */
    private static List<String> getMainPairs(Context context, Boolean ignorePractice) {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        List<String> pairLines = new ArrayList<>();

        String mainDir = prefs.getString(mainPairDir, rootDir);
        if (mainDir == rootDir) {
            createDefaults(prefs);
            mainDir = prefs.getString(mainPairDir, rootDir);
        }
        else if(SettingsActivity.readPracticeMode(context) == true && !ignorePractice) {
            List<String> fileNames = getFilesInDir(prefs, practiceDir);
            if (fileNames.size() != 0) {
                mainDir = (practiceDir+" "+fileNames.get(new Random().nextInt(fileNames.size()))).trim();
            }
        }

        int numPairs = prefs.getInt(mainDir+"  .numpairs", 0);
        for (int i = 0; i < numPairs; i++) {
            String first= prefs.getString(mainDir+"  ."+i+".first", "");
            String second= prefs.getString(mainDir+"  ."+i+".second", "");
            pairLines.add(first+","+second);
        }
        return pairLines;
    }

    /**
     * gets the default pairs (1-12) & (one-twelve)
     * @param context the current context
     * @return a list of the default pairs
     */
    public static List<String> getDefaultPairs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("  .mainPairDir", defaultDir);
        editor.commit();

        return getMainPairs(context, true);
    }

    /**
     * adds the current selected pair to the practice pairs folder
     * this is called when player indicates that the current pair was difficult
     * @param context the current context
     */
    public static void addPractice(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        copyFile(context, prefs.getString(mainPairDir, rootDir), rootDir+practiceDir);
    }

    /**
     * copies a file to a directory
     * @param context the current context
     * @param fileDir the location of the file
     * @param toDir the directory to copy the file to
     */
    public static void copyFile(Context context, String fileDir, String toDir) {
        SharedPreferences prefs = context.getSharedPreferences("WordBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String fileName = getFileName(prefs, fileDir);
        editor.putString(toDir+"  .files", (prefs.getString(toDir+"  .files", " ")+" "+fileName).trim());
        setFilePerms(editor, toDir, fileName, true, true);

        toDir = (toDir+" "+fileName).trim();
        int numPairs = prefs.getInt(fileDir+"  .numpairs", 0);
        editor.putInt(toDir+"  .numpairs", numPairs);
        for (int i = 0; i < numPairs; i++) {
            String first= prefs.getString(fileDir+"  ."+i+".first", "");
            String second= prefs.getString(fileDir+"  ."+i+".second", "");

            editor.putString(toDir+"  ."+i+".first",first);
            editor.putString(toDir+"  ."+i+".second",second);
        }

        editor.commit();
    }



}
