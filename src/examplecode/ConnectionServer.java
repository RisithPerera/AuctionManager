package examplecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class ConnectionServer implements Runnable {
    // some constants 
    public static final int WAIT_AUTH = 0;
    public static final int AUTH_DONE = 1;

    public static final String WAIT_AUTH_MSG = "Registration Number pls!\n";
    public static final String AUTH_DONE_MSG = "You are authorised to post\n";
    public static final String MSG_POSTED = "Your message posted\n";

    // per connection variables
    private Socket mySocket; // connection socket per thread 
    private int currentState;
    private String clientName;
    private MainServer mainServer;

    public ConnectionServer(MainServer mainServer) {
        this.mySocket = null; // we will set this later
        this.currentState = WAIT_AUTH;
        this.clientName = null;
        this.mainServer = mainServer;
        // who created me. He should give some interface
    }

    public boolean handleConnection(Socket socket) {
        this.mySocket = socket;
        Thread newThread = new Thread(this);
        newThread.start();
        return true;
    }

    public void run() { // can not use "throws .." interface is different
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));

            String line, outline;
            for (line = in.readLine(); line != null && !line.equals("quit"); line = in.readLine()) {

                switch (currentState) {
                    case WAIT_AUTH:
                        // we are waiting for login name
                        // e number should be the line
                        if (mainServer.isAuthorized(line)) {
                            currentState = AUTH_DONE;
                            clientName = mainServer.getName(line);
                            outline = AUTH_DONE_MSG;
                        } else {
                            outline = WAIT_AUTH_MSG;
                        }
                        break;
                    /*****************************/
                    case AUTH_DONE:
                        mainServer.postMSG(this.clientName + " Says: " + line);
                        outline = MSG_POSTED;
                        break;
                    default:
                        System.out.println("Undefined state");
                        return;
                } // case

                out.println(outline); // Send the said message
                out.flush(); // flush to network

            } // for

            // close everything
            out.close();
            in.close();
            this.mySocket.close();
        } // try
        catch (IOException e) {
            System.out.println(e);
        }
    }
}

    
    

