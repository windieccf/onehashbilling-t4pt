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
 * 14 March 2012    Aman Sharma	    0.1				Class creating												
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.bill;

import java.math.BigDecimal;
import java.util.Date;

import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public class PaymentDetail extends BaseEntity {
	
	private Date paymentDate;
	private BigDecimal amount;

	public PaymentDetail(Date paymentDate, BigDecimal amount) {
		this.paymentDate = paymentDate;
		this.amount = amount;
	}
	
	public Date getPaymentDate() {
		return paymentDate;
	}
	
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
