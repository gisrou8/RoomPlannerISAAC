package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;
import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IAppointmentContext;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentExContext implements IAppointmentContext {


    public AppointmentExContext(){
        throw new UnsupportedOperationException();
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
