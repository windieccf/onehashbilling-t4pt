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
public class BillSummary extends BaseEntity{
	
	private String description;
	
	private BigDecimal total;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	/************************************* UTILITY ***********************************************/
	public BillSummary(String description, BigDecimal total){
		this.setDescription(description);
		this.setTotal(total);
	}
}
