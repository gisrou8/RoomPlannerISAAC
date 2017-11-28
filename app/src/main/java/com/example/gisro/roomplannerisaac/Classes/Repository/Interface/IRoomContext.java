package com.example.gisro.roomplannerisaac.Classes.Repository.Interface;

import java.util.ArrayList;

import fhict.mylibrary.Room;

/**
 * Created by BePulverized on 20-11-2017.
 */

public interface IRoomContext {
    void addRoom(Room room);
    void updateRoom(Room room);
    void removeRoom(Room room);
    ArrayList<Room> getAllRooms();
    Room getCurrentRoom(   );
}
