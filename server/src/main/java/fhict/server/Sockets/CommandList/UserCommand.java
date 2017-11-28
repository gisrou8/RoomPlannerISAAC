package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class UserCommand implements ClientCommand {

    @Override
    public void execute(SocketServerReplyThread server, String[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiUsers();
        Thread.sleep(2000);
        server.send(controller.getUsers());
    }
}
