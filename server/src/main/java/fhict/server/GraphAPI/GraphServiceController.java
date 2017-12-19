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
    private fhict.mylibrary.User user;
    private ArrayList<Room> roomList;





    public Room getRoom() {
        return room;
    }

    public fhict.mylibrary.User getUser() { return user;}



    public void removeMeeting(String id)
    {
        mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents(id).buildRequest().delete();
    }

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
//        try {
////            mGraphServiceClient.getMe().getEvents().buildRequest().post(createEvent(appointment.getName(), appointment.getReserveringsTijdTZ(), appointment.getReserveringsTijdTZ(), appointment.getAttendees()));
////        }
//        catch (Exception ex){
//            Log.d("GraphServiceController", ex.getMessage());
//        }
    }

    /**
     * Updates the Event with the same name as given Appointment (by deleting the Event and creating a new one with the same name..)
     */
    public void updateAppointment(Appointment appointment){
        try {
        }
        catch (Exception ex){
            Log.d("GraphServiceController", ex.getMessage());
        }
    }

    /**
     * Removes the event corresponding with the name of the given Appointment
     */
    public void removeAppointment(Appointment appointment){

    }

    /**
     * Retrieves all Appointments
     */
    public List<Appointment> getAllAppointment(){
        return null;
    }

    /**
     * Add's a new User
     */
    public void addUser(fhict.mylibrary.User user){

    }

    /**
     * Updates User with the same name to User object specified
     */
    public void updateUser(fhict.mylibrary.User user){

    }

    /**
     * Removes a User
     */
    public void removeUser(fhict.mylibrary.User user){

    }

    /**
     * Retrieves all Users
     */
    public List<User> getAllUsers(){
        return null;
    }

    /**
     * Add's a new User
     */
    public void addRoom(Room room){

    }

    /**
     * Updates room
     */
    public void updateRoom(Room room){

    }

    /**
     * Removes a Room
     */
    public void removeRoom(Room room){

    }

    /**
     * Retrieves all Rooms
     */
    public List<Room> getAllRooms(){
        return null;
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
                setThisRoom(user);
//                apiAppointmentsforRoom();
            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }

    public void apiAppointments(final Room room, ICallback<IEventCollectionPage> callback){
        mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents().buildRequest().get(callback);
    }


    /**
     * This method gets all the rooms in the organisation from the api. Puts them in the roomlist.
     */
    public void apiRooms(ICallback<IUserCollectionPage> callback){
        Log.d(this.getClass().toString(), "Starting room get");
        mGraphServiceClient.getUsers().buildRequest().get(callback);
    }



    /**
     * @param appointment the appointment and its information, will get converted to an event before posting to API
     * @param
     */
    public void apiScheduleMeeting(Appointment appointment)
    {
        try {
            Event e = createEvent(appointment.getName(), timeZoneConvert(appointment.getReserveringsTijd()), timeZoneConvert(appointment.getReserveringsTijd()), userToAttendees(appointment.getAttendees()));
            mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents().buildRequest().post(e);
        }
        catch (Exception ex){
            Log.d("GraphServiceController", ex.getMessage());
        }
    }

    public ArrayList<Attendee> userToAttendees(List<fhict.mylibrary.User> users)
    {
        ArrayList<Attendee> attendeeList = new ArrayList<>();
        for(fhict.mylibrary.User user : users)
        {
            Attendee att = new Attendee();
            EmailAddress email = new EmailAddress();
            email.name = user.getName();
            email.address = user.getEmail();
            att.emailAddress = email;
            attendeeList.add(att);
        }
        return attendeeList;
    }

    public DateTimeTimeZone timeZoneConvert(DateTime dt)
    {
        DateTimeTimeZone dtz = new DateTimeTimeZone();
        dtz.dateTime = dt.toString();
        dtz.timeZone = "GMT";
        return dtz;
    }


    /**
     * @param name
     * @param reserveringsTijd
     * @param reserveringsTijd1
     * @param attendees
     * @return converts our own appointments object to an usable object for posting to the API.
     */
    private Event createEvent(String name, DateTimeTimeZone reserveringsTijd, DateTimeTimeZone reserveringsTijd1, List<Attendee> attendees) {
        reserveringsTijd.timeZone = "UTC";
        reserveringsTijd1.timeZone = "UTC";
        Event event = new Event();
        event.subject = name;
        event.start = reserveringsTijd;
        event.end = reserveringsTijd1;
        event.attendees = attendees;
//        Attendee att = new Attendee();
//        EmailAddress email = new EmailAddress();
//        email.address = "xandersteinmann@ISAACFontys1.onmicrosoft.com";
//        att.emailAddress = email;
//        //enzzz je maakt all attendees aan
//        List<Attendee> attendees;
//        // gooit ze daarna in een list<attentee>
//        // en voegt ze toe aan het te pushen event
//        event.attendees = attendees;
        return event;
    }

    public void setThisRoom(User user)
    {

        if(user.surname != null) {
            room = new Room(user.givenName, user.id, Integer.parseInt(user.surname));
        }
        else{
            room = new Room(user.givenName, user.id, 0);
        }
    }



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