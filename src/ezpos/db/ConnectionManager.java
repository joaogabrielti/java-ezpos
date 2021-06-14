package ezpos.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final int MAX_CONNECTIONS = 5;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private final Connection[] connections;
    private static final ConnectionManager instance = new ConnectionManager();

    private ConnectionManager() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/config/config.properties");
        try {
            properties.load(inputStream);
            URL = properties.getProperty("DB_URL");
            USERNAME = properties.getProperty("DB_USERNAME");
            PASSWORD = properties.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }
        connections = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            if(instance.connections[i] == null || instance.connections[i].isClosed()){
                instance.connections[i] = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                return instance.connections[i];
            }
        }
        throw new SQLException("O limite de conexões com o banco de dados foi atingido!");
    }
}