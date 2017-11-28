package fhict.server.Sockets;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.CommandList.AppointmentCommand;
import fhict.server.Sockets.CommandList.ClientCommand;
import fhict.server.Sockets.CommandList.Commands;
import fhict.server.Sockets.CommandList.DisconnectCommand;
import fhict.server.Sockets.CommandList.RoomCommand;
import fhict.server.Sockets.CommandList.UserCommand;

/**
 * Created by BePulverized on 16-11-2017.
 */

public class SocketServerReplyThread extends Thread {

    private static final int WAIT_TIME = 2000;
    private Socket hostThreadSocket;
    private boolean isConnected;
    private Commands cmds;
    private ObjectInputStream ois;
    private ObjectOutputStream oossend;
    private GraphServiceController controller;

    SocketServerReplyThread(Socket socket, GraphServiceController controller) {
        hostThreadSocket = socket;
        cmds = new Commands();
        cmds.addCommand(new RoomCommand(), "Room");
        cmds.addCommand(new DisconnectCommand(), "disconnect");
        cmds.addCommand(new UserCommand(), "Users");
        cmds.addCommand(new AppointmentCommand(), "Appointment");
        Commands server = new Commands();
        cmds.addCommand(server, "server");
        this.controller = controller;
        isConnected = true;
    }

    public void addCommand(ClientCommand cmd, String name){
        cmds.addCommand(cmd, name);
    }

    public void close(){
        isConnected = false;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(hostThreadSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(hostThreadSocket.getInputStream());

            oossend = oos;

            while (isConnected) {
                String recv = (String)ois.readObject();
                Log.d("Server says", "Received message: " + recv);
                if (recv != null) {
                    String[] data = recv.split("%");

                    cmds.execute(this, data, controller);

                }
            }
        } catch (IOException e) {} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void send(Object data) throws IOException {
        oossend.writeObject(data);
    }
}
