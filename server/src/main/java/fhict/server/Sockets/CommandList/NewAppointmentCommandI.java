package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.mylibrary.Appointment;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 19-12-2017.
 */

public class NewAppointmentCommandI implements IClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiScheduleMeeting((Appointment)params[0]);
    }
}
