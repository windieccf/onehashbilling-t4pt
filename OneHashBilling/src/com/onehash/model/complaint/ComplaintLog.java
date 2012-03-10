/*
 * CONFIDENTIAL AND PROPRIETARY SOURCE CODE OF
 * Institute of Systems Science, National University of Singapore
 *
 * Copyright 2012 Team 4(Part-Time), ISS, NUS, Singapore. All rights reserved.
 * Use of this source code is subjected to the terms of the applicable license
 * agreement.
 *
 * -----------------------------------------------------------------
 * REVISION HISTORY
 * -----------------------------------------------------------------
 * DATE             AUTHOR          REVISION		DESCRIPTION
 * 10 March 2012    Robin Foe	    0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.complaint;

import java.util.Date;

import com.onehash.enumeration.EnumComplaint;
import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public class ComplaintLog extends BaseEntity{

	private String issueNo;
	public String getIssueNo() {return issueNo;}
	public void setIssueNo(String issueNo) {this.issueNo = issueNo;}
	
	private String issueDescription;
	public String getIssueDescription() {return issueDescription;}
	public void setIssueDescription(String issueDescription) {this.issueDescription = issueDescription;}
	
	private Date complaintDate;
	public Date getComplaintDate() {return complaintDate;}
	public void setComplaintDate(Date complaintDate) {this.complaintDate = complaintDate;}
	
	private Date closedDate;
	public Date getClosedDate() {return closedDate;}
	public void setClosedDate(Date closedDate) {this.closedDate = closedDate;}
	
	private EnumComplaint status;
	public EnumComplaint getStatus() {return status;}
	public void setStatus(String status) {this.status = EnumComplaint.getEnumByStatus(status);}
	
	private String followUp;
	public String getFollowUp() {return followUp;}
	public void setFollowUp(String followUp) {this.followUp = followUp;}
	
	
}
