package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;


import com.example.gisro.roomplannerisaac.Classes.Client.Client;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IAppointmentContext;

import fhict.mylibrary.Appointment;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentExContext implements IAppointmentContext {

    private Client client;

    public AppointmentExContext(){
        client = new Client("192.168.178.118", 8080, "Appointments");
        client.start();
    }

    @Override
    public void addAppointment(Appointment item) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
}
