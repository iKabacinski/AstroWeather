package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;

public class GetForecast {


    String weatherDays[] = new String[5];
    int counter = 0;



    // haredPreferences res = ShowWeatherInformation.ACTIVITY_SERVICE.getActivity().getSharedPreferences("SaveInformations", MODE_PRIVATE);

    public void saveInformations(String cName, Context context) {

        String content;
        SharedPreferences myPreferences = context.getSharedPreferences("SaveInformations", MODE_PRIVATE);
        Connection weather = new Connection();
        try {
            content = weather.execute("http://api.openweathermap.org/data/2.5/forecast?q=" +
                    cName + "&APPID=f85019ba832469304b6d38b29275ac0b").get();
            Log.i("contentData 5 day ", content);

//JSON

            JSONObject jsonObject = new JSONObject(content);
            JSONArray forecastArray = jsonObject.getJSONArray("list");
      //      System.out.println("testujeym " + forecastArray.length());
       //     System.out.println(forecastArray.toString());
            DecimalFormat df = new DecimalFormat("####0.00");


            for (int i = 0; i < forecastArray.length(); i++) {
                // System.out.println(forecastArray.get(i).toString());
                System.out.println();
                System.out.println();
                JSONObject dailyForecast = forecastArray.getJSONObject(i);
                JSONObject weather2 = dailyForecast.getJSONArray("weather").getJSONObject(0);
                String time = dailyForecast.getString("dt_txt");
                if (time.contains("09:00:00")) {
                    String description = weather2.getString("main");

                    String date = time.substring(0, time.indexOf(" "));
                    JSONObject main = dailyForecast.getJSONObject("main");
                    String tempe = String.valueOf(df.format(Double.parseDouble(main.getString("temp"))-273.15));
                    String pressure = main.getString("pressure");
                    String humidity = main.getString("humidity");

                    JSONObject wind = dailyForecast.getJSONObject("wind");
                    String windSpeed = wind.getString("speed");
                    weatherDays[counter] = "Day: " + date + "\nDescription: " + description + "\nTemperature: " + tempe + "*C\nPressure: " + pressure + "\nHumidity: " + humidity + "\nWind speed: " + windSpeed + "\n";
                    counter++;
                    System.out.println("Description: " + description + "\nTemperature: " + tempe + "\nPressure: " + pressure + "\nHumidity: " + humidity + "\nWind speed: " + windSpeed + "\ncounter: " + cName + "\n" + counter);
                }
            }


//            String weatherData = jsonObject.getString("weather");

//Now we will show this result on screen

        } catch (Exception e) {
            e.printStackTrace();
        }

        //write(cName, context);

    }

    public void write(String cName, Context context) {

        // String imageUrl = "http://openweathermap.org/img/w/" + iconWeather + ".png";

        try {
            System.out.println("zapisuje miasto " + cName);
            FileOutputStream fileOutputStream = context.openFileOutput(cName + "Forecast.txt", MODE_PRIVATE);
            for (int i = 0; i < 5; i++)
                fileOutputStream.write(weatherDays[i].getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] read(String cName, Context context) {

        String weatherDays[] = new String[5];

        int counter = 0;
        try {
            System.out.println("czytam miasto " + cName);

            FileInputStream fileInputStream = context.openFileInput(cName + "Forecast.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;

            while ((lines = bufferedReader.readLine()) != null) {
                System.out.println("moja linijka: " + lines);
                stringBuffer.append(lines + "\n");
                if (lines.contains("speed")) {
                    weatherDays[counter] = stringBuffer.toString();
                    counter++;
                    stringBuffer.delete(0, stringBuffer.length());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherDays;
    }
}
