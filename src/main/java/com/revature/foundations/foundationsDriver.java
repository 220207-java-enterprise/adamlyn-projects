package com.revature.foundations;

import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class foundationsDriver {

    public static void main(String[] args) {
        ConnectionFactory connFactory = ConnectionFactory.getInstance();
        ConnectionFactory connFactory2 = ConnectionFactory.getInstance();

        System.out.println(connFactory == connFactory2);

        Connection conn = null;

        try {
            conn = connFactory.getConnection();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        if (conn == null) {
            System.out.println("Error");
        } else {
            System.out.println("Success");
        }


    }
}