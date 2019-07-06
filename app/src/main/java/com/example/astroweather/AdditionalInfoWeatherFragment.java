package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class AdditionalInfoWeatherFragment extends Fragment {

TextView restResultText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_additional_info_weather, container, false);

        restResultText = view.findViewById(R.id.restResultText);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("SaveInformations", MODE_PRIVATE);

        String cName = sharedPreferences.getString("cityName", "London");

        SaveToFile saveToFile = new SaveToFile();



        restResultText.setText(saveToFile.readOther(cName, this.getActivity()));

        // Inflate the layout for this fragment
        return view;
    }





}
