package com.example.ash.hangman;

import android.content.res.Resources;

/**
 * Created by Ash on 2016-12-04.
 */

public class Hangman {

    // singleton reference
    private static Hangman instance = null;

    // private variables
    private Resources resources;
    private String guess;
    private int count;
    private String[] easyWords;
    private String[] mediumWords;
    private String[] hardWords;
    private String[] aryWords;
    private String[] aryGuesses;


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
        aryWords = easyWords;
        count = 0;
    }

    // ------------------------------------------------ get/set methods

    public void setResources(Resources myResources) {
        // pass resources from the view
        resources = myResources;
        easyWords = resources.getStringArray(R.array.aryEasy);
        mediumWords = resources.getStringArray(R.array.aryMedium);
        hardWords = resources.getStringArray(R.array.aryHard);

    }

    public void setGuess(String myGuess){
        guess = myGuess;
    }

    public void getError(){

    }

    // ------------------------------------------------ private methods

    public Boolean errorCheck(){
        if (guess.length() == 1) {
            aryGuesses[count] = guess;
            count++;
            return true;
        } else if (guess.equals("")) {
            return false;
        }
        else if (guess.length() > 1){
            char[] chars = guess.toCharArray();

            for (char c : chars) {
                if(!Character.isLetter(c)) {
                    return false;
                }
            }

            aryGuesses[count] = guess;
            count++;
            return true;
        }
        else {
            return false;
        }
    }

    // ------------------------------------------------ public methods

    public void setDifficulty(int difficultyLevel){
        switch (difficultyLevel){
            case 0:
                aryWords = easyWords;
                break;
            case 1:
                aryWords = mediumWords;
                break;
            case 2:
                aryWords = hardWords;
        }


    }
}
