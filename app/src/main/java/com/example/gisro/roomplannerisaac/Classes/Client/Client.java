package com.example.gisro.roomplannerisaac.Classes.Client;

import android.os.AsyncTask;
import android.util.Log;


import com.example.gisro.roomplannerisaac.Classes.Acitivities.MainActivity;

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

    @Override
    public void run() {
        Socket socket = null;
        while (isConnected) {
            try {
                socket = new Socket(dstAddress, dstPort);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                switch (task.id) {
                    case "Appointments":
                        Room room = (Room) task.data;
                        oos.writeObject("Appointments%" + room.getName());
                        appointments = (ArrayList<Appointment>) ois.readObject();
                        oos.writeObject("disconnect");
                        oos.close();
                        ois.close();
                        break;
                    case "Room":
                        oos.writeObject("Room");
                        room = (Room) ois.readObject();
                        roomList = (ArrayList<Room>) ois.readObject();
                        oos.writeObject("disconnect");
                        oos.close();
                        ois.close();
                        break;
                    case "Users":
                        oos.writeObject("Users");
                        userList = (ArrayList<User>) ois.readObject();
                        oos.close();
                        ois.close();
                        break;
                    case "PostAppointment":
                        oos.writeObject(new Appointment("blabla", DateTime.now()));
                        oos.close();
                        ois.close();
                        break;
                }


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
}