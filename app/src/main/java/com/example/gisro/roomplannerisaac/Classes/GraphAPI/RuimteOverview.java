package com.example.gisro.roomplannerisaac.Classes.GraphAPI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.Classes.Room;
import com.example.gisro.roomplannerisaac.R;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuimteOverview extends AppCompatActivity{

    public static final String ARG_GIVEN_NAME = "givenName";
    final private GraphServiceController mGraphServiceController = new GraphServiceController();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        //Demo data
        lv = (ListView) findViewById(R.id.listView);
        final List<String> rooms = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rooms);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) lv.getItemAtPosition(i);
                String splitArray[] = s.split(" , ");
            }
        });

        // Init roomlist from API
        mGraphServiceController.apiRooms();
        // Wait for the graph api to get the data, when data has arrived do something with this data
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mGraphServiceController.getRooms() != null) {
                    for (Room r : mGraphServiceController.getRooms()){
                        //Check if appointment is today
                        rooms.add(r.toString());
                    }
                    // Order the appointments on time

                    arrayAdapter.notifyDataSetChanged();
                }
                else{
                    Log.d("MainActivity", "userlist is null");
                }
            }
        }, 2000);





    }



}
