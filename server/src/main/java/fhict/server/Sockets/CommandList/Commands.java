package fhict.server.Sockets;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class Commands implements ClientCommand {

    private HashMap<String, ClientCommand> cmds;

    public Commands(){
        cmds = new HashMap<>();
    }

    public void addCommand(ClientCommand cmd, String name)
    {
        cmds.put(name, cmd);
    }

    @Override
    public void execute(SocketServerReplyThread server, String[] params) {
        ClientCommand cmd = cmds.get(params[0]);
        if(cmd != null)
        {
            cmd.execute(server, Arrays.copyOfRange(params, 1, params.length));
        }
    }
}
