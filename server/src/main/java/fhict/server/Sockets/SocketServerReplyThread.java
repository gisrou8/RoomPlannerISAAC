package fhict.server.Sockets;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.MainActivity;
import fhict.server.Objects.Appointment;

/**
 * Created by BePulverized on 16-11-2017.
 */

public class SocketServerReplyThread extends Thread {

    GraphServiceController mGraphServiceController = new GraphServiceController();
    private static final int WAIT_TIME = 2000;
    private Socket hostThreadSocket;
    int cnt;

    SocketServerReplyThread(Socket socket, int c) {
        hostThreadSocket = socket;
        cnt = c;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        String msgReply = "Hello from Android, you are #" + cnt;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(outputStream);
            final ArrayList<Appointment> appointments = new ArrayList<>();
            final Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (mGraphServiceController.getRoom().getAppointments() != null) {
                        for (Appointment appointment : mGraphServiceController.getRoom().getAppointments()) {
                            //Check if appointment is today
                            if (LocalDate.now().compareTo(new LocalDate(appointment.getReserveringsTijd())) == 0) {
                                appointments.add(appointment);
                            }
                        }
                        // Order the appointments on time
                        Collections.sort(appointments);
                    } else {
                        Log.d("MainActivity", "userlist is null");
                    }
                    handler.postDelayed(this, 5000);

                }
            }, WAIT_TIME);



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
