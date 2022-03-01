package com.revature.daos;

import com.revature.dtos.responses.UserResponse;
import com.revature.models.*;
import com.revature.models.UserRole;
import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.DataSourceException;
import com.revature.util.exceptions.ResourcePersistenceException;

import java.sql.*;
import java.util.ArrayList;



public class UserDAO implements CrudDAO<User> {

    // Inner join for ALL information
    private final String rootCall = "SELECT " +
            "US.USER_ID, US.USERNAME, US.EMAIL, US.PASSWORD, US.GIVEN_NAME, " +
            "US.SURNAME, US.IS_ACTIVE, US.ROLE_ID, URS.ROLE " +
            "FROM ERS_USERS US " +
            "JOIN ERS_USER_ROLES URS " +
            "ON US.ROLE_ID = URS.ROLE_ID ";

    @Override
    public void save(User newObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ERS_USERS VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newObject.getUser_id());
            pstmt.setString(2, newObject.getUsername());
            pstmt.setString(3, newObject.getEmail());
            pstmt.setString(4, newObject.getPassword());
            pstmt.setString(5, newObject.getGiven_name());
            pstmt.setString(6, newObject.getSurname());
            pstmt.setBoolean(7, newObject.isActive());
            pstmt.setString(8, newObject.getRole().getRole_id());

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

    @Override
    public User getById(String id) {
        User myUser = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootCall + " WHERE USER_ID = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                myUser = new User();
                myUser.setUser_id(rs.getString("USER_ID"));
                myUser.setGiven_name(rs.getString("GIVEN_NAME"));
                myUser.setSurname(rs.getString("SURNAME"));
                myUser.setEmail(rs.getString("EMAIL"));
                myUser.setUsername(rs.getString("USERNAME"));
                myUser.setPassword(rs.getString("PASSWORD"));
                myUser.setActive(rs.getBoolean("IS_ACTIVE"));
                myUser.setRole(new UserRole(rs.getString("ROLE_ID"), rs.getString("ROLE")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return myUser;
    }



    public User getByUsername(String username) {
        User myUser = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootCall + "WHERE USERNAME = ?");
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                myUser = new User();
                myUser.setUser_id(rs.getString("USER_ID"));
                myUser.setGiven_name(rs.getString("GIVEN_NAME"));
                myUser.setSurname(rs.getString("SURNAME"));
                myUser.setEmail(rs.getString("EMAIL"));
                myUser.setUsername(rs.getString("USERNAME"));
                myUser.setPassword(rs.getString("PASSWORD"));
                myUser.setActive(rs.getBoolean("IS_ACTIVE"));
                myUser.setRole(new UserRole(rs.getString("ROLE_ID"), rs.getString("ROLE")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return myUser;
    }


    public User getByEmail(String id) {
        User myUser = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootCall + " WHERE EMAIL = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                myUser = new User();
                myUser.setUser_id(rs.getString("USER_ID"));
                myUser.setGiven_name(rs.getString("GIVEN_NAME"));
                myUser.setSurname(rs.getString("SURNAME"));
                myUser.setEmail(rs.getString("EMAIL"));
                myUser.setUsername(rs.getString("USERNAME"));
                myUser.setPassword(rs.getString("PASSWORD"));
                myUser.setActive(rs.getBoolean("IS_ACTIVE"));
                myUser.setRole(new UserRole(rs.getString("ROLE_ID"), rs.getString("ROLE")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return myUser;
    }

    public User getByUsernameandPassword(String username, String password ) {
        User myUser = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootCall + " WHERE USERNAME = ? and PASSWORD = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                myUser = new User();
                myUser.setUser_id(rs.getString("USER_ID"));
                myUser.setGiven_name(rs.getString("GIVEN_NAME"));
                myUser.setSurname(rs.getString("SURNAME"));
                myUser.setEmail(rs.getString("EMAIL"));
                myUser.setUsername(rs.getString("USERNAME"));
                myUser.setPassword(rs.getString("PASSWORD"));
                myUser.setActive(rs.getBoolean("IS_ACTIVE"));
                myUser.setRole(new UserRole(rs.getString("ROLE_ID"), rs.getString("ROLE")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return myUser;
    }


    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> selectedUsers = new ArrayList<User>();
        User myUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootCall);
            while(rs.next()) {
                myUser = new User();
                myUser.setUser_id(rs.getString("USER_ID"));
                myUser.setGiven_name(rs.getString("GIVEN_NAME"));
                myUser.setSurname(rs.getString("SURNAME"));
                myUser.setEmail(rs.getString("EMAIL"));
                myUser.setUsername(rs.getString("USERNAME"));
                myUser.setPassword(rs.getString("PASSWORD"));
                myUser.setActive(rs.getBoolean("IS_ACTIVE"));
                myUser.setRole(new UserRole(rs.getString("ROLE_ID"), rs.getString("ROLE")));

                selectedUsers.add(myUser);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return selectedUsers;
    }

    public ArrayList<User> getAllByActive(UserResponse active) {
        ArrayList<User> selectedUsers = new ArrayList<User>();
        User myUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootCall + " WHERE IS_ACTIVE = ?");
            pstmt.setBoolean(1, active.isActive());

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                myUser = new User();
                myUser.setUser_id(rs.getString("USER_ID"));
                myUser.setGiven_name(rs.getString("GIVEN_NAME"));
                myUser.setSurname(rs.getString("SURNAME"));
                myUser.setEmail(rs.getString("EMAIL"));
                myUser.setUsername(rs.getString("USERNAME"));
                myUser.setPassword(rs.getString("PASSWORD"));
                myUser.setActive(rs.getBoolean("IS_ACTIVE"));
                myUser.setRole(new UserRole(rs.getString("ROLE_ID"), rs.getString("ROLE")));

                selectedUsers.add(myUser);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return selectedUsers;
    }

    @Override
    public void update(User updatedObject) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ERS_USERS " +
                    "SET GIVEN_NAME = ?, " +
                    "SURNAME = ?, " +
                    "EMAIL = ?, " +
                    "USERNAME = ?, " +
                    "PASSWORD = ?, " +
                    "IS_ACTIVE = ?, " +
                    "ROLE_ID = ? " +
                    "WHERE USER_ID = ?");
            pstmt.setString(1, updatedObject.getGiven_name());
            pstmt.setString(2, updatedObject.getSurname());
            pstmt.setString(3, updatedObject.getEmail());
            pstmt.setString(4, updatedObject.getUsername());
            pstmt.setString(5, updatedObject.getPassword());
            pstmt.setBoolean(6, updatedObject.isActive());
            pstmt.setString(7, updatedObject.getRole().getRole_id());
            pstmt.setString(8, updatedObject.getUser_id());
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Failed to update user data within datasource.");
            }
            conn.commit();
        } catch (SQLException e) {
            System.out.println();
            throw new DataSourceException(e);
        }
    }

    //Setting to inactive instead of deleting
    @Override
    public void deleteById(String user_id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ERS_USERS " +
                    "SET IS_ACTIVE = FALSE" +
                    "WHERE USER_ID = ?");
            pstmt.setString(1, user_id);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
}
