package com.example.ash.hangman;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class View extends AppCompatActivity {

    private Presenter presenter;
    private EditText txtGuess;
    private TextView lblGuesses;
    private TextView lblWord;
    private TextView lblTopInfo;
    private ImageButton btnGuess;
    private ImageView imgLives;
    private ImageView imgHeader;
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
        imgLives.setImageResource(R.drawable.hangman);
        imgHeader = (ImageView) findViewById(R.id.imgHeader);

        Typeface chalk = Typeface.createFromAsset(getAssets(), "fonts/chalk-dash.ttf");
        Typeface samba = Typeface.createFromAsset(getAssets(), "fonts/samba.ttf");

        lblWord.setTypeface(chalk);
        lblGuesses.setTypeface(samba);
        txtGuess.setTypeface(chalk);
        lblTopInfo.setTypeface(samba);

        // get reference and setup event listeners for buttons
        btnGuess = (ImageButton) findViewById(R.id.btnGuess);

        // wire up listener for the button
        btnGuess.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                clickGuess();
            }
        });

        imgHeader.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                showMenu();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        presenter = new Presenter(this);
    }

    // ------------------------------------------------------------------------- public methods
    public String getGuess(){ return txtGuess.getText().toString();}

    public void setText(String word, String output){

        lblWord.setText(word);
        lblGuesses.setText(output);
        txtGuess.setText("");

    }

    public void reset(){

    }

    public void showError(String message){
        // display toast message if already guessed
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void showDifficulty(String message){
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void showMenu(){
        // construct intent object
        Intent intent = new Intent("com.example.ash.hangman.MENU");
        startActivity(intent);
    }

    // ------------------------------------------------------------------------ private methods

    private void clickGuess(){
        //imgLives

        presenter.guess();

    }
}
