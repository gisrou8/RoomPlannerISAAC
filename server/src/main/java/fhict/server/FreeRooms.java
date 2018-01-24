package fhict.server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;

import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;

public class FreeRooms extends AppCompatActivity {
    ArrayList<Room> rooms;
    ArrayList<Room> roomList;
    GraphServiceController servicecontroller;
    CustomGrid customgrid;
    GridView gv;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freerooms);
        servicecontroller = new GraphServiceController();
        rooms = new ArrayList<>();
        getRooms();
        customgrid = new CustomGrid(this, rooms, "Reserve");

        gv = (GridView) findViewById(R.id.roomlist);
        gv.setAdapter(customgrid);
    }


    public void getRooms()
    {
        // Init roomlist from API
        servicecontroller.apiRooms(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(IUserCollectionPage iUserCollectionPage) {
                List<User> theirUsers = iUserCollectionPage.getCurrentPage();
                for(Room r : setRooms(theirUsers))
                {
                    rooms.add(r);
                }
                customgrid.notifyDataSetChanged();

            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }

    private ArrayList<Room> setRooms(List<User> users){
        Log.d("GraphServiceController", "Adding rooms to roomlist");
        roomList = new ArrayList<>();
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
