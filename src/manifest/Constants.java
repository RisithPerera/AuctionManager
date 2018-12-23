package manifest;

public interface Constants {
    /*
        This interface keeps all necessary Constant values for the application
        Its very easy to handle these values such as promt messages, port number
        at one place
     */


    //Server Port
    public static final int SERVER_PORT = 1250;

    //Stock.csv file path
    public static final String STOCK_PATH  = "src/csvfile/stocks.csv";

    //Style Sheet path
    public static final String STYLE_PATH  = "style/style.css";

    //Client Connection States
    public static final int LOGIN_STATE  = 0;
    public static final int SYMBOL_STATE = 1;
    public static final int PRICE_STATE  = 2;

    //Client States Messages
    public static final String LOGIN_STATE_MSG   = "Enter your name  : ";
    public static final String SYMBOL_STATE_MSG  = "Provide a symbol : ";
    public static final String CURRENT_PRICE_MSG = "Current Price is : ";
    public static final String ENTER_PRICE_MSG   = "What is your Bid : ";
    public static final String SUCCESS_PRICE_MSG = "Price Updated....!\n------------------";
    public static final String WRONG_PRICE_MSG   = "Invalid Price....!\n------------------";

    public static final String[] SECURITY_LIST = {"FB", "VRTU", "MSFT", "GOOGL", "YHOO", "XLNX", "TSLA", "TXN"};

    //User Interface Constrains
    public static final int SPACE = 5;
}
