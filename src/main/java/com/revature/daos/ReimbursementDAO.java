package com.revature.daos;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.DataSourceException;
import com.revature.util.exceptions.ResourcePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReimbursementDAO implements CrudDAO<Reimbursement>{

    private final String rootSelect = "SELECT " +
            "rmb.REIMB_ID, rmb.AMOUNT, rmb.SUBMITTED, rmb.RESOLVED, rmb.DESCRIPTION, rmb.RECEIPT, rmb.PAYMENT_ID, " +
            "rmb.AUTHOR_ID, rmb.RESOLVE_ID, rmb.STATUS_ID, rmb_TYPE_ID, rmbs.STATUS, rmbt.TYPE " +
            "FROM ERS_REIMBURSEMENTS rmb " +
            "JOIN ERS_REIMBURSEMENT_STATUSES rmbs " +
            "ON rmb.STATUS_ID = rmbs.STATUS_ID " +
            "JOIN JOIN ERS_REIMBURSEMENT_TYPES rmbt " +
            "ON rmb.TYPE_ID = rmbt.TYPE_ID";
    private UserDAO userDAO = new UserDAO();

    @Override
    public void save(Reimbursement newObject) {
        System.out.println(newObject +"  WHAT IS GOING ON");
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ERS_REIMBURSEMENTS VALUES(?, ?, " +
                    "TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS'), TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS'), ?, null," +
                    " ?, ?, ?, ?, ?)");

            pstmt.setString(1, newObject.getReimb_id());
            pstmt.setFloat(2, newObject.getAmount());
            pstmt.setString(3, newObject.getSubmitted());
            pstmt.setString(4, newObject.getResolved());
            pstmt.setString(5, newObject.getDescription());
            //pstmt.setString(6, newObject.getReceipt());
            pstmt.setString(6, newObject.getPayment_id());
            pstmt.setString(7, newObject.getAuthor_id().getUser_id());
            pstmt.setString(8, null);
            pstmt.setString(9, newObject.getStatus_id().getStatus_id());
            pstmt.setString(10, newObject.getType_id().getType_id());

            System.out.println(pstmt);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user to data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }


    public Reimbursement getById(User authUser, String id) {
        Reimbursement myNotes = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect +
                    "WHERE AUTHOR_ID = ? AND REIMB_ID = ?");
            pstmt.setString(1, authUser.getUser_id());
            pstmt.setString(2, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                myNotes = new Reimbursement();
                myNotes.setReimb_id(rs.getString("USER_ID"));
                myNotes.setAmount(rs.getFloat("AMOUNT"));
                myNotes.setSubmitted(rs.getString("SUBMITTED"));
                myNotes.setResolved(rs.getString("RESOLVED"));
                myNotes.setDescription(rs.getString("DESCRIPTION"));
                myNotes.setReceipt(rs.getString("RECEIPT"));
                myNotes.setPayment_id(rs.getString("PAYMENT_ID"));
                myNotes.setAuthor_id(userDAO.getById(rs.getString("AUTHOR_ID")));
                myNotes.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                myNotes.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                myNotes.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return myNotes;
    }

    // User/Manager access to only 1 ID
    public ArrayList<Reimbursement> getAllByUserID(String user_id) {
        ArrayList<Reimbursement> ReimbursementList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE AUTHOR_ID = ?");
            pstmt.setString(1, user_id);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                userReimbursement = new Reimbursement();
                userReimbursement.setReimb_id(rs.getString("USER_ID"));
                userReimbursement.setAmount(rs.getFloat("AMOUNT"));
                userReimbursement.setSubmitted(rs.getString("SUBMITTED"));
                userReimbursement.setResolved(rs.getString("RESOLVED"));
                userReimbursement.setDescription(rs.getString("DESCRIPTION"));
                userReimbursement.setReceipt(rs.getString("RECEIPT"));
                userReimbursement.setPayment_id(rs.getString("PAYMENT_ID"));
                userReimbursement.setAuthor_id(userDAO.getById(rs.getString("AUTHOR_ID")));
                userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                ReimbursementList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return ReimbursementList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    public ArrayList<Reimbursement> getAllByType(String type_id) {
        ArrayList<Reimbursement> ReimbursementList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE TYPE_ID = ?");
            pstmt.setString(1, type_id);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                userReimbursement = new Reimbursement();
                userReimbursement.setReimb_id(rs.getString("USER_ID"));
                userReimbursement.setAmount(rs.getFloat("AMOUNT"));
                userReimbursement.setSubmitted(rs.getString("SUBMITTED"));
                userReimbursement.setResolved(rs.getString("RESOLVED"));
                userReimbursement.setDescription(rs.getString("DESCRIPTION"));
                userReimbursement.setReceipt(rs.getString("RECEIPT"));
                userReimbursement.setPayment_id(rs.getString("PAYMENT_ID"));
                userReimbursement.setAuthor_id(userDAO.getById(rs.getString("AUTHOR_ID")));
                userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                ReimbursementList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return ReimbursementList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    public ArrayList<Reimbursement> getAllByStatus(String status_id) {
        ArrayList<Reimbursement> ReimbursementList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE STATUS_ID = ?");
            pstmt.setString(1, status_id);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                userReimbursement = new Reimbursement();
                userReimbursement.setReimb_id(rs.getString("USER_ID"));
                userReimbursement.setAmount(rs.getFloat("AMOUNT"));
                userReimbursement.setSubmitted(rs.getString("SUBMITTED"));
                userReimbursement.setResolved(rs.getString("RESOLVED"));
                userReimbursement.setDescription(rs.getString("DESCRIPTION"));
                userReimbursement.setReceipt(rs.getString("RECEIPT"));
                userReimbursement.setPayment_id(rs.getString("PAYMENT_ID"));
                userReimbursement.setAuthor_id(userDAO.getById(rs.getString("AUTHOR_ID")));
                userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                ReimbursementList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return ReimbursementList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }


    //Admin only access
    public ArrayList<Reimbursement> getAll() {
        ArrayList<Reimbursement> ReimbursementList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {


            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
            while(rs.next()) {
                userReimbursement = new Reimbursement();
                userReimbursement.setReimb_id(rs.getString("USER_ID"));
                userReimbursement.setAmount(rs.getFloat("AMOUNT"));
                userReimbursement.setSubmitted(rs.getString("SUBMITTED"));
                userReimbursement.setResolved(rs.getString("RESOLVED"));
                userReimbursement.setDescription(rs.getString("DESCRIPTION"));
                userReimbursement.setReceipt(rs.getString("RECEIPT"));
                userReimbursement.setPayment_id(rs.getString("PAYMENT_ID"));
                userReimbursement.setAuthor_id(userDAO.getById(rs.getString("AUTHOR_ID")));
                userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                ReimbursementList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return ReimbursementList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    // TODO: 2/25/2022
    @Override
    public void update(Reimbursement updatedObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ERS_REIMBURSEMENTS " +
                    "SET DESCRIPTION = ?, STATUS_ID = ? " +
                    "WHERE ID = ?");
            pstmt.setString(1, updatedObject.getDescription());
            pstmt.setString(2, updatedObject.getStatus_id().getStatus_id());
            pstmt.setString(3, updatedObject.getReimb_id());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Failed to update user data within datasource.");
            }
            conn.commit();
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public Reimbursement getById(String id) {
        return null;
    }
}
