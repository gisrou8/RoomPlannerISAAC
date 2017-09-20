package Classes;

import java.util.Date;

/**
 * Created by gisro on 20-9-2017.
 */

public class Appointment {
    private String Name;
    private Date reserveringsTijd;

    private Room room;


    public Appointment(String Name, Date reserveringsTijd, Room room)
    {
        this.Name = Name;
        this.reserveringsTijd = reserveringsTijd;
        this.room = room;
    }


}
