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
import com.onehash.view.panel.complaint.ComplaintEditPanel;
import com.onehash.view.panel.subscription.SubscriptionPanel;

@SuppressWarnings("serial")
public class CustomerTabPanel extends BasePanel{
	
	public CustomerTabPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	

	private CustomerEditPanel customerPanel;
	private SubscriptionPanel subscriptionPanel;
	private ComplaintEditPanel complaintPanel;
	
	@Override
	protected void init() {
		customerPanel = new CustomerEditPanel(super.getMainFrame());
		subscriptionPanel = new SubscriptionPanel(super.getMainFrame());
		complaintPanel = new ComplaintEditPanel(super.getMainFrame());
		
		JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customer Detail", customerPanel );
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        tabbedPane.addTab("Subscription", subscriptionPanel );
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        tabbedPane.addTab("Complaint", complaintPanel );
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        tabbedPane.setBounds(1, 20, ConstantGUIAttribute.GUI_MAIN_WIDTH - 172, ConstantGUIAttribute.GUI_MAIN_HEIGHT-90);
        tabbedPane.setBackground(Color.WHITE);
         
        //Add the tabbed pane to this panel.
       // add(tabbedPane);
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
		complaintPanel.initializeCustomer(customer);
	//	subscriptionPanel.postCreate(parameters);
		
	}
	

}
