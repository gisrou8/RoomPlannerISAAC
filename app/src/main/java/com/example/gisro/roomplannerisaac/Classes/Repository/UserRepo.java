package com.example.gisro.roomplannerisaac.Classes.Repository;

import java.util.List;

import com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces.IUserContext;
import com.example.gisro.roomplannerisaac.Classes.User;

/**
 * Created by Martien on 20-Sep-17.
 */

public class UserRepo {
    private final IUserContext context;

    public UserRepo(IUserContext context){
        this.context = context;
    }

    public List<User> getUsers() {
        throw new UnsupportedOperationException();
    }

    public void addUser(User newUser){
        throw new UnsupportedOperationException();
    }

    public void removeUser(User user){
        throw new UnsupportedOperationException();
    }

    public void updateUser(User newUser){
        throw new UnsupportedOperationException();
    }

}


