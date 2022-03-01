package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.dtos.requests.NewReimbursementRequest;
import com.revature.dtos.responses.ReimbursementResponse;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO;


    // Any new reimbursement
    public Reimbursement newReimbursement(NewReimbursementRequest reimbursementRequest) throws IOException{
        Reimbursement newRmb = reimbursementRequest.extractReimbursement();

        newRmb.setAmount(convertAmount(newRmb.getAmount()));
        newRmb.setReimb_id(UUID.randomUUID().toString());
        newRmb.setType_id(new ReimbursementType("7c3521f5-ff75-4e8a-9913-01d15ee4dc97", "LODGING"));
        newRmb.setStatus_id(new ReimbursementStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc98", "PENDING"));
        reimbursementDAO.save(newRmb);
        return newRmb;
    }
    //User get all method
    public List<ReimbursementResponse> getAllUserReimbursements(User authUser){
        List<Reimbursement> rmb = reimbursementDAO.getAll(authUser.getUser_id());
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    //Manager only get ALL
    public List<ReimbursementResponse> getAllReimbursements( ){
        List<Reimbursement> rmb = reimbursementDAO.getAll();
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    // Manager get by userID
    public List<ReimbursementResponse> getUsersReimbursements(String id){
        List<Reimbursement> rmb = reimbursementDAO.getAll(id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    // User get single reimbursement
    public Reimbursement getReimbursementByID(String id){
        return reimbursementDAO.getById(id);
    }

    //Manager Approve/Deny
    public void Approve(User authUser){
    }

    //User Update
    public float convertAmount(float number) {
        return Float.parseFloat(String.format("%6.2f", number));
    }


}
