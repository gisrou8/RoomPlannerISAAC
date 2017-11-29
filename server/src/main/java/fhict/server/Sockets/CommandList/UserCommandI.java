package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class UserCommandI implements IClientCommand {

    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiUsers();
        Thread.sleep(2000);
        server.send(controller.getUsers());
    }
}
