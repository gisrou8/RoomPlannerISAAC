package fhict.server;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TimePicker;


import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.EmailAddress;
import com.microsoft.graph.extensions.Event;
import com.microsoft.graph.extensions.User;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Appointment;
import fhict.server.GraphAPI.GraphServiceController;

public class Reservering extends AppCompatActivity {

    private ListView gebruikers;
    private ListView selectedUserslv;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private NumberPicker numPicker;
    private EditText appointmentNameText;
    final List<fhict.mylibrary.User> selUsers = new ArrayList<>();
    final private GraphServiceController mGraphServiceController = new GraphServiceController();
    private ProgressBar mProgressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservering);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        numPicker = (NumberPicker) findViewById(R.id.numberPicker2);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(60);
        numPicker.setWrapSelectorWheel(true);
        gebruikers = (ListView) findViewById(R.id.listView);
        appointmentNameText = (EditText)findViewById(R.id.editText);
        selectedUserslv = (ListView) findViewById(R.id.lvSelectedUsers);
        mProgressbar = (ProgressBar)findViewById(R.id.progressBar);
        final List<fhict.mylibrary.User> users = new ArrayList<>();
        final ArrayAdapter<fhict.mylibrary.User> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);

        final ArrayAdapter<fhict.mylibrary.User> selUsersarrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selUsers);
        gebruikers.setChoiceMode(ListView.CHOICE_MODE_NONE);
        gebruikers.setAdapter(arrayAdapter);
        gebruikers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!selUsers.contains(users.get(i))) {
                    selUsers.add(users.get(i));
                    selUsersarrayAdapter.notifyDataSetChanged();
                }
            }
        });
        selectedUserslv.setAdapter(selUsersarrayAdapter);
        selectedUserslv.setOnItemClickListener(null);

        // Init userlist from API
        mGraphServiceController.apiUsers();
        // Wait for the graph api to get the data, when data has arrived do something with this data
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressbar.setVisibility(View.VISIBLE);

                    mProgressbar.setVisibility(View.INVISIBLE);
                    if (mGraphServiceController.getUsers() != null) {
                        for (fhict.mylibrary.User user : mGraphServiceController.getUsers()) {
                            users.add(new fhict.mylibrary.User(user.getName(), user.getEmail()));
                        }


                        arrayAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("MainActivity", "userlist is null");
                    }

            }
        }, 2000);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnReserveer(View v){
        Appointment newApp = new Appointment(appointmentNameText.getText().toString(), new DateTime(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute()));
        for(fhict.mylibrary.User user: selUsers)
        {

            Attendee att = new Attendee();
            EmailAddress email = new EmailAddress();
            email.name = user.getName();
            email.address = user.getEmail();
            att.emailAddress = email;
            newApp.addAttendee(new fhict.mylibrary.User(att.emailAddress.name, att.emailAddress.address));
        }
        scheduleMeeting(newApp);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void scheduleMeeting(final Appointment appointment)
    {
//        mGraphServiceController.apiScheduleMeeting(appointment, new ICallback<Event>() {
//            @Override
//            public void success(Event event) {
//                Log.d("ReserveringView", "Succes");
//            }
//
//            @Override
//            public void failure(ClientException ex) {
//                Log.d("ReserveringView", "Failure");
//            }
//        });
        mGraphServiceController.apiScheduleMeeting(appointment);
    }


}
