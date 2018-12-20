package manifest;

public interface Constants {

    //Server Port
    public static final int BASE_PORT = 1250;

    //Stock.csv file path
    public static final String STOCK_PATH  = "src/csvfile/stocks.csv";

    //Client Connection States
    public static final int LOGIN_STATE = 0;
    public static final int SYMBOL_STATE = 1;
    public static final int PRICE_STATE = 2;

    //Client States Messages
    public static final String LOGIN_STATE_MSG   = "Enter your name: ";
    public static final String AUTH_DONE_MSG = "You are authorised to bid\n";
    public static final String SYMBOL_STATE_MSG  = "Provide the symbol: ";
    public static final String CURRENT_PRICE_MSG = "Current Price: ";
    public static final String ENTER_PRICE_MSG   = "Enter your price: ";

    public static final String[] SECURITY_LIST = {"FB", "VRTU", "MSFT", "GOOGL", "YHOO", "XLNX", "TSLA", "TXN"};

}
