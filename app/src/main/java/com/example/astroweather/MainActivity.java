package com.example.astroweather;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton: {
                //Intent start = new Intent(this, App.class);
                //startActivity(App);
                break;
            }

            case R.id.settingsButton: {
                Intent preferences = new Intent(this, Preferences.class);
                startActivity(preferences);
                break;
            }
            case R.id.exitButton: {
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
                break;
            }
        }
    }
}
