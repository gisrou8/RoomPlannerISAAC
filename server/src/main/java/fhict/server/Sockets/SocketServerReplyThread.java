package fhict.server.Sockets;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.CommandList.AppointmentCommandI;
import fhict.server.Sockets.CommandList.IClientCommand;
import fhict.server.Sockets.CommandList.Commands;
import fhict.server.Sockets.CommandList.DisconnectCommandI;
import fhict.server.Sockets.CommandList.RoomCommandI;
import fhict.server.Sockets.CommandList.ScheduleCommandI;
import fhict.server.Sockets.CommandList.UserCommandI;

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
        cmds.addCommand(new RoomCommandI(), "Room");
        cmds.addCommand(new DisconnectCommandI(), "disconnect");
        cmds.addCommand(new UserCommandI(), "Users");
        cmds.addCommand(new AppointmentCommandI(), "Appointment");
        cmds.addCommand(new ScheduleCommandI(), "schedule");
        Commands server = new Commands();
        cmds.addCommand(server, "server");
        this.controller = controller;
        isConnected = true;
    }

    public void addCommand(IClientCommand cmd, String name){
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
                Object[] data = (Object[])ois.readObject();
                cmds.execute(this, data, controller);
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
