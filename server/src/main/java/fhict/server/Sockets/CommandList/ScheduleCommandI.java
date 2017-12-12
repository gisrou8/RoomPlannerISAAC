package fhict.server.Sockets.CommandList;

import com.microsoft.graph.extensions.Attendee;

import java.io.IOException;

import fhict.mylibrary.Appointment;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by Martien on 29-Nov-17.
 */

public class ScheduleCommandI implements IClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiScheduleMeeting((Appointment)params[1]);
}
}
