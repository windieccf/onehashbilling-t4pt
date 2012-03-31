package com.onehash.view.panel.payment;

import com.onehash.view.OneHashGui;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class PaymentPanel extends BasePanel{

	public PaymentPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void init() {
		/**
		 * select customer
		 * 
		 * upon selected, Show customer monthly bill, and show pay status in break down format / monthly bill
		 * to add in add payment button and field
		 * 
		 * */
		
	}

	@Override
	protected String getScreenTitle() {return "Payment";}

}
