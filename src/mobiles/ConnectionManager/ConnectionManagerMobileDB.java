package mobiles.ConnectionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerMobileDB implements ConnectionManager {
    private static ConnectionManager connectionManager;
    private static final String CONFIG =
            "D:\\dev_edu\\STC13_HT\\HT12_JDBC_DAOs\\src\\mobiles\\conf\\settings.cfg";

    // приватный контсруктор, что бы нельзя было создавать объекты менеджера
    private ConnectionManagerMobileDB() {
    }

    public static ConnectionManager getInstance() {
        // если объект не создан, то создается
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerMobileDB();
        }
        // возвращаем объект, если он был создан, то вернется уже имеющийся
        return connectionManager;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;

        String password = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIG))) {
            password = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mobile",
                    "postgres",
                    password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
