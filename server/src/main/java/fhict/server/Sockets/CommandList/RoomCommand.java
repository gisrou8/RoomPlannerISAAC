package fhict.server.Sockets;

import java.io.IOException;

import fhict.server.GraphAPI.GraphServiceController;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class RoomCommand implements ClientCommand {
    private GraphServiceController controller = new GraphServiceController();
    @Override
    public void execute(SocketServerReplyThread server, String[] params) throws IOException {
        controller.apiRooms();
        server.send(controller.getRoom());
    }
}
