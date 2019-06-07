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


    private SharedPreferences preferences;

    private AstroDateTime astroDateTime;
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sun, container, false);
        context = getActivity();

        wschodSlonca = view.findViewById(R.id.wschodSlonce);
        azymutWschoduSlonca = view.findViewById(R.id.azymutWschoduSlonca);
        zachodSlonca = view.findViewById(R.id.zachodSlonce);
        azymutZachoduSlonca = view.findViewById(R.id.azymutZachoduSlonca);
        zmierzchSlonca = view.findViewById(R.id.zmierzchSlonce);
        switCywilny = view.findViewById(R.id.switSlonce);
        yearFormat = new SimpleDateFormat("yyyy");
        monthFormat = new SimpleDateFormat("MM");
        dayFormat = new SimpleDateFormat("dd");
        hourFormat = new SimpleDateFormat("HH");
        minuteFormat = new SimpleDateFormat("mm");
        secondsFormat = new SimpleDateFormat("ss");

        //context = getActivity();
        preferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        setAstroDateTime();
        //setData();


        return view;
    }

    private void setData() {
        wschodSlonca.setText("Wschód Słońca: " + String.valueOf(astroCalculator.getSunInfo().getSunrise().toString()));
        azymutWschoduSlonca.setText("Azymut wschodu Słońca: " + String.format("%.2f", (astroCalculator.getSunInfo().getAzimuthRise())));
        zachodSlonca.setText("Zachód Słońca: " + String.valueOf(astroCalculator.getSunInfo().getSunset()));
        azymutZachoduSlonca.setText("Azymut zachodu Słońca: " + String.format("%.2f", (astroCalculator.getSunInfo().getAzimuthSet())));
        zmierzchSlonca.setText("Zmierzch Słońca: " + String.valueOf(astroCalculator.getSunInfo().getTwilightEvening()));
        switCywilny.setText("Świt cywilny: " + String.valueOf(astroCalculator.getSunInfo().getTwilightMorning()));
//        Toast.makeText(context, "Nowe dane",
//                Toast.LENGTH_SHORT).show();
    }

    private void setAstroDateTime() {
        date = new Date();
        astroDateTime = new AstroDateTime(Integer.valueOf(yearFormat.format(date)), Integer.valueOf(monthFormat.format(date)), Integer.valueOf(dayFormat.format(date)), Integer.valueOf(hourFormat.format(date)), Integer.valueOf(minuteFormat.format(date)), Integer.valueOf(secondsFormat.format(date)), 2, false);
        //context = getActivity();

        //  preferences = context.getSharedPreferences(
        //       getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        double szerokosc = Double.parseDouble(preferences.getString("szerokoscOdczytana", "51.45"));
        double dlugosc = Double.parseDouble(preferences.getString("dlugoscOdczytana", "19.28"));

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

                Toast.makeText(context, "Nowe dane",
                        Toast.LENGTH_SHORT).show();
            }
        };
        thread.start();
    }

    private void runThread(int refreshTime) {
        while (true) {

            //context=getActivity();
            if (getActivity() != null) {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            setAstroDateTime();
                            setData();
//                            Toast.makeText(context, "Nowe dane",
//                                    Toast.LENGTH_SHORT).show();

                        }

                    });
                    Thread.sleep(minuteInMillisecconds * refreshTime);
//                    Toast.makeText(context, "Nowe dane",
//                            Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }

}
