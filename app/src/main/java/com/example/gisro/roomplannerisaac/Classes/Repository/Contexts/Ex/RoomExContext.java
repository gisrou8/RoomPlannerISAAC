package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;

import com.example.gisro.roomplannerisaac.Classes.Acitivities.MainActivity;
import com.example.gisro.roomplannerisaac.Classes.Client.Client;
import com.example.gisro.roomplannerisaac.Classes.Client.Task;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IRoomContext;

import fhict.mylibrary.Room;


/**
 * Created by Martien on 20-Sep-17.
 */

public class RoomExContext implements IRoomContext {

    private Client client;
    public RoomExContext()
    {
        client = new Client("192.168.178.118", 8080, new Task("Room", null));
        client.start();
    }

    @Override
    public void addRoom(Room room) {

    }

    @Override
    public void updateRoom(Room room) {

    }

    @Override
    public void removeRoom(Room room) {

    }

    @Override
    public ArrayList<Room> getAllRooms() {
        return client.getAllRooms();
    }

    @Override
    public Room getCurrentRoom() {
        return client.getRoom();
    }
}