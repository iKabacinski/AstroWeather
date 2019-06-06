package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Objects;

public class App extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private TextClock data;


    private TextView szerokoscGeograficzna;
    private TextView dlugoscGeograficzna;
    private SharedPreferences preferences;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        context = getApplicationContext();

        viewPager = (ViewPager) findViewById(R.id.fragmentContainer);

        adapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);


        szerokoscGeograficzna = findViewById(R.id.szerokoscGeograficzna);
        dlugoscGeograficzna = findViewById(R.id.dlugośćGeograficzna);

        preferences = getApplication().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        szerokoscGeograficzna.setText(szerokoscGeograficzna.getText() + preferences.getString("szerokoscOdczytana", "0"));
        dlugoscGeograficzna.setText(dlugoscGeograficzna.getText() + preferences.getString("dlugoscOdczytana", "0"));


    }


}
