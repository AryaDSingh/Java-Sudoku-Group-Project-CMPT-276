package com.theta.android.sudokuapp;

/**
 * Model class for Wordbank
 */
public class WordBank {

    /**
     * changes the directory
     * @param fileName name of the file/folder to change directory to,
     *             if "" goes up a directory instead
     * @return string for the new directory
     */
    public String changeDir(String dir, String fileName, String rootDir) {
        if (fileName.equals("")) {
            if (dir.equals(rootDir)) {
                return dir;
            }
            int i = dir.lastIndexOf(" ");
            if (i == -1) {i = 0;}
            dir = dir.substring(0, i);
        }

        else {
            dir = (dir+" "+fileName).trim();

        }
        return dir;
    }
}
