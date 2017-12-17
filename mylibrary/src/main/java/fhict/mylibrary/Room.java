package fhict.mylibrary;

import com.microsoft.graph.extensions.Attendee;

import java.io.Serializable;
import java.util.ArrayList;

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
                this.state = State.Gesloten;
                break;
            case 2:
                this.state = State.Bezet;
                break;
        }
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

    public void updateState() {
        for (Appointment a : new ArrayList<Appointment>() /*vervang door opgehaalde lijst Appointments voor deze kamer*/) {
            /*if (a.getState() == State.Vrij){
                this.state = State.Bezet;
            }*/
        }
        this.state = State.Vrij;
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

    public String getId() {
        return id;
    }

    public String getName(){
        return Name;
    }

}
