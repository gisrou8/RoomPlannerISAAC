package com.example.gisro.roomplannerisaac.Classes.GraphAPI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
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
import com.microsoft.graph.extensions.Attendee;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{

    public static final String ARG_GIVEN_NAME = "givenName";
    private static final int WAIT_TIME = 2000;
    final private GraphServiceController mGraphServiceController = new GraphServiceController();
    private ListView lv;
    private ListView lvAttendees;
    private Button btnOpenClose;
    private Button btnLogout;
    private Appointment a;
    private ProgressBar mProgressbar;
    private TextView tvDate;
    private int checkCount = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenClose = (Button)findViewById(R.id.btnVergadering);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        tvDate = (TextView)findViewById(R.id.appointmentTitle);
        tvDate.setText("Geplande vergaderingen " + DateTime.now().toString("dd-MM-yyyy") + ":");
        mProgressbar = (ProgressBar)findViewById(R.id.appointmentProgressbar);
        //Listview for attendees + adapter
        lvAttendees = (ListView) findViewById(R.id.listView2);
        final List<String> attendees = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapterAttendees = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, attendees);
        lvAttendees.setAdapter(arrayAdapterAttendees);
        lvAttendees.setEnabled(false);
        lvAttendees.setOnItemClickListener(null);
        //Listview for appointments + adapter
        lv = (ListView) findViewById(R.id.listView);
        final ArrayList<Appointment> appointments = new ArrayList<>();
        final ArrayAdapter<Appointment> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayAdapterAttendees.clear();
                arrayAdapterAttendees.notifyDataSetChanged();
                Appointment app = (Appointment)adapterView.getAdapter().getItem(i);
                for(Attendee att : app.getAttendees())
                {
                    attendees.add(att.emailAddress.name);
                }
            }
        });

        final Handler apiHandler = new Handler();
        apiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Init userlist from API
                mGraphServiceController.apiThisRoom();
                // Init roomlist from API
//        mGraphServiceController.apiRooms();
//        // Add appointments to rooms
                mGraphServiceController.apiAppointmentsforRoom();
                apiHandler.postDelayed(this, 10000);
            }
        }, 0);

        // Wait for the graph api to get the data, when data has arrived do something with this data
        final Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    arrayAdapter.clear();
                    arrayAdapterAttendees.clear();

                    mProgressbar.setVisibility(View.INVISIBLE);
                    if (mGraphServiceController.getRoom().getAppointments() != null) {
                        for (Appointment appointment : mGraphServiceController.getRoom().getAppointments()) {
                            //Check if appointment is today
                            if (LocalDate.now().compareTo(new LocalDate(appointment.getReserveringsTijd())) == 0) {
                                appointments.add(appointment);
                            }
                        }
                        // Order the appointments on time
                        Collections.sort(appointments);
                        Log.d("MainActivity", "Adding " + appointments.size() + " items to appointment list");
                        arrayAdapter.notifyDataSetChanged();
                        arrayAdapterAttendees.notifyDataSetChanged();
                    } else {
                        Log.d("MainActivity", "userlist is null");
                    }
                    if (mGraphServiceController.getRoom() != null) {
                        TextView textViewRoom = (TextView) findViewById(R.id.textViewRoom);
                        // Set current room, currently defaulted to NewtonRuimte
                        textViewRoom.setText(mGraphServiceController.getRoom().getName());
                    }
                    handler.postDelayed(this, 5000);

            }
        }, WAIT_TIME);
    }

    public void btnLogout(View v){
        AuthenticationManager.getInstance().disconnect();
        Intent i = new Intent(this, ConnectActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
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
