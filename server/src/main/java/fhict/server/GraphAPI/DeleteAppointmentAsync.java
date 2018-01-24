package fhict.server.GraphAPI;

import android.os.AsyncTask;

import fhict.mylibrary.Appointment;

/**
 * Created by gisro on 24-1-2018.
 */

public class DeleteAppointmentAsync extends AsyncTask<Appointment,Void,Void> {
    GraphServiceController serviceController = new GraphServiceController();
    @Override
    protected Void doInBackground(Appointment... appointments) {
        serviceController.closeMeeting(appointments[0]);
        return null;
    }
}
