package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

class FragmentAdapter extends FragmentStatePagerAdapter {


    private static int NUMBER =2;

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
            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return NUMBER;
    }
}
