package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;


import com.example.gisro.roomplannerisaac.Classes.Acitivities.MainActivity;
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

    public AppointmentExContext(Room room){
        client = new Client("192.168.178.118", 8080, new Task("Appointments", room));
        client.start();
    }

    @Override
    public void addAppointment(Appointment item) {

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
        return client.getAppointments();
    }
}
