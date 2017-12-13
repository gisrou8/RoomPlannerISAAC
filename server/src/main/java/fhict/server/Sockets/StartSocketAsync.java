package fhict.server.Sockets;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fhict.server.GraphAPI.GraphServiceController;

/**
 * Created by BePulverized on 12-12-2017.
 */

public class StartSocketAsync extends AsyncTask {

    GraphServiceController controller = new GraphServiceController();
    @Override
    protected Object doInBackground(Object[] objects) {
        Socket s = null;
        ServerSocket ss2 = null;
        try{
            ss2 = new ServerSocket(8080); // can also use static final PORT_NUM , when defined

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");

        }

        while(true){
            try{
                s= ss2.accept();
                System.out.println("connection Established");
                SocketServerReplyThread st = new SocketServerReplyThread(s, controller);
                st.start();

            }

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }
}
