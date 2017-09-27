package Classes.Repository.Interfaces;

import java.util.List;

import Classes.User;

/**
 * Created by Martien on 20-Sep-17.
 */

public interface IUserContext {
    void add(User u);
    void update(User u);
    void removeUSer(User u);
    List<User> getAllUsers();
}
