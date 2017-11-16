package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.GraphAPI.GraphServiceController;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces.IRoomContext;
import com.example.gisro.roomplannerisaac.Classes.Room;
import com.example.gisro.roomplannerisaac.Classes.User;

/**
 * Created by Martien on 20-Sep-17.
 */

public class RoomExContext implements IRoomContext {
    final private GraphServiceController gsc;

    public RoomExContext(){
        this.gsc = new GraphServiceController();
    }

    @Override
    public void addRoom(Room room) {
        gsc.addRoom(room);
    }

    @Override
    public void updateRoom(Room room) {
        gsc.updateRoom(room);
    }

    @Override
    public void removeRoom(Room room) {
        gsc.removeRoom(room);
    }

    @Override
    public List getAllRooms() {
        return gsc.getAllRooms();
    }
}