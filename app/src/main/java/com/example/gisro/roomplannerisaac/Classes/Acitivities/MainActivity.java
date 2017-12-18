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
import android.widget.ImageView;
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
import org.joda.time.Minutes;
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
    private TextView tvOpenUntil;
    private TextView tvPerson;
    private ImageView imgPerson;
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
        tvOpenUntil = (TextView)findViewById(R.id.tvOpenUntil);
        tvPerson = (TextView)findViewById(R.id.tvReservedBy);
        imgPerson = (ImageView)findViewById(R.id.imgPerson);
        layout = (ConstraintLayout)findViewById(R.id.layoutMain);
        updateClock();
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
        arrayAdapterAttendees = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, attendees);
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
            btnOpenClose.setText("Sluit vergadering");
            tvStatus.setText("OCCUPIED");
            layout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundOccupied));
            btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackgroundOccupied));

        }
        else if("Sluit vergadering".equalsIgnoreCase((String)btnOpenClose.getText())){

            tvStatus.setText("FREE");
            btnOpenClose.setText("Open vergadering");
            layout.setBackgroundColor(getResources().getColor(R.color.colorBackground));
            btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackground));
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
                DateTime minDate = null;
                for(Appointment appointment : (ArrayList<Appointment>)data)
                {
                    minDate = appointment.getReserveringsTijd();
                    if (LocalDate.now().compareTo(new LocalDate(appointment.getReserveringsTijd())) == 0) {
                        appointments.add(appointment);
                        if(appointment.getReserveringsTijd().isBefore(minDate))
                        {
                            minDate = appointment.getReserveringsTijd();
                        }
                        if(appointment.getReserveringEind().isAfterNow() && appointment.getReserveringsTijd().isBeforeNow()){
                            layout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundReserved));
                            btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackgroundReserved));

                            tvStatus.setText("RESERVED");
                            // Total time
                            Minutes minutes = Minutes.minutesBetween(new DateTime(), appointment.getReserveringEind());
                            tvOpenUntil.setTextSize(20);
                            tvOpenUntil.setText("for " + minutes.getMinutes() + " more minutes");
                            imgPerson.setVisibility(View.VISIBLE);

                            tvPerson.setText("by " + appointment.getAttendees().get(0).toString());
                        }
                        Log.d("DateCheck", "Yes");

                    }
                }
                if(minDate != null) {
                    if(tvStatus.getText() != "RESERVED") {
                        tvOpenUntil.setText("until " + minDate.toString("HH:mm"));
                    }
                }
                else{
                    if(tvStatus.getText() != "RESERVED")
                    {
                        tvOpenUntil.setText("N/A");
                    }
                }
                arrayAdapter.notifyDataSetChanged();
                arrayAdapterAttendees.notifyDataSetChanged();
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void updateClock(){
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Current time
                                Calendar c = Calendar.getInstance();
                                c.add(Calendar.HOUR_OF_DAY, 1);
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                                tvTime.setText(df.format(c.getTime()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }
}
