package com.example.gisro.roomplannerisaac.Classes.Repository;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IAppointmentContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentRepo implements IAppointmentContext {

    private IAppointmentContext appointment;

    public AppointmentRepo(IAppointmentContext appointment)
    {
        this.appointment = appointment;
    }
    @Override
    public void addAppointment(Appointment item) {
        appointment.addAppointment(item);
    }

    @Override
    public void updateAppointment(Appointment item) {
        appointment.updateAppointment(item);
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
