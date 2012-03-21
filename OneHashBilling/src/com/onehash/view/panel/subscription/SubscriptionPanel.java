package com.onehash.view.panel.subscription;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.onehash.constant.ConstantSummary;
import com.onehash.controller.OneHashDataCache;
import com.onehash.exception.BusinessLogicException;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.service.plan.CableTvPlan;
import com.onehash.model.service.plan.DigitalVoicePlan;
import com.onehash.model.service.plan.MobileVoicePlan;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.comboboxitem.ComboBoxItem;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BaseOperationImpl;
import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.customer.CustomerListPanel;
import com.onehash.view.panel.customer.impl.CustomerTabInterface;

@SuppressWarnings("serial")
public class SubscriptionPanel extends BasePanel implements BaseOperationImpl , CustomerTabInterface{
	
	private static final String LBL_SERVICE_PLAN = "LBL_SERVICE_PLAN";
	private static final String LBL_SERVICE_RATE = "LBL_SERVICE_RATE";
	
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
	
	private static final String SERVICEPLAN_LABEL_STARTDATE = "SERVICEPLAN_LABEL_STARTDATE";
	private static final String SERVICEPLAN_LABEL_MONTH = "SERVICEPLAN_LABEL_MONTH";
	private static final String SERVICEPLAN_COMBOBOX_MONTH = "SERVICEPLAN_COMBOBOX_MONTH";
	private static final String SERVICEPLAN_LABEL_YEAR = "SERVICEPLAN_LABEL_YEAR";
	private static final String SERVICEPLAN_COMBOBOX_YEAR = "SERVICEPLAN_COMBOBOX_YEAR";
	
	private List<ServicePlan> servicePlans = new ArrayList<ServicePlan>();

	private ServicePlan selectedServicePlan;
	private String servicePlanMode;
	private static final String SERVICEPLAN_EDIT_MODE = "SERVICEPLAN_EDIT_MODE";
	private static final String SERVICEPLAN_CREATE_MODE = "SERVICEPLAN_CREATE_MODE";

	private List<ServiceRate> selectedServiceRates = new ArrayList<ServiceRate>();
	
	private final static Vector<ComboBoxItem> servicePlanList;

	private Customer customer  = new Customer(); // for data binding
	
