package fhict.server.Sockets.CommandList;

import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class Commands implements IClientCommand {

    private HashMap<String, IClientCommand> cmds;

    public Commands(){
        cmds = new HashMap<>();
    }

    public void addCommand(IClientCommand cmd, String name)
    {
        cmds.put(name, cmd);
    }

    @Override
    public void execute(SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        IClientCommand cmd = cmds.get(params[0]);
        if(cmd != null)
        {
            try {
                cmd.execute(server, Arrays.copyOfRange(params, 1, params.length), controller);
            }
            catch (Exception ex)
            {
                Log.d("Error API", ex.getMessage());
            }
        }
    }
}
