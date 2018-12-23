package scene;

import database.AuctionBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import manifest.Constants;
import model.Bid;
import model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuctionScene extends Scene {
    private static HBox root;

    private TableView<Item> itemTable;
    private TableView<Bid>  bidTable;
    private ObservableList<Item> data;
    private TableColumn symbolCol,nameCol,priceCol;
    private TableColumn clientCol,timeCol,bidPriceCol;
    private Item selectedItem;

    private Timeline timer;

    static {
        root = new HBox();
    }

    public AuctionScene() {
        super(root);

        itemTable = new TableView<>();
        data = AuctionBase.getDataBase().getAllItems();
        symbolCol = new TableColumn("Symbol");
        nameCol = new TableColumn("Company Name");
        priceCol = new TableColumn("Current Price");

        VBox detailVBox = new VBox();

        LineChart<String, Number> bidChart = new LineChart<>(new CategoryAxis(), new NumberAxis());

        bidChart.setStyle( "-fx-background-color: #FFFFFF;");
        final XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        bidChart.setTitle("Item Price Monitoring");
        bidChart.setLegendVisible(false);
        bidChart.getData().addAll(dataSeries);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        bidTable = new TableView<>();
        clientCol = new TableColumn("Client Name");
        timeCol = new TableColumn("Time");
        bidPriceCol = new TableColumn("Offered Price");

        symbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("securityName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));

        clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        bidPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        symbolCol.setMinWidth(75);
        symbolCol.setMaxWidth(100);

        priceCol.setMinWidth(100);
        priceCol.setMaxWidth(125);
        priceCol.setStyle( "-fx-alignment: CENTER-RIGHT;");

        timeCol.setMinWidth(100);
        timeCol.setMaxWidth(125);
        timeCol.setStyle( "-fx-alignment: CENTER;");

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
        bidTable.getColumns().addAll(clientCol,timeCol,bidPriceCol);

        detailVBox.getChildren().addAll(bidChart,bidTable);
        HBox.setHgrow(detailVBox,Priority.ALWAYS);
        detailVBox.setSpacing(Constants.SPACE);

        root.getChildren().addAll(itemTable,detailVBox);
        root.setSpacing(Constants.SPACE);
        root.setPrefWidth(800);
        root.setPrefHeight(600);
        root.setStyle( "-fx-background-color: #3b224b;");
        root.setPadding(new Insets(Constants.SPACE,Constants.SPACE,Constants.SPACE,Constants.SPACE));

        itemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection != oldSelection) {
                selectedItem = newSelection;
                bidTable.setItems(selectedItem.getBidList());
                bidChart.setTitle(selectedItem.getSymbol()+" Price Monitoring");
            }

            if(newSelection != null){
                dataSeries.getData().clear();
                for (Bid bid : selectedItem.getBidList()) {
                    dataSeries.getData().add(new XYChart.Data(bid.getTime(),bid.getPrice()));
                }
            }
        });

        timer = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
            itemTable.refresh();

        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        this.getStylesheets().add("style/style.css");
    }
}
