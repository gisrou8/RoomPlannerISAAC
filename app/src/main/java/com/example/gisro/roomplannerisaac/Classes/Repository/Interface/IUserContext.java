package com.example.gisro.roomplannerisaac.Classes.Repository.Interface;

import com.example.gisro.roomplannerisaac.Classes.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BePulverized on 20-11-2017.
 */

public interface IUserContext {
    void addUser(User u);
    void updateUser(User u);
    void removeUser(User u);
    ArrayList<User> getAllUsers();
}
