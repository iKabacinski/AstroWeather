package com.example.astroweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Preferences extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;

    private EditText szerokoscText, dlugoscText;
    private Button ustawLokalizacjeButton, ustawCzasOdswiezaniaButton;

    private String szerokoscOdczytana, dlugoscOdczytana;

    public int odswiezanieOdczytane = 1;
    private Double dlugosc, szerokosc;
    private Context context;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        spinner = findViewById(R.id.refreshing);

        szerokoscText = findViewById(R.id.szerokosc);
        szerokoscText.setOnClickListener(this);
        dlugoscText = findViewById(R.id.dlugosc);
        dlugoscText.setOnClickListener(this);

        ustawLokalizacjeButton = findViewById(R.id.ustawLokalizacje);
        ustawLokalizacjeButton.setOnClickListener(this);
        ustawCzasOdswiezaniaButton = findViewById(R.id.ustawCzasOdswiezania);
        ustawCzasOdswiezaniaButton.setOnClickListener(this);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.refresh_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ustawLokalizacje: {
                if (dlugoscText.getText().toString().equals("") || dlugoscText.getText().toString().equals("") || dlugoscText.getText().toString().isEmpty()) {
                    dlugosc = 19.28;
                    Toast.makeText(getApplicationContext(), "ZŁA DŁUGOSC,Ustawiam domyslna", Toast.LENGTH_SHORT).show();
                } else {
                    if (Double.parseDouble(dlugoscText.getText().toString()) < -180 || Double.parseDouble(dlugoscText.getText().toString()) > 180 || dlugoscText.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "ZŁA DŁUGOSC,Ustawiam domyslna", Toast.LENGTH_SHORT).show();
                        dlugosc = 19.28;
                    } else
                        dlugosc = Double.parseDouble(dlugoscText.getText().toString());
                }
            }
            if (szerokoscText.getText().toString().equals("") || szerokoscText.getText().toString().equals("") || szerokoscText.getText().toString().isEmpty()) {
                szerokosc = 51.45;
                Toast.makeText(getApplicationContext(), "ZŁA SZEROKOSC,Ustawiam domyslna", Toast.LENGTH_SHORT).show();
            } else {
                if (Double.parseDouble(szerokoscText.getText().toString()) < -90 || Double.parseDouble(szerokoscText.getText().toString()) > 90 || szerokoscText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ZŁA SZEROKOSC,Ustawiam domyslna", Toast.LENGTH_SHORT).show();
                    szerokosc = 51.45;
                } else {

                    szerokosc = Double.parseDouble(szerokoscText.getText().toString());
                }
            }


            context = getApplication();
            preferences = context.getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("szerokoscOdczytana", szerokosc.toString());
            editor.putString("dlugoscOdczytana", dlugosc.toString());
            editor.apply();

        break;

        case R.id.ustawCzasOdswiezania:

        odswiezanieOdczytane = Integer.parseInt(spinner.getSelectedItem().toString());
        context = getApplication();
        preferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
       editor = preferences.edit();
        editor.putInt("odswiezanieOdczytane", odswiezanieOdczytane);
        editor.apply();
        break;
    }

}

}

