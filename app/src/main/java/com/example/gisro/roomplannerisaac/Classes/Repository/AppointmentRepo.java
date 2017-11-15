package com.example.gisro.roomplannerisaac.Classes.Repository;

import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces.IAppointmentContext;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentRepo implements IAppointmentContext {

    private IAppointmentContext appointment;

    public AppointmentRepo(IAppointmentContext appointment){
        this.appointment = appointment;
    }

    @Override
    public void addAppointment(Object item) {
        appointment.addAppointment(item);
    }

    @Override
    public void updateAppointment(Object item) {
        appointment.updateAppointment(item);
    }

    @Override
    public void removeAppointment(Object item) {
        appointment.removeAppointment(item);
    }

    @Override
    public List getAllAppointments() {
        return appointment.getAllAppointments();
    }
}
