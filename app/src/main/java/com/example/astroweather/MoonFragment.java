package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;


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
    private Toast toast;
    private Thread thread;

    private SharedPreferences preferences;

    private AstroDateTime astroDateTime;
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // In
        //Context context = getActivity();
        // flate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
        context = getActivity();
        wschodKsiezyca = view.findViewById(R.id.wschodKsiezycaaaa);
        zachodKsiezyca = view.findViewById(R.id.zachodKsiezyca);
        najblizszaPelnia = view.findViewById(R.id.pelniaKsiezyc);
        najblizszaNow = view.findViewById(R.id.nowKsiezyca);
        fazaKsiezyca = view.findViewById(R.id.fazaKsiezyca);
        dzienMiesiacaSynodycznego = view.findViewById(R.id.dzienMiesiacaSynodycznego);


        yearFormat = new SimpleDateFormat("yyyy");
        monthFormat = new SimpleDateFormat("MM");
        dayFormat = new SimpleDateFormat("dd");
        hourFormat = new SimpleDateFormat("HH");
        minuteFormat = new SimpleDateFormat("mm");
        secondsFormat = new SimpleDateFormat("ss");
        context = getActivity();
        preferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        setAstroDateTime();
        //setData();

        return view;
    }

    private void setData() {
        wschodKsiezyca.setText("Wschód Księżyca: " + String.valueOf(astroCalculator.getMoonInfo().getMoonrise().toString()));
        zachodKsiezyca.setText("Zachód Księżyca: " + String.valueOf(astroCalculator.getMoonInfo().getMoonset().toString()));
        najblizszaPelnia.setText("Najbliższa pełnia Księżyca: " + String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().toString()));
        najblizszaNow.setText("Najbliższa Nów Księżyca: " + String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().toString()));
        fazaKsiezyca.setText("Faza Księżyca: " + String.valueOf(round(astroCalculator.getMoonInfo().getIllumination() * 100)) + " %");
        dzienMiesiacaSynodycznego.setText("Dzień miesiąca synodycznego: " + String.valueOf(round(astroCalculator.getMoonInfo().getAge()/29.531)));


//        Toast.makeText(context, "Odswiezono dane",
//                Toast.LENGTH_SHORT).show();

    }

    private void setAstroDateTime() {
        date = new Date();
        astroDateTime = new AstroDateTime(Integer.valueOf(yearFormat.format(date)), Integer.valueOf(monthFormat.format(date)), Integer.valueOf(dayFormat.format(date)), Integer.valueOf(hourFormat.format(date)), Integer.valueOf(minuteFormat.format(date)), Integer.valueOf(secondsFormat.format(date)), 2, false);
//        context = getActivity();
        //  SharedPreferences preferences = context.getSharedPreferences(
        //        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        double szerokosc = Double.parseDouble(preferences.getString("szerokoscOdczytana", "0"));
        double dlugosc = Double.parseDouble(preferences.getString("dlugoscOdczytana", "0"));

        location = new AstroCalculator.Location(szerokosc, dlugosc);

        astroCalculator = new AstroCalculator(astroDateTime, location);

        refreshTime = preferences.getInt("odswiezanieOdczytane", 1);

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
        while (true) {
            try {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setAstroDateTime();
                        setData();
//                        Toast.makeText(getActivity(), "Odswiezono dane",
//                                Toast.LENGTH_SHORT).show();

                    }

                });
                Thread.sleep(minuteInMillisecconds * refreshTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }
}
