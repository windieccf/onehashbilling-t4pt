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
 * 15 March 2012    Robin Foe	    0.1				Class creation and screean loading
 * 16 March 2012    Yue Yang	    0.2				Validation and call to save
 * 17 March 2012	Kenny Hartono	0.3				Adding manage ServicePlan													
 * 													
 * 
 */

package com.onehash.view.panel.customer;


import java.util.List;

import javax.swing.JOptionPane;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantAction;
import com.onehash.controller.OneHashDataCache;
import com.onehash.exception.BusinessLogicException;
import com.onehash.exception.InsufficientInputParameterException;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.CheckboxAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.BooleanCheckBoxListener;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.OneHashTextFieldListener;
import com.onehash.view.panel.base.BaseOperationImpl;
import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.serviceplan.ServicePlanListPanel;

@SuppressWarnings("serial")
public class CustomerEditPanel  extends BasePanel implements BaseOperationImpl{
	private static final String COMP_LBL_ACCOUNT_NO = "LBL_ACCOUNT_NO";
	private static final String COMP_LBL_NAME = "LBL_NAME";
	private static final String COMP_LBL_NRIC = "LBL_NRIC";
	private static final String COMP_LBL_CONTACT = "LBL_CONTACT_NO";
	private static final String COMP_LBL_ADDRESS = "LBL_ADDRESS";
	private static final String COMP_LBL_ACTIVATED = "LBL_ACTIVATED";
	
	
	private static final String COMP_LBL_ACCOUNT = "LBL_ACCOUNT";
	private static final String COMP_TEXT_NAME = "TXT_NAME";
	private static final String COMP_TEXT_NRIC = "TXT_NRIC";
	private static final String COMP_TEXT_CONTACT = "TXT_CONTACT";
	private static final String COMP_CHECKBOX_ACTIVATED = "CHK_ACTIVATED";
	
	private static final String COMP_TEXTAREA_ADDRESS = "TXT_AREA_ADDRESS";


	private static final String COMP_BUTTON_NAME_SAVE = "BTN_SAVE";
	private static final String COMP_BUTTON_NAME_CANCEL = "BTN_CANCEL";
	
	private static final String COMP_BUTTON_NAME_MANAGE_SERVICEPLAN = "BTN_MANAGE_SERVICEPLAN";
	
	
	private Customer customer  = new Customer(); // for data binding
	
	public CustomerEditPanel(OneHashGui mainFrame) {
		super(mainFrame);
		
	}
	
	@Override
	protected void init() {
		this.customer  = new Customer();
		
		super.registerComponent(COMP_LBL_ACCOUNT_NO , FactoryComponent.createLabel("Account Number", new PositionScalar(20, 23, 100, 14)));
		super.registerComponent(COMP_LBL_ACCOUNT , FactoryComponent.createLabel("Auto Gen", new PositionScalar(136, 23, 100, 14)));
		
		super.registerComponent(COMP_LBL_NAME , FactoryComponent.createLabel("Name", new PositionScalar(20, 48, 46, 14)));
		
		super.registerComponent(COMP_LBL_NRIC , FactoryComponent.createLabel("NRIC.", new PositionScalar(20, 77, 46, 14)));
		super.registerComponent(COMP_LBL_CONTACT , FactoryComponent.createLabel("Contact No.", new PositionScalar(20, 102, 67, 14)));
		super.registerComponent(COMP_LBL_ACTIVATED , FactoryComponent.createLabel("Activated", new PositionScalar(20, 130, 100, 14)));
		
		super.registerComponent(COMP_LBL_ADDRESS , FactoryComponent.createLabel("Address", new PositionScalar(20, 158, 100, 14)));
		
		// component registration
		super.registerComponent(COMP_TEXT_NAME , 
							FactoryComponent.createTextField( 
										new TextFieldAttributeScalar(136, 48, 150, 20,10 , new OneHashTextFieldListener(this,"name",String.class)) ));
		
		super.registerComponent(COMP_TEXT_NRIC , 
				FactoryComponent.createTextField( 
							new TextFieldAttributeScalar(136, 77, 150, 20,10 , new OneHashTextFieldListener(this,"nric",String.class)) ));

		super.registerComponent(COMP_TEXT_CONTACT , 
				FactoryComponent.createTextField( 
							new TextFieldAttributeScalar(136, 102, 150, 20,10 , new OneHashTextFieldListener(this,"phoneNumber",String.class)) ));

		super.registerComponent(COMP_CHECKBOX_ACTIVATED , 
				FactoryComponent.createCheckBox("", 
							new CheckboxAttributeScalar(136, 130, 150, 20, new BooleanCheckBoxListener(this,"status")) ));

		
		super.registerComponent(COMP_TEXTAREA_ADDRESS , 
				FactoryComponent.createTextArea( 
							new TextFieldAttributeScalar(136, 158, 218, 75,0 , new OneHashTextFieldListener(this,"address",String.class)) ));
		
		
		super.registerComponent(COMP_BUTTON_NAME_SAVE , FactoryComponent.createButton("Save", new ButtonAttributeScalar(136, 250, 100, 23 , new ButtonActionListener(this,"saveCustomer"))));
		super.registerComponent(COMP_BUTTON_NAME_CANCEL , FactoryComponent.createButton("Cancel", new ButtonAttributeScalar(256, 250, 100, 23 , new ButtonActionListener(this,"cancel"))));
		
		super.registerComponent(COMP_BUTTON_NAME_MANAGE_SERVICEPLAN , FactoryComponent.createButton("Manage Subscription", new ButtonAttributeScalar(136, 290, 220, 23 , new ButtonActionListener(this,"loadManageServicePlanScreen"))));
	}
	
