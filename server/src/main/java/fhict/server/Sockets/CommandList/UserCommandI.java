package fhict.server.Sockets.CommandList;

import android.util.Log;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class UserCommandI implements IClientCommand {


    @Override
    public void execute(final SocketServerReplyThread server, Object[] params, final GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiUsers(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(IUserCollectionPage result) {
                List<User> users = result.getCurrentPage();

                try {
                    server.send(setUsers(users));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ClientException ex) {

            }
        });

    }

    private ArrayList<fhict.mylibrary.User> setUsers(List<User> users){
        ArrayList<fhict.mylibrary.User> userList;
        Log.d("GraphServiceController", "Adding users to userlist");
        userList = userConvert(users);
        return userList;
    }


    private ArrayList<fhict.mylibrary.User> userConvert(List<User> users)
    {
        ArrayList<fhict.mylibrary.User> newUsers = new ArrayList<>();
        for(User user: users)
        {
            if(!user.displayName.toUpperCase().contains("ROOM") && !user.displayName.contains("MOD Administrator")) {
                newUsers.add(new fhict.mylibrary.User(user.displayName, user.mail));
                Log.d("Server", "Sending user: " + user.displayName);
            }
        }
        return newUsers;
    }
}
