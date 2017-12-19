package com.example.gisro.roomplannerisaac.Classes.Acitivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.RoomExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Test.RoomTestContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.RoomRepo;
import com.example.gisro.roomplannerisaac.R;

import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Room;

public class RuimteOverview extends AppCompatActivity{

    public static final String ARG_GIVEN_NAME = "givenName";
    RoomRepo roomController = new RoomRepo(new RoomExContext(null));
    private ListView lv;
    private ProgressBar mProgressbar;
    private int checkCount = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        //Demo data
        lv = (ListView) findViewById(R.id.listView);
        mProgressbar = (ProgressBar)findViewById(R.id.RoomprogressBar);
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
//        mGraphServiceController.apiRooms();
        // Wait for the graph api to get the data, when data has arrived do something with this data
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressbar.setVisibility(View.VISIBLE);
                checkCount -= 1000;
                if(checkCount > 0){
                    handler.postDelayed(this, 1000);
                }
                else {
                    mProgressbar.setVisibility(View.INVISIBLE);
                    if (roomController.getAllRooms() != null) {
                        rooms.clear();
                        for (Room r: roomController.getAllRooms()) {
                            //Check if appointment is today
                            rooms.add(r.toString());
                        }
                        // Order the appointments on time

                        arrayAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("MainActivity", "userlist is null");
                    }
                }
            }
        }, 2000);





    }



}
