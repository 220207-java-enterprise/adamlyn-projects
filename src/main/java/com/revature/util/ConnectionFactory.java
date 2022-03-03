package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory connectionFactory;

    static{
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private Properties myprops = new Properties();

    private ConnectionFactory() {
        try {
            //myprops.load(new FileReader("src/main/resources/application.properties"));
            myprops.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static ConnectionFactory getInstance() {
            if (connectionFactory == null) {
                connectionFactory = new ConnectionFactory();
            }
            return connectionFactory;
        }

        public Connection getConnection() throws SQLException{
        Connection conn = DriverManager.getConnection(myprops.getProperty("db-url"), myprops.getProperty("db-username"), myprops.getProperty("db-password"));

        if(conn == null) {
            throw new RuntimeException("Could not establish connection with database!");
        }

        return conn;
    }
}
