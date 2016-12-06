package com.example.ash.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class MenuView extends AppCompatActivity {

    private MenuPresenter presenter;
    private ImageButton imgEasy;
    private ImageButton imgMedium;
    private ImageButton imgHard;
    private ImageButton imgReset;
    private ImageButton imgReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        imgEasy = (ImageButton) findViewById(R.id.imgEasy);
        imgMedium = (ImageButton) findViewById(R.id.imgMedium);
        imgHard = (ImageButton) findViewById(R.id.imgHard);
        imgReset = (ImageButton) findViewById(R.id.imgReset);
        imgReturn = (ImageButton) findViewById(R.id.imgReturn);

        imgEasy.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.changeDifficulty(0);
            }
        });

        imgMedium.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.changeDifficulty(1);
            }
        });

        imgHard.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.changeDifficulty(2);
            }
        });

        imgReset.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.reset();
            }
        });

        imgReturn.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.close();
            }
        });

        presenter = new MenuPresenter(this);
    }
}
