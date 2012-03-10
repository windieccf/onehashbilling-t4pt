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
 * 10 March 2012    Robin Foe	    2.0				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.service.plan;

@SuppressWarnings("serial")
public abstract class VoicePlan extends ServicePlan{

	private String sim;
	public String getSim() {return sim;}
	public void setSim(String sim) {this.sim = sim;}

	private String registeredPhoneNumber;
	public String getRegisteredPhoneNumber() {return registeredPhoneNumber;}
	public void setRegisteredPhoneNumber(String registeredPhoneNumber) {this.registeredPhoneNumber = registeredPhoneNumber;}
	
}
