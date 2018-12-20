package examplecode;

import java.util.*;

class VisualServer extends MainServer { 
    private static String msgs;

    public VisualServer(int socket, StudentDB user) { 
        super(socket, user);
    }

    @Override 
    public synchronized void postMSG(String str) { 
        // I can override and make function synchronized
        msgs = str;
    }

    public String getMSG() {
	if(!msgs.isEmpty()) return msgs;
	    return null;
    }
}
	