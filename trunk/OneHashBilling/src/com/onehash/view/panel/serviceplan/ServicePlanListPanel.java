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
 * 17 March 2012    Kenny Hartono	0.1				Class initialization
 * 													
 * 													
 * 
 */

package com.onehash.view.panel.serviceplan;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantAction;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class ServicePlanListPanel extends BasePanel {
	private static final String COMP_TABLE = "TABLE";
	private static final String COMP_BUTTON_CREATE = "CREATE_BUTTON";

	private List<ServicePlan> servicePlans = new ArrayList<ServicePlan>();
	
	public ServicePlanListPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void init() {
		
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , this.getData()));
        table.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15,30,700,200);
		super.registerComponent(COMP_TABLE, scrollPane);
		
		JButton loginButton = FactoryComponent.createButton("Create", new ButtonAttributeScalar(615, 240, 100, 23 , new ButtonActionListener(this,"loadAddScreen")));
		super.registerComponent(COMP_BUTTON_CREATE , loginButton);
	}

	@Override
	protected String getScreenTitle() {return "Subscription List";}

	@PostCreate
	public void postCreate(List<String> parameters){
		if(parameters == null)
			throw new IllegalArgumentException("ServicePlanEditPanel.postCreate parameter is required");
		
		if(ConstantAction.ADD_SERVICE_PLAN.equals(parameters.get(0))) {
			servicePlans = new ArrayList<ServicePlan>();
		}
		else {
			servicePlans = OneHashDataCache.getInstance().getCustomerByAccountNumber(parameters.get(1)).getServicePlans();
		}
		
	}

	/**************************** REFLECTION UTILITY **********************************/
	public void loadAddScreen(){
		this.getMainFrame().doLoadScreen(ServicePlanEditPanel.class, ConstantAction.ADD_SERVICE_PLAN);
	}
	
	public void loadEditScreen(String parameter){
		this.getMainFrame().doLoadScreen(ServicePlanEditPanel.class, ConstantAction.EDIT_SERVICE_PLAN,parameter);
	}
	
	
	/******************************** TABLE UTILITY******************************************/
	public Object[][] getData(){
		Object[][] rowData = new String[1][4];
		if(servicePlans.isEmpty()){
			rowData = new Object[0][4];
		}else{
			rowData = new Object[servicePlans.size()][4];
			for(int i = 0 ; i < servicePlans.size(); i++){
				ServicePlan servicePlan = servicePlans.get(i);
				rowData[i][0] = servicePlan.getPlanId();
				rowData[i][1] = servicePlan.getPlanName();
				rowData[i][2] = servicePlan.getStartDate();
				rowData[i][3] = servicePlan.getEndDate();
			}
		}
		
		return rowData;
	}
	
	public String[] getTableColumnNames(){
		return new String[]{"Subscription Number", "Name", "Commencement Date" , "Termination Date"};
	}
}
