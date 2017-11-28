package fhict.server.Sockets.CommandList;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class DisconnectCommand implements ClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, String[] params, GraphServiceController controller) {
        server.close();
    }
}
