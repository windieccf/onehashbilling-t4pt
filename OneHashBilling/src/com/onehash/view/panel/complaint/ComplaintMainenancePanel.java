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
 * 28 March 2012    Robin Foe		0.1				Class creating
 * 30 March 2012    Chen Changfeng	0.2				Adding comments													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.view.panel.complaint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantAction;
import com.onehash.controller.OneHashDataCache;
import com.onehash.enumeration.EnumComplaint;
import com.onehash.enumeration.EnumUserAccess;
import com.onehash.exception.BusinessLogicException;
import com.onehash.exception.InsufficientInputParameterException;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.utility.OneHashDateUtil;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.dialog.search.CustomerLookupDialog;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.OneHashTextFieldListener;
import com.onehash.view.panel.base.BaseOperationImpl;
import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.base.CustomerOperationImpl;

@SuppressWarnings("serial")
public class ComplaintMainenancePanel extends BasePanel implements BaseOperationImpl, CustomerOperationImpl{

	private static final String COMP_LBL_ACCOUNT_NO = "COMP_LBL_ACCOUNT_NO";
	private static final String COMP_LBL_ACCOUNT = "COMP_LBL_ACCOUNT";
	private static final String COMP_LBL_ISSUE_NO_TXT = "COMP_LBL_ISSUE_NO_TXT";
	
	private static final String COMP_LBL_ISSUE_NO = "COMP_LBL_ISSUE_NO";
	private static final String COMP_LBL_DATE_FROM = "COMP_LBL_DATE_FROM";
	private static final String COMP_LBL_DATE_FROM_TXT = "COMP_LBL_DATE_FROM_TXT";
	
	private static final String COMP_LBL_DATE_TO = "COMP_LBL_DATE_TO";
	private static final String COMP_LBL_DATE_TO_TXT = "COMP_LBL_DATE_TO_TXT";
	private static final String COMP_LBL_ISSUE_LOG = "COMP_LBL_ISSUE_LOG";
	private static final String COMP_LBL_FOLLOW_UP = "COMP_LBL_FOLLOW_UP";
	private static final String COMP_INPUT_ISSUE_LOG = "COMP_INPUT_ISSUE_LOG";
	private static final String COMP_INPUT_FOLLOW_UP= "COMP_INPUT_FOLLOW_UP";
	
	private static final String COMP_BUTTON_SEARCH = "COMP_BUTTON_SEARCH";
	private static final String COMP_BUTTON_SAVE = "COMP_BUTTON_SAVE";
	private static final String COMP_BUTTON_BACK = "COMP_BUTTON_BACK";
	private static final String COMP_BUTTON_CLOSE_CASE = "COMP_BUTTON_CLOSE_CASE";


	
	
	public ComplaintMainenancePanel(OneHashGui mainFrame) {super(mainFrame);}

	private ComplaintLog complaintLog = new ComplaintLog();
	private Customer selectedCustomer = new Customer();
	
