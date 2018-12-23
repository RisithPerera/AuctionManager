package server;

import database.AuctionBase;
import manifest.Constants;
import model.Bid;
import model.Item;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuctionClientWorker implements Runnable{

    // per connection variables
    private Socket clientSocket; // connection socket per thread
    private int clientState;
    private String clientName;
    private Item selectedItem;
    private BufferedReader reader;
    private PrintWriter writer;

    public AuctionClientWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.clientState = Constants.LOGIN_STATE;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String outputLine = Constants.LOGIN_STATE_MSG;
            writer.print(outputLine);
            writer.flush();

            String inputLine = reader.readLine();
            while ( inputLine != null && !inputLine.equals("quit")) {
                switch (clientState) {
                    case Constants.LOGIN_STATE:
                        // We are waiting for client name
                        clientName = inputLine;
                        writer.print(Constants.SYMBOL_STATE_MSG);
                        clientState = Constants.SYMBOL_STATE;
                        break;

                    case Constants.SYMBOL_STATE:
                        selectedItem = AuctionBase.getDataBase().getItem(inputLine);
                        //mainServer.postMSG(this.clientName + " Says: " + line);
                        if(selectedItem == null){
                            writer.println(Integer.toString(-1));
                            writer.print(Constants.SYMBOL_STATE_MSG);
                        }else{
                            writer.println(Constants.CURRENT_PRICE_MSG + selectedItem.getCurrentPrice());
                            writer.print(Constants.ENTER_PRICE_MSG);
                            clientState = Constants.PRICE_STATE;
                        }
                        break;

                    case Constants.PRICE_STATE:
                        try{
                            String bidTime = new SimpleDateFormat("hh:mm:ss").format(new Date());
                            double newPrice = Double.parseDouble(inputLine);
                            if(selectedItem.setCurrentPrice(clientName,bidTime,newPrice)) {
                                writer.println(Constants.SUCCESS_PRICE_MSG);
                                writer.print(Constants.SYMBOL_STATE_MSG);
                                clientState = Constants.SYMBOL_STATE;
                            }else{
                                writer.println(Constants.WRONG_PRICE_MSG);
                                writer.print(Constants.ENTER_PRICE_MSG);
                            }
                        }catch (NumberFormatException e){
                            writer.println(Constants.WRONG_PRICE_MSG);
                            writer.print(Constants.ENTER_PRICE_MSG);
                            clientState = Constants.PRICE_STATE;
                        }
                        break;

                    default:
                        System.out.println("Undefined state");
                        return;
                }

                // flush to the client
                writer.flush();
                inputLine = reader.readLine();
            }

            // close everything
            writer.close();
            reader.close();
            clientSocket.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
