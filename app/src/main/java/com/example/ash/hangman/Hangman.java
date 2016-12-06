package com.example.ash.hangman;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ash on 2016-12-04.
 */

public class Hangman {

    // singleton reference
    private static Hangman instance = null;

    // private variables
    private Resources resources;
    private String guess;
    private String[] aryWords;
    private ArrayList<String> aryGuess;
    private String errLong;
    private String errOther;
    private String errorMsg;
    private String output;
    private String word;
    private String guessWord;
    private int guessCount;
    private int difficultyLevel;
    private String difficultyMsg;


    // ----------------------------------------------- singleton constructor method
    public static Hangman getInstance(Resources myResources) {
        if (instance == null){
            instance = new Hangman(myResources);
        }
        return instance;
    }

    // ----------------------------------------------- constructor method
    private Hangman(Resources myResources) {
        // use easy as default difficulty
        aryWords = new String[10];
        aryGuess = new ArrayList<String>();

        guess = "";
        errLong = "";
        errOther = "";
        errorMsg = "";

        guessCount = 8;
        word = "";
        guessWord = "";
        difficultyLevel = -1;

        // pass resources from the view
        resources = myResources;
        output = resources.getString(R.string.guessText);
        aryWords = resources.getStringArray(R.array.aryEasy);
        errLong = resources.getString(R.string.errLong);
        errOther = resources.getString(R.string.error);

        randomize();
    }

    // ------------------------------------------------ get/set methods

    public void setGuess(String myGuess){
        guess = myGuess.toUpperCase();
    }

    public String getError(){
        return errorMsg;
    }

    public String getOutput(){
        return output;
    }

    public String getWord(){
        return guessWord;
    }

    public int getGuessCount(){
        return guessCount;
    }

    public int getDifficultyLevel() {return difficultyLevel;}

    public String getDifficultyMsg() {return difficultyMsg;}

    // ------------------------------------------------ private methods

    private Boolean makeGuess(){

        // check for correct input and subtract from the guest count if true
        if (guess.length() == 1) {
            // add guess to array
            aryGuess.add(guess);

            // convert to array of chars
            char[] targetChars = word.toCharArray();
            char guessChar = guess.charAt(0);

            char[] guessedChars = guessWord.toCharArray();

            guessWord = "";

            // loop through chars and replace with letter or -
            for (int n=0; n<targetChars.length; n++) {
                if (guessChar == targetChars[n]) {
                    // this character matches the answers character
                    guessWord += "" + guessChar;

                } else {
                    // it does not
                    if (guessedChars[n] == '-') {
                        guessWord += "-";
                    } else {
                        guessWord += guessedChars[n];
                    }
                }
            }

            return true;


        } else if (guess.length() > 10) {
            errorMsg = errLong;
            return false;
        }
        else if (guess.length() > 1){
            aryGuess.add(guess);

            if (guess.equals(word)){
                guessWord = word;
            }
            return true;
        }
        else {
            errorMsg = errOther;
            return false;
        }
    }



    private void randomize(){
        //int number = (int) (Math.random() * 10);
        int number = 0;
        word = aryWords[number].toUpperCase();

        // create a hidden word
        for (int i = 0; i < word.length(); i++) {
             guessWord += "-";
         }
    }

    private Boolean checkDuplicates(){
        boolean duplicates = false;

        for (int i = 0; i < aryGuess.size(); i++){
            if (guess.equals(aryGuess.get(i))) {
                duplicates = true;
                errorMsg = resources.getString(R.string.dupGuess);
            } else {
                duplicates = false;
            }
        }
        return duplicates;
    }

    // ------------------------------------------------ public methods

    public void setDifficulty(int myDifficultyLevel){
        // change the difficulty level based on selected difficulty
        difficultyLevel = myDifficultyLevel;
        switch (difficultyLevel){
            case 0:
                reset();
                break;
            case 1:
                reset();
                break;
            case 2:
                reset();
                break;
        }

    }

    public Boolean output(){
        if (makeGuess()) {
            if (checkWin()){
                output = resources.getString(R.string.win);
            } else if (guess.equals("")){
                output = resources.getString(R.string.guessText);;
            } else {
                // lower the guess count
                guessCount--;
                StringBuilder sb = new StringBuilder();
                sb.append(resources.getString(R.string.guessText)).append(System.getProperty("line.separator"));
                for(int i = 0; i < aryGuess.size(); i++) {
                    sb.append(aryGuess.get(i)).append(" ");
                }
                output = sb.toString();
            }

            return true;
        } else {
            return false;
        }
    }

    public Boolean checkWin() {
        if (guessWord.equals(word)){
            return true;
        } else {
            return false;
        }
    }

    public void reset(){

        Arrays.fill(aryWords, null);
        guessWord = "";
        output = resources.getString(R.string.guessText);

        aryGuess.clear();

        if (difficultyLevel == 1){
            aryWords = resources.getStringArray(R.array.aryMedium);
            guessCount = 6;
            difficultyMsg = resources.getString(R.string.mediumDif);

        } else if (difficultyLevel == 2) {
            aryWords = resources.getStringArray(R.array.aryHard);
            guessCount = 5;
            difficultyMsg = resources.getString(R.string.hardDif);
        } else {
            aryWords = resources.getStringArray(R.array.aryEasy);
            guessCount = 8;
            difficultyMsg = resources.getString(R.string.easyDif);
        }

        randomize();

    }
}
