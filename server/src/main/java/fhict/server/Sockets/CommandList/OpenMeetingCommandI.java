package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 18-12-2017.
 */

public class OpenMeetingCommandI implements IClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiOpenMeeting((Room)params[0]);
    }
}
