package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manifest.Constants;
import model.Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuctionBase {

    /*
        This class read the given csv file and map all data into hashMap
     */
    private static AuctionBase auctionBase;
    private Map<String, Item> itemMap;
    private ObservableList<Item> itemList;

    //Used Singleton Design Pattern
    private AuctionBase(String filePath) {
        mapFileData(filePath);
    }

    public static void initialize(String filePath) {
        if (auctionBase == null) {
            auctionBase = new AuctionBase(filePath);
        }
    }

    public static AuctionBase getDataBase() {
        /*
            If auctionBas is null, It will be initialize by the default filepath
         */
        if (auctionBase == null) {
            auctionBase = new AuctionBase(Constants.STOCK_PATH);
        }
        return auctionBase;
    }

    private void mapFileData(String fileName) {
        try {
            //Check the file existence
            File file = new File(fileName);
            if (!file.exists()) {
                throw new IOException(fileName + " file doesn't exist");
            }

            String line;
            //Read the file
            try (FileReader fileReader = new FileReader(file)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                /* read the CSV file's first line which has
                 * the names of fields.
                 */
                line = bufferedReader.readLine();

                if (line.trim().equals("Symbol,Security Name,Price")) {

                    // Map reference initialized by HashMap
                    itemMap = new HashMap<String, Item>();
                    Item item;
                    while ((line = bufferedReader.readLine()) != null) {
                        //Divide the string at the commas
                        String[] parts = line.split(",");
                        item = new Item(parts[0],parts[1], Double.parseDouble(parts[2]));
                        itemMap.put(parts[0], item);
                    }
                } else {
                    throw new IOException(fileName + " file doesn't have data");
                }

                if (fileReader != null) fileReader.close();
                if (bufferedReader != null) bufferedReader.close();
            }
        } catch (IOException e) {
            System.out.println(fileName + " has a error");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(fileName + " file is malformed");
            e.printStackTrace();
        }
    }

    //public getter for map items
    public Item getItem(String key) {
        return itemMap.get(key);
    }

    //Every Bids are kept inside a Observable List.
    //Observablelist is a list that allows listeners to track changes when they occur.
    public ObservableList<Item> getFilteredItems() {
        itemList = FXCollections.observableArrayList();
        for (String key: Constants.SECURITY_LIST) {
            itemList.add(itemMap.get(key));
        }
        return itemList;
    }
}
