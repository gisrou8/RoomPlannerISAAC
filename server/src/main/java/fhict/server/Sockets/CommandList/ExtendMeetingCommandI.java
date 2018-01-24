package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.mylibrary.Appointment;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 19-12-2017.
 */

public class ExtendMeetingCommandI implements IClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.extendMeeting((Appointment)params[0]);
    }
}
