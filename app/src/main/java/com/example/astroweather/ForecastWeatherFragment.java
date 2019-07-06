package com.example.astroweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class ForecastWeatherFragment extends Fragment {

    TextView forecastText;
    String resultForecast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_future_info_weather, container, false);
        forecastText = view.findViewById(R.id.forecast);
        forecastText.setMovementMethod(new ScrollingMovementMethod());


        SharedPreferences res = this.getActivity().getSharedPreferences("SaveInformations", MODE_PRIVATE);

        String cName = res.getString("cityName", "London");



        GetForecast getForecast = new GetForecast();
        String forecast[] = getForecast.read(cName, this.getActivity());

        resultForecast="";

        for (int i = 0; i < 5; i++) {

            String units = res.getString("unitName", "Celsius");
            if (units.equals("Fahrenheit")) {
                forecast[i] = unitChange(forecast[i]);
                resultForecast += forecast[i];
            } else {
                resultForecast += forecast[i];
            }

            resultForecast += "\n\n";
        }
        forecastText.setText(resultForecast);



        return view;
    }

    private String unitChange(String temp) {

        String temperature = temp.substring(temp.indexOf("ture: ") + 6, temp.indexOf("*"));
        String temperature1 = temperature;
        temperature = temperature.replace(",", ".");
        Double tempDouble = Double.parseDouble(temperature);
        tempDouble = tempDouble + 273.15;
        String temperatureResult = String.valueOf(tempDouble).substring(0, String.valueOf(tempDouble).indexOf(".") + 2);
        temperature =  temperatureResult + "*F";
        temp = temp.replace(temperature1 + "*C", temperature);

        return temp;
    }


}
