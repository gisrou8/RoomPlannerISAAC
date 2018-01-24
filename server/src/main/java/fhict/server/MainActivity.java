package fhict.server;

import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;


import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.IEventCollectionPage;
import com.microsoft.graph.extensions.IUserCollectionPage;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fhict.mylibrary.Room;
import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.State;
import fhict.server.GraphAPI.CloseTask;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.CommandList.AppointmentCommandI;
import fhict.server.Sockets.SocketServerReplyThread;
import fhict.server.Sockets.StartSocketAsync;


public class MainActivity extends AppCompatActivity implements ActivityData{

    TextView info, infoip;
    private ListView lv;
    private ListView lvMeetings;
    final private GraphServiceController mGraphServiceController = new GraphServiceController();
    public static final String ARG_GIVEN_NAME = "givenName";
    private int checkCount = 2000;
    List<String> rooms;
    ArrayAdapter<String> arrayAdapter;


    GraphServiceController controller = new GraphServiceController();
    ArrayList<Appointment> appointments = new ArrayList<>();
    ScheduledExecutorService exec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        infoip.setText(getIpAddress());
        new StartSocketAsync().execute();
        update();
    }

    public void btnReservedRooms(View v){
        Intent i = new Intent(this, ReservedRooms.class);
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

    public void update()
    {
        if (exec != null) {
            exec.shutdown();
        }
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    new AppointmentCommandI().execute(controller, MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        }, 0, 10, TimeUnit.SECONDS);
    }


    @Override
    public void setData(Object data) throws ClassNotFoundException {
        if (data instanceof ArrayList<?>) {
            if (((ArrayList) data).size() > 0) {
                    appointments.clear();
                    for (Appointment appointment : (ArrayList<Appointment>) data) {
                        Log.d("Date", LocalDate.now().toString());
                        Calendar calnow = Calendar.getInstance();
                        Calendar calappointment = Calendar.getInstance();
                        calnow.setTime(LocalDate.now().toDate());
                        calappointment.setTime(appointment.getReserveringsTijd().toDate());
                        if (calnow.get(Calendar.YEAR) == calappointment.get(Calendar.YEAR) &&
                                calnow.get(Calendar.DAY_OF_YEAR) == calappointment.get(Calendar.DAY_OF_YEAR)) {
                            appointments.add(appointment);
                        }
                    }
            }
        }
        for(Appointment appointment: appointments)
        {
            if(DateTime.now().isAfter(appointment.getReserveringEind()))
            {
                new CloseTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, appointment);
            }
        }
    }

    @Override
    public Context getContext() {
        return null;
    }
}
