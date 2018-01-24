package com.example.gisro.roomplannerisaac.Classes.Acitivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.gisro.roomplannerisaac.Classes.ClientModels.DayViewDecoration;
import com.example.gisro.roomplannerisaac.Classes.ClientModels.Event;
import com.example.gisro.roomplannerisaac.Classes.ClientModels.Popup;
import com.example.gisro.roomplannerisaac.Classes.Repository.AppointmentRepo;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.AppointmentExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex.RoomExContext;
import com.example.gisro.roomplannerisaac.Classes.Repository.RoomRepo;
import com.example.gisro.roomplannerisaac.R;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.data.IEvent;
import com.framgia.library.calendardayview.data.IPopup;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.State;
import fhict.mylibrary.User;

public class MainActivity extends AppCompatActivity implements ActivityData {

    public static final String ARG_GIVEN_NAME = "givenName";
    private static final int WAIT_TIME = 7000;
    RoomRepo roomController = null;
    AppointmentRepo appointmentController;
    ScheduledExecutorService exec;
    private Button btnOpenClose;
    private Button btnAlternateRoom;
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
    private Button btnEnd;
    private Button btnExtend;
    private ImageView imgPerson;
    private ConstraintLayout layout;
    private int checkCount = 2000;
    private Room thisRoom;
    private CalendarDayView dayView;
    ArrayList<IEvent> events;
    ArrayList<IPopup> popups;
    ArrayList<Appointment> appointments = null;
    List<String> attendees = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenClose = (Button) findViewById(R.id.btnVergadering);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvFloor = (TextView) findViewById(R.id.tvMeetingTime);
        tvPersons = (TextView) findViewById(R.id.tvPersons);
        tvRoom = (TextView) findViewById(R.id.textViewRoom);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvOpenUntil = (TextView) findViewById(R.id.tvOpenUntil);
        tvPerson = (TextView) findViewById(R.id.tvReservedBy);
        btnEnd = (Button) findViewById(R.id.btEnd);
        btnExtend = (Button) findViewById(R.id.btExtend);
        btnAlternateRoom = (Button) findViewById(R.id.btBack);
        imgPerson = (ImageView) findViewById(R.id.imgPerson);
        layout = (ConstraintLayout) findViewById(R.id.layoutMain);
        dayView = (CalendarDayView)findViewById(R.id.dayViewC);
        dayView.setLimitTime(7, 19);
        DayViewDecoration dec = new DayViewDecoration(this);
        dayView.setDecorator(dec);
        dayView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        dayView.setForegroundGravity(20);
        updateClock();
        //Setting this room
        thisRoom = (Room) getIntent().getSerializableExtra("Room");
        tvRoom.setText(thisRoom.getName());
        tvPersons.setText(thisRoom.getPersons() + " " + getString(R.string.persons));
        tvFloor.setText(getString(R.string.floor) + " " + thisRoom.getFloor());
        //Progressbar
        mProgressbar = (ProgressBar) findViewById(R.id.appointmentProgressbar);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Listview for attendees + adapter
        appointmentController = new AppointmentRepo(new AppointmentExContext(thisRoom, this));
        roomController = new RoomRepo(new RoomExContext(this));
        refreshUI();
        Log.d("Main", "Current room:" + thisRoom.toString());
        attendees = new ArrayList<String>();
        events = new ArrayList<>();
        popups = new ArrayList<>();
        //Listview for appointments + adapter
        appointments = new ArrayList<>();
//        Calendar timeStart = Calendar.getInstance();
//        timeStart.set(Calendar.HOUR_OF_DAY, 18);
//        timeStart.set(Calendar.MINUTE, 0);
//        Calendar timeEnd = (Calendar) timeStart.clone();
//        timeEnd.set(Calendar.HOUR_OF_DAY, 20);
//        timeEnd.set(Calendar.MINUTE, 30);
//        Event event = new Event(1, timeStart, timeEnd, "Another event", "Hockaido", Color.parseColor("#FFFFFF"));
//        events.add(event);
//        Calendar timeStar = Calendar.getInstance();
//        timeStar.set(Calendar.HOUR_OF_DAY, 12);
//        timeStar.set(Calendar.MINUTE, 0);
//        Calendar timeEn = (Calendar) timeStar.clone();
//        timeEn.set(Calendar.HOUR_OF_DAY, 13);
//        timeEn.set(Calendar.MINUTE, 30);
//
//        Popup popup = new Popup();
//        popup.setStartTime(timeStar);
//        popup.setEndTime(timeEn);
//        popup.setImageStart("http://sample.com/image.png");
//        popup.setTitle("event 1 with title");
//        popup.setDescription("Yuong alsdf");
//        popups.add(popup);
//        dayView.setEvents(events);
//        dayView.setPopups(popups);
    }

    public void updateList(final ArrayList<Appointment> apps) {

    }

    public void btnLogout(View v) {
//        AuthenticationManager.getInstance().disconnect();
//        Intent i = new Intent(this, ConnectActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(i);
    }

    public void btnReserveer(View v) {
        Intent i = new Intent(this, Reservering.class);
        startActivity(i);
    }

    public void btnVergaderingOnClick(View v) {
        if (thisRoom.getState() == State.Gereserveerd) {
            thisRoom.setState(State.Bezet);
            roomController.updateRoom(thisRoom);
//            btnOpenClose.setText(getString(R.string.findaRoom));
//            tvStatus.setText(getString(R.string.occupied));
//            tvStatus.setTextSize(100);
//            layout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundOccupied));
//            btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackgroundOccupied));
//            btnExtend.setVisibility(View.VISIBLE);
//            btnEnd.setVisibility(View.VISIBLE);
//            btnAlternateRoom.setVisibility(View.INVISIBLE);
        } else if (thisRoom.getState() == State.Bezet) {
            Intent i = new Intent(this, RuimteSelectie.class);
            i.putExtra("Room", thisRoom);
            startActivity(i);

        } else if (thisRoom.getState() == State.Vrij) {
            Intent i = new Intent(this, Reservering.class);
            i.putExtra("Room", thisRoom);
            startActivity(i);
        }
    }

    public void btnEnd(View v) {
        appointmentController.removeAppointment(thisRoom.updateState());
        thisRoom.setState(State.Vrij);
        roomController.updateRoom(thisRoom);
    }

    public void btnExtend(View v)
    {

    }

    public void btnAltRoomOnClick(View v) {
        Intent i = new Intent(this, RuimteSelectie.class);
        i.putExtra("Room", thisRoom);
        startActivity(i);
    }


    @Override
    public void setData(final Object data) throws ClassNotFoundException {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (data instanceof ArrayList<?>) {
                    if (((ArrayList) data).size() > 0) {
                        if (((ArrayList<?>) data).get(0) instanceof Appointment) {
                            appointments.clear();
                            for (Appointment appointment : (ArrayList<Appointment>) data) {
                                Log.d("Date", LocalDate.now().toString());
                                Calendar calnow = Calendar.getInstance();
                                Calendar calappointment = Calendar.getInstance();
                                calnow.setTime(LocalDate.now().toDate());
                                calappointment.setTime(appointment.getReserveringsTijd().toDate());
                                if (calnow.get(Calendar.YEAR) == calappointment.get(Calendar.YEAR) &&
                                        calnow.get(Calendar.DAY_OF_YEAR) == calappointment.get(Calendar.DAY_OF_YEAR)) {
                                    appointments.add(appointment);
                                }
                            }
                            //Add today's appointments to this room
                            for(Appointment appointment: appointments)
                            {
                                int eventColor = Color.parseColor("#FFCCCCCC");
                                Event event = new Event(1, appointment.getReserveringsTijd(), appointment.getReserveringEind().minusMinutes(1), appointment.getName(), "", eventColor);
                                events.add(event);
                            }
                            dayView.setEvents(events);
                            //Check for reservations
                            thisRoom.setAppointments(appointments);
                            //Check for appointments to cancel
                            Appointment currentReservation = thisRoom.updateState();
                            if (currentReservation != null && thisRoom.getState() == State.Bezet) {
                                setGuiOccupied(currentReservation);
                            }

                            if (currentReservation != null && thisRoom.getState() == State.Gereserveerd) {
                                setGuiReserved(currentReservation);
                            }
                            //Check time till next appointment
                            if (thisRoom.getTimeUntilNext() != null) {
                                if (thisRoom.getState() != State.Gereserveerd) {
                                    tvOpenUntil.setText(getString(R.string.untill) + thisRoom.getTimeUntilNext().toString("HH:mm"));
                                }
                            }             //when no appointments available set it to n/a

                            if (currentReservation == null) {
                                thisRoom.setState(State.Vrij);
                                roomController.updateRoom(thisRoom);
                                setGuiFree();
                            }
                            mProgressbar.setVisibility(View.INVISIBLE);
                        } else {
                            for (Room room : (ArrayList<Room>) data) {
                                if (room.getName().equals(thisRoom.getName())) {
                                        thisRoom = room;
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void setGuiFree()
    {
        tvOpenUntil.setText(getString(R.string.na));
        tvStatus.setText(getString(R.string.freewilly));
        btnOpenClose.setText(R.string.usemeetingroom);
        layout.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackground));
        btnEnd.setVisibility(View.INVISIBLE);
        tvStatus.setTextSize(100);
        btnExtend.setVisibility(View.INVISIBLE);
        imgPerson.setVisibility(View.INVISIBLE);
        tvPerson.setText("");
        btnAlternateRoom.setVisibility(View.VISIBLE);
    }

    public void setGuiReserved(Appointment currentReservation)
    {
        layout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundReserved));
        btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackgroundReserved));
        btnOpenClose.setText(getString(R.string.startmeeting));
        tvStatus.setTextSize(100);
        if (Locale.getDefault().getLanguage() == "nl") {
            tvStatus.setTextSize(60);
        }
        tvStatus.setText(getString(R.string.reserved));
        // Total time reservation has left
        Minutes minutes = Minutes.minutesBetween(new DateTime(), currentReservation.getReserveringEind());
        tvOpenUntil.setTextSize(20);
        tvOpenUntil.setText(getString(R.string.for_) + " " + minutes.getMinutes() + " " + getString(R.string.moreminutes));
        imgPerson.setVisibility(View.VISIBLE);
        tvPerson.setText(getString(R.string.by) + " " + currentReservation.getAttendees().get(0).toString());
    }

    public void setGuiOccupied(Appointment currentReservation)
    {
        btnOpenClose.setText(getString(R.string.findafreeroom));
        tvStatus.setText(getString(R.string.occupied));
        layout.setBackgroundColor(getResources().getColor(R.color.colorBackgroundOccupied));
        btnOpenClose.setTextColor(getResources().getColor(R.color.colorBackgroundOccupied));
        tvOpenUntil.setTextSize(20);
        tvOpenUntil.setText(getString(R.string.untill) + " " + currentReservation.getReserveringEind().toString("HH:mm"));
        imgPerson.setVisibility(View.VISIBLE);
        tvPerson.setText(getString(R.string.by) + " " +  currentReservation.getAttendees().get(0).toString());
        btnExtend.setVisibility(View.VISIBLE);
        btnEnd.setVisibility(View.VISIBLE);
        btnAlternateRoom.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    public void updateClock() {
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
                                SimpleDateFormat dfdate = new SimpleDateFormat("dd-MMM-yyyy");
                                tvDate.setText(dfdate.format(c.getTime()));
                                events.clear();
//                                //Current time
//                                int eventColorc = Color.parseColor("#FF009DDC");
//                                Event eventc = new Event(1, DateTime.now(), DateTime.now().plusSeconds(45), "", "", eventColorc);
//                                events.add(eventc);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

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
                appointmentController.getAllAppointments();
            }
        }, 0, 5, TimeUnit.SECONDS);

    }
}
