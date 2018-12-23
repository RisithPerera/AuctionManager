package main;

import database.AuctionBase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manifest.Constants;
import scene.AuctionPane;
import server.AuctionServer;

public class Main extends Application {

    /*
        The main entry point for all JavaFX applications.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Auction Manager");
        primaryStage.setScene(new Scene(new AuctionPane()));

        //Launch the JavaFX User Interface
        primaryStage.show();
    }

    public static void main(String[] args) {
        //First initialize auction database
        AuctionBase.initialize(Constants.STOCK_PATH);

        //Then create sever by given port number
        AuctionServer auctionServer = new AuctionServer(Constants.SERVER_PORT);

        //Start the sever thread
        auctionServer.start();
        launch(args);
    }
}
