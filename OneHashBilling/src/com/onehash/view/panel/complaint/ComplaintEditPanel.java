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
 * 26 March 2012    Chen Changfeng	0.1				Class creating 													
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.utility.OneHashDateUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.dialog.AddComplaintDialog;
import com.onehash.view.component.dialog.EditComplaintDialog;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BaseOperationImpl;
import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.customer.impl.CustomerTabInterface;

@SuppressWarnings("serial")
public class ComplaintEditPanel extends BasePanel implements BaseOperationImpl , CustomerTabInterface{
	
	//Complaint Module
	private static final String COMPLAINT_TABLE = "COMPLAINT_TABLE";
	private static final String COMPLAINT_BUTTON_ADD = "COMPLAINT_BUTTON_ADD";
	private static final String COMPLAINT_BUTTON_REM = "COMPLAINT_BUTTON_REM";
	
	private Customer customer  = new Customer(); // for data binding
	
	public ComplaintEditPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	
	private List<ComplaintLog> complaintLogs = new ArrayList<ComplaintLog>();
	
	@Override
	protected void init() {
		this.customer  = new Customer();
		this.registerComplaintListComponent();
	}
	
	protected void registerComplaintListComponent() {
		// Add ComplaintListPanel
		JTable complaintTable = new JTable();
		complaintTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
		complaintTable.setFillsViewportHeight(true);
		complaintTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		complaintTable.setModel(new OneHashTableModel(this.getComplaintTableColumnNames() , this.getComplaintData(this.customer)));
		complaintTable.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
        
        JScrollPane complaintScrollPane = new JScrollPane(complaintTable);
        complaintScrollPane.setBounds(20,20,700,315);
		super.registerComponent(COMPLAINT_TABLE, complaintScrollPane);
		
		JButton complaintaddButton = FactoryComponent.createButton("Add", new ButtonAttributeScalar(20, 350, 100, 23 , new ButtonActionListener(this,"addComplaintLog")));
		super.registerComponent(COMPLAINT_BUTTON_ADD , complaintaddButton);
		
		JButton complaintremoveButton = FactoryComponent.createButton("Remove", new ButtonAttributeScalar(140, 350, 100, 23 , new ButtonActionListener(this,"removeComplaintLog")));
		super.registerComponent(COMPLAINT_BUTTON_REM , complaintremoveButton);
	}
	
	@Override
	protected String getScreenTitle() {return "Complaint Maintenance";}
	
	@Override
	public BaseEntity getSelectedEntity() {return this.customer;}
	
	@Override
	protected boolean isEnableHeader(){return false;}
	
	@Override
	public void initializeCustomer(Customer customer) {
		this.customer = customer;
		this.refreshComplaintListComponent();
		
	}

	private void refreshComplaintListComponent() {
		JTable complaintTable = new JTable();
		complaintTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
		complaintTable.setFillsViewportHeight(true);
		complaintTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		complaintTable.setModel(new OneHashTableModel(this.getComplaintTableColumnNames() , this.getComplaintData(this.customer)));
		complaintTable.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
		JScrollPane servicePlansScrollPane = (JScrollPane) super.getComponent(COMPLAINT_TABLE);
		servicePlansScrollPane.setViewportView(complaintTable);
		
	}

	@Override
	public void setSelectedEntity(BaseEntity baseEntity) {this.customer = (Customer) baseEntity;}
	
	
	
	public String[] getComplaintTableColumnNames(){
		return new String[]{"Issue No.", "Description", "Complaint Date" , "Closed Date", "Status"};
	}
	
	public Object[][] getComplaintData(Customer customer){
		System.out.println("come here"+customer.getAccountNumber());
		complaintLogs = customer.getComplaintLogs();
		for (ComplaintLog c : complaintLogs) {
			System.out.println(c.getIssueNo());
			
		}
		Object[][] rowData = new String[1][5];
		if (complaintLogs == null)
			complaintLogs = new ArrayList<ComplaintLog>();
		if(complaintLogs.isEmpty()){
			rowData = new Object[0][5];
		}else{
			rowData = new Object[complaintLogs.size()][5];
			for(int i = 0 ; i < complaintLogs.size(); i++){
				ComplaintLog complaintLog = complaintLogs.get(i);
				rowData[i][0] = complaintLog.getIssueNo();
				rowData[i][1] = complaintLog.getIssueDescription();
				rowData[i][2] = OneHashDateUtil.format(complaintLog.getComplaintDate(), "dd/MM/yyyy");
				rowData[i][3] = OneHashDateUtil.format(complaintLog.getClosedDate(), "dd/MM/yyyy");
				rowData[i][4] = complaintLog.getStatus().getLabel();
			}
		}
		return rowData;
	}
	
	public void addComplaintLog() {
		AddComplaintDialog complaintDialog = new AddComplaintDialog(this);
		complaintDialog.pack();
		complaintDialog.setVisible (true);
		//TODO immediately refresh
		this.refreshComplaintListComponent();
	}
	
	public void removeComplaintLog() {
		JScrollPane selectedComplaintLogScrollPane = (JScrollPane)super.getComponent(COMPLAINT_TABLE);
		JTable ComplaintLogJTable = (JTable)selectedComplaintLogScrollPane.getViewport().getView();
		
		complaintLogs.remove(ComplaintLogJTable.getSelectedRow());
		JOptionPane.showMessageDialog(this, "Complaint Successfully Removed");
		this.refreshComplaintListComponent();

	}
	
	public void loadEditScreen(String parameter) throws Exception {
		JScrollPane selectedComplaintLogScrollPane = (JScrollPane)super.getComponent(COMPLAINT_TABLE);
		JTable ComplaintLogJTable = (JTable)selectedComplaintLogScrollPane.getViewport().getView();
		String issueNumber = (String) ComplaintLogJTable.getValueAt(ComplaintLogJTable.getSelectedRow(), 0);
		System.out.println(issueNumber);
		ComplaintLog complaintLog = OneHashDataCache.getInstance().getComplaintLogByIssueNo(issueNumber, (ArrayList<ComplaintLog>) complaintLogs);
		EditComplaintDialog editCompalintDialog = new EditComplaintDialog(this, complaintLog);
		System.out.println(complaintLog.getIssueDescription());
		
		editCompalintDialog.pack();
		editCompalintDialog.setVisible (true);

	}


	
	

}
