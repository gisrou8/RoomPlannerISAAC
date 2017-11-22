package fhict.server.Sockets;

import fhict.server.GraphAPI.GraphServiceController;

/**
 * Created by BePulverized on 20-11-2017.
 */

public class currentRoomCommand implements Command {

    GraphServiceController controller;

    public currentRoomCommand(final GraphServiceController controller)
    {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.apiThisRoom();
    }
}
