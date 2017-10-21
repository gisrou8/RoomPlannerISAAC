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
    public void add(Object item) {
        appointment.add(item);
    }

    @Override
    public void update(Object item) {
        appointment.update(item);
    }

    @Override
    public void remove(Object item) {
        appointment.remove(item);
    }

    @Override
    public List getAll() {
        return appointment.getAll();
    }
}
