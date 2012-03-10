package com.onehash.model.customer;

import java.util.ArrayList;
import java.util.List;

import com.onehash.constant.ConstantStatus;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.utility.OneHashStringUtil;

@SuppressWarnings("serial")
public class Customer extends BaseEntity{

	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	private String nric;
	public String getNric() {return nric;}
	public void setNric(String nric) {this.nric = nric;}
	
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
	/************************************* UTILITY ***********************************************/
	public ServicePlan getServicePlan(String planCode){
		if(OneHashStringUtil.isEmpty(planCode)) return null;
		
		for(ServicePlan servicePlan : servicePlans){
			if(planCode.equals(servicePlan.getPlanCode()))
				return servicePlan;
		}
		
		return null;
		
	}

}
