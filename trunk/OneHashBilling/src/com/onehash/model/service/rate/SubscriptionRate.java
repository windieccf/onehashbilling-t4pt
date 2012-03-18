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
package com.onehash.model.service.rate;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class SubscriptionRate extends ServiceRate{

	@Override
	public BigDecimal calculateMonthlyRate() {
		if (this.isFreeCharge())
			return new BigDecimal(0);
		return this.getRatePrice();
	}

}
