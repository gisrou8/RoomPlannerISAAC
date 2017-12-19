package com.example.gisro.roomplannerisaac.Classes.Client;

import java.io.ObjectInputStream;

/**
 * Created by BePulverized on 28-11-2017.
 */

public class Task {
    String id;
    Object data;

    public Task(String id, Object data)
    {
        this.id = id;
        this.data = data;
    }
}
