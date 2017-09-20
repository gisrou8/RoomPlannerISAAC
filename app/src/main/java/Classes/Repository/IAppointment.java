package Classes.Repository;

import java.util.List;

/**
 * Created by pieni on 20/09/2017.
 */

public interface IAppointment<A> {
    void add(A item);
    void update(A item);
    void remove(A item);
    List<A> getAll();
}
