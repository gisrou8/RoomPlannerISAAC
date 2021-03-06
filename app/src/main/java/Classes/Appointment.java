package Classes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gisro on 20-9-2017.
 */

public class Appointment {
    private String Name;
    private Date reserveringsTijd;
    private State state;

    private Room room;


    public Appointment(String Name, Date reserveringsTijd, Room room)
    {
        this.Name = Name;
        this.reserveringsTijd = reserveringsTijd;
        this.room = room;
        this.state = State.Closed;
    }

    public State getState(){
        return state;
    }

    public boolean open(){
      if(reserveringsTijd.after(new Date()) && reserveringsTijd.before(new Date(System.currentTimeMillis()+5*60*1000))){
          this.state = State.Open;
          this.room.updateState();
          return true;
      }
      return false;
    }

    public void close(){
        this.state = State.Closed;
        this.room.updateState();
    }

    @Override
    public String toString(){
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd '|' hh:mm");

        return Name + " , " + ft.format(reserveringsTijd) + " , " + state;
    }
}