	public Customer getSelectedCustomer() {return selectedCustomer;}
	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
		super.getLabelComponent(COMP_LBL_ACCOUNT).setText(selectedCustomer.getAccountNumber());
	}

	@Override
	public BaseEntity getSelectedEntity() {return complaintLog;}

	@Override
	public void setSelectedEntity(BaseEntity baseEntity) {this.complaintLog = (ComplaintLog)complaintLog;}

	@Override
	protected void init() {
		// popup dialog
		complaintLog = new ComplaintLog();
		super.registerComponent(COMP_LBL_ACCOUNT_NO , FactoryComponent.createLabel("Customer Account", new PositionScalar(20, 23, 150, 14)));
		super.registerComponent(COMP_LBL_ACCOUNT , FactoryComponent.createLabel("Please Select", new PositionScalar(180, 23, 200, 14)));
		super.registerComponent(COMP_BUTTON_SEARCH , FactoryComponent.createButton("Search", new ButtonAttributeScalar(330, 23, 100, 23 )));
		
		super.registerComponent(COMP_LBL_ISSUE_NO , FactoryComponent.createLabel("Issue Number", new PositionScalar(20, 53, 150, 14)));
		super.registerComponent(COMP_LBL_ISSUE_NO_TXT , FactoryComponent.createLabel("Auto number", new PositionScalar(180, 53, 150, 14)));
		
		super.registerComponent(COMP_LBL_DATE_FROM , FactoryComponent.createLabel("Complaint Date", new PositionScalar(20, 83, 150, 14)));
		super.registerComponent(COMP_LBL_DATE_FROM_TXT , FactoryComponent.createLabel(OneHashDateUtil.format(this.complaintLog.getComplaintDate(), "dd/MM/yyyy"), new PositionScalar(180, 83, 150, 14)));
		
		super.registerComponent(COMP_LBL_DATE_TO , FactoryComponent.createLabel("Closed Date", new PositionScalar(20, 113, 150, 14)));
		super.registerComponent(COMP_LBL_DATE_TO_TXT , FactoryComponent.createLabel("-", new PositionScalar(180, 113, 150, 14)));
		
		
		super.registerComponent(COMP_LBL_ISSUE_LOG , FactoryComponent.createLabel("Issue Log", new PositionScalar(20, 143, 150, 14)));
		super.registerComponent(COMP_INPUT_ISSUE_LOG , FactoryComponent.createTextArea( 
				new TextFieldAttributeScalar(20, 163, 418, 75,0 , new OneHashTextFieldListener(this,"issueDescription",String.class)) ));
		
		
		super.registerComponent(COMP_LBL_FOLLOW_UP , FactoryComponent.createLabel("Follow Up", new PositionScalar(20, 250, 150, 14)));
		super.registerComponent(COMP_INPUT_FOLLOW_UP , FactoryComponent.createTextArea( 
				new TextFieldAttributeScalar(20, 270, 418, 75,0 , new OneHashTextFieldListener(this,"followUp",String.class)) ));
		
		
		super.registerComponent(COMP_BUTTON_SAVE , FactoryComponent.createButton("Save", new ButtonAttributeScalar(20, 355, 100, 23 , new ButtonActionListener(this,"saveComplaint"))));
		super.registerComponent(COMP_BUTTON_BACK , FactoryComponent.createButton("Cancel", new ButtonAttributeScalar(130, 355, 100, 23 , new ButtonActionListener(this,"cancel"))));
		super.registerComponent(COMP_BUTTON_CLOSE_CASE , FactoryComponent.createButton("Close Issue", new ButtonAttributeScalar(240, 355, 100, 23 , new ButtonActionListener(this,"saveAndCloseComplaint"))));
		
		// registering command button
		final ComplaintMainenancePanel custPanel = this;
		super.getButtonComponent(COMP_BUTTON_SEARCH).addActionListener(
				new ActionListener() { public void actionPerformed(ActionEvent e) {
			        	new CustomerLookupDialog(new JFrame("Title") , custPanel);
			        }});
		
	}
	
	/**
	 * Save complaints
	 * Mandatory field: Customer, issue description
	 * @throws Exception
	 */
	public void saveComplaint() throws Exception {
		try{
			if(this.selectedCustomer == null || OneHashStringUtil.isEmpty(this.selectedCustomer.getAccountNumber()))
				throw new InsufficientInputParameterException("Please select customer");
			
			if(OneHashStringUtil.isEmpty(this.complaintLog.getIssueDescription()))
				throw new InsufficientInputParameterException("Please fill in issue description");
			
			OneHashDataCache.getInstance().saveComplaintLog(selectedCustomer, this.complaintLog);
			JOptionPane.showMessageDialog(this, "Complaint Successfully Saved");
			this.cancel();
			
		}catch(Exception e){
			if(e instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
			throw e;
		}
	}
	
	/**
	 * Closed date will be the date status changed to closed
	 * @throws Exception
	 */
	public void saveAndCloseComplaint() throws Exception{
		this.complaintLog.setClosedDate(new Date());
		this.complaintLog.setStatus(EnumComplaint.STS_CLOSED.getStatus());
		this.saveComplaint();
	}
	
	public void cancel(){
		this.getMainFrame().doLoadScreen(ComplaintListPanel.class);
	}

	
	@PostCreate
	public void postCreate(List<String> parameters){
		if(parameters == null)
			throw new IllegalArgumentException("ComplaintMaintenancePanel.postCreate parameter is required");
		
		if(ConstantAction.ADD.equals(parameters.get(0)))
			complaintLog = new ComplaintLog();
		else{
			
			try {
				this.selectedCustomer = OneHashDataCache.getInstance().getCustomerByAccountNumber(parameters.get(1));
				this.complaintLog = OneHashDataCache.getInstance().getComplaintLog(this.selectedCustomer, parameters.get(2));
				
				super.getLabelComponent(COMP_LBL_ACCOUNT).setText(this.selectedCustomer.getAccountNumber());
				super.getButtonComponent(COMP_BUTTON_SEARCH).setVisible(false);
				
				super.getLabelComponent(COMP_LBL_ISSUE_NO_TXT).setText(this.complaintLog.getIssueNo());
				
				super.getLabelComponent(COMP_LBL_DATE_FROM_TXT).setText(OneHashDateUtil.format(this.complaintLog.getComplaintDate(), "dd/MM/yyyy"));
				super.getLabelComponent(COMP_LBL_DATE_TO_TXT).setText(OneHashDateUtil.format(this.complaintLog.getClosedDate(), "dd/MM/yyyy"));
				super.getTextAreaComponent(COMP_INPUT_ISSUE_LOG).setText(this.complaintLog.getIssueDescription());
				super.getTextAreaComponent(COMP_INPUT_FOLLOW_UP).setText(this.complaintLog.getFollowUp());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected String getScreenTitle() {return "Complaint Maintenance";}
	
	
	@Override
	protected void initiateAccessRights() {
		if(!OneHashDataCache.getInstance().getCurrentUser().hasRights(EnumUserAccess.COMPLAINT_UPDATE))
			super.disableComponent(COMP_BUTTON_BACK);
	}


}
