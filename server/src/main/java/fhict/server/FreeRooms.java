package fhict.server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import fhict.mylibrary.Room;

public class FreeRooms extends AppCompatActivity {
    ArrayList<Room> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        rooms = new ArrayList<>();
         

    }
}
