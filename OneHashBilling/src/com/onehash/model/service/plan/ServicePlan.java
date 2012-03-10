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
