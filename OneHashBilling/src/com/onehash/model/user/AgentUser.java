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
 * 14 March 2012    Mansoor M I	    0.1				Class creating												
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.user;

import java.util.ArrayList;
import java.util.List;

public class AgentUser extends User {

	private static ArrayList<String> lstMenu = new ArrayList<String>();
	
	public AgentUser () {
		lstMenu.add("Add Complaint");
		lstMenu.add("Update Complaint");
		lstMenu.add("View Customer");
	}
	
	@Override
	public List<String> getAvailableMenu() {
		// TODO Auto-generated method stub
		return lstMenu;
	}

}
