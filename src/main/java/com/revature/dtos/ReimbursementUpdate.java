package com.revature.dtos;

import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;

public class ReimbursementUpdate {
    private String reimb_id;
    private String resolved;
    private String description;
    private String receipt;
    private String payment_id;
    private String author_id;
    private String resolver_id;
    private ReimbursementStatus status_id;
    private ReimbursementType type_id;

}
