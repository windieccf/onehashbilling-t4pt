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
 * 13 March 2012    Yue Yang        0.2             Add attributes and Constructor
 * 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.customer;

import java.util.ArrayList;
import java.util.List;

import com.onehash.constant.ConstantStatus;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.model.bill.Bill;

@SuppressWarnings("serial")
public class Customer extends BaseEntity{
	
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	private String nric;
	public String getNric() {return nric;}
	public void setNric(String nric) {this.nric = nric;}
	
	private String address;
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}
	
	private String phoneNumber;
	public String getPhoneNumber() {return phoneNumber;}
	public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
	
	private String accountNumber;
	public String getAccountNumber() {return accountNumber;}
	public void setAccountNumber(String accountNumber) {this.accountNumber = accountNumber;}
	
	private String status = ConstantStatus.ACTIVE;
	public boolean isActivated() {return ConstantStatus.ACTIVE.equals(status);}
	public void setStatus(boolean status) {this.status = (status) ? ConstantStatus.ACTIVE : ConstantStatus.DEACTIVATE;}

	private List<ServicePlan> servicePlans = new ArrayList<ServicePlan>();
	public List<ServicePlan> getServicePlans() {return servicePlans;}
	public void setServicePlans(List<ServicePlan> servicePlans) {this.servicePlans = servicePlans;}
	public void addServicePlan(ServicePlan servicePlan){
		// INFO :: checking on duplication will be done at controller class.
		servicePlans.add(servicePlan);
	}
	
	private List<ComplaintLog> complaintLogs = new  ArrayList<ComplaintLog>();
	public List<ComplaintLog> getComplaintLogs() {return complaintLogs;}
	public void setComplaintLogs(List<ComplaintLog> complaintLogs) {this.complaintLogs = complaintLogs;}
	public void addComplaintLog(ComplaintLog complaintLog) {complaintLogs.add(complaintLog);}
	
	private List<Bill> bill = new ArrayList<Bill>();
	public List<Bill> getBill() {return bill;}
	public void setBill(List<Bill> bill) {this.bill = bill;}

	
	/************************************* UTILITY ***********************************************/
	public ServicePlan getServicePlan(String planCode){
		if(OneHashStringUtil.isEmpty(planCode)) return null;
		
		for(ServicePlan servicePlan : servicePlans){
			if(planCode.equals(servicePlan.getPlanCode()))
				return servicePlan;
		}
		
		return null;
		
	}
	
	public Customer(){}
	
    public Customer(String name,String nric,String address,String phoneNumber,String accountNumber){
       this.setName(name);
       this.setNric(nric);
       this.setAddress(address);
       this.setPhoneNumber(phoneNumber);
       this.setAccountNumber(accountNumber);
       this.setStatus(true);
    }

}
