package com.onehash.view.panel.complaint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.onehash.exception.BusinessLogicException;
import com.onehash.exception.InsufficientInputParameterException;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.utility.OneHashDateUtil;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.dialog.search.CustomerLookupDialog;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.OneHashTextFieldListener;
import com.onehash.view.panel.base.BaseOperationImpl;
import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.base.CustomerOperationImpl;

@SuppressWarnings("serial")
public class ComplaintMainenancePanel extends BasePanel implements BaseOperationImpl, CustomerOperationImpl{

	private static final String COMP_LBL_ACCOUNT_NO = "COMP_LBL_ACCOUNT_NO";
	private static final String COMP_LBL_ACCOUNT = "COMP_LBL_ACCOUNT";
	private static final String COMP_LBL_ISSUE_NO_TXT = "COMP_LBL_ISSUE_NO_TXT";
	
	private static final String COMP_LBL_ISSUE_NO = "COMP_LBL_ISSUE_NO";
	private static final String COMP_LBL_DATE_FROM = "COMP_LBL_DATE_FROM";
	private static final String COMP_LBL_DATE_FROM_TXT = "COMP_LBL_DATE_FROM_TXT";
	
	private static final String COMP_LBL_DATE_TO = "COMP_LBL_DATE_TO";
	private static final String COMP_LBL_DATE_TO_TXT = "COMP_LBL_DATE_TO_TXT";
	private static final String COMP_LBL_DESCRIPTION = "COMP_LBL_DESCRIPTION";
	private static final String COMP_INPUT_DESCRIPTION = "COMP_INPUT_DESCRIPTION";
	
	private static final String COMP_BUTTON_SEARCH = "COMP_BUTTON_SEARCH";
	private static final String COMP_BUTTON_SAVE = "COMP_BUTTON_SAVE";
	private static final String COMP_BUTTON_BACK = "COMP_BUTTON_BACK";
	private static final String COMP_BUTTON_CLOSE_CASE = "COMP_BUTTON_CLOSE_CASE";


	
	
	public ComplaintMainenancePanel(OneHashGui mainFrame) {super(mainFrame);}

	private ComplaintLog complaintLog = new ComplaintLog();
	private Customer selectedCustomer = new Customer();
	
	public Customer getSelectedCustomer() {return selectedCustomer;}
	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
		super.getLabelComponent(COMP_LBL_ACCOUNT).setText(selectedCustomer.getAccountNumber());
	}

	@Override
	public BaseEntity getSelectedEntity() {return complaintLog;}

	@Override
	public void setSelectedEntity(BaseEntity baseEntity) {this.complaintLog = (ComplaintLog)complaintLog;}

	@Override
	protected void init() {
		// popup dialog
		complaintLog = new ComplaintLog();
		super.registerComponent(COMP_LBL_ACCOUNT_NO , FactoryComponent.createLabel("Customer Account", new PositionScalar(20, 23, 150, 14)));
		super.registerComponent(COMP_LBL_ACCOUNT , FactoryComponent.createLabel("Please Select", new PositionScalar(180, 23, 200, 14)));
		super.registerComponent(COMP_BUTTON_SEARCH , FactoryComponent.createButton("Search", new ButtonAttributeScalar(330, 23, 100, 23 , new ButtonActionListener(this,"saveCustomer"))));
		
		super.registerComponent(COMP_LBL_ISSUE_NO , FactoryComponent.createLabel("Issue Number", new PositionScalar(20, 53, 150, 14)));
		super.registerComponent(COMP_LBL_ISSUE_NO_TXT , FactoryComponent.createLabel("Auto number", new PositionScalar(180, 53, 150, 14)));
		
		super.registerComponent(COMP_LBL_DATE_FROM , FactoryComponent.createLabel("Date From", new PositionScalar(20, 83, 150, 14)));
		super.registerComponent(COMP_LBL_DATE_FROM_TXT , FactoryComponent.createLabel(OneHashDateUtil.format(this.complaintLog.getComplaintDate(), "dd/MM/yyyy"), new PositionScalar(180, 83, 150, 14)));
		
		super.registerComponent(COMP_LBL_DATE_TO , FactoryComponent.createLabel("Date To", new PositionScalar(20, 113, 150, 14)));
		super.registerComponent(COMP_LBL_DATE_TO_TXT , FactoryComponent.createLabel("-", new PositionScalar(180, 83, 150, 14)));
		
		super.registerComponent(COMP_LBL_DESCRIPTION , FactoryComponent.createLabel("Issue Log", new PositionScalar(20, 143, 150, 14)));
		super.registerComponent(COMP_INPUT_DESCRIPTION , FactoryComponent.createTextArea( 
				new TextFieldAttributeScalar(20, 163, 418, 75,0 , new OneHashTextFieldListener(this,"issueDescription",String.class)) ));
		
		super.registerComponent(COMP_BUTTON_SAVE , FactoryComponent.createButton("Save", new ButtonAttributeScalar(20, 250, 100, 23 , new ButtonActionListener(this,"saveComplaint"))));
		super.registerComponent(COMP_BUTTON_BACK , FactoryComponent.createButton("Cancel", new ButtonAttributeScalar(130, 250, 100, 23 , new ButtonActionListener(this,"cancel"))));
		super.registerComponent(COMP_BUTTON_CLOSE_CASE , FactoryComponent.createButton("Close Issue", new ButtonAttributeScalar(240, 250, 100, 23 , new ButtonActionListener(this,"saveAndCloseComplaint"))));
		
		
		// registering command button
		final ComplaintMainenancePanel custPanel = this;
		super.getButtonComponent(COMP_BUTTON_SEARCH).addActionListener(
				new ActionListener() { public void actionPerformed(ActionEvent e) {
			        	new CustomerLookupDialog(new JFrame("Title") , custPanel);
			        }});
		
	}
	
	public void saveComplaint() throws Exception {
		try{
			if(this.selectedCustomer == null || OneHashStringUtil.isEmpty(this.selectedCustomer.getAccountNumber()))
				throw new InsufficientInputParameterException("Please select customer");
			
			if(OneHashStringUtil.isEmpty(this.complaintLog.getIssueDescription()))
				throw new InsufficientInputParameterException("Please fill in issue description");
				
		}catch(Exception e){
			if(e instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
			throw e;
		}
	}
	
	public void saveAndCloseComplaint() throws Exception{
		this.complaintLog.setClosedDate(new Date());
		this.saveComplaint();
	}
	
	public void cancel(){
		this.getMainFrame().doLoadScreen(ComplaintListPanel.class);
	}

	@Override
	protected String getScreenTitle() {return "Complaint Maintenance";}

}
