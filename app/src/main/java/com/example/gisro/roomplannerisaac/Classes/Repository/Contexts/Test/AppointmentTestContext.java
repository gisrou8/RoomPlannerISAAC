package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Test;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces.IAppointmentContext;
import com.microsoft.graph.extensions.Attendee;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Martien on 08-Nov-17.
 */

public class AppointmentTestContext implements IAppointmentContext {
    private ArrayList<Appointment> appointments;

    public AppointmentTestContext() {
        appointments = new ArrayList<>();
        appointments.add(new Appointment("Standup", new DateTime(2017, 12, 5, 13, 45)));
        appointments.add(new Appointment("McDonalds", new DateTime(2017, 12, 5, 11, 10)));
        appointments.add(new Appointment("Dinosaurus", new DateTime(2017, 12, 5, 19, 0)));
    }

    @Override
    public void addAppointment(Object item) {
        if (!appointments.contains(item)) {
            appointments.add((Appointment)item);
        }else{
            //Todo: Improve exception handling to relay this message to user
            System.out.println("Appointment already exists");
        }
    }

    @Override
    public void updateAppointment(Object item) {
        if (appointments.contains(item)) {
            appointments.set(appointments.indexOf(item), (Appointment)item);
        }else{
            //Todo: Improve exception handling to relay this message to user
            System.out.println("Appointment doesn't exist");
        }
    }

    @Override
    public void removeAppointment(Object item) {
        if (appointments.contains(item)) {
            appointments.remove(item);
        }else{
            //Todo: Improve exception handling to relay this message to user
            System.out.println("Appointment doesn't exist");
        }
    }

    @Override
    public List getAllAppointments() {
        return appointments;
    }
}
