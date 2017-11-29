package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Test;

import com.example.gisro.roomplannerisaac.Classes.Acitivities.MainActivity;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IAppointmentContext;
import org.joda.time.DateTime;

import java.util.ArrayList;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;

/**
 * Created by Martien on 08-Nov-17.
 */

public class AppointmentTestContext implements IAppointmentContext {
    private ArrayList<Appointment> appointments;

    public AppointmentTestContext() {
        appointments = new ArrayList<>();
        appointments.add(new Appointment("Standup", DateTime.now()));
        appointments.add(new Appointment("McDonalds", new DateTime(2017, 12, 5, 11, 10)));
        appointments.add(new Appointment("Dinosaurus", new DateTime(2017, 12, 5, 19, 0)));
    }

    @Override
    public void addAppointment(Appointment item) {
        if (!appointments.contains(item)) {
            appointments.add((Appointment)item);
        }else{
            //Todo: Improve exception handling to relay this message to user
            System.out.println("Appointment already exists");
        }
    }

    @Override
    public void updateAppointment(Appointment item) {
        if (appointments.contains(item)) {
            appointments.set(appointments.indexOf(item), (Appointment)item);
        }else{
            //Todo: Improve exception handling to relay this message to user
            System.out.println("Appointment doesn't exist");
        }
    }

    @Override
    public void removeAppointment(Appointment item) {
        if (appointments.contains(item)) {
            appointments.remove(item);
        }else{
            //Todo: Improve exception handling to relay this message to user
            System.out.println("Appointment doesn't exist");
        }
    }

    @Override
    public ArrayList<Appointment> getAllAppointments() {
        return null;
    }

}
