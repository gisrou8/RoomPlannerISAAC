package fhict.server.Sockets;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;

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
        final ObjectOutputStream oos;
        final ObjectInputStream ois;

        String msgReply = "Hello from Android, you are #" + cnt;

        try {
            oos = new ObjectOutputStream(hostThreadSocket.getOutputStream());
            ois = new ObjectInputStream(hostThreadSocket.getInputStream());
            //writing api data
            Log.d("Writing", "Sending room");
            String task = (String)ois.readObject();
            switch(task)
            {
                case "getRoom":
                    mGraphServiceController.apiThisRoom();
                    mGraphServiceController.apiRooms();
                    Thread.sleep(1000);
                    Log.d("ServiceController", mGraphServiceController.getRoom().toString());
                    oos.writeObject(mGraphServiceController.getRoom());
                    oos.writeObject(mGraphServiceController.getRooms());
                    oos.close();
                    ois.close();
                    break;
                case "getApointments":
                    oos.writeObject(new Room("LOL", "lol", 0));
                    oos.close();
                    ois.close();
                    break;
                case "getUsers":
                    mGraphServiceController.apiUsers();
                    Thread.sleep(1000);
                    oos.writeObject(mGraphServiceController.getUsers());
                    oos.close();
                    ois.close();
                    break;
                case "postAppointment":
                    mGraphServiceController.apiScheduleMeeting((Appointment)ois.readObject());
            }






        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
