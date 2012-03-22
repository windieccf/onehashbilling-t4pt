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

public class AdminUser extends User {

	private static ArrayList<String> lstMenu = new ArrayList<String>();
	
	public AdminUser(String userId, String firstName, String lastName,
			String password, String status) {
		super(userId, firstName, lastName, password, "Admin", status);
		
		//System.out.println(lstMenu.isEmpty());
		// TODO Auto-generated constructor stub
		if (lstMenu.isEmpty()) {
			lstMenu.add("Add Customer");
			lstMenu.add("Update Customer");
			lstMenu.add("Delete Customer");
			lstMenu.add("Add Subscription Plan");
			lstMenu.add("Update Subscription Plan");
			lstMenu.add("Delete Subscription Plan");
			lstMenu.add("Add Subscription Plan For Customer");
			lstMenu.add("Update Subscription Plan For Customer");
			lstMenu.add("Delete Subscription Plan For Customer");
			lstMenu.add("Add Complaint");
			lstMenu.add("Update Complaint");
		}
	}

	@Override
	public List<String> getAvailableMenu() {
		// TODO Auto-generated method stub
		return lstMenu;
	}

}
