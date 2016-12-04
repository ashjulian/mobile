package com.example.ash.hangman;

import android.content.res.Resources;

import java.util.ArrayList;

/**
 * Created by Ash on 2016-12-04.
 */

public class Hangman {

    // singleton reference
    private static Hangman instance = null;

    // private variables
    private Resources resources;
    private String guess;
    private String[] easyWords;
    private String[] mediumWords;
    private String[] hardWords;
    private String[] aryWords;
    private ArrayList<String> aryGuess;
    private String errLong;
    private String errOther;
    private String errorMsg;
    private String output;
    private String word;
    private int guessCount;


    // ----------------------------------------------- singleton constructor method
    public static Hangman getInstance() {
        if (instance == null){
            instance = new Hangman();
        }
        return instance;
    }

    // ----------------------------------------------- constructor method
    private Hangman() {
        // use easy as default difficulty
        aryWords = new String[10];
        aryGuess = new ArrayList<String>();
        easyWords = new String[10];
        mediumWords = new String[10];
        hardWords = new String[10];

        //aryWords = resources.getStringArray(R.array.aryEasy);
        guess = "";
        errLong = "";
        errOther = "";
        errorMsg = "";
        output = "";
        guessCount = 8;
        word = "";

        randomize();
    }

    // ------------------------------------------------ get/set methods

    public void setResources(Resources myResources) {
        // pass resources from the view
        resources = myResources;
        aryWords = resources.getStringArray(R.array.aryEasy);
        easyWords = resources.getStringArray(R.array.aryEasy);
        mediumWords = resources.getStringArray(R.array.aryMedium);
        hardWords = resources.getStringArray(R.array.aryHard);
        errLong = resources.getString(R.string.errLong);
        errOther = resources.getString(R.string.error);

    }

    public void setGuess(String myGuess){
        guess = myGuess;
    }

    public String getError(){
        return errorMsg;
    }

    public String getOutput(){
        return output;
    }

    public String getWord(){
        return word;
    }

    public int getGuessCount(){
        return guessCount;
    }

    // ------------------------------------------------ private methods

    private Boolean errorCheck(){

        if (guess.length() == 1) {
            aryGuess.add(guess);
            guessCount--;
            return true;
        } else if (guess.length() > 10) {
            errorMsg = errLong;
            return false;
        }
        else if (guess.length() > 1){
            aryGuess.add(guess);
            guessCount--;
            return true;
        }
        else {
            errorMsg = errOther;
            return false;
        }
    }

    public void randomize(){
        int number = (int) (Math.random() * 10);
        word = "word";
    }

    // ------------------------------------------------ public methods

    public void setDifficulty(int difficultyLevel){
        // change the difficulty level based on selected difficulty
        switch (difficultyLevel){
            case 0:
                aryWords = resources.getStringArray(R.array.aryEasy);
                guessCount = 8;
                randomize();
                break;
            case 1:
                aryWords = resources.getStringArray(R.array.aryMedium);
                guessCount = 6;
                randomize();
                break;
            case 2:
                aryWords = resources.getStringArray(R.array.aryHard);
                guessCount = 5;
                randomize();
                break;
        }

    }

    public Boolean makeGuess(){
        if (errorCheck()) {
            StringBuilder sb = new StringBuilder();
            sb.append(resources.getString(R.string.guessText)).append(System.getProperty("line.separator"));
            for(int i = 0; i < aryGuess.size(); i++) {
                sb.append(aryGuess.get(i)).append(" ");
            }
            output = sb.toString();
            return true;
        } else {
            return false;
        }
    }

    public void reset(){
        aryGuess.clear();
    }
}
