package com.example.ash.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class View extends AppCompatActivity {

    private Presenter presenter;
    private EditText txtGuess;
    private TextView lblGuesses;
    private TextView lblWord;
    private ImageButton btnGuess;
    private ImageView imgLives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to objects
        txtGuess = (EditText) findViewById(R.id.txtGuess);
        lblGuesses = (TextView) findViewById(R.id.lblGuesses);
        lblWord = (TextView) findViewById(R.id.lblWord);

        // get reference and setup event listeners for buttons
        btnGuess = (ImageButton) findViewById(R.id.btnGuess);

        // wire up listener for the button
        btnGuess.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                clickGuess();
            }
        });

        setText("--------", "Guesses: " + "\n" + "a");

        presenter = new Presenter(this);

    }

    // ------------------------------------------------------------------------- public methods
    public String getGuess(){ return txtGuess.getText().toString();}

    public void setText(String guess, String output){

        lblWord.setText(guess);
        lblGuesses.setText(output);

    }

    public void reset(){

    }

    public void showError(){

    }

    // ------------------------------------------------------------------------ private methods

    private void clickGuess(){
        //imgLives

        presenter.guess();

    }
}
