package fhict.server.Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by BePulverized on 16-11-2017.
 */

public class SocketServerThread extends Thread {

    //Global info
    public static ArrayList<Socket> connectionArray = new ArrayList<>();
    public static ArrayList<String> currentUser = new ArrayList<>();
    ServerSocket serverSocket;
    String message = "";
    static final int SocketServerPORT = 8080;
    int count = 0;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(SocketServerPORT);

            while (true) {
                Socket socket = serverSocket.accept();
                connectionArray.add(socket);
                count++;
                message += "#" + count + " from " + socket.getInetAddress()
                        + ":" + socket.getPort() + "\n";
                SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                        socket, count);
                socketServerReplyThread.run();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addUser(Socket socket) throws IOException {
        Scanner input = new Scanner(socket.getInputStream());
        String userName = input.nextLine();
        currentUser.add(userName);
    }


}
