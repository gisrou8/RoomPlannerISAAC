/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package fhict.server.GraphAPI;

import android.util.Log;

import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.extensions.*;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.mylibrary.State;

public class GraphServiceController {

    private final IGraphServiceClient mGraphServiceClient;

    /**
     * Voordat er een methode wordt aangeroepen altijd eerst de constructor aan hebben gemaakt.
     * Deze zorgt ervoor dat de authenticatie wordt meegestuurd met de API request. Zorg er ook voor dat er gebruikt wordt gemaakt van een handler, dit zodat de
     * api genoeg tijd heeft om data terug te sturen
     */
    public GraphServiceController() {
        mGraphServiceClient = GraphServiceClientManager.getInstance().getGraphServiceClient();
    }

    /**
     * @param callback - Response received from server
     * Deze methode haalt alle gebruikers op die zich in de organisatie bevinden en slaat ze op in de userlist.
     */
    public void apiUsers(ICallback<IUserCollectionPage> callback) {
        Log.d(this.getClass().toString(), "Starting user get");
        mGraphServiceClient.getUsers().buildRequest().get(callback);
    }


    /**
     * @param room - Room to retrieve Appointments for
     * @param callback -  Response received from server
     */
    public void apiAppointments(final Room room, ICallback<IEventCollectionPage> callback) {
        mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents().buildRequest().get(callback);
    }

    /**
     * @param callback -  Response received from server
     * This method gets all the rooms in the organisation from the api. Puts them in the roomlist.
     */
    public void apiRooms(ICallback<IUserCollectionPage> callback) {
        Log.d(this.getClass().toString(), "Starting room get");
        mGraphServiceClient.getUsers().buildRequest().get(callback);
    }

    /**
     * @param room -  Room to open
     * This method attempts to open the current meeting of the given Room
     */
    public void apiOpenMeeting(Room room) {
        User user = new User();
        if (room.getState() == State.Bezet) {
            user.surname = "1";
        } else if (room.getState() == State.Vrij) {
            user.surname = "0";
        }
        mGraphServiceClient.getUsers(room.getId()).buildRequest().patch(user);
    }


    /**
     * @param appointment - Appointment to close
     * This method deletes given Appointment
     */
    public void closeMeeting(Appointment appointment) {
        mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents(appointment.getId()).buildRequest().delete();
    }

    /**
     * @param appointment -  The Appointment and its information, will get converted to an event before posting to API
     * Add's a new Appointment to the Graph server
     */
    public void apiScheduleMeeting(Appointment appointment) {
        try {
            Event e = createEvent(appointment.getName(), appointment.getReserveringsTijd(), appointment.getReserveringEind(), appointment.getAttendees());
            mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents().buildRequest().post(e);
        } catch (Exception ex) {
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
        for (fhict.mylibrary.User user : libattendees) {
            Attendee att = new Attendee();
            EmailAddress email = new EmailAddress();
            email.name = user.getName();
            if (user.getEmail() != null) {
                email.address = user.getEmail();
            } else {
                email.address = "N/A";
            }
            att.emailAddress = email;
            attendees.add(att);
        }
        event.attendees = attendees;
        return event;
    }

    /**
     * @param appointment - Appointment to extend
     * Extends a given Appointment by a set ammount.
     */
    public void extendMeeting(Appointment appointment) {
        try {
            Event e = createEvent(appointment.getName(), appointment.getReserveringsTijd(), appointment.getReserveringEind(), appointment.getAttendees());
            mGraphServiceClient.getGroups("ced4d46f-5fc8-450f-9fa0-c1149c7a5238").getEvents(appointment.getId()).buildRequest().patch(e);
        } catch (Exception ex) {
            Log.d("GraphServiceController", ex.getMessage());
        }
    }
}