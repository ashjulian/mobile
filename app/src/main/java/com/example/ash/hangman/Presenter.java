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


    }

    // ----------------------------------------------------------------- private methods

    // --------------------------------------------------------------- public methods

    public void guess(){
        hangman.setGuess(view.getGuess());

        if (hangman.errorCheck()){
            view.setText("true", "true");
        } else {
            view.setText("false", "false");
        }
    }
}
