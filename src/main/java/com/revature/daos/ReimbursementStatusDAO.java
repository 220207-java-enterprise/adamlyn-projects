package com.revature.daos;

import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.DataSourceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReimbursementStatusDAO implements CrudDAO<ReimbursementStatus> {

    private final String rootCall = "SELECT * FROM ERS_REIMBURSEMENT_STATUSES";

    @Override
    public void save(ReimbursementStatus newObject) {

    }

    @Override
    public ReimbursementStatus getById(String status_id) {
        ReimbursementStatus myStatus = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootCall + " WHERE STATUS_ID = ?");
            pstmt.setString(1, status_id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                myStatus = new ReimbursementStatus();
                myStatus.setStatus_id(rs.getString("STATUS_ID"));
                myStatus.setStatus(rs.getString("STATUS"));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return myStatus;
    }

    @Override
    public ArrayList<ReimbursementStatus> getAll() {
        ArrayList<ReimbursementStatus> allStatus = new ArrayList<>();
        ReimbursementStatus myStatus = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootCall);
            while(rs.next()) {
                myStatus = new ReimbursementStatus();
                myStatus.setStatus_id(rs.getString("STATUS_ID"));
                myStatus.setStatus(rs.getString("STATUS"));

                allStatus.add(myStatus);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return allStatus;
    }

    @Override
    public void update(ReimbursementStatus updatedObject) {

    }

    @Override
    public void deleteById(String id) {

    }
}
