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
 * 18 March 2012	Kenny Hartono	0.2				Added status, calculateBill
 * 31 March 2012	Yue Yang		0.3				Bug Fixing for the calculateBill
 * 
 */
package com.onehash.model.service.plan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.onehash.constant.ConstantStatus;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.usage.MonthlyUsage;

@SuppressWarnings("serial")
public abstract class ServicePlan extends BaseEntity {

	private String planId;
	public String getPlanId() {return planId;}
	public void setPlanId(String planId) {this.planId = planId;}
	
	private String planCode;
	public String getPlanCode() {return planCode;}
	public void setPlanCode(String planCode) {this.planCode = planCode;}
	
	public String status = "";
	public String getStatus() {return status;}
	public void setStatus(String status) {
		this.status=status;
		if (status.equals(ConstantStatus.SERVICEPLAN_DELETED)) {
			this.setEndDate(Calendar.getInstance().getTime());
			this.setDeletedDate(Calendar.getInstance().getTime());
		}
	}
	
	private String planName;
	public String getPlanName() {return planName;}
	public void setPlanName(String planName) {this.planName = planName;}
	
	private Date startDate = new Date();
	public Date getStartDate() {return startDate;}
	public void setStartDate(Date startDate) {this.startDate = startDate;}
	
	private Date endDate = new Date();
	public Date getEndDate() {return endDate;}
	public void setEndDate(Date endDate) {this.endDate = endDate;}
	
	private Date deletedDate = new Date();
	public Date getDeletedDate() {return deletedDate;}
	public void setDeletedDate(Date deletedDate) {this.deletedDate = deletedDate;}
	
	private List<ServiceRate> serviceRates = new ArrayList<ServiceRate>();
	public List<ServiceRate> getServiceRates() {return serviceRates;}
	public void setServiceRates(List<ServiceRate> serviceRates) {this.serviceRates = serviceRates;}
	
	private List<MonthlyUsage> monthlyUsage = new ArrayList<MonthlyUsage>();
	public List<MonthlyUsage> getMonthlyUsages() {return monthlyUsage;}
	public void setMonthlyUsages(List<MonthlyUsage> monthlyUsage) {this.monthlyUsage = monthlyUsage;}
}
