package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.GraphAPI.GraphServiceController;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces.IUserContext;
import com.example.gisro.roomplannerisaac.Classes.User;

/**
 * Created by Martien on 20-Sep-17.
 */

public class UserExContext implements IUserContext {
    GraphServiceController gsc;

    public UserExContext(){
        this.gsc = new GraphServiceController();
    }

    @Override
    public void addUser(User u) {
        gsc.addUser(u);
    }

    @Override
    public void updateUser(User u) {
        gsc.updateUser(u);
    }

    @Override
    public void removeUser(User u) {
        gsc.removeUser(u);
    }

    @Override
    public List getAllUsers() {
        return gsc.getAllUsers();
    }
}
