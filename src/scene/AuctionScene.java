package scene;

import database.AuctionBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Bid;
import model.Item;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;

public class AuctionScene extends Scene {
    private static HBox root;

    private TableView<Item> itemTable;
    private TableView<Bid>  bidTable;
    private ObservableList<Item> data;
    private TableColumn symbolCol,nameCol,priceCol;
    private TableColumn clientCol,timeCol,bidPriceCol;

    private Timeline timer;

    static {
        root = new HBox();
    }

    public AuctionScene() {
        super(root);

        itemTable = new TableView<>();
        data = AuctionBase.getDataBase().getAllItems();
        symbolCol = new TableColumn("Symbol");
        nameCol = new TableColumn("Name");
        priceCol = new TableColumn("Price");

        bidTable = new TableView<>();
        clientCol = new TableColumn("Client Name");
        timeCol = new TableColumn("Time");
        bidPriceCol = new TableColumn("Offered Price");

        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("securityName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));

        clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        bidPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        symbolCol.setMinWidth(75);
        symbolCol.setMaxWidth(100);

        priceCol.setMinWidth(100);
        priceCol.setMaxWidth(125);
        priceCol.setStyle( "-fx-alignment: CENTER-RIGHT;");
        
        bidPriceCol.setMinWidth(100);
        bidPriceCol.setMaxWidth(125);
        bidPriceCol.setStyle( "-fx-alignment: CENTER-RIGHT;");

        itemTable.setEditable(false);
        itemTable.setItems(data);
        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        HBox.setHgrow(itemTable, Priority.ALWAYS);
        itemTable.getColumns().addAll(symbolCol, nameCol, priceCol);


        bidTable.setEditable(false);
        bidTable.setItems(null);
        bidTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        HBox.setHgrow(bidTable, Priority.ALWAYS);
        bidTable.getColumns().addAll(clientCol,timeCol,bidPriceCol);

        root.getChildren().addAll(itemTable,bidTable);
        root.setSpacing(10);
        root.setPrefWidth(800);
        root.setPrefHeight(600);

        timer = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
            System.out.println("heyyyyy");
            itemTable.refresh();
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
}
