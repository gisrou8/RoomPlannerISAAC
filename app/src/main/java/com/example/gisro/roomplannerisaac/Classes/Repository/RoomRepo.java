package com.example.gisro.roomplannerisaac.Classes.Repository;

import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IRoomContext;


import java.util.ArrayList;
import java.util.Currency;

import fhict.mylibrary.Room;


/**
 * Created by Martien on 20-Sep-17.
 */

public class RoomRepo implements IRoomContext {

    private IRoomContext room;

    public RoomRepo(IRoomContext room){
        this.room = room;
    }

    @Override
    public void addRoom(Room item) {
        room.addRoom(item);
    }

    @Override
    public void updateRoom(Room item) {
        room.updateRoom(item);
    }

    @Override
    public void removeRoom(Room item) {
        room.updateRoom(item);
    }

    @Override
    public ArrayList<Room> getAllRooms() {
        return room.getAllRooms();
    }

    @Override
    public Room getCurrentRoom() {
        return room.getCurrentRoom();
    }
}
