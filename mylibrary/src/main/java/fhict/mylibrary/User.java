package fhict.mylibrary;

import java.io.Serializable;

/**
 * Created by gisro on 20-9-2017.
 */

public class User implements Serializable{
    private String Name;
    private String Email;

    /**
     * @param Name - Name given to the User
     * @param Email - Email corresponding with the User
     */
    public User(String Name, String Email)
    {
        this.Name = Name;
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }

    public String getName(){
        return Name;
    }

    @Override
    public String toString() {
        return Name;
    }
}
