package com.example.gisro.roomplannerisaac.Classes.Repository;


import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IAppointmentContext;

import java.util.ArrayList;

import fhict.mylibrary.Appointment;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentRepo implements IAppointmentContext {

    private IAppointmentContext appointment;

    public AppointmentRepo(IAppointmentContext appointment) {
        this.appointment = appointment;
    }

    @Override
    public void addAppointment(Appointment item) {
        appointment.addAppointment(item);
    }

    @Override
    public boolean updateAppointment(Appointment item) {
        return appointment.updateAppointment(item);
    }

    @Override
    public void removeAppointment(Appointment item) {
        appointment.removeAppointment(item);
    }

    @Override
    public ArrayList<Appointment> getAllAppointments() {
        return appointment.getAllAppointments();
    }
}
