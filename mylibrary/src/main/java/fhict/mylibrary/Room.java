package fhict.mylibrary;

import com.microsoft.graph.extensions.Attendee;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gisro on 20-9-2017.
 */

public class Room implements Serializable{
    private String Name;
    private String id;
    private State state;
    private int persons;
    private int floor;
    private ArrayList<Appointment> appointments;

    public Room(String name, String id, int state, int persons, int floor)
    {
        this.Name = name;
        this.appointments = new ArrayList<>();
        this.id = id;
        this.persons = persons;
        this.floor = floor;
        switch (state)
        {
            case 0:
                this.state = State.Vrij;
                break;
            case 1:
                this.state = State.Bezet;
                break;
            case 2:
                this.state = State.Gereserveerd;
                break;
        }
    }

    public Room(String name, ArrayList<Appointment> appointments)
    {
        this.Name = name;
        this.appointments = appointments;
    }

    public int getPersons(){
        return persons;
    }

    public int getFloor(){
        return floor;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public Appointment updateState() {
        ArrayList<DateTime> dates = new ArrayList<>();
        for (Appointment appointment : appointments) {
            dates.add(appointment.getReserveringsTijd());
        }
        if (dates.size() > 0) {
            DateTime minDate = Collections.min(dates);
            for(Appointment appointment : appointments) {
                if(appointment.getReserveringsTijd().equals(minDate)) {
                    if (appointment.getReserveringEind().isAfterNow() && appointment.getReserveringsTijd().isBeforeNow()) {
                        if (this.state == State.Vrij) {
                            this.state = State.Gereserveerd;
                            return appointment;
                        }
                        if (this.state == State.Bezet) {
                            return appointment;
                        }
                    } else {
                        this.state = State.Vrij;
                    }
                }
            }
        }
        this.state = State.Vrij;
        return null;
    }

    @Override
    public String toString(){
        return Name + "\t" + state;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public DateTime getTimeUntilNext()
    {
        DateTime minDate = null;
        if(appointments.size() != 0 && appointments != null) {
            minDate = appointments.get(0).getReserveringsTijd();
            for (Appointment appointment : appointments) {
                if (appointment.getReserveringsTijd().isBefore(minDate) && appointment.getReserveringsTijd().isAfterNow()) {
                    minDate = appointment.getReserveringsTijd();
                }
            }
            return null;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return Name;
    }

}
