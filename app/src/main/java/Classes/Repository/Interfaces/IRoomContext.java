package Classes.Repository.Interfaces;

import java.util.List;

/**
 * Created by Martien on 20-Sep-17.
 */

public interface IRoomContext<R> {
    void add(R item);
    void update(R item);
    void remove(R item);
    List<R> getAll();
}
