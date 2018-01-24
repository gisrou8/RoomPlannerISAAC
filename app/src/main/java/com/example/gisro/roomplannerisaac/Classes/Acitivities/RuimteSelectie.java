package com.example.gisro.roomplannerisaac.Classes.Acitivities;

import android.content.Intent;
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

import fhict.mylibrary.Room;

public class RuimteSelectie extends AppCompatActivity implements ActivityData {

    public static final String ARG_GIVEN_NAME = "givenName";
    RoomRepo roomController = new RoomRepo(new RoomExContext(this));
    private GridView gv;
    private ProgressBar mProgressbar;
    private int checkCount = 2000;
    final List<Room> rooms = new ArrayList<>();
    CustomGrid arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruimte_selectie);
        //Demo data
        gv = (GridView) findViewById(R.id.roomList);
        mProgressbar = (ProgressBar)findViewById(R.id.RoomprogressBar);
        mProgressbar.setVisibility(View.VISIBLE);
        arrayAdapter = new CustomGrid(this, rooms);
        gv.setAdapter(arrayAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RuimteSelectie.this, MainActivity.class);
                intent.putExtra("Room", rooms.get(i));
                startActivity(intent);
            }
        });

        // Init roomlist from API
        roomController.getAllRooms();




    }

    public void setList(ArrayList<Room> roomList)
    {

    }


    @Override
    public void setData(final Object data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Room r : (ArrayList<Room>)data)
                {
                    rooms.add(r);
                }
                arrayAdapter.notifyDataSetChanged();
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
