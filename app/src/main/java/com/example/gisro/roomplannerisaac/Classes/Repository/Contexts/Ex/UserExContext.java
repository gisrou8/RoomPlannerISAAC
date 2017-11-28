package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;

import com.example.gisro.roomplannerisaac.Classes.Client.Client;
import com.example.gisro.roomplannerisaac.Classes.Client.Task;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IUserContext;

import fhict.mylibrary.User;


/**
 * Created by Martien on 20-Sep-17.
 */

public class UserExContext implements IUserContext {

    private Client client;

    public UserExContext()
    {
        client = new Client("192.168.178.118", 8080, new Task("Users", null));
        client.start();
    }
    @Override
    public void addUser(User u) {

    }

    @Override
    public void updateUser(User u) {

    }

    @Override
    public void removeUser(User u) {

    }

    @Override
    public ArrayList<User> getAllUsers() {
        return client.getUsers();
    }
}
