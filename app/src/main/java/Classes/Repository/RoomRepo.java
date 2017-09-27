package Classes.Repository;

import java.util.List;

import Classes.Repository.Interfaces.IRoomContext;

/**
 * Created by Martien on 20-Sep-17.
 */

public class RoomRepo implements IRoomContext{

    private IRoomContext room;

    public RoomRepo(IRoomContext room){
        this.room = room;
    }

    @Override
    public void add(Object item) {
        room.add(item);
    }

    @Override
    public void update(Object item) {
        room.update(item);
    }

    @Override
    public void remove(Object item) {
        room.update(item);
    }

    @Override
    public List getAll() {
        return room.getAll();
    }
}
