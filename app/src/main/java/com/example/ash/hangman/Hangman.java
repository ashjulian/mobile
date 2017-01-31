package com.example.ash.hangman;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ash on 2016-12-04.
 */

public class Hangman implements Serializable{
    // Constant with a file name
    private static String FILE_NAME = "data.dat";
    private static final long serialVersionUID = 5L;

    // singleton reference
    private static Hangman instance = null;

    // private variables
    private transient Resources resources;
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
    private Boolean loadFlag;


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
        loadFlag = false;

        guessCount = 8;
        word = "";
        difficultyLevel = -1;

        if (guessWord == null){
            guessWord = "";
        }

        // pass resources from the view
        resources = myResources;
        output = resources.getString(R.string.guessText);
        aryWords = resources.getStringArray(R.array.aryEasy);
        errLong = resources.getString(R.string.errLong);
        errOther = resources.getString(R.string.error);

        randomize();
    }

    // ------------------------------------------------------ set resources

    public void setResources(Resources myResources){
        resources = myResources;
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

    public Boolean getLoadFlag() {return loadFlag;}

    // ------------------------------------------------ private methods

    private Boolean makeGuess(){

        // check for correct input and subtract from the guest count if true
        if (guess.length() == 1) {
            // add guess to array
            aryGuess.add(guess);
            Boolean flag = false;

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
                    flag = true;

                } else {
                    // it does not
                    if (guessedChars[n] == '-') {
                        guessWord += "-";

                    } else {
                        guessWord += guessedChars[n];
                    }
                }
            }

            // check to see if guess was correct
            if (!flag) {
                guessCount--;
            }

            return true;


        } else if (guess.length() > 10) {
            errorMsg = errLong;
            return false;
        }
        else if (guess.length() > 1){
            aryGuess.add(guess);

            // check to see if guess is the same as word
            if (guess.equals(word)){
                guessWord = word;
            } else {
                guessCount --;
            }
            return true;
        }
        else {
            errorMsg = errOther;
            return false;
        }
    }



    private void randomize(){
        int number = (int) (Math.random() * 10);
        word = aryWords[number].toUpperCase();

        // create a hidden word
        for (int i = 0; i < word.length(); i++) {
             guessWord += "-";
         }
    }

    private Boolean checkDuplicates(){
        // check for duplicate guesses
        for (int i = 0; i < aryGuess.size(); i++){
            if (guess.equals(aryGuess.get(i))) {
                errorMsg = resources.getString(R.string.dupGuess);
                return true;
            }
        }
        return false;
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
        // build a string to output to the user return false if string is error msg
        if (!checkDuplicates()){
            if (makeGuess()) {
                if (checkWin()){
                    output = resources.getString(R.string.win);
                } else if (guess.equals("")){
                    output = resources.getString(R.string.guessText);;
                } else {
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
        return false;
    }

    public Boolean checkWin() {
        // check to see if game has been won
        if (guessWord.equals(word)){
            return true;
        } else {
            if (guessCount == 0){
                guessWord = word;
            }
            return false;
        }
    }

    public void reset(){

        // reset the game
        Arrays.fill(aryWords, null);
        guessWord = "";
        output = resources.getString(R.string.guessText);

        aryGuess.clear();

        // set the difficulty level
        if (difficultyLevel == 1){
            aryWords = resources.getStringArray(R.array.aryMedium);
            guessCount = 6;

        } else if (difficultyLevel == 2) {
            aryWords = resources.getStringArray(R.array.aryHard);
            guessCount = 5;
        } else {
            aryWords = resources.getStringArray(R.array.aryEasy);
            guessCount = 8;
        }

        randomize();

    }

    public void saveGame(Context context){
        try {
            //setup the streams
            FileOutputStream outFileStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream outDataStream = new ObjectOutputStream(outFileStream);

            //write values of primitive data types to the stream
            outDataStream.writeObject(this);

            //output done, so flush and close the stream
            outDataStream.flush();
            outDataStream.close();
            outFileStream.close();

            Log.d("Ash", "Saved Sucessfully!");

        } catch (Exception e) {
            Log.d("Ash","!!! EXCEPTION", e);
        }
    }

    public Hangman loadGame(Context context) {
        Hangman hangman = null;

        try {
            //setup file and stream
            FileInputStream inFileStream = context.openFileInput(FILE_NAME);
            ObjectInputStream inDataStream = new ObjectInputStream(inFileStream);

            //read values back from the stream and display them
            hangman = (Hangman) inDataStream.readObject();

            inFileStream.close();
            inDataStream.close();

            Log.d("Ash", "Loaded successfully!");

        } catch (Exception e) {
            Log.d("Ash","!!! EXCEPTION", e);
        }

        return hangman;
    }

    public Boolean checkSave(Context context){
        File file = context.getFileStreamPath(FILE_NAME);

        if(file.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
