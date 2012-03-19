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
 * 19 March 2012	Chen Changfeng	0.4				Complaint module start													
 * 
 */

package com.onehash.view.panel.customer;


import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantAction;
import com.onehash.constant.ConstantSummary;
import com.onehash.controller.OneHashDataCache;
import com.onehash.exception.BusinessLogicException;
import com.onehash.exception.InsufficientInputParameterException;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.CheckboxAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.model.service.plan.CableTvPlan;
import com.onehash.model.service.plan.DigitalVoicePlan;
import com.onehash.model.service.plan.MobileVoicePlan;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.comboboxitem.ComboBoxItem;
import com.onehash.view.component.dialog.AddComplaintDialog;
import com.onehash.view.component.listener.BooleanCheckBoxListener;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.listener.OneHashTextFieldListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BaseOperationImpl;
import com.onehash.view.panel.base.BasePanel;

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
		
	private static final String SERVICEPLAN_TABLE = "SERVICEPLAN_TABLE";
	private static final String SERVICEPLAN_BUTTON_ADD = "SERVICEPLAN_BUTTON_ADD";
	private static final String SERVICEPLAN_BUTTON_REM = "SERVICEPLAN_BUTTON_REM";
	private static final String SERVICEPLAN_COMBOBOX_SELECTION = "SERVICEPLAN_COMBOBOX_SELECTION";
	private static final String SERVICEPLAN_LIST_SELECTED = "SERVICEPLAN_LIST_SELECTED";
	private static final String SERVICEPLAN_LIST_AVAILABLE = "SERVICEPLAN_LIST_AVAILABLE";
	private static final String SERVICEPLAN_BUTTON_SAVE_SERVICE_PLAN = "SERVICEPLAN_BUTTON_SAVE_SERVICE_PLAN";
	private static final String SERVICEPLAN_BUTTON_CANCEL_SERVICE_PLAN = "SERVICEPLAN_BUTTON_CANCEL_SERVICE_PLAN";
	private static final String SERVICEPLAN_BUTTON_ADD_OPTIONS = "SERVICEPLAN_BUTTON_ADD_OPTIONS";
	private static final String SERVICEPLAN_BUTTON_REMOVE_OPTIONS = "SERVICEPLAN_BUTTON_REMOVE_OPTIONS";
	
	//Complaint Module
	private static final String COMPLAINT_TABLE = "COMPLAINT_TABLE";
	private static final String COMPLAINT_BUTTON_ADD = "COMPLAINT_BUTTON_ADD";
	private static final String COMPLAINT_BUTTON_REM = "COMPLAINT_BUTTON_REM";
	
	private List<ComplaintLog> complaintLogs = new ArrayList<ComplaintLog>();
	
	private List<ServiceRate> selectedServiceRates = new ArrayList<ServiceRate>();

	private List<ServicePlan> servicePlans = new ArrayList<ServicePlan>();
	

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
		

		// Add ServicePlanListPanel
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(100, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , this.getData()));
        table.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(390,20,400,215);
		super.registerComponent(SERVICEPLAN_TABLE, scrollPane);
		JButton addButton = FactoryComponent.createButton("Add", new ButtonAttributeScalar(570, 250, 100, 23 , new ButtonActionListener(this,"addServicePlan")));
		super.registerComponent(SERVICEPLAN_BUTTON_ADD , addButton);
		JButton removeButton = FactoryComponent.createButton("Remove", new ButtonAttributeScalar(690, 250, 100, 23 , new ButtonActionListener(this,"removeServicePlan")));
		super.registerComponent(SERVICEPLAN_BUTTON_REM , removeButton);

		
		Vector<ComboBoxItem> servicePlanList = new Vector<ComboBoxItem>();
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_DIGITAL_VOICE, ConstantSummary.DigitalVoice));
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_MOBILE_VOICE, ConstantSummary.MobileVoice));
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_CABLE_TV, ConstantSummary.CableTV));

		JComboBox servicePlanSelection = FactoryComponent.createComboBox(servicePlanList, new ButtonAttributeScalar(390, 20, 100, 23 , new ButtonActionListener(this,"updateServiceRateBySelectedServicePlan")));
		super.registerComponent(SERVICEPLAN_COMBOBOX_SELECTION , servicePlanSelection);
		super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION).setVisible(false);
		
		table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getSelectedServiceRateColumnNames() , this.getSelectedServiceRate()));
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(390,50,200,185);
		super.registerComponent(SERVICEPLAN_LIST_SELECTED, scrollPane);
		super.getComponent(SERVICEPLAN_LIST_SELECTED).setVisible(false);

		table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getAvailableServiceRateColumnNames() , this.getAvailableServiceRate()));
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(600,50,200,185);
		super.registerComponent(SERVICEPLAN_LIST_AVAILABLE, scrollPane);
		super.getComponent(SERVICEPLAN_LIST_AVAILABLE).setVisible(false);

		super.registerComponent(SERVICEPLAN_BUTTON_SAVE_SERVICE_PLAN , FactoryComponent.createButton("Save Subscription", new ButtonAttributeScalar(520, 20, 150, 23 , new ButtonActionListener(this,"saveServicePlan"))));
		super.getComponent(SERVICEPLAN_BUTTON_SAVE_SERVICE_PLAN).setVisible(false);

		super.registerComponent(SERVICEPLAN_BUTTON_CANCEL_SERVICE_PLAN , FactoryComponent.createButton("Cancel", new ButtonAttributeScalar(680, 20, 100, 23 , new ButtonActionListener(this,"cancelServicePlan"))));
		super.getComponent(SERVICEPLAN_BUTTON_CANCEL_SERVICE_PLAN).setVisible(false);

		super.registerComponent(SERVICEPLAN_BUTTON_ADD_OPTIONS , FactoryComponent.createButton("Add Option", new ButtonAttributeScalar(600, 250, 120, 23 , new ButtonActionListener(this,"addOptions"))));
		super.getComponent(SERVICEPLAN_BUTTON_ADD_OPTIONS).setVisible(false);

		super.registerComponent(SERVICEPLAN_BUTTON_REMOVE_OPTIONS , FactoryComponent.createButton("Remove Option", new ButtonAttributeScalar(390, 250, 120, 23 , new ButtonActionListener(this,"removeOptions"))));
		super.getComponent(SERVICEPLAN_BUTTON_REMOVE_OPTIONS).setVisible(false);
		
		// Add ComplaintListPanel
		JTable complaintTable = new JTable();
		complaintTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
		complaintTable.setFillsViewportHeight(true);
		complaintTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		complaintTable.setModel(new OneHashTableModel(this.getComplaintTableColumnNames() , this.getComplaintData(this.customer)));
		complaintTable.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
        
        JScrollPane complaintScrollPane = new JScrollPane(complaintTable);
        complaintScrollPane.setBounds(390,280,400,115);
		super.registerComponent(COMPLAINT_TABLE, complaintScrollPane);
		JButton complaintaddButton = FactoryComponent.createButton("Add", new ButtonAttributeScalar(570, 400, 100, 23 , new ButtonActionListener(this,"addComplaintLog")));
		super.registerComponent(COMPLAINT_BUTTON_ADD , complaintaddButton);
		JButton complaintremoveButton = FactoryComponent.createButton("Remove", new ButtonAttributeScalar(690, 400, 100, 23 , new ButtonActionListener(this,"removeComplaintLog")));
		super.registerComponent(COMPLAINT_BUTTON_REM , complaintremoveButton);



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
	
	public void addServicePlan() {
		this.updateSelectedServiceRate();
		this.updateAvailableServiceRate();
		super.getComponent(SERVICEPLAN_TABLE).setVisible(false);
		super.getComponent(SERVICEPLAN_BUTTON_ADD).setVisible(false);
		super.getComponent(SERVICEPLAN_BUTTON_REM).setVisible(false);
		super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION).setVisible(true);
		super.getComponent(SERVICEPLAN_LIST_SELECTED).setVisible(true);
		super.getComponent(SERVICEPLAN_LIST_AVAILABLE).setVisible(true);
		super.getComponent(SERVICEPLAN_BUTTON_SAVE_SERVICE_PLAN).setVisible(true);
		super.getComponent(SERVICEPLAN_BUTTON_CANCEL_SERVICE_PLAN).setVisible(true);
		super.getComponent(SERVICEPLAN_BUTTON_ADD_OPTIONS).setVisible(true);
		super.getComponent(SERVICEPLAN_BUTTON_REMOVE_OPTIONS).setVisible(true);
	}
	
	public void cancelServicePlan() {
		this.selectedServiceRates = null;
		super.getComponent(SERVICEPLAN_TABLE).setVisible(true);
		super.getComponent(SERVICEPLAN_BUTTON_ADD).setVisible(true);
		super.getComponent(SERVICEPLAN_BUTTON_REM).setVisible(true);
		super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION).setVisible(false);
		super.getComponent(SERVICEPLAN_LIST_SELECTED).setVisible(false);
		super.getComponent(SERVICEPLAN_LIST_AVAILABLE).setVisible(false);
		super.getComponent(SERVICEPLAN_BUTTON_SAVE_SERVICE_PLAN).setVisible(false);
		super.getComponent(SERVICEPLAN_BUTTON_CANCEL_SERVICE_PLAN).setVisible(false);
		super.getComponent(SERVICEPLAN_BUTTON_ADD_OPTIONS).setVisible(false);
		super.getComponent(SERVICEPLAN_BUTTON_REMOVE_OPTIONS).setVisible(false);
	}
	
	public void saveServicePlan() throws Exception {
		try {
			JComboBox component = (JComboBox)super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION);
			ComboBoxItem prefix = (ComboBoxItem)component.getSelectedItem();
			ServicePlan servicePlan = new MobileVoicePlan();
			if (prefix.equals(ServiceRate.PREFIX_MOBILE_VOICE))
				servicePlan = new MobileVoicePlan();
			else if (prefix.equals(ServiceRate.PREFIX_DIGITAL_VOICE))
				servicePlan = new DigitalVoicePlan();
			else if (prefix.equals(ServiceRate.PREFIX_CABLE_TV))
				servicePlan = new CableTvPlan();
			servicePlan.setPlanName(prefix.getValue());
			servicePlan.setServiceRates(selectedServiceRates);
			this.customer.addServicePlan(servicePlan);
			OneHashDataCache.getInstance().saveCustomer(this.customer);
			JOptionPane.showMessageDialog(this, "Subscriptions Successfully Saved");
			this.cancelServicePlan();
		}catch(Exception e){
			if(e instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
			throw e;
		}
	}

	public void updateServiceRateBySelectedServicePlan() {
		this.selectedServiceRates = new ArrayList<ServiceRate>();
		this.updateSelectedServiceRate();
		this.updateAvailableServiceRate();
	}
	
	public void updateAvailableServiceRate() {
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getAvailableServiceRateColumnNames() , this.getAvailableServiceRate()));
		JScrollPane scrollPane = (JScrollPane) super.getComponent(SERVICEPLAN_LIST_AVAILABLE);
		scrollPane.setViewportView(table);
	}
	public void updateSelectedServiceRate() {
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getSelectedServiceRateColumnNames() , this.getSelectedServiceRate()));
		JScrollPane scrollPane = (JScrollPane) super.getComponent(SERVICEPLAN_LIST_SELECTED);
		scrollPane.setViewportView(table);
	}
	
	public void addOptions() {
		JScrollPane availableOptionsScrollPane = (JScrollPane)super.getComponent(SERVICEPLAN_LIST_AVAILABLE);
		JTable availableOptionsJTable = (JTable)availableOptionsScrollPane.getViewport().getView();

		HashSet<ServiceRate> selectedServiceRatesHashMap = new HashSet<ServiceRate>(selectedServiceRates);
		JScrollPane selectedOptionsScrollPane = (JScrollPane)super.getComponent(SERVICEPLAN_LIST_SELECTED);

		if (availableOptionsJTable.getSelectedRow() >= 0 && availableOptionsJTable.getSelectedColumn() >= 0) {
			String selectedValue = (String) availableOptionsJTable.getValueAt(availableOptionsJTable.getSelectedRow(), availableOptionsJTable.getSelectedColumn());
			for (ServiceRate serviceRate:OneHashDataCache.getInstance().getAvailableServiceRate()) {
				if (selectedValue.equals(serviceRate.getRateCode()+": "+serviceRate.getRateDescription()) && !selectedServiceRatesHashMap.contains(serviceRate)) {
					selectedServiceRates.add(serviceRate);
					break;
				}
			}
		}

		JTable newTable = new JTable();
		newTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
        newTable.setFillsViewportHeight(true);
        newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        newTable.setModel(new OneHashTableModel(this.getSelectedServiceRateColumnNames() , this.getSelectedServiceRate()));
		selectedOptionsScrollPane.setViewportView(newTable);
		updateAvailableServiceRate();
	}
	
	public void removeOptions() {
		JScrollPane selectedOptionsScrollPane = (JScrollPane)super.getComponent(SERVICEPLAN_LIST_SELECTED);
		JTable selectedOptionsJTable = (JTable)selectedOptionsScrollPane.getViewport().getView();

		if (selectedOptionsJTable.getSelectedRow() >= 0 && selectedOptionsJTable.getSelectedColumn() >= 0) {
			String selectedValue = (String) selectedOptionsJTable.getValueAt(selectedOptionsJTable.getSelectedRow(), selectedOptionsJTable.getSelectedColumn());
			int selectedServiceRateSize = selectedServiceRates.size();
			for (int i=0; i<selectedServiceRateSize; i++) {
				ServiceRate sr = selectedServiceRates.get(i);
				if (selectedValue.equals(sr.getRateCode()+": "+sr.getRateDescription())) {
					selectedServiceRates.remove(i);
					break;
				}
			}
		}

		JTable newTable = new JTable();
		newTable.setPreferredScrollableViewportSize(new Dimension(200, 70));
        newTable.setFillsViewportHeight(true);
        newTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        newTable.setModel(new OneHashTableModel(this.getSelectedServiceRateColumnNames() , this.getSelectedServiceRate()));
		selectedOptionsScrollPane.setViewportView(newTable);
		updateAvailableServiceRate();
	}

	/******************************** TABLE UTILITY******************************************/
	public Object[][] getData(){
		Object[][] rowData = new String[1][4];
		if (servicePlans == null)
			servicePlans = new ArrayList<ServicePlan>();
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
	
	public String[] getSelectedServiceRateColumnNames(){
		return new String[]{"Selected Options"};
	}
	public Object[][] getSelectedServiceRate() {
		Object[][] rowData = new String[1][1];
		if (selectedServiceRates == null)
			selectedServiceRates = new ArrayList<ServiceRate>();
		rowData = new String[selectedServiceRates.size()][1];
		int idx = 0;
		for(ServiceRate serviceRate:selectedServiceRates) {
			rowData[idx][0] = serviceRate.getRateCode()+": "+serviceRate.getRateDescription();
			idx++;
		}
		return rowData;
	}
	
	public Object[][] getAvailableServiceRate() {
		Object[][] rowData = new String[1][1];
		List<ServiceRate> serviceRates = OneHashDataCache.getInstance().getAvailableServiceRate();
		rowData = new String[serviceRates.toArray().length][1];

		ArrayList<String> _availableServiceRate = new ArrayList<String>();
		JComboBox component = (JComboBox)super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION);
		ComboBoxItem prefix = (ComboBoxItem)component.getSelectedItem();
		HashSet<ServiceRate> selectedServiceRatesHashMap = new HashSet<ServiceRate>(selectedServiceRates);
		int maxIdx = 0, idx = 0;
		for(ServiceRate serviceRate:serviceRates) {
			if (!serviceRate.getRateCode().startsWith(prefix.getKey()) || selectedServiceRatesHashMap.contains(serviceRate))
				continue;
			_availableServiceRate.add(serviceRate.getRateCode()+": "+serviceRate.getRateDescription());
			maxIdx++;
		}
		
		rowData = new String[maxIdx][1];
		idx = 0;
		for (String s:_availableServiceRate) {
			rowData[idx][0] = s;
			idx++;
		}
		return rowData;
	}
	public String[] getAvailableServiceRateColumnNames(){
		return new String[]{"Available Options"};
	}

	public String[] getTableColumnNames(){
		return new String[]{"Subs. Num.", "Name", "Start" , "End"};
	}
	/******************************** COMPLAINT TABLE UTILITY******************************************/
	public String[] getComplaintTableColumnNames(){
		return new String[]{"Issue No.", "Complaint Date" , "Closed Date", "Status"};
	}
	
	public Object[][] getComplaintData(Customer customer){
		System.out.println("comeh ere"+customer.getAccountNumber());
		complaintLogs = customer.getComplaintLogs();
		for (ComplaintLog c : complaintLogs) {
			System.out.println(c.getIssueNo());
			
		}
		Object[][] rowData = new String[1][4];
		if (complaintLogs == null)
			complaintLogs = new ArrayList<ComplaintLog>();
		if(complaintLogs.isEmpty()){
			rowData = new Object[0][4];
		}else{
			rowData = new Object[complaintLogs.size()][4];
			for(int i = 0 ; i < complaintLogs.size(); i++){
				ComplaintLog complaintLog = complaintLogs.get(i);
				rowData[i][0] = complaintLog.getIssueNo();
				rowData[i][1] = complaintLog.getComplaintDate();
				rowData[i][2] = complaintLog.getClosedDate();
				rowData[i][3] = complaintLog.getStatus();
			}
		}
		
		return rowData;
	}
	
	public void addComplaintLog() {
		AddComplaintDialog complaintDialog = new AddComplaintDialog(this);
		complaintDialog.pack();
		complaintDialog.setVisible (true);
	}

}
