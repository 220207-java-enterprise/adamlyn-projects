package com.revature.daos;

import com.revature.models.ReimbursementType;
import com.revature.models.ReimbursementType;
import com.revature.util.ConnectionFactory;
import com.revature.util.exceptions.DataSourceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReimbursementTypeDAO implements CrudDAO<ReimbursementType>{

    private final String rootCall = "SELECT * FROM ERS_REIMBURSEMENT_TYPES";

    @Override
    public void save(ReimbursementType newObject) {

    }

    @Override
    public ReimbursementType getById(String role_ID) {
        ReimbursementType myType = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootCall + " WHERE TYPE_ID = ?");
            pstmt.setString(1, role_ID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                myType = new ReimbursementType();
                myType.setType_id(rs.getString("TYPE_ID"));
                myType.setType(rs.getString("TYPE"));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return myType;
    }

    @Override
    public ArrayList<ReimbursementType> getAll() {
        ArrayList<ReimbursementType> types = new ArrayList<>();
        ReimbursementType myType = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootCall);
            while(rs.next()) {
                myType = new ReimbursementType();
                myType.setType_id(rs.getString("TYPE_ID"));
                myType.setType(rs.getString("TYPE"));

                types.add(myType);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return types;
    }

    @Override
    public void update(ReimbursementType updatedObject) {

    }

    @Override
    public void deleteById(String id) {

    }
}
