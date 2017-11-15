package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.Classes.GraphAPI.GraphServiceController;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces.IAppointmentContext;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentExContext implements IAppointmentContext {

    final private GraphServiceController gsc;

    public AppointmentExContext(){
        this.gsc = new GraphServiceController();
    }

    @Override
    public void addAppointment(Object item) {
        gsc.addAppointment((Appointment)item);
    }

    @Override
    public void updateAppointment(Object item) {
        gsc.updateAppointment((Appointment)item);
    }

    @Override
    public void removeAppointment(Object item) {
        gsc.removeAppointment((Appointment)item);
    }

    @Override
    public List getAllAppointments() {
        return gsc.getAllAppointment();
    }
}
