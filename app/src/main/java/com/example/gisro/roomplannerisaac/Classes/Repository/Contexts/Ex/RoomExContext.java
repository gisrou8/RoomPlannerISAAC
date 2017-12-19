package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;

import com.example.gisro.roomplannerisaac.Classes.Acitivities.ActivityData;
import com.example.gisro.roomplannerisaac.Classes.Acitivities.MainActivity;
import com.example.gisro.roomplannerisaac.Classes.Acitivities.RuimteSelectie;
import com.example.gisro.roomplannerisaac.Classes.Client.Client;
import com.example.gisro.roomplannerisaac.Classes.Client.Task;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IRoomContext;

import fhict.mylibrary.Room;


/**
 * Created by Martien on 20-Sep-17.
 */

public class RoomExContext implements IRoomContext {

    private Client client;
    private ActivityData activity;
    public RoomExContext(ActivityData activity)
    {
        this.activity = activity;
        client = new Client(new Task("Room", null), activity);
    }

    @Override
    public void addRoom(Room room) {

    }

    @Override
    public void updateRoom(Room room) {
        client = new Client(new Task("openMeeting", room), activity);
        client.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.end();
    }

    @Override
    public void removeRoom(Room room) {

    }

    @Override
    public ArrayList<Room> getAllRooms() {
        client.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.end();
        return client.getAllRooms();
    }

    @Override
    public Room getCurrentRoom() {
        client.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.end();
        return client.getRoom();
    }
}