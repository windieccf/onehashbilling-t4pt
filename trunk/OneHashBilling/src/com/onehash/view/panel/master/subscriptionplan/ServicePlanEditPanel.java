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
 * 02 April 2012    Kenny Hartono   0.1				Created class and functions
 * 04 April 2012	Song Lei		0.2				Added logic and fixing things													
 * 05 April 2012	Kenny Hartono	0.3				Added random service plan to customer function 														
 * 													
 * 
 */

package com.onehash.view.panel.master.subscriptionplan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantAction;
import com.onehash.constant.ConstantSummary;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.model.service.plan.CableTvPlan;
import com.onehash.model.service.plan.DigitalVoicePlan;
import com.onehash.model.service.plan.MobileVoicePlan;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.model.service.rate.UsageRate;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.comboboxitem.ComboBoxItem;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class ServicePlanEditPanel extends BasePanel{
	
	private static final String SERVICEPLANLIST_LBL_RATE_TYPE = "SERVICEPLANLIST_LBL_RATE_TYPE";
	private static final String SERVICEPLANLIST_LBL_RATE_CODE = "SERVICEPLANLIST_LBL_RATE_NAME";
	private static final String SERVICEPLANLIST_LBL_RATE_DESC = "SERVICEPLANLIST_LBL_RATE_DESC";
	private static final String SERVICEPLANLIST_LBL_RATE_PRICE = "SERVICEPLANLIST_LBL_RATE_PRICE";
	
	private static final String SERVICEPLANLIST_COMBOBOX_SELECTION = "SERVICEPLANLIST_COMBOBOX_SELECTION";
	private static final String SERVICEPLANLIST_TEXT_RATE_CODE = "SERVICEPLANLIST_TEXT_ACCOUNT_NO";
	private static final String SERVICEPLANLIST_TEXT_RATE_DESC = "SERVICEPLANLIST_TEXT_NAME";
	private static final String SERVICEPLANLIST_TEXT_RATE_PRICE = "SERVICEPLANLIST_TEXT_NRIC";

	private static final String SERVICEPLANLIST_BUTTON_NAME_SAVE = "BTN_SAVE";
	private static final String SERVICEPLANLIST_BUTTON_NAME_CANCEL = "BTN_CANCEL";
	private final static Vector<ComboBoxItem> servicePlanList;

	private ArrayList<ServicePlan> servicePlans;
	private ServicePlan selectedServicePlan;
	private ArrayList<ServiceRate> serviceRates;
	private ServiceRate selectedServiceRate;
	
	public ServicePlanEditPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}

	static{
		servicePlanList = new Vector<ComboBoxItem>();
		servicePlanList.add(new ComboBoxItem("Subscription Rate per Month", "Subscription Rate per Month"));
		servicePlanList.add(new ComboBoxItem("Usage Rate", "Usage Rate"));
	}

	@Override
	protected void init() {
		super.registerComponent(SERVICEPLANLIST_LBL_RATE_TYPE , FactoryComponent.createLabel("Rate Type", new PositionScalar(20, 23, 100, 14)));
		super.registerComponent(SERVICEPLANLIST_LBL_RATE_CODE , FactoryComponent.createLabel("Rate Code", new PositionScalar(20, 53, 100, 14)));
		super.registerComponent(SERVICEPLANLIST_LBL_RATE_DESC , FactoryComponent.createLabel("Rate Desc", new PositionScalar(20, 80, 100, 14)));
		super.registerComponent(SERVICEPLANLIST_LBL_RATE_PRICE , FactoryComponent.createLabel("Rate Price", new PositionScalar(20, 107, 100, 14)));
		
		JComboBox servicePlanSelection = FactoryComponent.createComboBox(servicePlanList, new ButtonAttributeScalar(136, 23, 250, 23 , new ButtonActionListener(this,"updateNothing")));
		servicePlanSelection.setEnabled(true);
		super.registerComponent(SERVICEPLANLIST_COMBOBOX_SELECTION , servicePlanSelection);
		super.registerComponent(SERVICEPLANLIST_TEXT_RATE_CODE , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 53, 250, 20,10) ));
		super.registerComponent(SERVICEPLANLIST_TEXT_RATE_DESC , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 80, 250, 20,10) ));
		super.registerComponent(SERVICEPLANLIST_TEXT_RATE_PRICE , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 107, 250, 20,10 ) ));

		super.registerComponent(SERVICEPLANLIST_BUTTON_NAME_SAVE , FactoryComponent.createButton("Save", new ButtonAttributeScalar(136, 140, 100, 23 , new ButtonActionListener(this,"saveServiceRate"))));
		super.registerComponent(SERVICEPLANLIST_BUTTON_NAME_CANCEL , FactoryComponent.createButton("Back", new ButtonAttributeScalar(256, 140, 100, 23 , new ButtonActionListener(this,"cancel"))));
	}

	public void initializeServiceRate(){
		JComboBox component = (JComboBox)super.getComponent(SERVICEPLANLIST_COMBOBOX_SELECTION);
		if (selectedServiceRate instanceof UsageRate)
			component.setSelectedIndex(1);
		else
			component.setSelectedIndex(0);
		component.setEnabled(false);
		super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_CODE).setText(selectedServiceRate.getRateCode());
		super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_DESC).setText(selectedServiceRate.getRateDescription());
		super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_PRICE).setText(selectedServiceRate.getRatePrice().toString());
	}
	
	@PostCreate
	public void postCreate(List<String> parameters){
		if(parameters == null)
			throw new IllegalArgumentException("SevicePlanEditPanel.postCreate parameter is required");
		if (OneHashDataCache.getInstance().getAvailableServiceRate() == null)
			serviceRates = new ArrayList<ServiceRate>();
		else
			serviceRates = new ArrayList<ServiceRate>(OneHashDataCache.getInstance().getAvailableServiceRate());
		
		if (OneHashDataCache.getInstance().getAvailableServicePlan() == null)
			servicePlans = new ArrayList<ServicePlan>();
		else
			servicePlans = new ArrayList<ServicePlan>(OneHashDataCache.getInstance().getAvailableServicePlan());
		if (servicePlans.isEmpty()) {
			ServicePlan servicePlan;
			servicePlan = new DigitalVoicePlan();
			servicePlan.setPlanName(ConstantSummary.DigitalVoice);
			servicePlan.setPlanCode(ServiceRate.PREFIX_DIGITAL_VOICE);
			servicePlans.add(servicePlan);
			
			servicePlan = new MobileVoicePlan();
			servicePlan.setPlanName(ConstantSummary.MobileVoice);
			servicePlan.setPlanCode(ServiceRate.PREFIX_MOBILE_VOICE);
			servicePlans.add(servicePlan);
			
			servicePlan = new CableTvPlan();
			servicePlan.setPlanName(ConstantSummary.CableTV);
			servicePlan.setPlanCode(ServiceRate.PREFIX_CABLE_TV);
			servicePlans.add(servicePlan);
			
			OneHashDataCache.getInstance().setAvailableServicePlan(servicePlans);
		}
		if (ConstantAction.EDIT.equals(parameters.get(0))) {
			for (ServicePlan servicePlan:servicePlans) {
				if (servicePlan.getPlanCode().startsWith(parameters.get(2))) {
					selectedServicePlan = servicePlan;
					break;
				}
			}
			for (ServiceRate serviceRate:serviceRates) {
				String name = serviceRate.getRateCode()+": "+serviceRate.getRateDescription();
				if (name.equals(parameters.get(1))) {
					selectedServiceRate = serviceRate;
					break;
				}
			}
			if (selectedServiceRate == null)
				selectedServiceRate = new SubscriptionRate();
			initializeServiceRate();
		} else {
			for (ServicePlan servicePlan:servicePlans) {
				if (servicePlan.getPlanCode().startsWith(parameters.get(1))) {
					selectedServicePlan = servicePlan;
					break;
				}
			}
			selectedServiceRate = null;
			super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_CODE).setText(selectedServicePlan.getPlanCode());
		}
	}

	public void updateNothing() {
	}
	
	@Override
	protected String getScreenTitle() {
		return "Service Plan Maintenance";
	}
	
	/***************************************** BUTTON LISTENER *****************************************************/
	public void saveServiceRate() {
		ArrayList<ServiceRate> availableServiceRate = new ArrayList<ServiceRate>(OneHashDataCache.getInstance().getAvailableServiceRate());
		if (!super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_CODE).getText().startsWith(selectedServicePlan.getPlanCode())) {
			JOptionPane.showMessageDialog(this, "RATE CODE must starts with " + selectedServicePlan.getPlanCode(),"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		for(ServiceRate serviceRate:availableServiceRate) {
			if (super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_CODE).getText().equals(serviceRate.getRateCode())) {
				JOptionPane.showMessageDialog(this, "Duplicate RATE CODE : " + serviceRate.getRateCode(), "Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (selectedServiceRate == null) {
			JComboBox component = (JComboBox)super.getComponent(SERVICEPLANLIST_COMBOBOX_SELECTION);
			ComboBoxItem prefix = (ComboBoxItem)component.getSelectedItem();
			if (prefix.getKey().equals("Usage Rate"))
				selectedServiceRate = new UsageRate(0);
			else
				selectedServiceRate = new SubscriptionRate();
			selectedServiceRate.setRateCode(super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_CODE).getText());
			selectedServiceRate.setRateDescription(super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_DESC).getText());
			try {
				selectedServiceRate.setRatePrice(new BigDecimal(super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_PRICE).getText()));
			} catch (Exception e) {
				selectedServiceRate.setRatePrice(new BigDecimal(9999));
				JOptionPane.showMessageDialog(this, "Error Decimal. Please edit again the RATE PRICE later.", "Error",JOptionPane.ERROR_MESSAGE);
			}
			availableServiceRate.add(selectedServiceRate);
			OneHashDataCache.getInstance().setAvailableServiceRate(availableServiceRate);
			
			//master set up
			ArrayList<ServiceRate> servicePlan_serviceRates = new ArrayList<ServiceRate>(selectedServicePlan.getServiceRates());
			servicePlan_serviceRates.add(selectedServiceRate);
			selectedServicePlan.setServiceRates(servicePlan_serviceRates);
			
		} else {
			for(ServiceRate serviceRate:availableServiceRate) {
				if (serviceRate == selectedServiceRate) {
					serviceRate.setRateCode(super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_CODE).getText());
					serviceRate.setRateDescription(super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_DESC).getText());
					serviceRate.setRatePrice(new BigDecimal(super.getTextFieldComponent(SERVICEPLANLIST_TEXT_RATE_PRICE).getText()));
					OneHashDataCache.getInstance().setAvailableServiceRate(availableServiceRate);
					JOptionPane.showMessageDialog(this, "Customer Successfully Saved");
				}
			}
		}
		this.cancel();
	}
	
	public void cancel() {
		this.getMainFrame().doLoadScreen(ServicePlanListPanel.class);
	}
	
}
