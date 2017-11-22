package com.example.gisro.roomplannerisaac.Classes.Client;

import android.os.AsyncTask;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import fhict.mylibrary.Room;
import fhict.mylibrary.User;


public class Client extends Thread {

    String dstAddress;
    int dstPort;
    String response = "";
    String textResponse;
    private Room room;
    private ArrayList<Room> roomList;
    String task = "";
    private ArrayList<User> userList;

    public Client(String addr, int port, String task) {
        dstAddress = addr;
        dstPort = port;
        this.task = task;
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
        try {
            socket = new Socket(dstAddress, dstPort);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            switch (task) {
                case "Appointments":
                    oos.writeObject("getTestAppointments");
                    oos.close();
                    ois.close();
                    break;
                case "Room":
                    oos.writeObject("getRoom");
                    room = (Room) ois.readObject();
                    roomList = (ArrayList<Room>) ois.readObject();
                    oos.close();
                    ois.close();
                    break;
                case "Users":
                    oos.writeObject("getUsers");
                    userList = (ArrayList<User>) ois.readObject();
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