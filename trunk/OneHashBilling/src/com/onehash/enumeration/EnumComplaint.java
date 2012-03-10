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
package com.onehash.enumeration;

public enum EnumComplaint {
	STS_ACTIVE("A","Active"),
	STS_CLOSED("C", "Closed"),
	STS_PENDING("P","Pending"),
	STS_FOLLOW_UP("F","Follow up");
	
	private String label;
	public String getLabel(){return this.label;}
	
	private String status;
	public String getStatus(){return this.status;}

	private EnumComplaint(String status, String label){
		this.status = status;
		this.label = label;
	}
	
	public static EnumComplaint getEnumByStatus(String status){
		for(EnumComplaint enumComplaint : EnumComplaint.values()){
			if(enumComplaint.getStatus().equalsIgnoreCase(status))
				return enumComplaint;
		}
		return null;
		
	}
	
}
