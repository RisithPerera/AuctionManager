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

public class AuctionPane extends HBox {


    private TableView<Item> itemTable;
    private TableView<Bid>  bidTable;
    private ObservableList<Item> data;
    private TableColumn symbolCol,nameCol,priceCol;
    private TableColumn clientCol,timeCol,bidPriceCol;
    private LineChart<String, Number> bidChart;
    private XYChart.Series<String, Number> dataSeries;
    private Timeline timer;
    private VBox detailVBox;

    private Item selectedItem;
    private int noOfBids = 0;

    public AuctionPane() {
        itemTable = new TableView<>();
        data = AuctionBase.getDataBase().getFilteredItems();
        symbolCol = new TableColumn("Symbol");
        nameCol = new TableColumn("Company Name");
        priceCol = new TableColumn("Current Price");

        detailVBox = new VBox();

        bidChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        dataSeries = new XYChart.Series<>();

        bidChart.setStyle( "-fx-background-color: #FFFFFF;");
        bidChart.setTitle("Item Price Monitoring");
        bidChart.setLegendVisible(false);
        bidChart.getData().addAll(dataSeries);

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

        //Add event listener for each row
        itemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection != oldSelection) {
                selectedItem = newSelection;
                bidTable.setItems(selectedItem.getBidList());
                bidChart.setTitle(selectedItem.getSymbol()+" Price Monitoring");

                dataSeries.getData().clear();
                noOfBids = selectedItem.getBidList().size();
                for (Bid bid : selectedItem.getBidList()) {
                    dataSeries.getData().add(new XYChart.Data(bid.getTime(),bid.getPrice()));
                }
            }
        });

        //Update user interface every 500ms
        timer = new Timeline(new KeyFrame(Duration.millis(500), (ActionEvent event) -> {
            itemTable.refresh();
            if(selectedItem != null && selectedItem.getBidList().size() > noOfBids){
                for(Bid bid : selectedItem.getBidList().subList(noOfBids,selectedItem.getBidList().size())) {
                    dataSeries.getData().add(new XYChart.Data(bid.getTime(), bid.getPrice()));
                }
                noOfBids = selectedItem.getBidList().size();
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        this.getChildren().addAll(itemTable,detailVBox);
        this.setSpacing(Constants.SPACE);
        this.setPrefWidth(800);
        this.setPrefHeight(600);
        this.setStyle( "-fx-background-color: #3b224b;");
        this.setPadding(new Insets(Constants.SPACE,Constants.SPACE,Constants.SPACE,Constants.SPACE));
        this.getStylesheets().add(Constants.STYLE_PATH);
    }
}
