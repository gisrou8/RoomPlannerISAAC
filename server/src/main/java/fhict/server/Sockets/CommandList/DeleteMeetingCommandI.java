package fhict.server.Sockets.CommandList;

import java.io.IOException;
import java.sql.ClientInfoStatus;

import fhict.mylibrary.Appointment;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 18-12-2017.
 */

public class DeleteMeetingCommandI implements IClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.closeMeeting((Appointment)params[0]);
    }
}
