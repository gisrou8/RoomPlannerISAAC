package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class AppointmentCommandI implements IClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiAppointments(new Room((String)params[0], null,0));
        Thread.sleep(2000);
        server.send(controller.getAppointmentsforRoom(new Room((String)params[0], null, 0)));
    }
}
