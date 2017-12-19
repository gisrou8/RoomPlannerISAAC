package com.example.gisro.roomplannerisaac.Classes.Client;

import android.util.Log;


import com.example.gisro.roomplannerisaac.Classes.Acitivities.ActivityData;
import com.example.gisro.roomplannerisaac.Classes.Acitivities.RuimteSelectie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.User;


public class Client extends Thread {

    private boolean isConnected;
    String dstAddress;
    int dstPort;
    String response = "";
    String textResponse;
    private Room room;
    private ArrayList<Room> roomList;
    private Task task;
    private ArrayList<User> userList;
    private ArrayList<Appointment> appointments;
    private ActivityData activity;

    public Client(Task task, ActivityData activity) {
        dstAddress = "192.168.178.118";
        dstPort = 8080;
        this.task = task;
        isConnected = true;
        this.activity =activity;
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
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            socket = new Socket(dstAddress, dstPort);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            switch (task.id) {
                case "Appointments":
                    Room room = (Room) task.data;
                    Object[] oa = {"Appointment", room};
                    oos.writeObject(oa);
                    appointments = (ArrayList<Appointment>) ois.readObject();
                    activity.setData(appointments);
                    break;
                case "Room":
                    Object[] ob = {"Room"};
                    oos.writeObject(ob);
                    roomList = (ArrayList<Room>) ois.readObject();
                    activity.setData(roomList);
                    break;
                case "Users":
                    Object[] oc = {"Users"};
                    oos.writeObject(oc);
                    userList = (ArrayList<User>) ois.readObject();
                    activity.setData(userList);
                    break;
                case "schedule":
                    Object[] od = {"schedule",};
                    oos.writeObject(od);
                    break;
                case "openMeeting":
                    Room meetingRoom = (Room)task.data;
                    Object[] oe = {"openMeeting", meetingRoom};
                    oos.writeObject(oe);
                    break;
                case "closeMeeting":
                    Appointment app = (Appointment)task.data;
                    Object[] of = {"closeMeeting", app};
                    oos.writeObject(of);
                    break;
                case "newAppointment":
                    Appointment newApp = (Appointment)task.data;
                    Object[] og = {"newAppointment", newApp};
                    oos.writeObject(og);
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


    public void end()
    {
        isConnected = false;
    }

}