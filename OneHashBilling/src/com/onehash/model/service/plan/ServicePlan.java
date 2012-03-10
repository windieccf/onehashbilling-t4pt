package com.onehash.model.service.plan;

import java.util.Date;
import java.util.List;

import com.onehash.model.base.BaseEntity;
import com.onehash.model.service.rate.ServiceRate;

@SuppressWarnings("serial")
public abstract class ServicePlan extends BaseEntity {

	private String planId;
	public String getPlanId() {return planId;}
	public void setPlanId(String planId) {this.planId = planId;}
	
	private String planCode;
	public String getPlanCode() {return planCode;}
	public void setPlanCode(String planCode) {this.planCode = planCode;}
	
	private String planName;
	public String getPlanName() {return planName;}
	public void setPlanName(String planName) {this.planName = planName;}
	
	private Date startDate;
	public Date getStartDate() {return startDate;}
	public void setStartDate(Date startDate) {this.startDate = startDate;}
	
	private Date endDate;
	public Date getEndDate() {return endDate;}
	public void setEndDate(Date endDate) {this.endDate = endDate;}
	
	private List<ServiceRate> serviceRates;
	public List<ServiceRate> getServiceRates() {return serviceRates;}
	public void setServiceRates(List<ServiceRate> serviceRates) {this.serviceRates = serviceRates;}
	
	public abstract void calculateBill();

}
