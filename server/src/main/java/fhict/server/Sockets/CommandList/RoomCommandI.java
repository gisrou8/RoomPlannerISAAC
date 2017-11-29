package fhict.server.Sockets.CommandList;

import java.io.IOException;
import java.util.ArrayList;

import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class RoomCommandI implements IClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiRooms();
        Thread.sleep(2000);
        Room room = controller.getRoom();
        ArrayList<Room> rooms = controller.getRooms();
        server.send(room);
        server.send(rooms);
    }
}
