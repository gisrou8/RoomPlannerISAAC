package fhict.mylibrary;

import android.support.annotation.NonNull;

import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.DateTimeTimeZone;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by gisro on 20-9-2017.
 */

public class Appointment implements Comparable<Appointment>, Serializable {

    private String id;
    private String Name;
    private DateTime reserveringsTijd;
    private DateTime reserveringEind;
    private State state;
    private List<User> attendees;

    /**
     * @param Name             - Name for the appointment
     * @param reserveringsTijd - Starting time for the appointment
     * @param reserveringEind  - End time for the appointment
     */
    public Appointment(String Name, DateTime reserveringsTijd, DateTime reserveringEind) {
        this.Name = Name;
        this.reserveringsTijd = reserveringsTijd;
        this.reserveringEind = reserveringEind;
        DateTimeTimeZone dt = new DateTimeTimeZone();
        dt.dateTime = reserveringsTijd.toString();
        this.state = State.Vrij;
        this.attendees = new ArrayList<>();
    }

    /**
     * @param Name             - Name for the appointment
     * @param reserveringsTijd - Starting time for the appointment
     * @param attendees        - People invited to the appointment
     */
    public Appointment(String Name, DateTimeTimeZone reserveringsTijd, List<User> attendees) {
        this.Name = Name;
        String date = reserveringsTijd.dateTime.substring(0, reserveringsTijd.dateTime.length() - 8);
        date = date.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime(date);
        this.reserveringsTijd = dt.plusHours(1);
        this.state = State.Vrij;
        this.attendees = attendees;
    }

    /**
     * @param Name             - Name for the appointment
     * @param reserveringsTijd - Starting time for the appointment
     * @param reserveringEinde - End time for the appointment
     * @param attendees        - People invited to the appointment
     * @param id               - Unique identifier
     * @throws ParseException
     */
    public Appointment(String Name, DateTimeTimeZone reserveringsTijd, DateTimeTimeZone reserveringEinde, List<User> attendees, String id) throws ParseException {
        this.Name = Name;
        String date = reserveringsTijd.dateTime.substring(0, reserveringsTijd.dateTime.length() - 8);
        date = date.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = formatter.parseDateTime(date);
        this.reserveringsTijd = dt.plusHours(1);
        String dateEnd = reserveringEinde.dateTime.substring(0, reserveringEinde.dateTime.length() - 8);
        dateEnd = dateEnd.replace("T", " ");
        DateTimeFormatter formatterEnd = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dtEnd = formatterEnd.parseDateTime(dateEnd);
        this.reserveringEind = dtEnd.plusHours(1);
        this.state = State.Vrij;
        this.attendees = attendees;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public DateTime getReserveringsTijd() {
        return reserveringsTijd;
    }

    public DateTime getReserveringEind() {
        return reserveringEind;
    }

    public void setReserveringEind(DateTime end) {
        this.reserveringEind = end;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void addAttendee(User attendee) {
        attendees.add(attendee);
    }

    @Override
    public String toString() {
        return Name + " , " + reserveringsTijd.toString("HH:mm") + " - " + reserveringEind.toString("HH:mm");
    }

    @Override
    public int compareTo(@NonNull Appointment appointment) {
        return reserveringsTijd.compareTo(appointment.getReserveringsTijd());
    }
}
