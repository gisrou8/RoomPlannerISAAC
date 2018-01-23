package com.example.gisro.roomplannerisaac.Classes.Acitivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.RoomExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.RoomRepo;
import com.example.gisro.roomplannerisaac.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fhict.mylibrary.Room;

public class RuimteSelectie extends AppCompatActivity implements ActivityData {

    public static final String ARG_GIVEN_NAME = "givenName";
    RoomRepo roomController;
    private GridView gv;
    private ProgressBar mProgressbar;
    private int checkCount = 2000;
    private List<Room> rooms = null;
    private Room thisRoom;
    CustomGrid arrayAdapter = null;
    ScheduledExecutorService exec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruimte_selectie);
        thisRoom = (Room)getIntent().getSerializableExtra("Room");
        try {
            roomController = new RoomRepo(new RoomExContext(this));
        }
        catch (Exception ex)
        {

        }
        refreshUI();
        //Language settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String language = preferences.getString("languageSettings", null);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //Demo data
        gv = (GridView) findViewById(R.id.roomList);
        mProgressbar = (ProgressBar)findViewById(R.id.RoomprogressBar);
        mProgressbar.setVisibility(View.VISIBLE);
        rooms = new ArrayList<>();
        arrayAdapter = new CustomGrid(this, rooms, thisRoom);
        gv.setAdapter(arrayAdapter);
    }

    public void btnSettings(View v)
    {
        Intent i = new Intent(RuimteSelectie.this, SettingsActivity.class);
        startActivity(i);
    }


    @Override
    public void setData(final Object data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rooms.clear();
                for(Room r : (ArrayList<Room>)data)
                {
                    rooms.add(r);
                }
                arrayAdapter.notifyDataSetChanged();
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    public void refreshUI() {
        if (exec != null) {
            exec.shutdown();
        }
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                roomController.getAllRooms();
            }
        }, 0, 5, TimeUnit.SECONDS);

    }
}
