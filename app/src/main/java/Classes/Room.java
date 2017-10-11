package Classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by gisro on 20-9-2017.
 */

public class Room {
    private String Name;
    private State state;

    public Room(String name)
    {
        this.Name = name;
    }

    public State getState(){
        return state;
    }

    public void updateState() {
        for (Appointment a : new ArrayList<Appointment>() /*vervang door opgehaalde lijst Appointments voor deze kamer*/) {
            if (a.getState() == State.Open){
                this.state = State.Bezet;
            }
        }
        this.state = State.Vrij;
    }

    @Override
    public String toString(){
        return Name + "\t" + state;
    }
}
