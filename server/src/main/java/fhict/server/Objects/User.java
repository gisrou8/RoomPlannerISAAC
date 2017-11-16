package fhict.server.Objects;

/**
 * Created by gisro on 20-9-2017.
 */

public class User {
    private String Name;
    private String Email;


    public User(String Name, String Email)
    {
        this.Name = Name;
        this.Email = Email;
    }

    @Override
    public String toString() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getName(){
        return Name;
    }
}
