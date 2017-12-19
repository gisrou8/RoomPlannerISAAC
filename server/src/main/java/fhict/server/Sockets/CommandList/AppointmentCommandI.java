package fhict.server.Sockets.CommandList;

import android.util.Log;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.Event;
import com.microsoft.graph.extensions.IEventCollectionPage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.User;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.Sockets.SocketServerReplyThread;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class AppointmentCommandI implements IClientCommand {

    @Override
    public void execute(final SocketServerReplyThread server, final Object[] params, GraphServiceController controller) throws IOException, InterruptedException {
        controller.apiAppointments((Room)params[0], new ICallback<IEventCollectionPage>() {
            @Override
            public void success(IEventCollectionPage iEventCollectionPage) {
                List<Event> events = iEventCollectionPage.getCurrentPage();
                try {
                    Log.d("API", "Starting appointment call");
                    server.send(setEvents((Room)params[0], events));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }

    private ArrayList<Appointment> setEvents(Room room, List<Event> events) throws ParseException {
        ArrayList<Event> eventforRoom = new ArrayList<>();
        for(Event event: events)
        {
            for(Attendee attendee:event.attendees)
            {
                if(room.getName().equals(attendee.emailAddress.name)){
                eventforRoom.add(event);
            }
            }


        }
       return eventsToAppointments(eventforRoom);
    }

    private ArrayList<Appointment> eventsToAppointments(ArrayList<Event> events) throws ParseException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for(Event event: events)
        {
            ArrayList<User> attendees = new ArrayList<>();
            for(Attendee attendee : event.attendees) {
               attendees.add(new User(attendee.emailAddress.name, attendee.emailAddress.address));
            }
            appointments.add(new Appointment(event.subject, event.start, event.end, attendees));
        }
        return appointments;
    }
}
