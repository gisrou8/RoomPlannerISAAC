package com.example.gisro.roomplannerisaac.Classes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by pieni on 27/09/2017.
 */
public class AppointmentTest {
    public Appointment appointment;

    @Before
    public void setUp() throws Exception {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strdate = "27-09-2017 11:40:42";
        Date newdate = dateformat.parse(strdate);

        appointment = new Appointment("TestAppointment", newdate, new Room("1.13"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void open() throws Exception {
        assertThat(appointment.open(), is(true));
    }

    @Test
    public void close() throws Exception {
        appointment.close();
        assertEquals(appointment.getState(), State.Closed);
    }

}