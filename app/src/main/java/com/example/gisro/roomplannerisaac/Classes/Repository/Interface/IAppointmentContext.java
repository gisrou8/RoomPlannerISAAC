package com.example.gisro.roomplannerisaac.Classes.Repository.Interface;

import com.example.gisro.roomplannerisaac.Classes.Acitivities.MainActivity;

import java.util.ArrayList;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;

/**
 * Created by BePulverized on 20-11-2017.
 */

public interface IAppointmentContext {
    /**
     * @param item -  Appointment to add
     * Adds a new Appointment to the context
     */
    void addAppointment(Appointment item);

    /**
     * @param item - Appointment to update, contains desired changes when called
     * @return boolean - Determines whether the update was succesfull
     * Updates gives Appointment in the context based on it's unique identifier
     */
    boolean updateAppointment(Appointment item);

    /**
     * @param item - Appointment to remove
     * Removes given Appointment from context
     */
    void removeAppointment(Appointment item);

    /**
     * @return ArrayList<Appointment> - List of all Appointments in the context
     * Retrieves all Appointments currently in the context
     */
    ArrayList<Appointment> getAllAppointments();
}
