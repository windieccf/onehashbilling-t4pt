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
 * 17 March 2012	Kenny Hartono	0.2				Adding static variable, getRateUnitFromString() 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.service.rate;

import java.math.BigDecimal;

import com.onehash.exception.BusinessLogicException;

@SuppressWarnings("serial")
public class UsageRate extends ServiceRate {
	public static int perSecond = 1;
	public static int perMinute = 2;
	public static int perDay = 3;
	public static int perMonth = 4;

	private int rateUnit;
	public static int getRateUnitFromString(String rateUnit) throws Exception {
		if (rateUnit.equals("Second"))
			return UsageRate.perSecond;
		else if (rateUnit.equals("Minute"))
			return UsageRate.perMinute;
		else if (rateUnit.equals("Day"))
			return UsageRate.perDay;
		else if (rateUnit.equals("Month"))
			return UsageRate.perMonth;
		else
			throw new BusinessLogicException("rateUnit: '"+rateUnit+"' is unknown.");
	}
	public long getRateUnit() {return rateUnit;}
	public void setRateUnit(int rateUnit) {this.rateUnit = rateUnit;}

	public UsageRate(int rateUnit) {
		this.setRateUnit(rateUnit);
	}
	
	@Override
	public BigDecimal calculateMonthlyRate() {
		return null;
	}

}
