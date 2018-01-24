package fhict.server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.Event;
import com.microsoft.graph.extensions.IEventCollectionPage;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.State;
import fhict.server.GraphAPI.GraphServiceController;

public class ReservedRooms extends AppCompatActivity {
    ArrayList<Room> rooms;
    ArrayList<Room> roomList;
    ArrayList<Appointment> appoinments;

    GraphServiceController servicecontroller;
    CustomGrid customgrid;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservedrooms);
        servicecontroller = new GraphServiceController();
        rooms = new ArrayList<>();
        appoinments = new ArrayList<>();
        getAppoinments();

        customgrid = new CustomGrid(this, rooms, "Cancel Appointments");

        gv = (GridView) findViewById(R.id.reservedroomlist);
        gv.setAdapter(customgrid);
    }


    public void getRooms()
    {
        // Init roomlist from API
        servicecontroller.apiRooms(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(IUserCollectionPage iUserCollectionPage) {
                List<User> theirUsers = iUserCollectionPage.getCurrentPage();
                int i = 0;
                ArrayList<Room> roomsss = setRooms(theirUsers);
                for(Room r : roomsss)
                {
                    ArrayList<Appointment> roomappoinments = new ArrayList<>();
                    for(Appointment a : appoinments)
                    {

                        for(fhict.mylibrary.User u : a.getAttendees())
                        {
                            String name = r.getName();
                            if(name.equals(u.getName()))
                            {
                                roomappoinments.add(a);
                            }
                        }
                    }
                    if(roomappoinments.size() != 0)
                    {
                        r.setAppointments(roomappoinments);
                        rooms.add(r);
                    }

                }
                customgrid.notifyDataSetChanged();

            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }

    public void getAppoinments()
    {
        // Init roomlist from API
        servicecontroller.apiAppointments(new ICallback<IEventCollectionPage>() {
            @Override
            public void success(IEventCollectionPage iAppoinmentCollectionPage) {
                List<Appointment> theirAppoinments = null;
                try {
                    theirAppoinments = eventsToAppointments(iAppoinmentCollectionPage.getCurrentPage());
                    for(Appointment a : theirAppoinments)
                    {
                        appoinments.add(a);

                    }
                    getRooms();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }

    private ArrayList<Appointment> eventsToAppointments(List<Event> events) throws ParseException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for(Event event: events)
        {
            ArrayList<fhict.mylibrary.User> attendees = new ArrayList<>();
            for(Attendee attendee : event.attendees) {
                attendees.add(new fhict.mylibrary.User(attendee.emailAddress.name, attendee.emailAddress.address));
            }
            appointments.add(new Appointment(event.subject, event.start, event.end, attendees, event.id));
        }
        return appointments;
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
