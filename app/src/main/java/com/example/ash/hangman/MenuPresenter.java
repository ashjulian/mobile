package com.example.ash.hangman;


public class MenuPresenter {

    private MenuView menuView;
    private Hangman hangman;


    public MenuPresenter(MenuView myMenuView){
        menuView = myMenuView;
        hangman = hangman.getInstance(menuView.getResources());
    }

    // ------------------------------------------------------- public methods

    public void changeDifficulty(int difficulty){
        hangman.setDifficulty(difficulty);
        menuView.finish();
    }

    public void reset(){
        hangman.reset();
        menuView.finish();
    }

    public void close(){

        menuView.finish();
    }
}
