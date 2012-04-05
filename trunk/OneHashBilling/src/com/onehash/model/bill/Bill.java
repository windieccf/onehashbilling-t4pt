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
 * 13 March 2012    Aman Sharma	    0.1				Class creating												
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.bill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public class Bill extends BaseEntity{
	
	private Date billDate;
	
	private BigDecimal currentBill;
	
	private BigDecimal totalBill;
	
	private BigDecimal carryForward;
	
	private BigDecimal gstRate;
	
	Map<String,List<BillSummary>> billSummaryMap = new HashMap<String,List<BillSummary>>();
	
	private List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
	
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public BigDecimal getCurrentBill() {
		return currentBill;
	}

	public void setCurrentBill(BigDecimal currentBill) {
		this.currentBill = currentBill;
	}

	public BigDecimal getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(BigDecimal totalBill) {
		this.totalBill = totalBill;
	}

	public BigDecimal getCarryForward() {
		return carryForward;
	}

	public void setCarryForward(BigDecimal carryForward) {
		this.carryForward = carryForward;
	}

	public BigDecimal getGstRate() {
		return gstRate;
	}

	public void setGstRate(BigDecimal gstRate) {
		this.gstRate = gstRate;
	}

	public Map<String, List<BillSummary>> getBillSummaryMap() {
		return billSummaryMap;
	}

	public void setBillSummaryMap(Map<String, List<BillSummary>> billSummaryMap) {
		this.billSummaryMap = billSummaryMap;
	}
	
	public List<PaymentDetail> getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public BigDecimal getTotalOutstanding() {
		return null;
	}
}
