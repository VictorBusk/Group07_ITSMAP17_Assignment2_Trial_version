package com.assignment2.victorbusk.group07_itsmap17_assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    private ListView weatherLV;
    private Button btnRefresh, btnAdd;
    private TextView txtCity;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list_activity);

        String[] cantos={"1: Abre Tu Oido", "2: A Cristo Quiero", "3: Acerquese Mi Clamor", "4: A Cristo Yo Alabare",
                "5: Acude Dios", "6: Adelante", "7: A Dios Canto", "8: Adios Para Siempre", "9: Ahora Senor", "10: A Jesucristo Ven",
                "11: Alabad A Dios" };

        //Instantiate listView with all the persisted data
//        final ArrayList<Demo> demoList = new ArrayList<Demo>();
//        for(int i = 0; i < 1000; i++){
//            demoList.add(new Demo("Demo #" + (i+1), "Demo #" + (i+1) + " is a great demo"));
//        }

        weatherLV = (ListView) findViewById(R.id.weatherListView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cantos);
        weatherLV.setAdapter(adapter);

        weatherLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_SHORT).show();

            }

        });


        //Add button pressed: Persist city name, add to list and clear textview
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    persistCity();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Refresh button pressed: Reload layout and reload listView (fresh data)
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CityListActivity.this, CityDetailsActivity.class);
                startActivity(in);
            }
        });

    }

    protected void persistCity() throws IOException {
        txtCity = (TextView) findViewById(R.id.tvAddCity);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(txtCity);
        stringBuilder.append("!");
        for (String s : reloadPreferences()) {
            stringBuilder.append(s);
            stringBuilder.append("!");
        }

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("words", stringBuilder.toString());
        editor.apply();
    }

    protected List<String> reloadPreferences() {
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        String wordString = settings.getString("words", "");
        String[] itemWords = wordString.split("!");
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < itemWords.length; i++) {
            items.add(itemWords[i]);
        }
        return items;
    }

    public void showSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(CityListActivity.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
