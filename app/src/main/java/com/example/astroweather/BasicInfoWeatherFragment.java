package com.example.astroweather;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class BasicInfoWeatherFragment extends Fragment {

    private TextView temperatureText;
    private TextView descriptionText;
    private TextView dateText;
    private String unit;
    private SharedPreferences preferences;
    private FragmentActivity context;

    public BasicInfoWeatherFragment() {
        // Required empty public constructor
    }


    public static BasicInfoWeatherFragment newInstance(String param1, String param2) {
        BasicInfoWeatherFragment fragment = new BasicInfoWeatherFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        context = getActivity();
        //initialize(view);

        //findWeather();
        // Inflate the layout for this fragment
        return view;
    }
//
//    private void findWeather() {
//        double szerokosc = Double.parseDouble(preferences.getString("szerokoscOdczytana", "51.45"));
//
//        double dlugosc = Double.parseDouble(preferences.getString("dlugoscOdczytana", "19.28"));
//
//        String API_KEY = "f85019ba832469304b6d38b29275ac0b";
//        String urlString = "api.openweathermap.org/data/2.5/weather?lat={" + szerokosc + "}&lon={" + dlugosc + "}" + "&appid=" + API_KEY;


//        try {
//            StringBuilder result = new StringBuilder();
//
//            URL url = new URL(urlString);
//            URLConnection connection = url.openConnection();
//            String line;
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            while ((line = bufferedReader.readLine()) != null) {
//                result.append(line);
//            }
//            bufferedReader.close();
//
//
//           // Map<String,Object> tempMap = jsonToMap
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void initialize(View view) {
//        temperatureText = view.findViewById(R.id.textTemp);
//        dateText = view.findViewById(R.id.textDate);
//        descriptionText = view.findViewById(R.id.textOpis);
//    }


}
