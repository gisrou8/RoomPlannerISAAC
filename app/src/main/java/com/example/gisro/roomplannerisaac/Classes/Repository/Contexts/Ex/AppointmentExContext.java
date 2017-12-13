package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;


import com.example.gisro.roomplannerisaac.Classes.Acitivities.ActivityData;
import com.example.gisro.roomplannerisaac.Classes.Client.Client;
import com.example.gisro.roomplannerisaac.Classes.Client.Task;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IAppointmentContext;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentExContext implements IAppointmentContext {

    private Client client;
    private Room room;
    private ActivityData activity;
    public AppointmentExContext(Room room, ActivityData activity){
        this.room = room;
        this.activity = activity;
    }

    @Override
    public void addAppointment(Appointment item) {
        client = new Client("192.168.178.118", 8080, new Task("schedule", item), activity);
        client.start();
    }
    @Override
    public void updateAppointment(Appointment item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAppointment(Appointment item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArrayList<Appointment> getAllAppointments() {
        client = new Client("192.168.178.118", 8080, new Task("Appointments", room), activity);
        client.start();
        return client.getAppointments();
    }
}
