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

import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public class BillDetail extends BaseEntity{
	
	private String palnName;
	
	private String rateName;
	
	private BigDecimal rate;
	
	private BigDecimal usageTime;
	
	private Long usageDuration;

	public String getPalnName() {
		return palnName;
	}

	public void setPalnName(String palnName) {
		this.palnName = palnName;
	}

	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getUsageTime() {
		return usageTime;
	}

	public void setUsageTime(BigDecimal usageTime) {
		this.usageTime = usageTime;
	}

	public Long getUsageDuration() {
		return usageDuration;
	}

	public void setUsageDuration(Long usageDuration) {
		this.usageDuration = usageDuration;
	}
}
