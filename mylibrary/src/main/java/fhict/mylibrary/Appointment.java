package fhict.mylibrary;

import android.support.annotation.NonNull;

import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.DateTimeTimeZone;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gisro on 20-9-2017.
 */

public class Appointment implements Comparable<Appointment>{

    private String Name;
    private DateTime reserveringsTijd;
    private DateTimeTimeZone reserveringsTijdTZ;
    private State state;
    private List<Attendee> attendees;

    public Appointment(String Name, DateTimeTimeZone reserveringsTijd) {
        this.Name = Name;
        String date = reserveringsTijd.dateTime.substring(0, reserveringsTijd.dateTime.length() - 8);
        date = date.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime(date);
        this.reserveringsTijd = dt;
        this.reserveringsTijdTZ = reserveringsTijd;
        this.state = State.Gesloten;
    }

    public Appointment(String Name, DateTime reserveringsTijd) {
        this.Name = Name;
        this.reserveringsTijd = reserveringsTijd;
        DateTimeTimeZone dt = new DateTimeTimeZone();
        dt.dateTime = reserveringsTijd.toString();
        this.reserveringsTijdTZ = dt;
        this.state = State.Gesloten;
        this.attendees = new ArrayList<>();
    }

    public Appointment(String Name, DateTimeTimeZone reserveringsTijd, List<Attendee> attendees) {
        this.Name = Name;
        String date = reserveringsTijd.dateTime.substring(0, reserveringsTijd.dateTime.length() - 8);
        date = date.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime(date);
        this.reserveringsTijd = dt.plusHours(2);
        this.reserveringsTijdTZ = reserveringsTijd;
        this.state = State.Gesloten;
        this.attendees = attendees;
    }

    public State getState() {
        return state;
    }

    public boolean open(){
        if(reserveringsTijd.isAfterNow() && reserveringsTijd.isBefore(new DateTime(System.currentTimeMillis()+10*60*1000))){
//            GraphServiceClientManager gcm = new GraphServiceClientManager();
//            gcm.updateStateRoom(State.Bezet);
            return true;
        }
        return false;
    }

//    public boolean open(){
//      if(reserveringsTijd.after(new Date()) && reserveringsTijd.before(new Date(System.currentTimeMillis()+5*60*1000))){
//          this.state = State.Open;
//          return true;
//      }
//      return false;
//    }

    public void close(){
//        GraphServiceClientManager gcm = new GraphServiceClientManager();
//        gcm.updateStateRoom(State.Vrij);
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
    public String toString() {
        return Name + " , " + reserveringsTijd.toString("HH:mm");
    }

    @Override
    public int compareTo(@NonNull Appointment appointment) {
        return reserveringsTijd.compareTo(appointment.getReserveringsTijd());
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }


    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }
}
