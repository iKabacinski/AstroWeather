package com.example.astroweather;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {
    private TextView cityText;
    private ViewPager viewPager;
    private FragmentWeatherAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityText = findViewById(R.id.CityText);
        viewPager =  findViewById(R.id.fragmentContainer);

        adapter = new FragmentWeatherAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        SharedPreferences res = getSharedPreferences("SaveInformations", MODE_PRIVATE);

        String cName = res.getString("cityName", "Lodz");

        cityText.setText(cName);
    }
}
