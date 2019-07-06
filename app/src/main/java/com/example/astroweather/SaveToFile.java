package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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

public class SaveToFile {

    String main = "";
    String pressure = "";
    String description = "";
    String temperature = "";
    String longitutde = "";
    String latitude = "";
    String iconWeather = "";
    String temp_min = "";
    String temp_max = "";

    int visibility = 0;
    String windSpeed = "";
    String windDeg = "";
    String humidityInfo = "";


    // haredPreferences res = ShowWeatherInformation.ACTIVITY_SERVICE.getActivity().getSharedPreferences("SaveInformations", MODE_PRIVATE);

    public void saveInformations(String cName, Context context) {

        String content;
        SharedPreferences myPreferences = context.getSharedPreferences("SaveInformations", MODE_PRIVATE);
        Connection weather = new Connection();
        try {
            content = weather.execute("http://api.openweathermap.org/data/2.5/weather?q=" +
                    cName + "&APPID=f85019ba832469304b6d38b29275ac0b").get();
            Log.i("contentData", content);

//JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");
            String coordinate = jsonObject.getString("coord");

            JSONArray array = new JSONArray(weatherData);

            DecimalFormat df = new DecimalFormat("####0.00");

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
                iconWeather = weatherPart.getString("icon");
            }

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = String.valueOf(df.format(Double.parseDouble(mainPart.getString("temp")) - 273.15));

            pressure = mainPart.getString("pressure");
            temp_min = mainPart.getString("temp_min");
            temp_max = mainPart.getString("temp_max");

            JSONObject coorPart = new JSONObject(coordinate);
            longitutde = coorPart.getString("lon");
            latitude = coorPart.getString("lat");

            humidityInfo = mainPart.getString("humidity");

            String windData = jsonObject.getString("wind");
            JSONObject windPart = new JSONObject(windData);
            windSpeed = windPart.getString("speed");


            Log.i("Temperature", temperature);


            write(cName, context);
            writeOther(cName, context);
            read(cName, context);

            SharedPreferences.Editor myEditor = myPreferences.edit();
            myEditor.putString("longitude", longitutde);
            myEditor.putString("latitude", latitude);
            myEditor.putString("longitudeAstro",longitutde);
            myEditor.putString("latitudeAstro",latitude);
            myEditor.commit();


//Now we will show this result on screen

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String read(String cName, Context context) {

        String result = "";
        try {
            FileInputStream fileInputStream = context.openFileInput(cName + ".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }
            result = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void write(String cName, Context context) {

        String imageUrl = "http://openweathermap.org/img/w/" + iconWeather + ".png";

        String Mytextmessage = "Longitude: " + longitutde +
                "\nLatitude: " + latitude +
                "\nPressure: " + pressure + "hPa" +
                "\nTemperature: " + temperature + "*C" +
                "\nMain: " + main +
                "\nDescription: " + description +
                "\n" + imageUrl;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(cName + ".txt", MODE_PRIVATE);
            fileOutputStream.write(Mytextmessage.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOther(String cName, Context context) {
        SharedPreferences res = context.getSharedPreferences("SaveInformations", MODE_PRIVATE);

        String units = res.getString("unitName", "Celsius");
        if (units.equals("Celsius")) {
            Double min = Double.parseDouble(temp_min)-273.15;
            Double max = Double.parseDouble(temp_max)-273.15;
            DecimalFormat df = new DecimalFormat("####0.00");

            temp_min=String.valueOf(df.format(min));
            temp_max=String.valueOf(df.format(max));
        }

        String resultText = "Wind speed: " + windSpeed +
                "\nMinimum temperature : " + temp_min +
                "\nMaximum temperature: " + temp_max +
                "\nHumidity: " + humidityInfo + "%";


        try {
            FileOutputStream fileOutputStream = context.openFileOutput(cName + "Other.txt", MODE_PRIVATE);
            fileOutputStream.write(resultText.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readOther(String cName, Context context) {

        String result = "";
        try {
            FileInputStream fileInputStream = context.openFileInput(cName + "Other.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }
            result = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[] getCoordinates(String cName, Context context) {

        String allInformations = read(cName, context);
        String[] lines = allInformations.split(System.getProperty("line.separator"));

        String[] coordinates = new String[2];
        if(lines.length!=0) {
            coordinates[0] = lines[0].substring(lines[0].indexOf(" "));
            coordinates[1] = lines[1].substring(lines[1].indexOf(" "));
        }
        else
            Toast.makeText(context,"Nie pobralem koordynatow",Toast.LENGTH_SHORT).show();
        return coordinates;
    }
}