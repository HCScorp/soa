package approver.data.database;

import java.util.Properties;
import java.io.IOException;

public class Network {

    private static String readProperties(String key) {
        try {
            Properties prop = new Properties();
            prop.load(Network.class.getResourceAsStream("/network.properties"));
            return prop.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String HOST = readProperties("databaseHostName");
    public static final int PORT = Integer.parseInt(readProperties("databasePort"));
    public static final String DATABASE = "btrs";
    public static final String COLLECTION = "btr";
}