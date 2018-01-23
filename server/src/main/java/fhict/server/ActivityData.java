package fhict.server;

import android.content.Context;

/**
 * Created by BePulverized on 12-12-2017.
 */

public interface ActivityData {
    void setData(Object data) throws ClassNotFoundException;
    Context getContext();
}
