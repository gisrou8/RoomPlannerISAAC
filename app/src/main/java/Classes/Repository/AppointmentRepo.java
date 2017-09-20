package Classes.Repository;

import java.util.List;

/**
 * Created by pieni on 20/09/2017.
 */

public class AppointmentRepo implements IAppointmentRepo {

    private IAppointmentRepo appointment;

    public AppointmentRepo(IAppointmentRepo appointment){
        this.appointment = appointment;
    }

    @Override
    public void add(Object item) {
        appointment.add(item);
    }

    @Override
    public void update(Object item) {
        appointment.update(item);
    }

    @Override
    public void remove(Object item) {
        appointment.remove(item);
    }

    @Override
    public List getAll() {
        return appointment.getAll();
    }
}
