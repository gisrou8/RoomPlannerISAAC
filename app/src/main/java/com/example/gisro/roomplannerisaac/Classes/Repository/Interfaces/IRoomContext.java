package com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces;

import com.example.gisro.roomplannerisaac.Classes.Room;

import java.util.List;

/**
 * Created by Martien on 20-Sep-17.
 */

public interface IRoomContext<R> {
    void addRoom(Room room);
    void updateRoom(Room room);
    void removeRoom(Room room);
    List<R> getAllRooms();
}
