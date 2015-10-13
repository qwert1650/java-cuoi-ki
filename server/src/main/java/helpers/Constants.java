package helpers;

public class Constants {

    public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String USER_DATA_FILE_NAME = "user-data.xml";
    public static final String USER_FOLDER = "user";
    public static final String USER_DATA_FILE_PATH = CURRENT_DIRECTORY + FILE_SEPARATOR + USER_FOLDER + FILE_SEPARATOR + USER_DATA_FILE_NAME;
}
