package com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces;

import java.util.List;

/**
 * Created by pieni on 20/09/2017.
 */

public interface IAppointmentContext<A> {
    void addAppointment(A item);
    void updateAppointment(A item);
    void removeAppointment(A item);
    List<A> getAllAppointments();
}
