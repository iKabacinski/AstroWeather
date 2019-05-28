package com.example.astroweather;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;

public class App extends AppCompatActivity {
    private final String tagActivity = "TEST_ACTIVITY";
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private TextClock data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

       viewPager =(ViewPager) findViewById(R.id.fragmentContainer);

        adapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
    }




}
