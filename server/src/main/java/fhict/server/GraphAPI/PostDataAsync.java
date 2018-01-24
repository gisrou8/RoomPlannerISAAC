package fhict.server.GraphAPI;

import android.os.AsyncTask;

import fhict.mylibrary.Appointment;

/**
 * Created by gisro on 23-1-2018.
 */

public class PostDataAsync extends AsyncTask<Appointment,Void,Void> {

    GraphServiceController serviceController = new GraphServiceController();
    @Override
    protected Void doInBackground(Appointment... appointments) {
        serviceController.apiScheduleMeeting(appointments[0]);
        return null;
    }
}
