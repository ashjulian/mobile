package com.example.ash.hangman;

/**
 * Created by Ash on 2016-12-04.
 */

public class Presenter {
    private View view;
    private Hangman hangman;

    public Presenter(View myView){
        view = myView;
        hangman = hangman.getInstance();
        hangman.setResources(view.getResources());


    }

    // ----------------------------------------------------------------- private methods

    // --------------------------------------------------------------- public methods

    public void guess() {
        hangman.setGuess(view.getGuess());
        hangman.randomize();

        if (hangman.makeGuess()) {
            view.setText(hangman.getWord(), hangman.getOutput());
        } else {
            view.showError(hangman.getError());
        }


    }
}
