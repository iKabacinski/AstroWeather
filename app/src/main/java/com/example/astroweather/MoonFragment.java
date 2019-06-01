package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class MoonFragment extends Fragment {


    private static final int minuteInMillisecconds = 60000;
    TextView wschodKsiezyca;
    TextView zachodKsiezyca;
    TextView najblizszaPelnia;
    TextView najblizszaNow;
    TextView fazaKsiezyca;
    TextView dzienMiesiacaSynodycznego;

    private int refreshTime;

    private Date date;
    private DateFormat yearFormat;
    private DateFormat monthFormat;
    private DateFormat dayFormat;
    private DateFormat hourFormat;
    private DateFormat minuteFormat;
    private DateFormat secondsFormat;

    private Thread thread;

    private SharedPreferences sharedPreferences;

    private AstroDateTime astroDateTime;
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;






    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // In
        //
        // flate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_moon, container, false);

        wschodKsiezyca = view.findViewById(R.id.wschodKsiezycaaaa);
        zachodKsiezyca = view.findViewById(R.id.zachodKsiezyca);
        najblizszaPelnia = view.findViewById(R.id.pelniaKsiezyc);
        najblizszaNow = view.findViewById(R.id.nowKsiezyca);
        fazaKsiezyca = view.findViewById(R.id.fazaKsiezyca);
        dzienMiesiacaSynodycznego = view.findViewById(R.id.dzienMiesiacaSynodycznego);

        date = new Date();
        yearFormat = new SimpleDateFormat("yyyy");
        monthFormat = new SimpleDateFormat("mm");
        dayFormat = new SimpleDateFormat("dd");
        hourFormat = new SimpleDateFormat("hh");
        minuteFormat = new SimpleDateFormat("mm");
        secondsFormat = new SimpleDateFormat("ss");

        sharedPreferences =getActivity().getSharedPreferences("ustawienia",0);
        setAstroDateTime();
        setData();

        return view;
    }

    private void setData() {
        wschodKsiezyca.setText(wschodKsiezyca.getText() +String.valueOf(astroCalculator.getMoonInfo().getMoonrise().toString()));
        zachodKsiezyca.setText(zachodKsiezyca.getText() + String.valueOf(astroCalculator.getMoonInfo().getMoonset().toString()));
        najblizszaPelnia.setText(najblizszaPelnia.getText() + String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().toString()));
        najblizszaNow.setText(najblizszaNow.getText() + String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().toString()));
        fazaKsiezyca.setText(fazaKsiezyca.getText() + String.valueOf(astroCalculator.getMoonInfo().getIllumination()) + "%");
        dzienMiesiacaSynodycznego.setText(dzienMiesiacaSynodycznego.getText() + String.valueOf(astroCalculator.getMoonInfo().getAge()));
    }

    private void setAstroDateTime() {

        astroDateTime = new AstroDateTime(Integer.valueOf(yearFormat.format(date)), Integer.valueOf(monthFormat.format(date)), Integer.valueOf(dayFormat.format(date)), Integer.valueOf(hourFormat.format(date)), Integer.valueOf(minuteFormat.format(date)), Integer.valueOf(secondsFormat.format(date)), 2, false);

        double szerokosc = Double.parseDouble(sharedPreferences.getString("szerokoscOdczytana", "0"));
        double dlugosc = Double.parseDouble(sharedPreferences.getString("dlugoscOdczytana", "0"));

        location = new AstroCalculator.Location(szerokosc, dlugosc);

        astroCalculator = new AstroCalculator(astroDateTime, location);

        refreshTime = sharedPreferences.getInt("odswiezanieOdczytane",1);

    }

    @Override
    public void onStart() {
        super.onStart();
        thread = new Thread() {
            @Override
            public void run() {
                runThread(refreshTime);
            }
        };
        thread.start();
    }

    private void runThread(int refreshTime) {
        try {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setAstroDateTime();
                    setData();
                }

            });
            Thread.sleep(minuteInMillisecconds * refreshTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }
}
