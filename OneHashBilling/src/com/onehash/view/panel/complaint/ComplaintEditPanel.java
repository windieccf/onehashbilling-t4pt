package com.onehash.view.panel.complaint;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.onehash.model.base.BaseEntity;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.dialog.AddComplaintDialog;
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
		// Add ComplaintListPanel
		JTable complaintTable = new JTable();
		complaintTable.setPreferredScrollableViewportSize(new Dimension(100, 70));
		complaintTable.setFillsViewportHeight(true);
		complaintTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		complaintTable.setModel(new OneHashTableModel(this.getComplaintTableColumnNames() , this.getComplaintData(this.customer)));
		complaintTable.addMouseListener(new MouseTableListener(this,"loadEditScreen"));
        
        JScrollPane complaintScrollPane = new JScrollPane(complaintTable);
        complaintScrollPane.setBounds(20,20,400,115);
		super.registerComponent(COMPLAINT_TABLE, complaintScrollPane);
		
		JButton complaintaddButton = FactoryComponent.createButton("Add", new ButtonAttributeScalar(20, 150, 100, 23 , new ButtonActionListener(this,"addComplaintLog")));
		super.registerComponent(COMPLAINT_BUTTON_ADD , complaintaddButton);
		
		JButton complaintremoveButton = FactoryComponent.createButton("Remove", new ButtonAttributeScalar(140, 150, 100, 23 , new ButtonActionListener(this,"removeComplaintLog")));
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
		// TODO :: perform init
		
	}

	@Override
	public void setSelectedEntity(BaseEntity baseEntity) {this.customer = (Customer) baseEntity;}
	
	
	
	public String[] getComplaintTableColumnNames(){
		return new String[]{"Issue No.", "Complaint Date" , "Closed Date", "Status"};
	}
	
	public Object[][] getComplaintData(Customer customer){
		System.out.println("come here"+customer.getAccountNumber());
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
