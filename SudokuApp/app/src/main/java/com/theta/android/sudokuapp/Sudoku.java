package com.theta.android.sudokuapp;

import android.util.Log;
import androidx.core.util.Pair;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Sudoku Model class
 */
public class Sudoku {
    private int size; //side length size
    private int gridH;// this should be smaller than (or equal) to gridW
    private int gridW;// this should be greater than (or equal) to gridW
    private int difficulty; //0 = easy, 1 = medium, 2=hard
    private List<Pair<String, String>> pairs;
    private List<List<String>> cells;
    private long startTime;
    private int moves;

    // change this to true if you only want 1 empty cell upon creating a game
    private final Boolean testing = true;

    public int getGridW() {
        return gridW;
    }

    public int getGridH() {
        return gridH;
    }

    public int getMoves() {
        return moves;
    }

    public int getDifficulty() { return difficulty; }

    public int getSize() {
        return size;
    }

    public int getTime() {
        return (int) ((Calendar.getInstance().getTimeInMillis() - startTime) / 1000);
    }
    public int getScore() {
        int winTime = getTime();
        return 10000 - (int) Math.sqrt(winTime*moves);
    }

    public String getWordAt(int y, int x) {
        return cells.get(y).get(x);
    }

    public void setDifficulty(final int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * initializes the board size and grid sizes using a size id
     * @param sizeId id that represents the board size
     */
    public void setSize(int sizeId) {
        if (sizeId == 0 || sizeId == 4) {
            size = 4;
            gridH = 2;
            gridW = 2;
        }
        else if (sizeId == 1 || sizeId == 6) {
            size = 6;
            gridH = 2;
            gridW = 3;
        }
        else if (sizeId == 2 || sizeId == 9) {
            size = 9;
            gridH = 3;
            gridW = 3;
        }
        else{
            size = 12;
            gridH = 3;
            gridW = 4;
        }
    }

    /**
     * saves board state as a csv string
     * @return csv string of the board state
     */
    public String getSaveString() {
        String s = "";
        for (List<String> row: cells) {
            for(String cell: row) {
                s = s + cell + " ,";
            }
        }
        s = s.substring(0, s.length()-1);
        return s;
    }

    /**
     * initializes values before starting a game
     */
    public void startGame() {
        this.startTime = Calendar.getInstance().getTimeInMillis();
        this.moves = 0;
    }

    /**
     * checks if all the cells in the sudoku are filled in
     * @param boardLayout cells value list
     * @return True if all cells are filled in, else false
     */
    private Boolean isBoardFull(List<List<Integer>> boardLayout) {
        for (List<Integer> i: boardLayout) {
            if (i.contains(-1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * initializes board data using a csv data from a save
     * @param layout csv string that represents a valid board state
     */
    public void loadSave(String layout) {
        cells = new ArrayList<>();
        String[] cellStrings = layout.split(",");
        for (int y = 0; y < size; y++) {
            cells.add(new ArrayList<>());
            for (int x = 0; x < size; x++) {
                cells.get(y).add(cellStrings[y*size+x].trim());
            }
        }
    }

    /**
     * recursive backtracking algorithm to create a fully solved random sudoku board
     * @param boardLayout a sudoku board that is initialed as empty (contains only -1 as values)
     * @param vals integers to populate the sudoku board with.
     * @return a fully solved sudoku board in the form of a list
     */
    private Boolean makeBoard(List<List<Integer>> boardLayout, List<Integer> vals ) {
        int y=0,x=0;
        for (int i = 0; i < size*size; i++) {
            y = i/size;
            x = i%size;
            if (boardLayout.get(y).get(x) == -1) {
                Collections.shuffle(vals);
                int yGrid = (y/gridH)*gridH;
                int xGrid = (x/gridW)*gridW;

                for (int val: vals) {
                    Boolean valid = true;
                    for (int j = 0; j < size; j++) { // check grid & col
                        if ((boardLayout.get(j).get(x) == val) || ((boardLayout.get(yGrid+(j%gridH)).get(xGrid+(j/gridH))) == val)) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid && !boardLayout.get(y).contains(val)) { //check row
                        boardLayout.get(y).set(x, val);
                        if (isBoardFull(boardLayout)) {
                            return true;
                        }
                        else if (makeBoard(boardLayout, vals)) {
                            return true;
                        }
                    }
                }
                break;
            }
        }

        boardLayout.get(y).set(x, -1);
        return false;
    }

    /**
     * creates a random sudoku board and fills it with numbers from -1 to size-1
     * -1 indicates an empty space while other numbers represent a word pair
     * @return a valid incomplete sudoku board represented as a 2d list
     */
    public List<List<Integer>> createBoard() {
        List<List<Integer>> boardLayout = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            boardLayout.add(new ArrayList<>());
            for (int x = 0; x < size; x++) {
                boardLayout.get(y).add(-1);
            }
        }

        List<Integer> vals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vals.add(i);
        }

        makeBoard(boardLayout, vals);

        if (testing) {
            boardLayout.get(0).set(0, -1);
        }
        else {
            for (int i = 0; i < size*(difficulty+3); i++) {
                int x = new Random().nextInt(size);
                int y = new Random().nextInt(size);

                boardLayout.get(y).set(x, -1);
            }
        }
        return boardLayout;
    }


    /**
     * Checks board cells to see if player has completed the game
     *
     * @return True if the player has won the game
     */
    public Boolean checkWin() {
        for (int i = 0; i < size; i++) {
            Boolean lineV = checkLineV(new Pair<>(0, i));
            Boolean lineH = checkLineH(new Pair<>(i, 0) );
            Boolean grid = checkGrid(new Pair<>((i/gridH)*gridH, (i%gridH)*gridH));

            if (!(lineV && lineH && grid)){
                return false;
            }
        }
        return true;
    }

    /**
     * checks and validates horizontal line at specified index
     * @param index y, x coordinate of line to check
     * @return true if line is completed and only contains unique values.
     */
    private boolean checkLineH(Pair<Integer,Integer> index) {
        List<Pair<String,String>> seen = new ArrayList<>();

        for (String word: cells.get(index.first)) {
            Pair<String,String> pair = findWordPair(word);
            if (pair == null || seen.contains(pair)) {
                return false;
            }
            seen.add(pair);
        }
        return true;
    }

    /**
     * checks and validates vertical line at specified index
     * @param index y, x coordinate of line to check
     * @return true if line is completed and only contains unique values.
     */
    private Boolean checkLineV(Pair<Integer,Integer> index) {
        List<Pair<String,String>> seen = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String word = cells.get(i).get(index.second);
            Pair<String,String> pair = findWordPair(word);
            if (pair == null || seen.contains(pair)) {
                return false;
            }
            seen.add(pair);
        }
        return true;
    }

    /**
     * checks and validates grid at specified index
     * @param index y, x coordinate of grid to check
     * @return true if grid is completed and only contains unique values.
     */
    private Boolean checkGrid(Pair<Integer,Integer> index) {
        index = new Pair<>((index.first/gridH)*gridH,(index.second/gridW)*gridW); //index of first cell in grid
        List<Pair<String, String>> seen = new ArrayList<>();

        for (int y = index.first; y < index.first + gridH; y++) {
            for (int x = index.second; x < index.second + gridW; x++) {
                String word = cells.get(y).get(x);
                Pair<String, String> pair = findWordPair(word);
                if (pair == null || seen.contains(pair)) {
                    return false;
                }
                seen.add(pair);
            }
        }
        return true;
    }

    /**
     * finds and returns word pair that matches the sting
     * @param text string to search for in word pairs list
     * @return word pair that matches the string otherwise null
     */
    public Pair<String, String> findWordPair(String text) {
        for (Pair<String, String> pair: pairs) {
            if (text.equals(pair.first) || text.equals(pair.second) ) {
                return pair;
            }
        }
        return null;
    }

    /**
     * finds the index of the string in pairs list
     * @param text the string to search from in word pairs list
     * @return index of specified string in pairs list
     */
    public int getPairIndex(String text) {
        int size = pairs.size();
        for (int i = 0; i < size; i++) {
            Pair<String, String> pair = pairs.get(i);
            if (text.equals(pair.first) || text.equals(pair.second)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * initializes cells list and populates it with correct word pairs.
     */
    public void generateBoard() {
        List<List<Integer>> boardLayout = createBoard();
        cells = new ArrayList<>();

        for (int y = 0; y < size; y++) {
            cells.add(new ArrayList<>());
            for (int x = 0; x < size; x++) {
                int pairIndex = boardLayout.get(y).get(x);

                String firstWord;
                if (pairIndex != -1) {
                    firstWord = pairs.get(pairIndex).first;
                }
                else {
                    firstWord = "";
                }

                cells.get(y).add(firstWord);
            }
        }
    }

    /**
     * initializes and populates pairs list
     * @param lines pair data in a csv format
     */
    public void initPairs(List<String> lines) {
        pairs = new ArrayList<>();
        for (String line: lines) {
            String[] items = line.split(",");
            if (items.length == 2) {
                items[0] = HelpFunc.cleanString(items[0]);
                items[1] = HelpFunc.cleanString(items[1]);
                pairs.add(new Pair<>(items[0], items[1]));
            }
        }

    }

    /**
     * Event that is called upon any grid cell changing text
     * @param index location of the cell that called this event
     * @param value new text of the cell that called this event
     * @return True if player has won the game, else false
     */
    public Boolean onCellChange(Pair<Integer, Integer> index, String value) {
        moves++;
        cells.get(index.first).set(index.second, value);

        return checkWin();
    }


}
