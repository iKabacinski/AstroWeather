package com.example.astroweather;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Objects;

public class App extends AppCompatActivity {
    private final String tagActivity = "TEST_ACTIVITY";
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private TextClock data;


    private TextView szerokoscGeograficzna;
    private TextView dlugoscGeograficzna;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

       viewPager =(ViewPager) findViewById(R.id.fragmentContainer);

        adapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        szerokoscGeograficzna = findViewById(R.id.szerokoscGeograficzna);
        dlugoscGeograficzna=findViewById(R.id.dlugośćGeograficzna);

        SharedPreferences sharedPreferences = Objects.requireNonNull(getPreferences(0));

        szerokoscGeograficzna.setText(szerokoscGeograficzna.getText()+sharedPreferences.getString("szerokoscOdczytana","0"));
        dlugoscGeograficzna.setText(dlugoscGeograficzna.getText()+sharedPreferences.getString("dlugoscodczytana","0"));




    }




}
