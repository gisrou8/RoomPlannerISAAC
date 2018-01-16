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
        setContentView(R.layout.activity_freerooms);
        rooms = new ArrayList<>();


    }
}
