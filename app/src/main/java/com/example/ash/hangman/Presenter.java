package com.example.ash.hangman;

import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;

import static android.R.attr.bitmap;

public class Presenter {
    private MainView mainView;
    private Hangman hangman;
    private Hangman stickMan;

    public Presenter(MainView myMainView){
        mainView = myMainView;
        hangman = hangman.getInstance(mainView.getResources());

        //loadState();

        populate();

    }

    // ----------------------------------------------------------------- private methods

    // --------------------------------------------------------------- public methods

    public void guess() {

        hangman.setGuess(mainView.getGuess());

        if (hangman.output()) {

            mainView.setText(hangman.getWord(), hangman.getOutput());
            mainView.setImage(hangman.getGuessCount(), hangman.getDifficultyLevel());

            if (hangman.getGuessCount() == 0 || hangman.checkWin()){
                    mainView.gameOver();
            }

        } else {
            mainView.showError(hangman.getError());
        }

    }

    public void saveState(){
        Thread thread = new Thread(
                // argument list of the thread constructor
                new Runnable(){
                    @Override
                    public void run() {
                        // any code in here is running on the separate thread
                        try {

                            hangman.saveGame(mainView.getApplicationContext());

                        } catch (Exception e) {
                            // this Log.d will actually dump out the whole stack trace
                            Log.d("ash", "!!! Exception", e);
                        }
                    }
                }
        );
        thread.start();

    }

    public void loadState(){
        Thread thread = new Thread(
                // argument list of the thread constructor
                new Runnable(){
                    @Override
                    public void run() {
                        // any code in here is running on the separate thread
                        try {
                                Log.d("Ash", "You're running this code.");
                                // update hangman with loaded data
                                stickMan = hangman.loadGame(mainView.getApplicationContext());
                                hangman = stickMan;
                                hangman.setResources(mainView.getResources());
                                //hangman.getInstance(mainView.getResources());

                            mainView.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    populate();
                                }
                            });

                        } catch (Exception e) {
                            // this Log.d will actually dump out the whole stack trace
                            Log.d("ash", "!!! Exception", e);
                        }
                    }
                }
        );
        thread.start();
    }

    public void populate(){
        // set the text for the game
        mainView.setText(hangman.getWord(), hangman.getOutput());

        // check to see if it's a win or lose
        if (hangman.getGuessCount() == 0 || hangman.checkWin()) {
            mainView.gameOver();
        } else{
            mainView.gameStart();
        }

        // set the image
        mainView.setImage(hangman.getGuessCount(), hangman.getDifficultyLevel());
    }

    // --------------------------------------------------------------------- private methods


}
