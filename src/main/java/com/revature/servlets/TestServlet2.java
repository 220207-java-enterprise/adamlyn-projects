package com.revature.servlets;

import com.revature.util.ConnectionFactory;

import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.SQLException;


public class TestServlet2 {

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