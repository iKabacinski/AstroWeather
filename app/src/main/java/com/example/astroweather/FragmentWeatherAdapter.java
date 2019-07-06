package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class FragmentWeatherAdapter extends FragmentStatePagerAdapter {
    private static int NUMBER = 3;

    public FragmentWeatherAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new BasicInfoWeatherFragment();
            case 1:
                return new AdditionalInfoWeatherFragment();
            case 2:
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
