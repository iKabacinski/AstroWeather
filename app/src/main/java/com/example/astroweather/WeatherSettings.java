package com.example.astroweather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WeatherSettings extends AppCompatActivity implements View.OnClickListener {


    Spinner temperatureSpinner;
    Spinner ulubioneMiastaSpinner;
    Spinner odswiezanieAstro;
    TextView dlugosGeograficznaWeather, szerokoscGeograficznaWeather;

    String geographicalCoordinates;

    Button dodajDoListyButton;
    Button saveButton, ustawRecznieAstro;
    SharedPreferences sharedPreferences;
    List<String> miasta;
    List<String> units;

    long timeMilli = 0;

    public int odswiezanieOdczytane;

    final Context c = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_settings);

        init();


    }


    private void init() {

        sharedPreferences = getSharedPreferences("SaveInformations", MODE_PRIVATE);
        dodajDoListyButton = findViewById(R.id.dodajDoListyButton);
        dodajDoListyButton.setOnClickListener(this);
        temperatureSpinner = findViewById(R.id.TempertureSpinner);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
//        odswiezButton = findViewById(R.id.odswiezButton);
//        odswiezButton.setOnClickListener(this);
        ulubioneMiastaSpinner = findViewById(R.id.ulubioneMiastaSpinner);
        odswiezanieAstro = findViewById(R.id.odswiezanieAstro);
        dlugosGeograficznaWeather = findViewById(R.id.dlugoscGeograficznaWeather);
        dlugosGeograficznaWeather.setOnClickListener(this);
        szerokoscGeograficznaWeather = findViewById(R.id.szerokoscGeograficznaWeather);
        szerokoscGeograficznaWeather.setOnClickListener(this);
        ustawRecznieAstro=findViewById(R.id.ustawAstroRecznieButton);
        ustawRecznieAstro.setOnClickListener(this);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.refresh_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        odswiezanieAstro.setAdapter(spinnerAdapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton: {
                showNewCity();

                String name = ulubioneMiastaSpinner.getSelectedItem().toString();

                SaveToFile save = new SaveToFile();



                geographicalCoordinates = dlugosGeograficznaWeather.getText().toString() + " " + szerokoscGeograficznaWeather.getText().toString();

                SharedPreferences.Editor myEditor = sharedPreferences.edit();
                myEditor.putInt("cityNameID", ulubioneMiastaSpinner.getSelectedItemPosition());
                myEditor.putInt("unitsID", temperatureSpinner.getSelectedItemPosition());
                myEditor.putString("cityName", miasta.get(ulubioneMiastaSpinner.getSelectedItemPosition()));
                myEditor.putString("unitName", units.get(temperatureSpinner.getSelectedItemPosition()));
                myEditor.putString("longitude", dlugosGeograficznaWeather.getText().toString());
                myEditor.putString("latitude", szerokoscGeograficznaWeather.getText().toString());
                myEditor.putLong("lastRefresh", timeMilli);
                myEditor.commit();

                odswiezanieOdczytane = Integer.parseInt(odswiezanieAstro.getSelectedItem().toString());
               SharedPreferences preferences = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("odswiezanieOdczytane", odswiezanieOdczytane);
                editor.apply();
                showNewCity();

                if (checkConnection())
                    refreshInformations();
                else
                    Toast.makeText(WeatherSettings.this, "Nie ma internetu", Toast.LENGTH_LONG).show();


                break;
            }
            case R.id.dodajDoListyButton: {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.input_city_layout, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                addNewCity();
                showNewCity();

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (!miasta.contains(userInputDialogEditText.getText().toString()) && !userInputDialogEditText.getText().toString().equals("")) {


                                    if (checkMyCity(userInputDialogEditText.getText().toString())) {
                                        String input = userInputDialogEditText.getText().toString();
                                        miasta.add(input);
                                        Gson gson = new Gson();
                                        String json = gson.toJson(miasta);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("listaMiast", json);
                                        editor.putInt("cityNameID", miasta.size() - 1);
                                        editor.commit();


                                        ulubioneMiastaSpinner.setSelection(miasta.indexOf(input));


                                    } else {
                                        Toast.makeText(WeatherSettings.this, "Nie moge znalezc tego miasta", Toast.LENGTH_LONG).show();
                                    }

                                }

                            }
                        })

                        .setNegativeButton("Wyjdz",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
            break;
            case R.id.ustawAstroRecznieButton:{
                Intent idzDoRecznych = new Intent(this,AstroManualSettings.class);
                startActivity(idzDoRecznych);
            }
//            case R.id.odswiezButton: {
//                if (checkConnection())
//                    refreshInformations();
//                else
//                    Toast.makeText(WeatherSettings.this, "Nie ma internetu", Toast.LENGTH_LONG).show();
//
//
//                break;
//            }
        }
    }


    public boolean checkMyCity(String cName) {

        String content = null;

        try {
            Connection weather = new Connection();
            content = weather.execute("http://api.openweathermap.org/data/2.5/weather?q=" +
                    cName + "&APPID=f85019ba832469304b6d38b29275ac0b").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (content.equals("error"))
            return false;
        else
            return true;

    }


    public void addNewCity() {
        SharedPreferences result = getSharedPreferences("SaveInformations", MODE_PRIVATE);

        miasta = new ArrayList<>();
        miasta.add("Lodz");


        Gson gson = new Gson();
        String json = result.getString("listaMiast", "");
        if (json.isEmpty()) {
            //Toast.makeText(ChangeWeatherInformation.this, "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            miasta = gson.fromJson(json, type);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, miasta);
        ulubioneMiastaSpinner.setAdapter(adapter);
        ulubioneMiastaSpinner.setSelection(result.getInt("cityNameID", 0));

        units = new ArrayList<>();
        units.add("Celsius");
        units.add("Fahrenheit");

        Gson gson2 = new Gson();
        String json2 = result.getString("units", "");

        if (json2.isEmpty()) {
            //Toast.makeText(ChangeWeatherInformation.this, "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type2 = new TypeToken<List<String>>() {
            }.getType();
            units = gson2.fromJson(json2, type2);
        }
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units);
        temperatureSpinner.setAdapter(adapter2);
        temperatureSpinner.setSelection(result.getInt("unitsID", 0));


    }


    public void showNewCity() {

        SharedPreferences result = getSharedPreferences("SaveInformations", MODE_PRIVATE);

        dlugosGeograficznaWeather.setText(result.getString("longitude", "10.00"));
        szerokoscGeograficznaWeather.setText( result.getString("latitude", "20.00"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        addNewCity();
        showNewCity();

    }


    public void refreshInformations() {
        getLatestInformations();
        addNewCity();
        showNewCity();
        String name = String.valueOf(ulubioneMiastaSpinner.getSelectedItem());
        if (name != null) {
            SaveToFile saveToFile = new SaveToFile();
            saveToFile.saveInformations(name, this);
            GetForecast getForecast = new GetForecast();
            getForecast.saveInformations(name, this);
            getForecast.write(name, this);
            Date date = new Date();
            timeMilli = date.getTime();
        } else
            Toast.makeText(WeatherSettings.this, "Nie moge odswiezyc", Toast.LENGTH_LONG).show();


    }

    public void getLatestInformations() {
        SaveToFile save = new SaveToFile();
        save.saveInformations(ulubioneMiastaSpinner.getSelectedItem().toString(), WeatherSettings.this);
    }

    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) &&
                !(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            return false;
        }
        return true;
    }

}



