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
            case R.id.ustawLokalizacje:
                if (dlugoscText.getText().toString().equals("") || dlugoscText.getText().toString().equals(""))
                    dlugosc = 0.0;
                else {
                    dlugosc = Double.parseDouble(dlugoscText.getText().toString());
                    if (dlugosc < 0)
                        dlugosc = Math.abs(dlugosc);
                    if (szerokoscText.getText().toString().equals("") || szerokoscText.getText().toString().equals("")) {
                        szerokosc = 0.0;
                    } else {
                        szerokosc = Double.parseDouble(szerokoscText.getText().toString());
                        if (szerokosc < 0)
                            szerokosc = Math.abs(szerokosc);
                    }


                    context = getApplication();
                    preferences = context.getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("szerokoscOdczytana", szerokosc.toString());
                    editor.putString("dlugoscOdczytana", dlugosc.toString());
                  editor.apply();
                }
                break;
            case R.id.ustawCzasOdswiezania:

                odswiezanieOdczytane = Integer.parseInt(spinner.getSelectedItem().toString());
                context = getApplication();
                preferences = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("odswiezanieOdczytane", odswiezanieOdczytane);
                editor.apply();
                break;
        }

    }

    public void setCzasOdswiezania(int odswiezanieOdczytane) {

        switch (odswiezanieOdczytane) {
            case 1:
                spinner.setSelection(0);
                break;
            case 2:
                spinner.setSelection(1);
                break;
            case 3:
                spinner.setSelection(2);
                break;
            case 4:
                spinner.setSelection(3);
                break;
            case 5:
                spinner.setSelection(4);
                break;
            default:
                spinner.setSelection(5);
                break;

        }


    }

}

