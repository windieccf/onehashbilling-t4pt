package com.onehash.view.panel.master.subscriptionplan;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.onehash.constant.ConstantAction;
import com.onehash.constant.ConstantSummary;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.scalar.ButtonAttributeScalar;
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
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class ServicePlanListPanel extends BasePanel{
	private static final String SERVICEPLANLIST_TABLE = "SERVICEPLANLIST_TABLE";
	private static final String SERVICEPLANLIST_BUTTON_ADD = "SERVICEPLANLIST_BUTTON_ADD";
	private static final String SERVICEPLANLIST_BUTTON_REM = "SERVICEPLANLIST_BUTTON_REM";
	private static final String SERVICEPLANLIST_COMBOBOX_SELECTION = "SERVICEPLANLIST_COMBOBOX_SELECTION";
	private static final String SERVICEPLANLIST_LIST_AVAILABLE = "SERVICEPLAN_LIST_AVAILABLE";

	private static final String SERVICEPLANLIST_BUTTON_NAME_CREATE = "SERVICEPLANLIST_BUTTON_NAME_CREATE";

	private List<ServiceRate> selectedServiceRates = new ArrayList<ServiceRate>();
	private Boolean enableEditing = true;
	private final static Vector<ComboBoxItem> servicePlanList;
	
	public ServicePlanListPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	
	static{
		servicePlanList = new Vector<ComboBoxItem>();
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_DIGITAL_VOICE, ConstantSummary.DigitalVoice));
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_MOBILE_VOICE, ConstantSummary.MobileVoice));
		servicePlanList.add(new ComboBoxItem(ServiceRate.PREFIX_CABLE_TV, ConstantSummary.CableTV));
	}

	@Override
	protected void init() {
		JComboBox servicePlanSelection = FactoryComponent.createComboBox(servicePlanList, new ButtonAttributeScalar(15, 20, 100, 23 , new ButtonActionListener(this,"updateServiceRateBySelectedServicePlan")));
		super.registerComponent(SERVICEPLANLIST_COMBOBOX_SELECTION , servicePlanSelection);

		JTable table = new JTable();
		table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(new OneHashTableModel(this.getAvailableServiceRateColumnNames() , this.getAvailableServiceRate()));
        table.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15,50,200,185);
        
		super.registerComponent(SERVICEPLANLIST_LIST_AVAILABLE, scrollPane);
		super.registerComponent(SERVICEPLANLIST_BUTTON_NAME_CREATE, FactoryComponent.createButton("Create", new ButtonAttributeScalar(15, 250, 100, 23 , new ButtonActionListener(this,"loadAddScreen"))));
	}

	public void updateServiceRateBySelectedServicePlan() {
		this.selectedServiceRates = new ArrayList<ServiceRate>();
		this.updateAvailableServiceRate();
	}
	
	public void updateAvailableServiceRate() {
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getAvailableServiceRateColumnNames() , this.getAvailableServiceRate()));
        table.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
		JScrollPane scrollPane = (JScrollPane) super.getComponent(SERVICEPLANLIST_LIST_AVAILABLE);
		scrollPane.setViewportView(table);
	}

	@Override
	protected String getScreenTitle() {return "Service Plan List";}

	/**************************** REFLECTION UTILITY **********************************/
	public void loadAddScreen(){
		//if (this.enableEditing) {
			JComboBox component = (JComboBox)super.getComponent(SERVICEPLANLIST_COMBOBOX_SELECTION);
			ComboBoxItem prefix = (ComboBoxItem)component.getSelectedItem();
			this.getMainFrame().doLoadScreen(ServicePlanEditPanel.class, ConstantAction.ADD,prefix.getKey());
		//}
	}
	public void loadEditScreen(String parameter){
		//if (this.enableEditing) {
			JComboBox component = (JComboBox)super.getComponent(SERVICEPLANLIST_COMBOBOX_SELECTION);
			ComboBoxItem prefix = (ComboBoxItem)component.getSelectedItem();
			this.getMainFrame().doLoadScreen(ServicePlanEditPanel.class, ConstantAction.EDIT,parameter,prefix.getKey());
		//}
	}
	
	/******************************** TABLE UTILITY******************************************/
	public Object[][] getData(){
		Object[][] rowData = new String[3][1];
		ArrayList<ServicePlan> servicePlans;
		if (OneHashDataCache.getInstance().getAvailableServicePlan() == null)
			servicePlans = new ArrayList<ServicePlan>();
		else
			servicePlans = new ArrayList<ServicePlan>(OneHashDataCache.getInstance().getAvailableServicePlan());
		if (servicePlans.isEmpty()) {
			ServicePlan servicePlan;
			servicePlan = new DigitalVoicePlan();
			servicePlan.setServiceRates(new ArrayList<ServiceRate>());
			servicePlan.setPlanName(ConstantSummary.DigitalVoice);
			servicePlan.setPlanCode(ServiceRate.PREFIX_DIGITAL_VOICE);
			servicePlans.add(servicePlan);
			
			servicePlan = new MobileVoicePlan();
			servicePlan.setServiceRates(new ArrayList<ServiceRate>());
			servicePlan.setPlanName(ConstantSummary.MobileVoice);
			servicePlan.setPlanCode(ServiceRate.PREFIX_MOBILE_VOICE);
			servicePlans.add(servicePlan);
			
			servicePlan = new CableTvPlan();
			servicePlan.setServiceRates(new ArrayList<ServiceRate>());
			servicePlan.setPlanName(ConstantSummary.CableTV);
			servicePlan.setPlanCode(ServiceRate.PREFIX_CABLE_TV);
			servicePlans.add(servicePlan);
			
			OneHashDataCache.getInstance().setAvailableServicePlan(servicePlans);
		}
		int i = 0;
		for(ServicePlan servicePlan:servicePlans) {
			rowData[i][0] = servicePlan.getPlanName();
			i++;
		}
		return rowData;
	}
	
	public Object[][] getAvailableServiceRate() {
		Object[][] rowData = new String[1][1];
		List<ServiceRate> serviceRates = OneHashDataCache.getInstance().getAvailableServiceRate();
		rowData = new String[serviceRates.toArray().length][1];

		ArrayList<String> _availableServiceRate = new ArrayList<String>();
		JComboBox component = (JComboBox)super.getComponent(SERVICEPLANLIST_COMBOBOX_SELECTION);
		ComboBoxItem prefix = (ComboBoxItem)component.getSelectedItem();
		HashSet<ServiceRate> selectedServiceRatesHashMap = new HashSet<ServiceRate>();
				
		int maxIdx = 0, idx = 0;
		for(ServiceRate serviceRate:serviceRates) {
			if (!serviceRate.getRateCode().startsWith(prefix.getKey()) || selectedServiceRatesHashMap.contains(serviceRate))
				continue;
			_availableServiceRate.add(serviceRate.getRateCode()+": "+serviceRate.getRateDescription());
			maxIdx++;
		}

		/*ServicePlan selectedServicePlan = new MobileVoicePlan();
		for (ServicePlan servicePlan:OneHashDataCache.getInstance().getAvailableServicePlan()) {
			if (servicePlan.getPlanCode().startsWith(prefix.getKey())) {
				selectedServicePlan = servicePlan;
				break;
			}
		}
		ArrayList<ServiceRate> servicePlan_serviceRates;
		if (selectedServicePlan.getServiceRates() == null) {
			servicePlan_serviceRates = new ArrayList<ServiceRate>();
			for(ServiceRate serviceRate:serviceRates) {
				if (!serviceRate.getRateCode().startsWith(prefix.getKey()) || selectedServiceRatesHashMap.contains(serviceRate))
					continue;
				servicePlan_serviceRates.add(serviceRate);
			}
			selectedServicePlan.setServiceRates(servicePlan_serviceRates);
		}
		*/
		
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
	
	@Override
	protected void initiateAccessRights() {
		this.enableEditing = true;
		/*if(!OneHashDataCache.getInstance().getCurrentUser().hasRights(EnumUserAccess.CUSTOMER_UPDATE))
			this.enableEditing = true;
		else
			this.enableEditing = false;
		*/
	}

}
