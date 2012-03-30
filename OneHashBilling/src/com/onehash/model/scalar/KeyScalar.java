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
 * 15 March 2012    Robin Foe	    0.1				Initial creating
 * 													
 * 													
 * 
 */
package com.onehash.model.scalar;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KeyScalar implements Serializable{

	private long accountNumber = 0L;
	public long getNextAccountNumber(){return ++accountNumber;}
	
	private long complaintNumber = 0L;
	public long getNextComplaintNumber(){return ++complaintNumber;}
	
	private long userId = 0L;
	public long getNextUserID(){return ++userId;}
	
}
