package com.revature.servlets;

import com.revature.util.ConnectionFactory;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TestServlet2 {

    public static void main(String[] args) {
        ConnectionFactory connFactory = ConnectionFactory.getInstance();
        ConnectionFactory connFactory2 = ConnectionFactory.getInstance();

        System.out.println(connFactory == connFactory2);

        Connection conn = null;
        try {
            conn = connFactory.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ERS_REIMBURSEMENTS VALUES('73b22313-a821-441e-844d-ac0ff9741cb1', 156.79, "
                    + "TO_TIMESTAMP('03-02-2022 00:41:44', 'DD-MM-YYYY HH24:MI:SS'), null, 'Misplaced some money', null, null, '1', null, '1', '2')");
            System.out.println("PSTMT: " + pstmt);

            PreparedStatement ostmt = conn.prepareStatement("INSERT INTO ERS_REIMBURSEMENTS VALUES(?, ?, " +
                    "TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS'), TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS'), ?, null," +
                    " ?, ?, ?, ?, ?)");
            ostmt.setString(1, "115c9e07-e5d9-4671-943c-5ae355e2a655");
            ostmt.setFloat(2, (float) 156.79);
            ostmt.setString(3, "03-02-2022 00:41:44");
            ostmt.setString(4, null);
            ostmt.setString(5, "Misplaced some money");
            //ostmt.setString(6, null);
            ostmt.setString(6, null);
            ostmt.setString(7, "1");
            ostmt.setString(8, null);
            ostmt.setString(9, "1");
            ostmt.setString(10, "2");
            System.out.println("OSTMT: " +ostmt);
            int counter = ostmt.executeUpdate();
            System.out.println(counter);
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