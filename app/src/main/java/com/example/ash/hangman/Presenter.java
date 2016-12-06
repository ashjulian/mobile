package com.example.ash.hangman;

/**
 * Created by Ash on 2016-12-04.
 */

public class Presenter {
    private View view;
    private Hangman hangman;

    public Presenter(View myView){
        view = myView;
        hangman = hangman.getInstance(view.getResources());

        view.setText(hangman.getWord(), hangman.getOutput());

        if (hangman.getDifficultyLevel() != -1) {
            view.showDifficulty(hangman.getDifficultyMsg());
        }

    }

    // ----------------------------------------------------------------- private methods

    // --------------------------------------------------------------- public methods

    public void guess() {
        hangman.setGuess(view.getGuess());

        if (hangman.output()) {
            view.setText(hangman.getWord(), hangman.getOutput() + "guess : " + hangman.getGuessCount());
        } else {
            view.showError(hangman.getError());
        }


    }
}
