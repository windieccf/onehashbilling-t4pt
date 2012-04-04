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
 * 20 March 2012    Robin Foe	    0.1				Initial Creation
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.view.panel.customer.impl;

import com.onehash.model.customer.Customer;
/**
 * @author robin.foe
 * Customer tab interface, user for JPanel that need to share same panel in CustomerTabPanel
 */
public interface CustomerTabInterface {
	
	public void initializeCustomer(Customer customer);

}
