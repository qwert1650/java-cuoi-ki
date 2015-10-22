package helpers;

public class Constants {

    public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String DATA_FILE_NAME = "sheet.csv";
    public static final String CSV_SPLIT_BY = ",";
    public static final int SERVER_PORT = 4443;
    public static final String USER_DATA_FOLDER = "user";
    public static final String USER_DATA_FILE_NAME = "user-data.xml";
    public static final String USER_DATA_FILE_PATH = CURRENT_DIRECTORY + FILE_SEPARATOR
            + USER_DATA_FOLDER + FILE_SEPARATOR + USER_DATA_FILE_NAME;


}
