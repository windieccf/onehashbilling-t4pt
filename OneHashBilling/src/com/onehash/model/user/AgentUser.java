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


import com.onehash.model.scalar.MenuScalar;
import com.onehash.view.panel.complaint.ComplaintListPanel;
import com.onehash.view.panel.customer.CustomerListPanel;

@SuppressWarnings("serial")
public class AgentUser extends User {
	
	@Override
	public MenuScalar getAvailableMenu() {
		
		MenuScalar rootMenu = MenuScalar.createParentMenu("One Hash Billing");
		
		/******************* CUSTOMER MENU ***********************/
		MenuScalar customerParentMenu = MenuScalar.createParentMenu("Customer");
		rootMenu.getChildMenus().add(customerParentMenu);
		
		customerParentMenu.getChildMenus().add(MenuScalar.createChildMenu("Customer",CustomerListPanel.class));
		customerParentMenu.getChildMenus().add(MenuScalar.createChildMenu("Complaint",ComplaintListPanel.class));
		
		return rootMenu;
	}

}
