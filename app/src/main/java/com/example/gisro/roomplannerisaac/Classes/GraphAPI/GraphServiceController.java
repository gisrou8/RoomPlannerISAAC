/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.example.gisro.roomplannerisaac.Classes.GraphAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.example.gisro.roomplannerisaac.Classes.Appointment;
import com.example.gisro.roomplannerisaac.Classes.Room;
import com.example.gisro.roomplannerisaac.R;
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.AttendeeType;
import com.microsoft.graph.extensions.DateTimeTimeZone;
import com.microsoft.graph.extensions.Event;
import com.microsoft.graph.extensions.IEventCollectionPage;
import com.microsoft.graph.extensions.IGraphServiceClient;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.IUserCollectionRequest;
import com.microsoft.graph.extensions.User;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


class GraphServiceController {

    private final IGraphServiceClient mGraphServiceClient;
    private List<User> userList;
    private Room room;
    private final CountDownLatch Latch = new CountDownLatch(1);

    public Room getRoom() {
        return room;
    }

    public enum StorageState {
        NOT_AVAILABLE, WRITEABLE, READ_ONLY
    }

    public GraphServiceController() {
        mGraphServiceClient = GraphServiceClientManager.getInstance().getGraphServiceClient();
    }


    public void apiUsers(){
        Log.d(this.getClass().toString(), "Starting user get");

        mGraphServiceClient.getUsers().buildRequest().get(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(final IUserCollectionPage result) {
                List <User> users = result.getCurrentPage();
                setUsers(users);
            }


            @Override
            public void failure(ClientException e) {
                e.printStackTrace();
            }
        });
    }

    public void apiThisRoom(){
        Log.d(this.getClass().toString(), "Starting user get");

        mGraphServiceClient.getMe().buildRequest().get(new ICallback<User>() {
            @Override
            public void success(User user) {
                setThisRoom(user);
            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }

//    public void apiRooms(){
//        Log.d(this.getClass().toString(), "Starting room get");
//
//        mGraphServiceClient.getUsers().buildRequest().get(new ICallback<IUserCollectionPage>() {
//            @Override
//            public void success(final IUserCollectionPage result) {
//                List <User> users = result.getCurrentPage();
//                setRooms(users);
//            }
//
//
//            @Override
//            public void failure(ClientException e) {
//                e.printStackTrace();
//            }
//        });
//    }

    public void apiAppointmentsforRoom() {
        Log.d(this.getClass().toString(), "Starting appointments get");
        mGraphServiceClient.getMe().getEvents().buildRequest().get(new ICallback<IEventCollectionPage>() {
            @Override
            public void success(IEventCollectionPage iEventCollectionPage) {
                setAppointmentsforRoom(iEventCollectionPage.getCurrentPage());
            }

            @Override
            public void failure(ClientException ex) {

            }
        });
    }



    public void apiScheduleMeeting(Appointment appointment, ICallback<Event> callback)
    {
        try {
            mGraphServiceClient.getMe().getEvents().buildRequest().post(createEvent(appointment.getName(), appointment.getReserveringsTijdTZ(), appointment.getReserveringsTijdTZ()), callback);
        }
        catch (Exception ex){
            Log.d("GraphServiceController", ex.getMessage());
        }
    }

    private Event createEvent(String name, DateTimeTimeZone reserveringsTijd, DateTimeTimeZone reserveringsTijd1) {
        Event event = new Event();
        event.subject = name;
        event.start = reserveringsTijd;
        event.end = reserveringsTijd1;
        return event;
    }


    private void setUsers(List<User> users){
        Log.d("GraphServiceController", "Adding users to userlist");
        userList = users;
    }

//    private void setRooms(List<User> users){
//        Log.d("GraphServiceController", "Adding rooms to roomlist");
//        for(User user : users)
//        {
//            if(user.displayName.contains("Ruimte")){
//                roomList.add(new Room(user.displayName, user.id));
//            }
//        }
//    }

    private void setAppointmentsforRoom(List<Event> events) {
        room.setAppointments(eventsToAppointments(events));
    }

    // Convert the events to our own appointments methods
    private ArrayList<Appointment> eventsToAppointments(List<Event> events) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for(Event event: events)
        {
            appointments.add(new Appointment(event.subject, event.start));
        }
        return appointments;
    }



    public List<User> getUsers(){
        return userList;
    }

    public void setThisRoom(User user)
    {
        room = new Room(user.displayName, user.id);
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