	public SubscriptionPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	
	static{
		servicePlanList = new Vector<ComboBoxItem>();
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_DIGITAL_VOICE, ConstantSummary.DigitalVoice));
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_MOBILE_VOICE, ConstantSummary.MobileVoice));
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_CABLE_TV, ConstantSummary.CableTV));
	}
	
	@Override
	public void draw(){
		super.draw();
		this.showServicePlanListPage();
	}
	
	@Override
	protected void init() {
		this.customer  = new Customer();
		this.registerListingComponent();
		this.registerMaintenanceComponent();
	}
	
	private void registerListingComponent(){
		/************************************ Subscription Listing****************************/
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(100, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , this.getData()));
        table.addMouseListener(new MouseTableListener(this,"loadServicePlanEditScreen"));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,20,400,215);
		super.registerComponent(SERVICEPLAN_TABLE, scrollPane);
		
		JButton addButton = FactoryComponent.createButton("Add", new ButtonAttributeScalar(20, 250, 100, 23 , new ButtonActionListener(this,"addServicePlan")));
		super.registerComponent(SERVICEPLAN_BUTTON_ADD , addButton);
		
		JButton removeButton = FactoryComponent.createButton("Remove", new ButtonAttributeScalar(150, 250, 100, 23 , new ButtonActionListener(this,"removeServicePlan")));
		super.registerComponent(SERVICEPLAN_BUTTON_REM , removeButton);
		/************************************ Subscription Listing****************************/
	}
	
	private void registerMaintenanceComponent(){
		super.registerComponent(LBL_SERVICE_PLAN, FactoryComponent.createLabel("Service Plan", new PositionScalar(20,15,100,10)));
		
		JComboBox servicePlanSelection = FactoryComponent.createComboBox(servicePlanList, new ButtonAttributeScalar(120, 10, 100, 23 , new ButtonActionListener(this,"updateServiceRateBySelectedServicePlan")));
		super.registerComponent(SERVICEPLAN_COMBOBOX_SELECTION , servicePlanSelection);
		
		super.registerComponent(SERVICEPLAN_LABEL_STARTDATE , FactoryComponent.createLabel("Start date:", new PositionScalar(20,50,100,10)));
		super.getComponent(SERVICEPLAN_LABEL_STARTDATE).setVisible(false);
		
        super.registerComponent(SERVICEPLAN_LABEL_MONTH , FactoryComponent.createLabel("Month :", new PositionScalar(120,50,100,10)));
		super.getComponent(SERVICEPLAN_LABEL_MONTH).setVisible(false);
		

		final Integer[] months = new Integer[12];
        for(int i=0;i<months.length;i++){
        	months[i] = (i+1);
        }
        JComboBox monthSelector = new JComboBox(months);
        monthSelector.setBounds(180, 45, 50, 20);
        monthSelector.setUI(new BasicComboBoxUI() { 
        	@Override 
     	    protected JButton createArrowButton() { 
     	        return new BasicArrowButton(BasicArrowButton.SOUTH); 
     	    } 
        }); 
        super.registerComponent(SERVICEPLAN_COMBOBOX_MONTH , monthSelector);
		super.getComponent(SERVICEPLAN_COMBOBOX_MONTH).setVisible(false);
        
		
        super.registerComponent(SERVICEPLAN_LABEL_YEAR , FactoryComponent.createLabel("Year :", new PositionScalar(250, 50, 50, 10)));
		super.getComponent(SERVICEPLAN_LABEL_YEAR).setVisible(false);
        JComboBox yearSelector = new JComboBox();
        Integer[] years = new Integer[100];
        for (int i=2000; i<2100; i++)
        	years[i-2000] = i;
        for (int i = 0; i < years.length; i++) {
        	yearSelector.addItem(years[i]);
        }
        yearSelector.setBounds(300, 45, 50, 20);
        yearSelector.setUI(new BasicComboBoxUI() { 
        	@Override 
     	    protected JButton createArrowButton() { 
     	        return new BasicArrowButton(BasicArrowButton.SOUTH); 
     	    } 
        }); 
        super.registerComponent(SERVICEPLAN_COMBOBOX_YEAR , yearSelector);
		super.getComponent(SERVICEPLAN_COMBOBOX_YEAR).setVisible(false);
		
		
		
		
		super.registerComponent(LBL_SERVICE_RATE, FactoryComponent.createLabel("Available Rates", new PositionScalar(20,80,100,10)));
		
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(new OneHashTableModel(this.getSelectedServiceRateColumnNames() , this.getSelectedServiceRate()));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,100, 200,185);
		super.registerComponent(SERVICEPLAN_LIST_SELECTED, scrollPane);
		
		super.registerComponent(SERVICEPLAN_BUTTON_ADD_OPTIONS , FactoryComponent.createButton("<<", new ButtonAttributeScalar(235, 130, 50, 23 , new ButtonActionListener(this,"addOptions"))));
		super.registerComponent(SERVICEPLAN_BUTTON_REMOVE_OPTIONS , FactoryComponent.createButton(">>", new ButtonAttributeScalar(235, 160, 50, 23 , new ButtonActionListener(this,"removeOptions"))));
		

		table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(new OneHashTableModel(this.getAvailableServiceRateColumnNames() , this.getAvailableServiceRate()));
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300,100,200,185);
        
		super.registerComponent(SERVICEPLAN_LIST_AVAILABLE, scrollPane);

		super.registerComponent(SERVICEPLAN_BUTTON_SAVE_SERVICE_PLAN , FactoryComponent.createButton("Save Subscription", new ButtonAttributeScalar(20, 300, 150, 23 , new ButtonActionListener(this,"saveServicePlan"))));

		super.registerComponent(SERVICEPLAN_BUTTON_CANCEL_SERVICE_PLAN , FactoryComponent.createButton("Cancel", new ButtonAttributeScalar(180, 300, 100, 23 , new ButtonActionListener(this,"showServicePlanListPage"))));
		
		
	}
	
	
	@Override
	protected String getScreenTitle() {return "Subscription Panel";}
	
	@Override
	protected boolean isEnableHeader(){return false;}
	
	@Override
	public BaseEntity getSelectedEntity() {return this.customer;}
	
	
	@Override
	public void initializeCustomer(Customer customer){
		this.customer = customer;
		this.servicePlans = customer.getServicePlans();
		this.refreshServicePlansJTable();
	}
	
	/***************************************** BUTTON LISTENER *****************************************************/
	public void cancel(){
		this.getMainFrame().doLoadScreen(CustomerListPanel.class);
	}

	public void refreshServicePlansJTable() {
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(100, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , this.getData()));
        table.addMouseListener(new MouseTableListener(this,"loadServicePlanEditScreen"));
		JScrollPane servicePlansScrollPane = (JScrollPane) super.getComponent(SERVICEPLAN_TABLE);
		servicePlansScrollPane.setViewportView(table);
	}
	
	public void addServicePlan() {
		servicePlanMode = SERVICEPLAN_CREATE_MODE;
		JComboBox component = (JComboBox)super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION);
		component.setEnabled(true);
		showServicePlanMaintenancePage();
	}
	
	public void removeServicePlan() {
		JScrollPane selectedServicePlanScrollPane = (JScrollPane)super.getComponent(SERVICEPLAN_TABLE);
		JTable ServicePlanJTable = (JTable)selectedServicePlanScrollPane.getViewport().getView();
		/**
		 * Disable removing permanent ServicePlan, flag to deleted 
		 */
		//servicePlans.remove(ServicePlanJTable.getSelectedRow());
		servicePlans.get(ServicePlanJTable.getSelectedRow()).setDeletedStatus(true);
		refreshServicePlansJTable();
	}

	public void showServicePlanListPage(){
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
		super.getComponent(LBL_SERVICE_PLAN).setVisible(false);
		super.getComponent(LBL_SERVICE_RATE).setVisible(false);

		super.getComponent(SERVICEPLAN_LABEL_STARTDATE).setVisible(false);
		super.getComponent(SERVICEPLAN_LABEL_MONTH).setVisible(false);
		super.getComponent(SERVICEPLAN_LABEL_YEAR).setVisible(false);
		super.getComponent(SERVICEPLAN_COMBOBOX_MONTH).setVisible(false);
		super.getComponent(SERVICEPLAN_COMBOBOX_YEAR).setVisible(false);
		
	}
	
	public void showServicePlanMaintenancePage(){
		this.updateSelectedServiceRate();
		this.updateAvailableServiceRate();
		this.updateSubscriptionField();
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
		super.getComponent(LBL_SERVICE_PLAN).setVisible(true);
		super.getComponent(LBL_SERVICE_RATE).setVisible(true);
		
		super.getComponent(SERVICEPLAN_LABEL_STARTDATE).setVisible(true);
		super.getComponent(SERVICEPLAN_LABEL_MONTH).setVisible(true);
		super.getComponent(SERVICEPLAN_LABEL_YEAR).setVisible(true);
		super.getComponent(SERVICEPLAN_COMBOBOX_MONTH).setVisible(true);
		super.getComponent(SERVICEPLAN_COMBOBOX_YEAR).setVisible(true);
	}
	
	public void saveServicePlan() throws Exception {
		try {
			JComboBox component = (JComboBox)super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION);
			ComboBoxItem prefix = (ComboBoxItem)component.getSelectedItem();
			/**
			 * Determine New or Edit ServicePlan
			 */
			if (servicePlanMode.equals(SERVICEPLAN_CREATE_MODE)) {
				if (prefix.getKey().equals(ServiceRate.PREFIX_MOBILE_VOICE)) {
					selectedServicePlan = new MobileVoicePlan();
				}
				else if (prefix.getKey().equals(ServiceRate.PREFIX_DIGITAL_VOICE)) {
					selectedServicePlan = new DigitalVoicePlan();
				}
				else {
					selectedServicePlan = new CableTvPlan();
				}
				selectedServicePlan.setPlanId(prefix.getKey() + (this.customer.getServicePlans().size()+1));
				selectedServicePlan.setPlanName(prefix.getValue());
				servicePlans.add(selectedServicePlan);
			}
			else {
				for(ServicePlan servicePlan:servicePlans) {
					if (servicePlan.getPlanId().equals(selectedServicePlan.getPlanId())) {
						servicePlan = selectedServicePlan;
						break;
					}
				}
			}
			
			Calendar calendar = Calendar.getInstance();
			Integer selectedYear = (Integer) super.getComboBoxComponent(SERVICEPLAN_COMBOBOX_YEAR).getSelectedItem();//(Integer) yearComboBox.getSelectedItem();
			Integer selectedMonth = (Integer) super.getComboBoxComponent(SERVICEPLAN_COMBOBOX_MONTH).getSelectedItem(); //(Integer) monthComboBox.getSelectedItem();
			
			Integer selectedDayOfMonth = 1;
			
			// fix on the selected month, by default java recognise JAN as 0.
			calendar.set(selectedYear , selectedMonth - 1 , selectedDayOfMonth);
			selectedServicePlan.setStartDate(calendar.getTime());
			
			selectedServicePlan.setServiceRates(selectedServiceRates);
			//use the Save Customer button
			//OneHashDataCache.getInstance().saveCustomer(this.customer);
			//JOptionPane.showMessageDialog(this, "Subscriptions Successfully Saved");
			
			this.refreshServicePlansJTable();
			this.showServicePlanListPage();
			
			// added by robin for dynamic object binding
			this.customer.setServicePlans(servicePlans);
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
	
	private void updateSubscriptionField(){
		if(selectedServicePlan!=null &&  selectedServicePlan.getStartDate() != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(selectedServicePlan.getStartDate());
			super.getComboBoxComponent(SERVICEPLAN_COMBOBOX_YEAR).setSelectedItem(cal.get(Calendar.YEAR));
			super.getComboBoxComponent(SERVICEPLAN_COMBOBOX_MONTH).setSelectedItem(cal.get(Calendar.MONTH) + 1);
			
		}
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

	/**************************** REFLECTION UTILITY **********************************/
	public void loadServicePlanEditScreen(String parameter){
		servicePlanMode = SERVICEPLAN_EDIT_MODE;
		selectedServicePlan = null;
		JComboBox component = (JComboBox)super.getComponent(SERVICEPLAN_COMBOBOX_SELECTION);
		int componentCount = component.getItemCount();
		for(int i=0; i<componentCount; i++) {
			ComboBoxItem comboBoxItem = (ComboBoxItem) component.getItemAt(i);
			if (comboBoxItem.getKey().equals(parameter.substring(0, comboBoxItem.getKey().length()))) {
				component.setSelectedIndex(i);
				break;
			}
		}
		component.setEnabled(false);
		for(ServicePlan servicePlan:servicePlans) {
			if (servicePlan.getPlanId().equals(parameter)) {
				selectedServicePlan = servicePlan;
				break;
			}
		}
		if (selectedServicePlan == null)
			throw new IllegalArgumentException("Could not find the selected ServicePlan : " + parameter);
		selectedServiceRates = selectedServicePlan.getServiceRates();
		this.showServicePlanMaintenancePage();
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

	
	@Override
	public void setSelectedEntity(BaseEntity baseEntity) {
		this.customer = (Customer) baseEntity;
	}


}
