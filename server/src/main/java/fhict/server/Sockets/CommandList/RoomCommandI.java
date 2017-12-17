package fhict.server.Sockets.CommandList;

import android.util.Log;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class RoomCommandI implements IClientCommand {

    @Override
    public void execute(final SocketServerReplyThread server, Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiRooms(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(IUserCollectionPage iUserCollectionPage) {
                try {
                    server.send(setRooms(iUserCollectionPage.getCurrentPage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ClientException ex) {

            }
        });

    }

    private ArrayList<Room> setRooms(List<User> users){
        Log.d("GraphServiceController", "Adding rooms to roomlist");
        ArrayList<Room> roomList = new ArrayList<>();
        for(User user : users)
        {
            // Only add the user if it is a room
            if(user.displayName.contains("Room")){
                if(user.givenName != null) {
                    String[] data = user.givenName.split("/");
                    //givenname = personmaximum per room
                    //jobTitle = floornumber
                    roomList.add(new Room(user.displayName, user.id, Integer.parseInt(user.surname), Integer.parseInt(data[0]), Integer.parseInt(data[1])));
                }
            }
        }
        return roomList;
    }
}
