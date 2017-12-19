package com.example.gisro.roomplannerisaac.Classes.Acitivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.gisro.roomplannerisaac.Classes.Repository.AppointmentRepo;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.AppointmentExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.UserExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Test.UserTestContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.UserRepo;
import com.example.gisro.roomplannerisaac.R;
import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.EmailAddress;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.User;

public class Reservering extends AppCompatActivity implements ActivityData, SearchView.OnQueryTextListener{

    SearchView svPersons;
    private ListView lvPersons;
    private ListView lv;
    private ListView lvAttendees;
    ArrayList<Appointment> appointments = null;
    ArrayAdapter<Appointment> arrayAdapter = null;
    ArrayAdapter<String> arrayAdapterAttendees = null;
    List<String> attendees = null;
    SearchListAdapter adapter;
    private TextView tvThisRoom;
    // Init button group
    private Button[] btn = new Button[4];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btn15min, R.id.btn30min, R.id.btn45min, R.id.btn1hour};
    // Reservetime
    private int reserveTime = 0;
    private User selectedUser;
    final List<User> users = new ArrayList<>();
    UserRepo userController = new UserRepo(new UserExContext(this));
    AppointmentRepo appointmentController;
    private Room thisRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservering);
        thisRoom = (Room)getIntent().getSerializableExtra("Room");
        appointmentController = new AppointmentRepo(new AppointmentExContext(thisRoom, this));
        appointmentController.getAllAppointments();
        svPersons = (SearchView)findViewById(R.id.sVPersons);
        lvPersons = (ListView)findViewById(R.id.lvPersons);
        lv = (ListView)findViewById(R.id.listView);
        lvAttendees = (ListView)findViewById(R.id.listView2);
        attendees = new ArrayList<String>();
        arrayAdapterAttendees = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, attendees);
        lvAttendees.setAdapter(arrayAdapterAttendees);
        lvAttendees.setEnabled(false);
        lvAttendees.setOnItemClickListener(null);
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
        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUser = (User)adapter.getItem(i);
            }
        });
        svPersons.setOnQueryTextListener(this);
        tvThisRoom = (TextView)findViewById(R.id.textViewRoom);
        selectedUser = null;
        // Set button group
        for(int i = 0; i < btn.length; i++)
        {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackground(getDrawable(R.drawable.reservebutton));
            btn[i].setTextColor(Color.parseColor("#FF535353"));
        }
        btn_unfocus = btn[0];
        tvThisRoom.setText("Reserve " + thisRoom.getName());
        userController.getUsers();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnReserveer(View v){
          if(selectedUser != null && reserveTime != 0) {
              User user = selectedUser;
              Appointment newApp = new Appointment("Meeting door: " + user.getName(), DateTime.now(), DateTime.now().plusMinutes(reserveTime));
              newApp.addAttendee(new User(selectedUser.getName(), selectedUser.getEmail()));
              newApp.addAttendee(new User(thisRoom.getName(), "Room@M365B679737.onmicrosoft.com"));
              scheduleMeeting(newApp);
              Intent i = new Intent(this, MainActivity.class);
              i.putExtra("Room", thisRoom);
              startActivity(i);
          }

    }

    public void btnCancel(View v){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Room", thisRoom);
        startActivity(i);
    }

    private void scheduleMeeting(final Appointment appointment)
    {
        appointmentController.addAppointment(appointment);
    }


    // Code to use the buttons in a group
    public void btn15min(View v)
    {
        setFocus(btn_unfocus, btn[0]);
        reserveTime = 15;
    }

    public void btn30min(View v)
    {
        setFocus(btn_unfocus, btn[1]);
        reserveTime = 30;
    }

    public void btn45min(View v)
    {
        setFocus(btn_unfocus, btn[2]);
        reserveTime = 45;
    }

    public void btn1hour(View v)
    {
        setFocus(btn_unfocus, btn[3]);
        reserveTime = 60;
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.parseColor("#FF535353"));
        btn_unfocus.setBackground(getDrawable(R.drawable.reservebutton));
        btn_focus.setTextColor(Color.parseColor("#FF118AD3"));
        btn_focus.setBackground(getDrawable(R.drawable.reservebuttonselected));
        this.btn_unfocus = btn_focus;
    }

    @Override
    public void setData(final Object data) throws ClassNotFoundException {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(data instanceof ArrayList<?>)
                {
                    if(((ArrayList<?>)data).get(0) instanceof User)
                    {
                        for (User user : ((ArrayList<User>)
                                data)) {
                            users.add(user);
                        }
                        adapter = new SearchListAdapter(getApplicationContext(), users);
                        lvPersons.setAdapter(adapter);
                        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedUser = (User)adapter.getItem(i);
                                svPersons.setQuery(selectedUser.getName(), false);
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        appointments.clear();
                        for(Appointment appointment : (ArrayList<Appointment>)data)
                        {
                            if (LocalDate.now().compareTo(new LocalDate(appointment.getReserveringsTijd())) == 0) {
                                appointments.add(appointment);
                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                        arrayAdapterAttendees.notifyDataSetChanged();
                    }
                }

            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        adapter.filter(text);
        return false;
    }
}
