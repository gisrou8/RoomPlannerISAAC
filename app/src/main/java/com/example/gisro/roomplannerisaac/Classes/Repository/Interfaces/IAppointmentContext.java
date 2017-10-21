package com.example.gisro.roomplannerisaac.Classes.Repository.Interfaces;

import java.util.List;

/**
 * Created by pieni on 20/09/2017.
 */

public interface IAppointmentContext<A> {
    void add(A item);
    void update(A item);
    void remove(A item);
    List<A> getAll();
}
