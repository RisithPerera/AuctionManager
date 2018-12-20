package database;

import model.Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionBase {

    private Map<String, Item> itemMap;

    public AuctionBase(String cvsFile)  {
        mapFileData(cvsFile);
    }

    //public interface
    public Item getItem(String key) {
        return itemMap.get(key);
    }

    public void mapFileData(String fileName){
        try{
            //Check the file existence
            File file = new File(fileName);
            if (!file.exists()){
                throw new IOException(fileName + "file doesn't exist");
            }

            String line;
            //Read the file
            try (FileReader fileReader = new FileReader(file)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                /* read the CSV file's first line which has
                 * the names of fields.
                 */
                line = bufferedReader.readLine();

                if(line.equals("Symbol,Security Name,Price")){

                    // Map reference initialized by HashMap
                    itemMap = new HashMap<String, Item>();
                    while ((line = bufferedReader.readLine()) != null) {
                        //Divide the string at the commas
                        String[] parts = line.split(",");
                        Item item = new Item(parts[1],Double.parseDouble(parts[2]));
                        itemMap.put(parts[0],item);
                    }
                }else{
                    throw new IOException(fileName + "file doesn't have data");
                }

                if(fileReader != null) fileReader.close();
                if(bufferedReader != null) bufferedReader.close();
            }
        }catch(IOException e){
            System.out.println("Error : When reading the " + fileName);
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(fileName + "file is malformed");
            System.out.println(e);
        }

    }
}
