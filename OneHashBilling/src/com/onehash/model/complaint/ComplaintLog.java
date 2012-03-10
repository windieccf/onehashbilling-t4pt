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
