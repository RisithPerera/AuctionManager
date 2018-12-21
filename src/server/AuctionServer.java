package server;


// File Name GreetingServer.java
import java.net.*;
import java.io.*;

public class AuctionServer extends Thread {
    private ServerSocket serverSocket;

    public AuctionServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            //serverSocket.setSoTimeout(300000);   // 5 min
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket client = serverSocket.accept();
                Thread clientWorker = new Thread(new AuctionClientWorker(client));
                clientWorker.start();
            } catch (SocketTimeoutException s) {
                System.out.println("Server timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
