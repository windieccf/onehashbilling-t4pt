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
package com.onehash.model.usage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onehash.model.base.BaseEntity;
import com.onehash.model.customer.Customer;

@SuppressWarnings("serial")
public abstract class TalkTimeUsage extends BaseEntity{
	
	private Date callTime;
	
	private Long usageDuration;
	
	private String callNumber;
	
	private String usageType;

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public Long getUsageDuration() {
		return usageDuration;
	}

	public void setUsageDuration(Long usageDuration) {
		this.usageDuration = usageDuration;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}
}
