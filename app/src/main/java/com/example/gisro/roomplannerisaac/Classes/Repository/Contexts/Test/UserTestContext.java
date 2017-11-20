package com.example.gisro.roomplannerisaac.Classes.Repository.Contexts.Test;
import com.example.gisro.roomplannerisaac.Classes.Repository.Interface.IUserContext;
import com.example.gisro.roomplannerisaac.Classes.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martien on 08-Nov-17.
 */

public class UserTestContext implements IUserContext {
    private ArrayList<User> users;

    public UserTestContext(){
        this.users = new ArrayList<>();
        users.add(new User("Stef, Sande van de", "stef_vdsande@hotmail.com"));
        users.add(new User("Evelien, Hermans", "evelien_hermans@gmail.com"));
        users.add(new User("Jordy, Verlaek", "jordyv@live.nl"));
        users.add(new User("Bob, Velzen van", "bob@bol.com"));
        users.add(new User("Abdo, Aryanzad", "abdoa@hotmail.com"));
        users.add(new User("Martien, Broek van den", "mariten.broek@gmail.com"));
    }

    @Override
    public void addUser(User u) {
        if(!users.contains(u)){
            users.add(u);
        }else{
            //Todo: Relay message to user
            System.out.println("User already exists");
        }
    }

    @Override
    public void updateUser(User u) {
        if(users.contains(u)){
            users.set(users.indexOf(u), u);
        }else{
            //Todo: Relay message to user
            System.out.println("User doesn't exist");
        }
    }

    @Override
    public void removeUser(User u) {
        if(users.contains(u)){
            users.remove(u);
        }else{
            //Todo: Relay message to user
            System.out.println("User doesn't exist");
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return users;
    }
}
