package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class AppointmentCommand implements ClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, String[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiAppointments(new Room(params[0], null,0));
        Thread.sleep(1100);
        server.send(controller.getAppointmentsforRoom(new Room(params[0], null, 0)));
    }
}
