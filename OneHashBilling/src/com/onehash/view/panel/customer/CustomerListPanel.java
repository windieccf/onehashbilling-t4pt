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
 * 12 March 2012    Robin Foe	    0.1				Class creating
 * 15 March 2012    Robin Foe	    0.2				Add in table and table listener
 * 15 March 2012    Yue Yang	    0.3				Add in screen redirection
 * 													
 * 													
 * 
 */

package com.onehash.view.panel.customer;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.onehash.constant.ConstantAction;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class CustomerListPanel extends BasePanel {
	private static final String COMP_TABLE = "TABLE";
	private static final String COMP_BUTTON_CREATE = "CREATE_BUTTON";
	
	
	public CustomerListPanel(OneHashGui mainFrame) {
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
	protected String getScreenTitle() {return "Customer List";}
	
	/**************************** REFLECTION UTILITY **********************************/
	public void loadAddScreen(){
		this.getMainFrame().doLoadScreen(CustomerEditPanel.class, ConstantAction.ADD);
	}
	
	public void loadEditScreen(String parameter){
		this.getMainFrame().doLoadScreen(CustomerEditPanel.class, ConstantAction.EDIT,parameter);
	}
	
	
	/******************************** TABLE UTILITY******************************************/
	public Object[][] getData(){
		Object[][] rowData = new String[1][5];
		List<Customer> customers = OneHashDataCache.getInstance().getCustomers();
		if(customers.isEmpty()){
			rowData = new Object[0][5];
		}else{
			rowData = new Object[customers.size()][5];
			for(int i = 0 ; i < customers.size(); i++){
				Customer customer = customers.get(i);
				rowData[i][0] = customer.getAccountNumber();
				rowData[i][1] = customer.getName();
				rowData[i][2] = customer.getNric();
				rowData[i][3] = customer.getPhoneNumber();
				rowData[i][4] = customer.isActivated();
			}
		}
		
		return rowData;
	}
	
	public String[] getTableColumnNames(){
		return new String[]{"Account Number", "Name","NRIC" , "Phone Number","Activated"};
	}


}
