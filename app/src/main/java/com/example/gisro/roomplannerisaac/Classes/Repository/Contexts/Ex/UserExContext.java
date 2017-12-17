package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Ex;

import java.util.ArrayList;

import com.example.gisro.roomplannerisaac.Classes.Acitivities.ActivityData;
import com.example.gisro.roomplannerisaac.Classes.Client.Client;
import com.example.gisro.roomplannerisaac.Classes.Client.Task;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IUserContext;

import fhict.mylibrary.User;


/**
 * Created by Martien on 20-Sep-17.
 */

public class UserExContext implements IUserContext {

    private Client client;
    private ActivityData activity;

    public UserExContext(ActivityData activity)
    {
        this.activity = activity;
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
        client = new Client(new Task("Users", null), activity);
        client.start();
        return client.getUsers();
    }
}
