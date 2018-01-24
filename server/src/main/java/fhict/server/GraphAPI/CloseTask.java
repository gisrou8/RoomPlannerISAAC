package fhict.server.GraphAPI;

import android.os.AsyncTask;

import fhict.mylibrary.Appointment;

/**
 * Created by BePulverized on 23-1-2018.
 */

public class CloseTask extends AsyncTask<Appointment, Void, Void> {

    GraphServiceController controller = new GraphServiceController();
    @Override
    protected Void doInBackground(Appointment... appointments) {
        Appointment appointment = appointments[0];
        controller.closeMeeting(appointment);
        return null;
    }
}
