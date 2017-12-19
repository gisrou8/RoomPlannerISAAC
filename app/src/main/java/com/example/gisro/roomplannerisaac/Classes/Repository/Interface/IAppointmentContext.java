package com.example.gisro.roomplannerisaac.Classes.Repository.Interface;

import com.example.gisro.roomplannerisaac.Classes.Acitivities.MainActivity;

import java.util.ArrayList;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;

/**
 * Created by BePulverized on 20-11-2017.
 */

public interface IAppointmentContext {
    void addAppointment(Appointment item);
    void updateAppointment(Appointment item);
    void removeAppointment(Appointment item);
    ArrayList<Appointment> getAllAppointments();
}
