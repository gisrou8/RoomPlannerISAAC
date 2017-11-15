package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Test;

import com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces.IRoomContext;
import com.example.gisro.roomplannerisaac.Classes.Room;
import com.example.gisro.roomplannerisaac.Classes.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martien on 08-Nov-17.
 */

public class RoomTestContext implements IRoomContext {
    private ArrayList<Room> rooms;

    public RoomTestContext(){
        this.rooms = new ArrayList<>();
        rooms.add(new Room("Room 1", "1", 0));
        rooms.add(new Room("Room 2", "2", 1));
        rooms.add(new Room("Room 3", "3", 2));
    }

    @Override
    public void addRoom(Room room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
        }else{
            //Todo: Improve exception handling to relay this message to user
            System.out.println("Room already exists");
        }
    }

    @Override
    public void updateRoom(Room room) {
        if(rooms.contains(room)){
            rooms.set(rooms.indexOf(room), room);
        }else{
            System.out.println("Room does not exist");
        }
    }

    @Override
    public void removeRoom(Room room) {
        if(rooms.contains(room)){
            rooms.remove(room);
        }else{
            System.out.println("Room does not exist");
        }
    }

    @Override
    public List getAllRooms() {
        return rooms;
    }
}
