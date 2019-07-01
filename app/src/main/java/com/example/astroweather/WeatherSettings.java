package com.example.astroweather;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class WeatherSettings extends ListActivity implements View.OnClickListener {
    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter = 0;

    Spinner spinner;
    EditText cityEditText;
    Button ustawButton;
    Button dodajDoListyButton;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    Cursor data;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_settings);

        init();
        displayCityList();


    }

    private void displayCityList() {
        data = databaseHelper.getListContents();
        listItems.clear();
        while (data.moveToNext()) {
            listItems.add(data.getString(1));
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }


    private void init() {

        setListAdapter(adapter);
        ustawButton = findViewById(R.id.ustawMiastoButton);
        ustawButton.setOnClickListener(this);
        dodajDoListyButton = findViewById(R.id.dodajDoListyButton);
        dodajDoListyButton.setOnClickListener(this);
        spinner = findViewById(R.id.TempertureSpinner);
        cityEditText = findViewById(R.id.cityNameEditText);
        cityEditText.setOnClickListener(this);
        listView = findViewById(android.R.id.list);
        databaseHelper = new DatabaseHelper(this);
        listItems =  new ArrayList<String>();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.Temperature_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);


    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v, String city) {

        if (cityEditText.length() != 0) {
            if (!listItems.contains(city)) {
                addData(city);
                cityEditText.setText("");
                displayCityList();
            } else {
                Toast.makeText(WeatherSettings.this, "To miasto jest już na liście", LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(WeatherSettings.this, "Wprowadz jakas wartosc!", LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ustawMiastoButton:
                break;
            case R.id.dodajDoListyButton:
                addItems(v, String.valueOf(cityEditText.getText()));
        }

    }
    public void addData(String record) {
        boolean result = databaseHelper.insertData(record);

        if (result) {
            Toast.makeText(WeatherSettings.this, "Dodano!", LENGTH_SHORT).show();
        } else {
            Toast.makeText(WeatherSettings.this, "Coś poszło nie tak :(", LENGTH_SHORT).show();
        }
    }
}
