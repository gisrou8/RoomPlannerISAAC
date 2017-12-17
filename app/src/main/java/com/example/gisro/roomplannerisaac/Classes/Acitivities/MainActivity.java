package com.example.gisro.roomplannerisaac.Classes.Acitivities;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import com.example.gisro.roomplannerisaac.Classes.Repository.AppointmentRepo;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.AppointmentExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.RoomExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Test.RoomTestContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.RoomRepo;
import com.example.gisro.roomplannerisaac.R;
import com.microsoft.graph.extensions.Attendee;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.w3c.dom.Text;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.User;

public class MainActivity extends AppCompatActivity implements ActivityData{

    public static final String ARG_GIVEN_NAME = "givenName";
    private static final int WAIT_TIME = 7000;
    RoomRepo roomController = new RoomRepo(new RoomExContext(null));
    AppointmentRepo appointmentController;
    private ListView lv;
    private ListView lvAttendees;
    private Button btnOpenClose;
    private Button btnLogout;
    private Appointment a;
    private ProgressBar mProgressbar;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvFloor;
    private TextView tvPersons;
    private TextView tvRoom;
    private TextView tvStatus;
    private ConstraintLayout layout;
    private int checkCount = 2000;
    private Room thisRoom;
    ArrayList<Appointment> appointments = null;
    ArrayAdapter<Appointment> arrayAdapter = null;
    ArrayAdapter<String> arrayAdapterAttendees = null;
    List<String> attendees = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenClose = (Button)findViewById(R.id.btnVergadering);
        tvDate = (TextView)findViewById(R.id.appointmentTitle);
        tvTime = (TextView)findViewById(R.id.tvTime);
        tvFloor = (TextView)findViewById(R.id.tvFloor);
        tvPersons = (TextView)findViewById(R.id.tvPersons);
        tvRoom = (TextView) findViewById(R.id.textViewRoom);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        layout = (ConstraintLayout)findViewById(R.id.layoutMain);
        //Current time
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, 1);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        tvTime.setText(df.format(c.getTime()));
        //Setting this room
        thisRoom = (Room)getIntent().getSerializableExtra("Room");
        tvRoom.setText(thisRoom.getName());
        tvPersons.setText(thisRoom.getPersons() + "Persons");
        tvFloor.setText("Floor " + thisRoom.getFloor());
        //Progressbar
        mProgressbar = (ProgressBar)findViewById(R.id.appointmentProgressbar);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Listview for attendees + adapter
        lvAttendees = (ListView) findViewById(R.id.listView2);
        appointmentController = new AppointmentRepo(new AppointmentExContext(thisRoom, this));
        appointmentController.getAllAppointments();
        Log.d("Main", "Current room:" + thisRoom.toString());
        attendees = new ArrayList<String>();
        arrayAdapterAttendees = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, attendees);
        lvAttendees.setAdapter(arrayAdapterAttendees);
        lvAttendees.setEnabled(false);
        lvAttendees.setOnItemClickListener(null);
        //Listview for appointments + adapter
        lv = (ListView) findViewById(R.id.listView);
        appointments = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayAdapterAttendees.clear();
                arrayAdapterAttendees.notifyDataSetChanged();
                Appointment app = (Appointment)adapterView.getAdapter().getItem(i);
                for(User att : app.getAttendees())
                {
                    if(!att.getName().toUpperCase().contains("ROOM") && !att.getName().toUpperCase().contains("MOD")) {
                        attendees.add(att.getName());
                    }
                }
            }
        });
    }

    public void updateList(final ArrayList<Appointment> apps)
    {

    }

    public void btnLogout(View v){
//        AuthenticationManager.getInstance().disconnect();
//        Intent i = new Intent(this, ConnectActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(i);
    }

    public void btnReserveer(View v){
        Intent i = new Intent(this, Reservering.class);
        startActivity(i);
    }

    public void btnVergaderingOnClick(View v){
        if("Open vergadering".equalsIgnoreCase((String)btnOpenClose.getText())){
            if(a != null) {
                a.open();
                btnOpenClose.setText("Sluit vergadering");
            }
        }
        else if("Sluit vergadering".equalsIgnoreCase((String)btnOpenClose.getText())){
            if(a != null) {
                a.close();
                btnOpenClose.setText("Open vergadering");
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

    @Override
    public void setData(final Object data) throws ClassNotFoundException {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Appointment appointment : (ArrayList<Appointment>)data)
                {
                    if (LocalDate.now().compareTo(new LocalDate(appointment.getReserveringsTijd())) == 0) {
                        appointments.add(appointment);
                        if(appointment.getReserveringEind().isAfterNow() && appointment.getReserveringsTijd().isBeforeNow()){
                            layout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundOccupied));
                            btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackgroundOccupied));
                            tvStatus.setText("RESERVED");
                        }
                        Log.d("DateCheck", "Yes");

                    }
                }
                arrayAdapter.notifyDataSetChanged();
                arrayAdapterAttendees.notifyDataSetChanged();
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
