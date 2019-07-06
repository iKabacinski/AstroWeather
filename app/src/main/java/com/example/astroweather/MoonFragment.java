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

import static android.content.Context.MODE_PRIVATE;
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
    SharedPreferences weathPreferences;

    private AstroDateTime astroDateTime;
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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

        weathPreferences = context.getSharedPreferences("SaveInformations", MODE_PRIVATE);
        setAstroDateTime();
        //setData();

        return view;
    }

    private void setData() {
        wschodKsiezyca.setText("Wschód Księżyca: " + String.format(astroCalculator.getMoonInfo().getMoonrise().toString()));
        zachodKsiezyca.setText("Zachód Księżyca: " + String.format(astroCalculator.getMoonInfo().getMoonset().toString()));
        najblizszaPelnia.setText("Najbliższa pełnia Księżyca: " + String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().toString()));
        najblizszaNow.setText("Najbliższa Nów Księżyca: " + String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().toString()));
        fazaKsiezyca.setText("Faza Księżyca: " + String.format("%.2f", (astroCalculator.getMoonInfo().getIllumination() * 100)) + " %");
        dzienMiesiacaSynodycznego.setText("Wiek księżyca " + String.format("%.2f", (astroCalculator.getMoonInfo().getAge())));

    }

//    private String getLunarDay() {
//        AstroDateTime nextMoon = astroCalculator.getMoonInfo().getNextNewMoon();
//        Date lastNextMoonDate = new Date(nextMoon.getYear(),
//                nextMoon.getMonth(),
//                nextMoon.getDay(),
//                nextMoon.getHour(),
//                nextMoon.getMinute(),
//                nextMoon.getSecond());
//        double diff = (lastNextMoonDate.getTime() - date.getTime()) * 60000;
//        double lunarDay = diff / 60.0 / 24 % 29.531;
//        if (lunarDay < 0)
//            lunarDay += 29.531;
//        return String.format("%.2f", lunarDay);
//    }

    private void setAstroDateTime() {
        date = new Date();
        astroDateTime = new AstroDateTime(Integer.valueOf(yearFormat.format(date)), Integer.valueOf(monthFormat.format(date)), Integer.valueOf(dayFormat.format(date)), Integer.valueOf(hourFormat.format(date)), Integer.valueOf(minuteFormat.format(date)), Integer.valueOf(secondsFormat.format(date)), 2, false);


        // weathPreferences = getActivity().getSharedPreferences("SaveInformations", MODE_PRIVATE);
        double dlugosc = Double.parseDouble(weathPreferences.getString("longitudeAstro", "19.28"));
        double szerokosc = Double.parseDouble(weathPreferences.getString("latitudeAstro", "51.45"));

       // double szerokosc = Double.parseDouble(resWeather.getString("longitude", "51.45"));
        //double dlugosc = Double.parseDouble(resWeather.getString("latitude", "19.28"));

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
                Toast.makeText(getActivity(), "Nowe dane",
                        Toast.LENGTH_SHORT).show();
            }
        };
        thread.start();
    }

    private void runThread(int refreshTime) {
        while (true) {
            try {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            setAstroDateTime();
                            setData();

                        }

                    });
                }
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