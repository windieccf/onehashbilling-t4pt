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
 * 12 March 2012    Chen Changfeng	0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.view.panel.complaint;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import com.onehash.constant.ConstantAction;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TableFilterScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.listener.OneHashTabelFilterListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class ComplaintListPanel extends BasePanel {
	private static final String COMP_TABLE = "TABLE";
	private static final String COMP_BUTTON_CREATE = "CREATE_BUTTON";
	
	private static final String COMP_LBL_ACCOUNT_NO = "LBL_ACCOUNT_NO";
	private static final String COMP_LBL_NAME = "LBL_NAME";
	private static final String COMP_LBL_ISSUE = "LBL_ISSUE";
	
	private static final String COMP_TEXT_NAME = "TXT_NAME";
	private static final String COMP_TEXT_ISSUE_NUMBER = "TXT_ISSUE_NUMBER";
	private static final String COMP_TEXT_ACCOUNT_NO = "COMP_TEXT_ACCOUNT_NO";
	
	public ComplaintListPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	
	@Override
	protected void init() {
		super.registerComponent(COMP_LBL_ACCOUNT_NO , FactoryComponent.createLabel("Account Number", new PositionScalar(20, 23, 100, 14)));
		super.registerComponent(COMP_LBL_NAME , FactoryComponent.createLabel("Name", new PositionScalar(20, 50, 46, 14)));
		super.registerComponent(COMP_LBL_ISSUE , FactoryComponent.createLabel("Issue Number", new PositionScalar(20, 77, 46, 14)));
		
		super.registerComponent(COMP_TEXT_ACCOUNT_NO , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 23, 150, 20,10) ));
		super.registerComponent(COMP_TEXT_NAME , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 50, 150, 20,10) ));
		super.registerComponent(COMP_TEXT_ISSUE_NUMBER , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 77, 150, 20,10 ) ));
		
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , this.getData()));
        table.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
        
        TableRowSorter<OneHashTableModel> sorter = new TableRowSorter<OneHashTableModel>( (OneHashTableModel) table.getModel());
        table.setRowSorter(sorter);

        // registering sorter
        List<TableFilterScalar> filters = new ArrayList<TableFilterScalar>();
        filters.add(new TableFilterScalar(super.getTextFieldComponent(COMP_TEXT_ACCOUNT_NO) , 0));
        filters.add(new TableFilterScalar(super.getTextFieldComponent(COMP_TEXT_NAME) , 1));
        filters.add(new TableFilterScalar(super.getTextFieldComponent(COMP_TEXT_ISSUE_NUMBER) , 2));
        
        OneHashTabelFilterListener filterListener = new OneHashTabelFilterListener(sorter, filters);
        super.getTextFieldComponent(COMP_TEXT_ACCOUNT_NO).getDocument().addDocumentListener(filterListener);
        super.getTextFieldComponent(COMP_TEXT_NAME).getDocument().addDocumentListener(filterListener);
        super.getTextFieldComponent(COMP_TEXT_ISSUE_NUMBER).getDocument().addDocumentListener(filterListener);
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15,120,700,200);
		super.registerComponent(COMP_TABLE, scrollPane);
		
		JButton loginButton = FactoryComponent.createButton("Create", new ButtonAttributeScalar(615, 340, 100, 23 , new ButtonActionListener(this,"loadAddScreen")));
		super.registerComponent(COMP_BUTTON_CREATE , loginButton);
		
		
	}
	
	@Override
	protected String getScreenTitle() {return "Complaint Listing";}
	
	/**************************** REFLECTION UTILITY **********************************/
	
	public void loadAddScreen(){
		this.getMainFrame().doLoadScreen(ComplaintMainenancePanel.class, ConstantAction.ADD);
	}
	
	public void loadEditScreen(String parameter){
		this.getMainFrame().doLoadScreen(ComplaintMainenancePanel.class, ConstantAction.EDIT,parameter);
	}
	
	/******************************** TABLE UTILITY******************************************/
	public Object[][] getData(){
		Object[][] rowData = new String[1][5];
		//List<Customer> customers = OneHashDataCache.getInstance().getCustomers();
		List<Object[]> rows = this.expandComplaintData();
		if(rows.isEmpty()){
			rowData = new Object[0][7];
		}else{
			rowData = new Object[rows.size()][7];
			for(int i = 0; i < rows.size(); i++){
				Object[] data = rows.get(i);
				for(int x = 0; x < 7; x++){
					rowData[i][x] = data[x];
				}
			}
		}
		return rowData;
	}
	
	private List<Object[]> expandComplaintData(){
		List<Customer> customers = OneHashDataCache.getInstance().getCustomers();
		List<Object[]> rowDatas = new ArrayList<Object[]>();
		for(Customer customer : customers){
			for(ComplaintLog complaintLog : customer.getComplaintLogs()){
				Object[] rowData = new Object[7];
				rowData[0] = customer.getAccountNumber();
				rowData[1] = customer.getName();

				rowData[2] = complaintLog.getIssueNo();
				rowData[3] = complaintLog.getIssueDescription();
				rowData[4] = complaintLog.getComplaintDate();
				rowData[5] = complaintLog.getComplaintDate();
				rowData[6] = complaintLog.getStatus();
				rowDatas.add(rowData);
			}
		}
		return rowDatas;
	}
	
	
	public String[] getTableColumnNames(){
		return new String[]{"Account Number", "Name","Issue No.", "Description", "Complaint Date" , "Closed Date", "Status"};
	}

	
}
