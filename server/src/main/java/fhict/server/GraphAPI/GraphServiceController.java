/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package fhict.server.GraphAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.DateTimeTimeZone;
import com.microsoft.graph.extensions.EmailAddress;
import com.microsoft.graph.extensions.Event;
import com.microsoft.graph.extensions.GraphServiceClient;
import com.microsoft.graph.extensions.IEventCollectionPage;
import com.microsoft.graph.extensions.IGraphServiceClient;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;
import com.microsoft.graph.extensions.UserCollectionPage;

import org.joda.time.DateTime;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.State;

public class GraphServiceController {

    private final IGraphServiceClient mGraphServiceClient;
    private Room room;
    private User user;
    private ArrayList<Room> roomList;





    public Room getRoom() {
        return room;
    }

    public User getUser() { return user;}

    public ArrayList<Room> getRooms(){
        return roomList;
    }

    /** Voordat er een methode wordt aangeroepen altijd eerst de constructor aan hebben gemaakt.
    Deze zorgt ervoor dat de authenticatie wordt meegestuurd met de API request. Zorg er ook voor dat er gebruikt wordt gemaakt van een handler, dit zodat de
     api genoeg tijd heeft om data terug te sturen*/
    public GraphServiceController() {
        mGraphServiceClient = GraphServiceClientManager.getInstance().getGraphServiceClient();
        roomList = new ArrayList<>();
    }

    /**
     * Create a new Graph Event based on given Appointment
     */
    public void addAppointment(Appointment appointment){
        try {
            mGraphServiceClient.getMe().getEvents().buildRequest().post(createEvent(appointment.getName(), appointment.getReserveringsTijd(), appointment.getReserveringEind(), appointment.getAttendees()));
       }
        catch (Exception ex){
            Log.d("GraphServiceController", ex.getMessage());
        }
    }


    /**
     * Deze methode haalt alle gebruikers op die zich in de organisatie bevinden en slaat ze op in de userlist.
     */
    public void apiUsers(ICallback<IUserCollectionPage> callback){
        Log.d(this.getClass().toString(), "Starting user get");
        mGraphServiceClient.getUsers().buildRequest().get(callback);
    }


    /**
     * This method gets the current room that the tablet is in. This is based on the logged in User since we use the login to identify the room.
     */
    public void apiThisRoom(){
        Log.d(this.getClass().toString(), "Starting user get");

        mGraphServiceClient.getMe().buildRequest().get(new ICallback<User>() {
            @Override
            public void success(User user) {
//                setThisRoom(user);
//                apiAppointmentsforRoom();
            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }

    public void apiAppointments(ICallback<IEventCollectionPage> callback){
        mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents().buildRequest().get(callback);
    }


    /**
     * This method gets all the rooms in the organisation from the api. Puts them in the roomlist.
     */
    public void apiRooms(ICallback<IUserCollectionPage> callback){
        Log.d(this.getClass().toString(), "Starting room get");
        mGraphServiceClient.getUsers().buildRequest().get(callback);
    }

    public void apiOpenMeeting(Room room)
    {
        User user = new User();
        if(room.getState() == State.Bezet) {
            user.surname = "1";
        }
        else if(room.getState() == State.Vrij){
            user.surname = "0";
        }
        mGraphServiceClient.getUsers(room.getId()).buildRequest().patch(user);
    }

    public void closeMeeting(Appointment appointment)
    {
        mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents(appointment.getId()).buildRequest().delete();
    }



    /**
     * @param appointment the appointment and its information, will get converted to an event before posting to API
     * @param
     */
    public void apiScheduleMeeting(Appointment appointment)
    {
        try {
            Event e = createEvent(appointment.getName(), appointment.getReserveringsTijd(), appointment.getReserveringEind(), appointment.getAttendees());
            mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents().buildRequest().post(e);
        }
        catch (Exception ex){
            Log.d("GraphServiceController", ex.getMessage());
        }
    }


    /**
     * @param name
     * @param reserveringsTijd
     * @param reserveringsTijd1
     * @param libattendees
     * @return converts our own appointments object to an usable object for posting to the API.
     */
    private Event createEvent(String name, DateTime reserveringsTijd, DateTime reserveringsTijd1, List<fhict.mylibrary.User> libattendees) {
        DateTimeTimeZone timeTimeZoneStart = new DateTimeTimeZone();
        timeTimeZoneStart.dateTime = reserveringsTijd.toString();
        timeTimeZoneStart.timeZone = "CET";
        DateTimeTimeZone timeTimeZoneEnd = new DateTimeTimeZone();
        timeTimeZoneEnd.dateTime = reserveringsTijd1.toString();
        timeTimeZoneEnd.timeZone = "CET";
        Event event = new Event();
        event.subject = name;
        event.start = timeTimeZoneStart;
        event.end = timeTimeZoneEnd;
        List<Attendee> attendees = new ArrayList<>();
        for(fhict.mylibrary.User user: libattendees)
        {
            Attendee att = new Attendee();
            EmailAddress email = new EmailAddress();
            email.name = user.getName();
            if(user.getEmail() != null) {
                email.address = user.getEmail();
            }
            else{
                email.address = "N/A";
            }
            att.emailAddress = email;
            attendees.add(att);
        }
        //enzzz je maakt all attendees aan
        // gooit ze daarna in een list<attentee>
        // en voegt ze toe aan het te pushen event
        event.attendees = attendees;
        return event;
    }

//    public void setThisRoom(User user)
//    {
//
//        if(user.surname != null) {
//            room = new Room(user.givenName, user.id, Integer.parseInt(user.surname));
//        }
//        else{
//            room = new Room(user.givenName, user.id, 0);
//        }
//    }



    /*
    * Opens a user dialog that shows the failure result of an exception and writes a log entry
    * */
    private void showException(Exception ex, String exceptionAction, String exceptionTitle, String exceptionMessage){
        Log.e("GraphServiceController", exceptionAction + ex.getLocalizedMessage());
        AlertDialog.Builder alertDialogBuidler = new AlertDialog.Builder(Connect.getContext());
        alertDialogBuidler.setTitle(exceptionTitle);
        alertDialogBuidler.setMessage(exceptionMessage);
        alertDialogBuidler.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialogBuidler.show();

    }
}