package com.revature.servlets;

import com.revature.daos.ReimbursementDAO;
import com.revature.daos.UserDAO;
import com.revature.dtos.responses.ReimbursementResponse;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.transform.Result;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class TestServlet2 {

    public static void main(String[] args) {
        ConnectionFactory connFactory = ConnectionFactory.getInstance();
        ConnectionFactory connFactory2 = ConnectionFactory.getInstance();

        System.out.println(connFactory == connFactory2);

        Connection conn = null;


        UserDAO userDAO = new UserDAO();
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        try {
            conn = connFactory.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ERS_REIMBURSEMENTS VALUES('73b22313-a821-441e-844d-ac0ff9741cb1', 156.79, "
                    + "TO_TIMESTAMP('03-02-2022 00:41:44', 'DD-MM-YYYY HH24:MI:SS'), null, 'Misplaced some money', null, null, '1', null, '1', '2')");
            System.out.println("PSTMT: " + pstmt);


            String timeStamp = new SimpleDateFormat("MM-dd-yyyy KK:mm:ss").format(new Date());
            System.out.println(timeStamp);

            PreparedStatement ostmt = conn.prepareStatement("SELECT rmb.REIMB_ID, rmb.AMOUNT, rmb.SUBMITTED, " +
                            "rmb.RESOLVED, rmb.DESCRIPTION, rmb.RECEIPT, rmb.PAYMENT_ID, rmb.AUTHOR_ID, " +
                            "rmb.RESOLVER_ID, rmb.STATUS_ID, rmb.TYPE_ID, " +
                            "rmbs.STATUS, rmbt.TYPE FROM ERS_REIMBURSEMENTS rmb JOIN ERS_REIMBURSEMENT_STATUSES rmbs " +
                            "ON rmb.STATUS_ID = rmbs.STATUS_ID JOIN ERS_REIMBURSEMENT_TYPES rmbt " +
                            "ON rmb.TYPE_ID = rmbt.TYPE_ID WHERE AUTHOR_ID = 'c3b1035f-66c7-4f31-a9f0-c29c5cf4ffa8' " +
                    "AND rmb.TYPE_ID = '2'"
            );
//            ostmt.setString(1, "115c9e07-e5d9-4671-943c-5ae355e2a655");
//            ostmt.setFloat(2, (float) 156.79);
//            ostmt.setString(3, "03-02-2022 00:41:44");
//            ostmt.setString(4, null);
//            ostmt.setString(5, "Misplaced some money");
//            //ostmt.setString(6, null);
//            ostmt.setString(6, null);
//            ostmt.setString(7, "1");
//            ostmt.setString(8, null);
//            ostmt.setString(9, "1");
//            ostmt.setString(10, "2");
            System.out.println("OSTMT: " +ostmt);
            ResultSet rs = (ostmt.executeQuery());
            ArrayList<Reimbursement> reimbList = new ArrayList<>();
            Reimbursement userReimbursement = null;
            while(rs.next()) {
                userReimbursement = new Reimbursement();
                userReimbursement.setReimb_id(rs.getString("REIMB_ID"));
                userReimbursement.setAmount(rs.getFloat("AMOUNT"));
                userReimbursement.setSubmitted(rs.getString("SUBMITTED"));
                userReimbursement.setResolved(rs.getString("RESOLVED"));
                userReimbursement.setDescription(rs.getString("DESCRIPTION"));
                userReimbursement.setReceipt(rs.getString("RECEIPT"));
                userReimbursement.setPayment_id(rs.getString("PAYMENT_ID"));
                userReimbursement.setAuthor_id(userDAO.getById(rs.getString("AUTHOR_ID")));
                if(rs.getString("RESOLVER_ID") != null)
                  userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }

            Properties myprops = new Properties();
            try {
                myprops.load(new FileReader("src/main/resources/application.properties"));
            }catch (IOException e){
                e.printStackTrace();
            }


            String salt = myprops.getProperty("db-salty-byte");
            System.out.println(salt);



        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        if (conn == null) {
            System.out.println("Error");
        } else {
            System.out.println("Success");
        }

        String astring = "p4$$Word";
        String bstring = "hello";

        System.out.println(astring + " " + BCrypt.hashpw(astring, BCrypt.gensalt(10)));
        System.out.println(astring + " " + BCrypt.hashpw(astring, BCrypt.gensalt(10)));
        String ahash = BCrypt.hashpw(astring, BCrypt.gensalt(10));
        String a2hash= BCrypt.hashpw(astring, BCrypt.gensalt(10));

        System.out.println("AHASSHSTRING: " + BCrypt.checkpw(ahash, a2hash));

        System.out.println(astring + " " + BCrypt.hashpw(astring, BCrypt.gensalt(10)));
        BCrypt.hashpw(bstring, BCrypt.gensalt(10));

        BCrypt.checkpw(astring, BCrypt.hashpw(astring, BCrypt.gensalt(10)));
        System.out.println("ASTRING: " + BCrypt.checkpw(astring, BCrypt.hashpw(astring, BCrypt.gensalt(10))));
        System.out.println("BSTRING: " + BCrypt.checkpw(bstring, BCrypt.hashpw(bstring, BCrypt.gensalt(10))));

    }
}