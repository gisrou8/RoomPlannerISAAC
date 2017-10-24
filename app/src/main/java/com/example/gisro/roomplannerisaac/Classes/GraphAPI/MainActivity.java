package com.example.gisro.roomplannerisaac.Classes.GraphAPI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.R;

import org.joda.time.LocalDate;

public class MainActivity extends AppCompatActivity{

    public static final String ARG_GIVEN_NAME = "givenName";
    private static final int WAIT_TIME = 1500;
    final private GraphServiceController mGraphServiceController = new GraphServiceController();
    private ListView lv;
    private Button btnOpenClose;
    private Appointment a;
    private ProgressBar mProgressbar;
    private int checkCount = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenClose = (Button)findViewById(R.id.btnVergadering);
        mProgressbar = (ProgressBar)findViewById(R.id.appointmentProgressbar);
        //Demo data
        lv = (ListView) findViewById(R.id.listView);
        final List<String> appointments = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appointments);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) lv.getItemAtPosition(i);
                String splitArray[] = s.split(" , ");
            }
        });

        // Init userlist from API
        mGraphServiceController.apiThisRoom();
        // Init roomlist from API
//        mGraphServiceController.apiRooms();
//        // Add appointments to rooms
        mGraphServiceController.apiAppointmentsforRoom();
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
                    if (mGraphServiceController.getRoom().getAppointments() != null) {
                        for (Appointment appointment : mGraphServiceController.getRoom().getAppointments()) {
                            //Check if appointment is today
                            if (LocalDate.now().compareTo(new LocalDate(appointment.getReserveringsTijd())) == 0) {
                                appointments.add(appointment.toString());
                            }
                        }
                        // Order the appointments on time
                        Collections.sort(appointments);
                        Log.d("MainActivity", "Adding " + appointments.size() + " items to appointment list");
                        arrayAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("MainActivity", "userlist is null");
                    }
                    if (mGraphServiceController.getRoom() != null) {
                        TextView textViewRoom = (TextView) findViewById(R.id.textViewRoom);
                        // Set current room, currently defaulted to NewtonRuimte
                        textViewRoom.setText(mGraphServiceController.getRoom().getName());
                    }
                }
            }
        }, WAIT_TIME);
    }

    public void btnReserveer(View v){
        Intent i = new Intent(this, Reservering.class);
        startActivity(i);
    }

    public void btnVergaderingOnClick(View v){
        Log.d("btnVergaderingOnClick", (String) btnOpenClose.getText());
        if("Open vergadering".equalsIgnoreCase((String)btnOpenClose.getText())){
            if(a != null) {
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
        Intent i = new Intent(this, RuimteOverview.class);
        startActivity(i);
    }

    public void getSelectAppointment(View v){
        String s = (String) lv.getSelectedItem();
        Log.d("getSelectedItem", s);
    }

}
