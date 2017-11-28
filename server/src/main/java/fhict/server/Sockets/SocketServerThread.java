package fhict.server.Sockets;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import fhict.server.GraphAPI.GraphServiceController;

/**
 * Created by BePulverized on 16-11-2017.
 */

public class SocketServerThread extends Thread {

    //Global info
    public static ArrayList<Socket> connectionArray = new ArrayList<>();
    public static ArrayList<String> currentUser = new ArrayList<>();
    ServerSocket serverSocket;
    private GraphServiceController controller;
    String message = "";
    static final int SocketServerPORT = 8080;
    int count = 0;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(SocketServerPORT);
            controller = new GraphServiceController();
            while (true) {
                Socket socket = serverSocket.accept();
                connectionArray.add(socket);
                Log.d("Server", socket.getInetAddress().toString() + " has connected to server!");
                SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                        socket, controller);
                socketServerReplyThread.run();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
