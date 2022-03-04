package com.revature.services;

import com.revature.daos.*;
import com.revature.dtos.requests.NewReimbRequest;
import com.revature.dtos.requests.ReimbUpdateRequest;
import com.revature.dtos.responses.ReimbursementResponse;
import com.revature.models.*;
import com.revature.util.exceptions.InvalidRequestException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO;
    private ReimbursementTypeDAO reimbursementTypeDAO;
    private ReimbursementStatusDAO reimbursementStatusDAO;
    private UserDAO userDAO;


    String timeStamp = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss").format(new Date());

    public ReimbursementService(ReimbursementDAO reimbursementDAO, ReimbursementTypeDAO reimbursementTypeDAO,
                                ReimbursementStatusDAO reimbursementStatusDAO, UserDAO userDAO){

        this.reimbursementDAO = reimbursementDAO;
        this.reimbursementTypeDAO = reimbursementTypeDAO;
        this.reimbursementStatusDAO = reimbursementStatusDAO;
        this.userDAO = userDAO;
    }
    // Any new reimbursement
    public Reimbursement newReimbursement(NewReimbRequest reimbRequest) throws IOException{
        Reimbursement newRmb = reimbRequest.extractReimb();
        User author = userDAO.getById(reimbRequest.getAuthor_id());

        //newRmb.setAmount(convertAmount(newRmb.getAmount()));
        newRmb.setReimb_id(UUID.randomUUID().toString());
        newRmb.setSubmitted(new SimpleDateFormat("yyyy-MM-dd KK:mm:ss").format(new Date()));
        newRmb.setAuthor_id(author);


        if(reimbRequest.getType_id() == null)
            reimbRequest.setType_id("4");                                               // defaults to other
        ReimbursementType myType = reimbursementTypeDAO.getById(reimbRequest.getType_id());
        newRmb.setType_id(myType);
        ReimbursementStatus myStatus = reimbursementStatusDAO.getById("1");     // Pending
        newRmb.setStatus_id(myStatus);

        reimbursementDAO.save(newRmb);
        return newRmb;
    }

    //Manager only get ALL
    public List<ReimbursementResponse> getAllReimbs( ){
        List<Reimbursement> rmb = reimbursementDAO.getAll();
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    // Manager get by userID
    public List<ReimbursementResponse> getUserReimbs(String id){
        List<Reimbursement> rmb = reimbursementDAO.getAllByUserID(id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        System.out.println("lol2");
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        System.out.println(rmbResponses);
        return rmbResponses;
    }

    public List<ReimbursementResponse> getUserReimbsByStatus(String id, String status_id){
        List<Reimbursement> rmb = reimbursementDAO.userGetAllByIDStatus(id, status_id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        System.out.println(rmbResponses);
        return rmbResponses;
    }

    public List<ReimbursementResponse> getUserReimbsByType(String id, String type_id){
        System.out.println(id + " " +type_id);
        List<Reimbursement> rmb = reimbursementDAO.userGetAllByIDType(id, type_id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        System.out.println("lol1");
        for (Reimbursement thisrmb : rmb){
            System.out.println(thisrmb);
            rmbResponses.add(new ReimbursementResponse(thisrmb));
            System.out.println(rmbResponses);
        }
        System.out.println(rmbResponses);
        return rmbResponses;
    }

    // User get single reimbursement
    public Reimbursement getReimbsByID(String id){
        return reimbursementDAO.getById(id);
    }

    // User get reimbursements by status
    public List<ReimbursementResponse> getReimbsByStatus(String id){
        List<Reimbursement> rmb = reimbursementDAO.getAllByStatus(id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }
    //Manager get all history
    public List<ReimbursementResponse> getReimbsByHistory(String id){
        List<Reimbursement> rmb = reimbursementDAO.getAllByHistory(id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    //User get their pending reimbs
    public List<ReimbursementResponse> getPendingReimbs(String id){
        List<Reimbursement> rmb = reimbursementDAO.userGetPending(id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb){
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    // User get reimbursements by type
    public List<ReimbursementResponse> getReimbByType(String id) {
        List<Reimbursement> rmb = reimbursementDAO.getAllByType(id);
        List<ReimbursementResponse> rmbResponses = new ArrayList<>();
        for (Reimbursement thisrmb : rmb) {
            rmbResponses.add(new ReimbursementResponse(thisrmb));
        }
        return rmbResponses;
    }

    //Manager Approve/Deny, user edit
    public void updateReimb(ReimbUpdateRequest reimbUpdate){

        Reimbursement newReimb = reimbursementDAO.getById(reimbUpdate.getReimb_id());
        System.out.println(newReimb);
        ReimbursementStatus newStatus = reimbursementStatusDAO.getById(reimbUpdate.getStatus_id());
        System.out.println(newStatus);
        if (newReimb.getStatus_id().getStatus().equals("APPROVED") ||
            newReimb.getStatus_id().getStatus().equals("DENIED")) {
            System.out.println("hiya");
            throw new InvalidRequestException("Cannot edit resolved ticket");
        }
        System.out.println("hi");
        if (!newStatus.getStatus().equals("PENDING") && reimbUpdate.getAuthor_id() != null) {
            System.out.println("hiuyuyyu");
            throw new InvalidRequestException("User cannot update resolved ticket");
        }
        ReimbursementType myType = reimbursementTypeDAO.getById(reimbUpdate.getType_id());
        ReimbursementStatus myStatus = reimbursementStatusDAO.getById(reimbUpdate.getStatus_id());
       // User myAuthor = userDAO.getById(reimbUpdate.getAuthor_id());
        User myResolver = userDAO.getById(reimbUpdate.getResolver_id());

        System.out.println(newReimb);
        System.out.println(reimbUpdate);
        if(newReimb.getAuthor_id().getUser_id().equals(reimbUpdate.getResolver_id()))
            throw new InvalidRequestException("Cannot look/approve own Reimbursement");

        //Check for any updates then prepare User to be updated
        if(reimbUpdate.getAmount() != null)
            newReimb.setAmount(reimbUpdate.getAmount());
        if(reimbUpdate.getSubmitted() == null && reimbUpdate.getAuthor_id()!= null) {
            newReimb.setSubmitted(reimbUpdate.getSubmitted());
            newReimb.setSubmitted(new SimpleDateFormat("yyyy-MM-dd KK:mm:ss").format(new Date()));
        }
        if(reimbUpdate.getDescription() != null)
            newReimb.setDescription(reimbUpdate.getDescription());
        if(reimbUpdate.getReceipt() != null)
            newReimb.setReceipt(reimbUpdate.getReceipt());

        if(reimbUpdate.getResolver_id() != null) {
            newReimb.setResolver_id(myResolver);
            newReimb.setResolved(new SimpleDateFormat("yyyy-MM-dd KK:mm:ss").format(new Date()));
        }

        if(reimbUpdate.getType_id() != null )
            newReimb.setType_id(myType);
        if(reimbUpdate.getStatus_id() != null)
            newReimb.setStatus_id(myStatus);

        System.out.println(newReimb);
        reimbursementDAO.update(newReimb);
    }

    //Entered amounts should be formatted to 6.2 spaces
    public float convertAmount(float number) {
        return Float.parseFloat(String.format("%6.2f", number));
    }


}
