package main;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.AuctionScene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Auction Manager");
        primaryStage.setScene(new AuctionScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
