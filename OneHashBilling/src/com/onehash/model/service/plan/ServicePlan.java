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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.onehash.model.base.BaseEntity;
import com.onehash.model.bill.BillDetail;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.model.usage.MonthlyUsage;
import com.onehash.utility.OneHashDateUtil;

@SuppressWarnings("serial")
public abstract class ServicePlan extends BaseEntity {

	private String planId;
	public String getPlanId() {return planId;}
	public void setPlanId(String planId) {this.planId = planId;}
	
	private String planCode;
	public String getPlanCode() {return planCode;}
	public void setPlanCode(String planCode) {this.planCode = planCode;}
	
	private Boolean deletedStatus = false;
	public Boolean getDeletedStatus() {return deletedStatus;}
	public void setDeletedStatus(Boolean deletedStatus) {
		this.deletedStatus = deletedStatus;
		Calendar calendar = Calendar.getInstance();
		this.setEndDate(calendar.getTime());
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
	
	private List<ServiceRate> serviceRates;
	public List<ServiceRate> getServiceRates() {return serviceRates;}
	public void setServiceRates(List<ServiceRate> serviceRates) {this.serviceRates = serviceRates;}
	
	private List<MonthlyUsage> monthlyUsage = new ArrayList<MonthlyUsage>();
	public List<MonthlyUsage> getMonthlyUsages() {return monthlyUsage;}
	public void setMonthlyUsages(List<MonthlyUsage> monthlyUsage) {this.monthlyUsage = monthlyUsage;}
	
	public List<BillDetail> calculateBill() {
		ArrayList<BillDetail> billDetails = new ArrayList<BillDetail>();
		
		BillDetail billDetail;
		for (ServiceRate serviceRate:this.getServiceRates()) {
			billDetail = new BillDetail();
			billDetail.setPlanName(this.getPlanName());
			billDetail.setRateName(serviceRate.getRateDescription());
			if (serviceRate.isFreeCharge()) {
				billDetail.setRate(new BigDecimal(0));
				continue;
			}
			if (serviceRate instanceof SubscriptionRate) {
				billDetail.setRate(serviceRate.getRatePrice());
			}
			else {
				BigDecimal usage = new BigDecimal(0);
				for(MonthlyUsage _monthlyUsage:monthlyUsage) {
					if (OneHashDateUtil.checkMonthYear(_monthlyUsage.getUsageYearMonth(), this.getStartDate())) {
						usage = new BigDecimal(_monthlyUsage.getCallUsages(serviceRate.getRateCode()));
						break;
					}
				}
				BigDecimal charge = serviceRate.getRatePrice().multiply(usage);
				billDetail.setRate(charge);
			}
		}
		return billDetails;
	}

}
