package com.example.astroweather;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
//        Button settingsButton = findViewById(R.id.settingsButton);
//        settingsButton.setOnClickListener(this);
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);
        Button settingsWeatherButton = findViewById(R.id.weatherSetting);
        settingsWeatherButton.setOnClickListener(this);
        Button openWeatherButton = findViewById(R.id.openWeatherButton);
        openWeatherButton.setOnClickListener(this);
        if(!checkConnection())
            Toast.makeText(getApplicationContext(),"Nie ma internetu, dane mogą być nieaktualne",Toast.LENGTH_LONG).show();

    }
    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) &&
                !(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton: {
                Intent astroIntent = new Intent(this, Astro.class);
                startActivity(astroIntent);
                break;
            }
            case R.id.openWeatherButton: {
                Intent openWeather = new Intent(this, WeatherActivity.class);
                startActivity(openWeather);
                break;
            }

//            case R.id.settingsButton: {
//                Intent preferences = new Intent(this, AstroManualSettings.class);
//                startActivity(preferences);
//                break;
    //        }
            case R.id.weatherSetting: {
                Intent weatherPref = new Intent(this, WeatherSettings.class);
                startActivity(weatherPref);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(!checkConnection())
            Toast.makeText(getApplicationContext(),"Nie ma internetu, dane mogą być nieaktualne",Toast.LENGTH_LONG).show();

    }
}
