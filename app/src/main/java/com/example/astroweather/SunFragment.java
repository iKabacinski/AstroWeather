package com.example.astroweather;

import android.content.SharedPreferences;
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


public class SunFragment extends Fragment {
    private static final int minuteInMillisecconds = 60000;

    private TextView wschodSlonca;
    private TextView azymutWschoduSlonca;
    private TextView zachodSlonca;
    private TextView azymutZachoduSlonca;
    private TextView zmierzchSlonca;
    private TextView switCywilny;

    private Thread thread;

    private int refreshTime;

    private Date date;
    private DateFormat yearFormat;
    private DateFormat monthFormat;
    private DateFormat dayFormat;
    private DateFormat hourFormat;
    private DateFormat minuteFormat;
    private DateFormat secondsFormat;


    private SharedPreferences sharedPreferences;

    private AstroDateTime astroDateTime;
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_sun, container, false);

        wschodSlonca = view.findViewById(R.id.wschodSlonce);
        azymutWschoduSlonca = view.findViewById(R.id.azymutWschoduSlonca);
        zachodSlonca = view.findViewById(R.id.zachodSlonce);
        azymutZachoduSlonca=view.findViewById(R.id.azymutZachoduSlonca);
        zmierzchSlonca=view.findViewById(R.id.zmierzchSlonce);
        switCywilny=view.findViewById(R.id.switSlonce);
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
        wschodSlonca.setText(wschodSlonca.getText() +String.valueOf(astroCalculator.getSunInfo().getSunrise().toString()));
        azymutWschoduSlonca.setText(azymutWschoduSlonca.getText() + String.valueOf(astroCalculator.getSunInfo().getAzimuthRise()));
        zachodSlonca.setText(zachodSlonca.getText() + String.valueOf(astroCalculator.getSunInfo().getSunset()));
        azymutZachoduSlonca.setText(azymutZachoduSlonca.getText() + String.valueOf(astroCalculator.getSunInfo().getAzimuthSet()));
        zmierzchSlonca.setText(zmierzchSlonca.getText() + String.valueOf(astroCalculator.getSunInfo().getTwilightEvening()));
        switCywilny.setText(switCywilny.getText() + String.valueOf(astroCalculator.getSunInfo().getTwilightMorning()));

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