	@Override
	protected String getScreenTitle() {return "Customer Maintenance";}
	
	@Override
	public BaseEntity getSelectedEntity() {return this.customer;}
	
	
	@PostCreate
	public void postCreate(List<String> parameters){
		if(parameters == null)
			throw new IllegalArgumentException("CustomerEditPanel.postCreate parameter is required");
		
		if(ConstantAction.ADD.equals(parameters.get(0)))
			customer = new Customer();
		else{
			customer = OneHashDataCache.getInstance().getCustomerByAccountNumber(parameters.get(1));
			if(customer != null){
				super.getLabelComponent(COMP_LBL_ACCOUNT).setText(customer.getAccountNumber());
				super.getTextFieldComponent(COMP_TEXT_NAME).setText(customer.getName());
				super.getTextFieldComponent(COMP_TEXT_NRIC).setText(customer.getNric());
				super.getTextFieldComponent(COMP_TEXT_CONTACT).setText(customer.getPhoneNumber());
				super.getTextAreaComponent(COMP_TEXTAREA_ADDRESS).setText(customer.getAddress());
			}
		}
		super.getCheckboxComponent(COMP_CHECKBOX_ACTIVATED).setSelected(customer.isActivated());
		
	}
	
	
	/***************************************** BUTTON LISTENER *****************************************************/
	public void cancel(){
		this.getMainFrame().doLoadScreen(CustomerListPanel.class);
	}
	
	public void saveCustomer() throws Exception{
		// check required field 
		try{
			if(OneHashStringUtil.isEmpty(this.customer.getName()))
				throw new InsufficientInputParameterException("Customer Name is required");
			
			if(OneHashStringUtil.isEmpty(this.customer.getNric()))
				throw new InsufficientInputParameterException("Customer NRIC is required");
			
			if(OneHashStringUtil.isEmpty(this.customer.getPhoneNumber()))
				throw new InsufficientInputParameterException("Customer Phone number is required");
			
			if(OneHashStringUtil.isEmpty(this.customer.getAddress()))
				throw new InsufficientInputParameterException("Customer Address is required");
			
			OneHashDataCache.getInstance().saveCustomer(this.customer);
			JOptionPane.showMessageDialog(this, "Customer Successfully Saved");
			this.cancel();
			
		}catch(Exception e){
			if(e instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
			throw e;
		}
		
	}

	public void loadManageServicePlanScreen() {
		this.getMainFrame().doLoadScreen(ServicePlanListPanel.class, ConstantAction.MANAGE, customer.getAccountNumber());
	}

}
