package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public interface IClientCommand {
    void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException;
}
