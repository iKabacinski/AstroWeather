package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class FragmentAdapter extends FragmentStatePagerAdapter {


    private static int NUMBER = 5;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new SunFragment();
            case 1:
                return new MoonFragment();
            case 2:
                return new BasicInfoWeatherFragment();
            case 3:
                return new AdditionalInfoWeatherFragment();
            case 4:
                return new ForecastWeatherFragment();
            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return NUMBER;
    }
}
