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

import java.util.ArrayList;

import com.onehash.constant.ConstantSummary;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.service.rate.ServiceRate;

@SuppressWarnings("serial")
public class MobileVoicePlan extends VoicePlan{

	public MobileVoicePlan() {
		ArrayList<ServiceRate> serviceRate = new ArrayList<ServiceRate>();
		this.setPlanName(ConstantSummary.MobileVoice);
		for(ServiceRate _serviceRate:OneHashDataCache.getInstance().getAvailableServiceRate()) {
			if (_serviceRate.getRateCode().equals(ServiceRate.SUBSCRPTION_MOBILE_VOICE)) {
				serviceRate.add(_serviceRate);
			}
			else if (_serviceRate.getRateCode().startsWith(ServiceRate.PREFIX_MOBILE_VOICE)) {
				serviceRate.add(_serviceRate);
			}
		}
	}

}
