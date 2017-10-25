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
    private DateTimeTimeZone reserveringsTijdTZ;
    private DateTime eindTijd;
    private DateTimeTimeZone eindTijdTZ;
    private State state;
    private ArrayList<Attendee> attendees;

    public Appointment(String Name, DateTime reserveringsTijd)
    {
        this.Name = Name;
        this.reserveringsTijd = reserveringsTijd;
        DateTimeTimeZone dttz = new DateTimeTimeZone();
        dttz.dateTime = reserveringsTijd.toString();
        this.reserveringsTijdTZ = dttz;
        this.state = State.Closed;
    }

    public Appointment(String name, DateTime reserveringsTijd, DateTime eindTijd){
        this.Name = name;

        this.reserveringsTijd = reserveringsTijd;
        DateTimeTimeZone dtr = new DateTimeTimeZone();
        dtr.dateTime = reserveringsTijd.toString();
        this.reserveringsTijdTZ = dtr;

        this.eindTijd = eindTijd;
        DateTimeTimeZone dt = new DateTimeTimeZone();
        dt.dateTime = reserveringsTijd.toString();

        this.state = State.Closed;
    }

    public Appointment(String name, DateTimeTimeZone reserveringsTijdTZ, DateTimeTimeZone eindTijdTZ){
        this.Name = name;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String date = reserveringsTijdTZ.dateTime.substring(0, reserveringsTijdTZ.dateTime.length() - 8);
        date = date.replace("T", " ");
        this.reserveringsTijd = formatter.parseDateTime(date);

        String date = reserveringsTijdTZ.dateTime.substring(0, reserveringsTijdTZ.dateTime.length() - 8);
        date = date.replace("T", " ");
        this.reserveringsTijd = formatter.parseDateTime(date);

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

    public DateTimeTimeZone getReserveringsTijdTZ(){return reserveringsTijdTZ;}

    @Override
    public String toString(){
        return Name + " , " + reserveringsTijd.toString("MM-dd-yyyy HH:mm");
    }

    @Override
    public int compareTo(@NonNull Appointment appointment) {
        return reserveringsTijd.compareTo(appointment.getReserveringsTijd());
    }
}
