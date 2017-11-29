package com.example.gisro.roomplannerisaac.Classes.Client;

import android.os.AsyncTask;
import android.util.Log;


import org.joda.time.DateTime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.User;


public class Client extends Thread {

    private final boolean isConnected;
    String dstAddress;
    int dstPort;
    String response = "";
    String textResponse;
    private Room room;
    private ArrayList<Room> roomList;
    private Task task;
    private ArrayList<User> userList;
    private ArrayList<Appointment> appointments;

    public Client(String addr, int port, Task task) {
        dstAddress = addr;
        dstPort = port;
        this.task = task;
        isConnected = true;
    }

    public Room getRoom()
    {
        return room;
    }

    public ArrayList<Room> getAllRooms() {
        return roomList;
    }

    public ArrayList<User> getUsers() {
        return userList;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public void run() {
        Socket socket = null;
        while (isConnected) {
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            try {
                socket = new Socket(dstAddress, dstPort);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                switch (task.id) {
                    case "Appointments":
                        Room room = (Room) task.data;
                        Object[] oa = {"Appointments", room};
                        oos.writeObject(oa);
                        appointments = (ArrayList<Appointment>) ois.readObject();
                        break;
                    case "Room":
                        Object[] ob = {"Room"};
                        oos.writeObject(ob);
                        roomList = (ArrayList<Room>) ois.readObject();
                        break;
                    case "Users":
                        oos.writeObject("Users");
                        userList = (ArrayList<User>) ois.readObject();
                        break;
                    case "schedule":
                        Object[] od = {"schedule", };
                        oos.writeObject(od);
                        break;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    oos.close();
                    ois.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("client", "Closing error; Sockits/Object streams failed to close");
                }
            }
        }
    }
}