package com.example.ash.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class MenuView extends AppCompatActivity {

    private MenuPresenter presenter;
    private ImageButton btnEasy;
    private ImageButton btnMedium;
    private ImageButton btnHard;
    private ImageButton btnReset;
    private ImageButton btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        // constructing the buttons
        btnEasy = (ImageButton) findViewById(R.id.imgEasy);
        btnMedium = (ImageButton) findViewById(R.id.imgMedium);
        btnHard = (ImageButton) findViewById(R.id.imgHard);
        btnReset = (ImageButton) findViewById(R.id.imgReset);
        btnReturn = (ImageButton) findViewById(R.id.imgReturn);

        // wiring up listeners
        btnEasy.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.changeDifficulty(0);
            }
        });

        btnMedium.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.changeDifficulty(1);
            }
        });

        btnHard.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.changeDifficulty(2);
            }
        });

        btnReset.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.reset();
            }
        });

        btnReturn.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                presenter.close();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter = new MenuPresenter(this);
    }

    // -------------------------------------------------------------- public methods
    public void disableHard(){
        // enable and disable buttons
        btnEasy.setEnabled(true);
        btnMedium.setEnabled(true);
        btnHard.setEnabled(false);
    }

    public void disableEasy(){
        // enable and disable buttons
        btnEasy.setEnabled(false);
        btnMedium.setEnabled(true);
        btnHard.setEnabled(true);
    }

    public void disableMedium(){
        // enable and disable buttons
        btnEasy.setEnabled(true);
        btnMedium.setEnabled(false);
        btnHard.setEnabled(true);
    }
}
