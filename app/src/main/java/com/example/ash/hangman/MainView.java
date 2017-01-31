package com.example.ash.hangman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends AppCompatActivity {

    private Presenter presenter;
    private EditText txtGuess;
    private TextView lblGuesses;
    private TextView lblWord;
    private TextView lblTopInfo;
    private ImageButton btnGuess;
    private ImageView imgLives;
    private ImageButton imgHeader;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to objects
        txtGuess = (EditText) findViewById(R.id.txtGuess);
        lblGuesses = (TextView) findViewById(R.id.lblGuesses);
        lblWord = (TextView) findViewById(R.id.lblWord);
        lblTopInfo = (TextView) findViewById(R.id.lblTopInfo);
        imgLives = (ImageView) findViewById(R.id.imgLives);

        imgHeader = (ImageButton) findViewById(R.id.imgHeader);
        lblGuesses.setMovementMethod(new ScrollingMovementMethod());

        Typeface chalk = Typeface.createFromAsset(getAssets(), "fonts/chalk-dash.ttf");
        Typeface plast = Typeface.createFromAsset(getAssets(), "fonts/plast.ttf");

        lblWord.setTypeface(chalk);
        lblGuesses.setTypeface(plast);
        txtGuess.setTypeface(chalk);
        lblTopInfo.setTypeface(plast);

        // get reference and setup event listeners for buttons
        btnGuess = (ImageButton) findViewById(R.id.btnGuess);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        // wire up listener for the button
        btnGuess.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                clickGuess();

                EditText edtView = (EditText) findViewById(R.id.txtGuess);

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtView.getWindowToken(), 0);
            }
        });

        imgHeader.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                showMenu();
            }
        });

        presenter = new Presenter(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.populate();
    }

    @Override
    public void onStop(){
        super.onStop();
        presenter.saveState();
    }

    // ------------------------------------------------------------------------- public methods
    public String getGuess(){ return txtGuess.getText().toString();}

    public void setText(String word, String output){

        lblWord.setText(word);
        lblGuesses.setText(output);
        txtGuess.setText("");

    }

    public void showError(String message){
        // display toast message if already guessed
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
        txtGuess.setText("");
    }

    public void showMenu(){
        // construct intent object
        Intent intent = new Intent("com.example.ash.hangman.MENU");
        startActivity(intent);
    }

    public void setImage(int guessCount, int difficulty){
        if (difficulty == 1){
            // medium mode
            if (guessCount == 0){
                // game over
                imgLives.setImageResource(R.mipmap.hangman_game_guess8_easy);
            } else if (guessCount == 1) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess7_easy);
            } else if (guessCount == 2) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess6_easy);
            } else if (guessCount == 3) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess4_easy);
            } else if (guessCount == 4) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess2_easy);
            } else if (guessCount == 5) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess1_easy);
            } else {
                // game start
                imgLives.setImageResource(R.mipmap.hangman_game_start);
            }

        } else if (difficulty == 2){
            // hard mode
            if (guessCount == 0){
                // game over
                imgLives.setImageResource(R.mipmap.hangman_game_guess8_easy);
            } else if (guessCount == 1) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess6_easy);
            } else if (guessCount == 2) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess4_easy);
            } else if (guessCount == 3) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess2_easy);
            } else if (guessCount == 4) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess1_easy);
            } else {
                // game start
                imgLives.setImageResource(R.mipmap.hangman_game_start);
            }

        } else {
            // easy mode
            if (guessCount == 0){
                // game over
                imgLives.setImageResource(R.mipmap.hangman_game_guess8_easy);
            } else if (guessCount == 1) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess7_easy);
            } else if (guessCount == 2) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess6_easy);
            } else if (guessCount == 3) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess5_easy);
            } else if (guessCount == 4) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess4_easy);
            } else if (guessCount == 5) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess3_easy);
            } else if (guessCount == 6) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess2_easy);
            } else if (guessCount == 7) {
                imgLives.setImageResource(R.mipmap.hangman_game_guess1_easy);
            } else {
                // game start
                imgLives.setImageResource(R.mipmap.hangman_game_start);
            }
        }
    }

    public void gameOver(){
        btnGuess.setEnabled(false);
        txtGuess.setVisibility(View.INVISIBLE);
        lblTopInfo.setText(R.string.gameOver);

    }

    public void gameStart(){
        btnGuess.setEnabled(true);
        txtGuess.setVisibility(View.VISIBLE);
        lblTopInfo.setText(R.string.top_info);
    }

    // ------------------------------------------------------------------------ private methods

    private void clickGuess(){
        presenter.guess();
    }
}
