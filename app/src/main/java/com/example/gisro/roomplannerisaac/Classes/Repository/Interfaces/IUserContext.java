package com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces;

import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.User;

/**
 * Created by Martien on 20-Sep-17.
 */

public interface IUserContext {
    void addUser(User u);
    void updateUser(User u);
    void removeUser(User u);
    List<User> getAllUsers();
}
