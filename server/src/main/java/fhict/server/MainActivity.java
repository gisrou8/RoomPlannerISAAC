package fhict.server;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;
import fhict.server.Sockets.StartSocketAsync;


public class MainActivity extends AppCompatActivity {

    TextView info, infoip;
    private ListView lv;
    private ListView lvMeetings;
    final private GraphServiceController mGraphServiceController = new GraphServiceController();
    public static final String ARG_GIVEN_NAME = "givenName";
    private int checkCount = 2000;
    List<String> rooms;
    ArrayAdapter<String> arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
//        info = (TextView) findViewById(R.id.info);
//        infoip = (TextView) findViewById(R.id.infoip);
//
//        infoip.setText(getIpAddress());
//        new StartSocketAsync().execute();
//
//
//        lv = (ListView) findViewById(R.id.listview);
//        lvMeetings = (ListView) findViewById(R.id.listview1);
//        rooms = new ArrayList<String>();
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rooms);
//        lv.setAdapter(arrayAdapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = (String) lv.getItemAtPosition(i);
//                String splitArray[] = s.split(" , ");
//            }
//        });
//        getRooms();

    }

    public void btnReservedRooms(View v){
        //mGraphServiceController.removeMeeting("Hier komt de id");
        Intent i = new Intent(this, FreeRooms.class);
        startActivity(i);
    }



    public void btnAllRooms(View v){
        Intent i = new Intent(this, FreeRooms.class);
        startActivity(i);
    }


    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    public void getRooms()
    {
        // Init roomlist from API
        mGraphServiceController.apiRooms(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(IUserCollectionPage iUserCollectionPage) {
                List<User> theirUsers = iUserCollectionPage.getCurrentPage();
                List<Room> roomss = setRooms(theirUsers);
                for (Room r : roomss) {

                    rooms.add(r.toString());
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(ClientException ex) {

            }
           });
//        // Wait for the graph api to get the data, when data has arrived do something with this data
//        final android.os.Handler handler = new android.os.Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                checkCount -= 1000;
//                if(checkCount > 0){
//                    handler.postDelayed(this, 1000);
//                }
//                else {
//                    if (mGraphServiceController.getRooms() != null) {
//                        for (Room r : mGraphServiceController.getRooms()) {
//                            //Check if appointment is today
//                            rooms.add(r.toString());
//                        }
//                        // Order the appointments on time
//
//                        arrayAdapter.notifyDataSetChanged();
//                    } else {
//                        Log.d("MainActivity", "userlist is null");
//                    }
//                }
//            }
//        }, 2000);
    }



    private ArrayList<Room> setRooms(List<User> users){
        Log.d("GraphServiceController", "Adding rooms to roomlist");
        ArrayList<Room> roomList = new ArrayList<>();
        for(User user : users)
        {
            // Only add the user if it is a room
            if(user.displayName.contains("Room")){
                //roomList.add(new Room(user.displayName, user.id, Integer.parseInt(user.surname)));
            }
        }
        return roomList;
    }





}
