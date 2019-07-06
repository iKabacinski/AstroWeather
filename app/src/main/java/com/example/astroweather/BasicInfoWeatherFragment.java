package com.example.astroweather;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import static android.content.Context.MODE_PRIVATE;


public class BasicInfoWeatherFragment extends Fragment {

    private TextView result;

    private ImageView imageView;

    private String unit;
    private SharedPreferences preferences;


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


        result = view.findViewById(R.id.result);
        imageView = view.findViewById(R.id.imageWeather);

        SharedPreferences res = this.getActivity().getSharedPreferences("SaveInformations", MODE_PRIVATE);

        String cName = res.getString("cityName", "London");


        SaveToFile saveToFile = new SaveToFile();
        String weatherDescription = saveToFile.read(cName, this.getActivity());
        String[] lines = weatherDescription.split(System.getProperty("line.separator"));
        String units = res.getString("unitName", "Celsius");
        if (units.equals("Fahrenheit")) {

            String temperature = lines[3].substring(lines[3].indexOf(" ") + 1, lines[3].indexOf("*"));
            String temperature1 = temperature;
            temperature = temperature.replace(",", ".");
            Double temp = Double.parseDouble(temperature);
            temp = temp + 273.15;
            temperature = String.valueOf(temp).substring(0, String.valueOf(temp).indexOf(".") + 2) + "*F";
            weatherDescription = weatherDescription.replace(temperature1 + "*C", temperature);
        }
        result.setText(weatherDescription.substring(0, weatherDescription.indexOf(lines[lines.length - 1])));
        if(lines[lines.length-1].isEmpty()==false) {
            Picasso.with(this.getActivity()).load(lines[lines.length - 1]).into(imageView);
        }

        return view;
    }
//


}
