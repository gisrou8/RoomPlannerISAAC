package com.example.gisro.roomplannerisaac.Classes.GraphAPI;

import android.content.Intent;
import android.os.Build;
import android.preference.EditTextPreference;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.R;
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.DateTimeTimeZone;
import com.microsoft.graph.extensions.Event;
import com.microsoft.graph.extensions.Message;

import org.joda.time.DateTime;

import java.util.Date;

public class Reservering extends AppCompatActivity {

    private ListView gebruikers;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private EditText textViewDuration;
    final private GraphServiceController mGraphServiceController = new GraphServiceController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservering);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        textViewDuration =(EditText) findViewById(R.id.editText);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnReserveer(View v){
        DateTime dt = new DateTime(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
        DateTime dte = dt.
        scheduleMeeting(new Appointment("Test meeting", dt );
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void scheduleMeeting(final Appointment appointment)
    {
        mGraphServiceController.apiScheduleMeeting(appointment, new ICallback<Event>() {
            @Override
            public void success(Event event) {
                Log.d("ReserveringView", "Succes");
            }

            @Override
            public void failure(ClientException ex) {
                Log.d("ReserveringView", "Failure");
            }
        });
    }


}
