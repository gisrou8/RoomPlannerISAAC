package com.example.gisro.roomplannerisaac.Classes;

import android.support.annotation.NonNull;

import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.DateTimeTimeZone;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gisro on 20-9-2017.
 */

public class Appointment implements Comparable<Appointment>{
    private String Name;
    private DateTime reserveringsTijd;
    private State state;
    private ArrayList<Attendee> attendees;

    public Appointment(String Name, DateTimeTimeZone reserveringsTijd)
    {
        this.Name = Name;
        String date = reserveringsTijd.dateTime.substring(0, reserveringsTijd.dateTime.length() - 8);
        date = date.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime(date);
        this.reserveringsTijd = dt;
        this.state = State.Closed;
    }

    public State getState(){
        return state;
    }

//    public boolean open(){
//      if(reserveringsTijd.after(new Date()) && reserveringsTijd.before(new Date(System.currentTimeMillis()+5*60*1000))){
//          this.state = State.Open;
//          return true;
//      }
//      return false;
//    }

    public void close(){
        this.state = State.Closed;
    }

    public String getName()
    {
        return Name;
    }

    public DateTime getReserveringsTijd()
    {
        return reserveringsTijd;
    }

    @Override
    public String toString(){
        return Name + " , " + reserveringsTijd.toString("MM-dd-yyyy HH:mm");
    }

    @Override
    public int compareTo(@NonNull Appointment appointment) {
        return reserveringsTijd.compareTo(appointment.getReserveringsTijd());
    }
}
