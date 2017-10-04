package com.example.gisro.roomplannerisaac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Classes.Appointment;
import Classes.Room;

public class MainActivity extends AppCompatActivity{

    private ListView lv;
    private Button btnOpenClose;

    private Appointment a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenClose = (Button)findViewById(R.id.btnVergadering);

        //Demo data
        lv = (ListView) findViewById(R.id.listView);

        final List<String> appointments = new ArrayList<String>();
        appointments.add(new Appointment("A1", new Date(), new Room("113")).toString());
        appointments.add(new Appointment("A2", new Date(System.currentTimeMillis()+20*60*1000), new Room("113")).toString());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(appointments);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) lv.getItemAtPosition(i);
                String splitArray[] = s.split(" , ");

                try {
                    SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd '|' hh:mm");
                    Date date = ft.parse(splitArray[1]);
                    a = new Appointment(splitArray[0], date, new Room(splitArray[2]));
                    Log.d("setOnItemClickListener", a.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void btnReserveer(View v){
        Intent i = new Intent(this, Reservering.class);
        startActivity(i);
    }

    public void btnVergaderingOnClick(View v){
        Log.d("btnVergaderingOnClick", (String) btnOpenClose.getText());
        if("Open vergadering".equalsIgnoreCase((String)btnOpenClose.getText())){
            if(a != null) {
                a.open();
                Log.d("btnVergaderingOnClick", "open");
            }
        }
        else if("Sluit vergadering".equalsIgnoreCase((String)btnOpenClose.getText())){
            if(a != null) {
                a.close();
                Log.d("btnVergaderingOnClick", "close");
            }
        }
    }

    public void btnAltRoomOnClick(View v){

    }

    public void getSelectAppointment(View v){
        String s = (String) lv.getSelectedItem();
        Log.d("getSelectedItem", s);
    }
}
