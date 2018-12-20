package main;

import database.AuctionBase;
import javafx.application.Application;
import javafx.stage.Stage;
import manifest.Constants;
import scene.AuctionScene;
import server.AuctionServer;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Auction Manager");
        primaryStage.setScene(new AuctionScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        AuctionBase auctionBase = AuctionBase.getDataBase();
        //auctionBase.printMap();
        AuctionServer auctionServer = new AuctionServer(Constants.BASE_PORT);
        auctionServer.start();
        launch(args);
    }
}
