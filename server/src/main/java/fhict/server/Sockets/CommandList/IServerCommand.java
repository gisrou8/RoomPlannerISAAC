package fhict.server.Sockets.CommandList;

import java.io.IOException;

import fhict.server.ActivityData;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public interface IServerCommand {
    void execute(GraphServiceController controller, final ActivityData data) throws IOException, InterruptedException;
}
