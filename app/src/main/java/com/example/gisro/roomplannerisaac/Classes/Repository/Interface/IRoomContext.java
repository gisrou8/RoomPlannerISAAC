package com.example.gisro.roomplannerisaac.Classes.Repository.Interface;

import com.example.gisro.roomplannerisaac.Classes.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BePulverized on 20-11-2017.
 */

public interface IRoomContext {
    void addRoom(Room room);
    void updateRoom(Room room);
    void removeRoom(Room room);
    ArrayList<Room> getAllRooms();
    Room getCurrentRoom();
}
