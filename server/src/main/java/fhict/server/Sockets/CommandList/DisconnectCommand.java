package fhict.server.Sockets;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class DisconnectCommand implements ClientCommand {
    @Override
    public void execute(SocketServerReplyThread server, String[] params) {
        server.close();
    }
}
