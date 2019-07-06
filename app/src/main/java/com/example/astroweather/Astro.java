package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

public class Astro extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentAstroAdapter adapter;
    private TextClock data;


    private TextView szerokoscGeograficzna;
    private TextView dlugoscGeograficzna;
    private TextView czasOdswiezania;
    private TextView cityName;
    private SharedPreferences preferences;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        context = getApplicationContext();

        viewPager =  findViewById(R.id.fragmentContainer);

        adapter = new FragmentAstroAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);


        szerokoscGeograficzna = findViewById(R.id.szerokoscGeograficzna);
        dlugoscGeograficzna = findViewById(R.id.dlugośćGeograficzna);
        czasOdswiezania = findViewById(R.id.czasOdswiezania);
//        cityName = findViewById(R.id.cityName);
        preferences = getApplication().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences result = getSharedPreferences("SaveInformations", MODE_PRIVATE);
        szerokoscGeograficzna.setText(szerokoscGeograficzna.getText() + result.getString("latitudeAstro", "51.45"));
        dlugoscGeograficzna.setText(dlugoscGeograficzna.getText() + result.getString("longitudeAstro", "19.28"));
        czasOdswiezania.setText("Czas odświeżania: "+preferences.getInt("odswiezanieOdczytane",1)+ " min");
//        cityName.setText("Miasto: "+ result.getString("cityName","Lodz"));



    }


}