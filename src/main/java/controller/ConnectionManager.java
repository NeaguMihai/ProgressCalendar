package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private String user = "root";
    private String passwd = "";
    private String host = "jdbc:mysql://localhost/calendar?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection;

    private static final class SingletonHolder {

        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    public Connection getConnection() {
        return connection;
    }

    private ConnectionManager() {

        try {
            connection = DriverManager.getConnection(host,user,passwd);
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
    }

    public static ConnectionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }


}
