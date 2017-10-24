package com.example.gisro.roomplannerisaac.Classes;

import java.util.ArrayList;

/**
 * Created by gisro on 20-9-2017.
 */

public class Room {
    private String Name;
    private String id;
    private State state;
    private ArrayList<Appointment> appointments;
    public Room(String name, String id, int state)
    {
        this.Name = name;
        this.appointments = new ArrayList<>();
        this.id = id;
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

    public State getState(){
        return state;
    }

    public void updateState() {
        for (Appointment a : new ArrayList<Appointment>() /*vervang door opgehaalde lijst Appointments voor deze kamer*/) {
            if (a.getState() == State.Vrij){
                this.state = State.Bezet;
            }
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
