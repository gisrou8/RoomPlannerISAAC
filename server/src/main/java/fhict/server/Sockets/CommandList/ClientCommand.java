package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public interface ClientCommand {
    void execute(SocketServerReplyThread server, String[] params, GraphServiceController controller) throws IOException, InterruptedException;
}
