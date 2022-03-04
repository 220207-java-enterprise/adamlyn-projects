package com.revature.daos;

import com.revature.models.*;
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
            "rmb.AUTHOR_ID, rmb.RESOLVER_ID, rmb.STATUS_ID, rmb.TYPE_ID, rmbs.STATUS, rmbt.TYPE " +
            "FROM ERS_REIMBURSEMENTS rmb " +
            "JOIN ERS_REIMBURSEMENT_STATUSES rmbs " +
            "ON rmb.STATUS_ID = rmbs.STATUS_ID " +
            "JOIN ERS_REIMBURSEMENT_TYPES rmbt " +
            "ON rmb.TYPE_ID = rmbt.TYPE_ID";
    private UserDAO userDAO = new UserDAO();

    @Override
    public void save(Reimbursement newObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ERS_REIMBURSEMENTS VALUES(?, ?, " +
                    "TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, null," +
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

    // User get all by status
    public ArrayList<Reimbursement> userGetAllByIDStatus(String id, String status_id) {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect +
                    " WHERE AUTHOR_ID = ? AND rmb.STATUS_ID = ?");
            pstmt.setString(1, id);
            pstmt.setString(2, status_id);

            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    // User get all by status
    public ArrayList<Reimbursement> userGetPending(String id) {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect +
                    " WHERE AUTHOR_ID = ? AND rmb.STATUS_ID = '1' ");
            pstmt.setString(1, id);
            //pstmt.setString(2, status_id);

            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    public ArrayList<Reimbursement> userGetAllByIDType(String id, String type_id) {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect +
                    " WHERE AUTHOR_ID = ? AND rmb.TYPE_ID = ?");
            pstmt.setString(1, id);
            pstmt.setString(2, type_id);

            ResultSet rs = pstmt.executeQuery();
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    // User/Manager access to only 1 ID
    public ArrayList<Reimbursement> getAllByUserID(String user_id) {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE AUTHOR_ID = ?");
            pstmt.setString(1, user_id);

            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    public ArrayList<Reimbursement> getAllByType(String type_id) {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE rmb.TYPE_ID = ?");
            pstmt.setString(1, type_id);

            ResultSet rs = pstmt.executeQuery();
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //reimbList.toArray(new Reimbursement[0]);
    }

    public ArrayList<Reimbursement> getAllByStatus(String status_id) {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE rmb.STATUS_ID = ?");
            pstmt.setString(1, status_id);

            ResultSet rs = pstmt.executeQuery();
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    public ArrayList<Reimbursement> getAllByHistory(String status_id) {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE RESOLVER_ID = ?");
            pstmt.setString(1, status_id);

            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //reimbList.toArray(new Reimbursement[0]);
    }


    //Admin only access
    public ArrayList<Reimbursement> getAll() {
        ArrayList<Reimbursement> reimbList = new ArrayList<>();
        Reimbursement userReimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {


            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
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
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));

                reimbList.add(userReimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbList;       //ReimbursementList.toArray(new Reimbursement[0]);
    }

    @Override
    public void update(Reimbursement updatedObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);

//            "INSERT INTO ERS_REIMBURSEMENTS VALUES(?, ?, " +
//                    "TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS'), TO_TIMESTAMP(?, 'DD-MM-YYYY HH24:MI:SS'), ?, null," +
//                    " ?, ?, ?, ?, ?)"
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ERS_REIMBURSEMENTS " +
                    "SET AMOUNT = ?, DESCRIPTION = ?, STATUS_ID = ?, TYPE_ID = ? , RESOLVER_ID = ?, " +
                    "RESOLVED = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), " +
                    "SUBMITTED = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') " +
                    "WHERE REIMB_ID = ?");
            pstmt.setFloat(1, updatedObject.getAmount());
            pstmt.setString(2, updatedObject.getDescription());
            pstmt.setString(3, updatedObject.getStatus_id().getStatus_id());
            pstmt.setString(4, updatedObject.getType_id().getType_id());


            if (updatedObject.getResolver_id() != null)
                pstmt.setString(5, updatedObject.getResolver_id().getUser_id());
            else
                pstmt.setString(5, null);

            pstmt.setString(6, updatedObject.getResolved());
            pstmt.setString(7, updatedObject.getSubmitted());
            pstmt.setString(8, updatedObject.getReimb_id());

            System.out.println(pstmt);
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
        Reimbursement userReimbursement = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootSelect + " WHERE REIMB_ID = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userReimbursement = new Reimbursement();
                userReimbursement.setReimb_id(rs.getString("REIMB_ID"));
                userReimbursement.setAmount(rs.getFloat("AMOUNT"));
                userReimbursement.setSubmitted(rs.getString("SUBMITTED"));
                userReimbursement.setResolved(rs.getString("RESOLVED"));
                userReimbursement.setDescription(rs.getString("DESCRIPTION"));
                userReimbursement.setReceipt(rs.getString("RECEIPT"));
                userReimbursement.setPayment_id(rs.getString("PAYMENT_ID"));
                userReimbursement.setAuthor_id(userDAO.getById(rs.getString("AUTHOR_ID")));
                if (rs.getString("RESOLVER_ID") != null)
                    userReimbursement.setResolver_id(userDAO.getById(rs.getString("RESOLVER_ID")));
                userReimbursement.setStatus_id(new ReimbursementStatus(rs.getString("STATUS_ID"),
                        rs.getString("STATUS")));
                userReimbursement.setType_id(new ReimbursementType(rs.getString("TYPE_ID"),
                        rs.getString("TYPE")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return userReimbursement;
    }
}
