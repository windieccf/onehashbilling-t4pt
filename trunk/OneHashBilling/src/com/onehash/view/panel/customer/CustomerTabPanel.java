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

package com.onehash.view.panel.customer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JTabbedPane;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantAction;
import com.onehash.constant.ConstantGUIAttribute;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.customer.Customer;
import com.onehash.view.OneHashGui;
import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.subscription.SubscriptionPanel;

@SuppressWarnings("serial")
public class CustomerTabPanel extends BasePanel{
	
	public CustomerTabPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	

	private CustomerEditPanel customerPanel;
	private SubscriptionPanel subscriptionPanel;
	
	@Override
	protected void init() {
		customerPanel = new CustomerEditPanel(super.getMainFrame());
		subscriptionPanel = new SubscriptionPanel(super.getMainFrame());
		
		JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customer Detail", customerPanel );
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        tabbedPane.addTab("Subscription", subscriptionPanel );
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        
        tabbedPane.setBounds(1, 20, ConstantGUIAttribute.GUI_MAIN_WIDTH - 180, ConstantGUIAttribute.GUI_MAIN_HEIGHT-100);
        tabbedPane.setBackground(Color.WHITE);
         
        //Add the tabbed pane to this panel.
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		super.registerComponent("TAB",tabbedPane);
		
	}
	
	@Override
	protected String getScreenTitle() {return "Customer Maintenance";}
	
	@PostCreate
	public void postCreate(List<String> parameters){
		if(parameters == null)
			throw new IllegalArgumentException("CustomerEditPanel.postCreate parameter is required");
		
		Customer customer = (ConstantAction.ADD.equals(parameters.get(0))) ? new Customer() : OneHashDataCache.getInstance().getCustomerByAccountNumber(parameters.get(1));
		customerPanel.initializeCustomer(customer);
		subscriptionPanel.initializeCustomer(customer);
		
	}
	

}
