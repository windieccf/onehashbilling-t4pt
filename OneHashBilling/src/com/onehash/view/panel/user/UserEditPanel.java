package com.onehash.view.panel.user;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantAction;
import com.onehash.controller.OneHashDataCache;
import com.onehash.enumeration.EnumUserAccess;
import com.onehash.exception.BusinessLogicException;
import com.onehash.exception.InsufficientInputParameterException;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.CheckboxAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.model.user.User;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.BooleanCheckBoxListener;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.OneHashTextFieldListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BaseOperationImpl;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class UserEditPanel extends BasePanel implements BaseOperationImpl {
	
	
	private static final String COMP_LBL_USERNAME = "LBL_USERNAME";
	private static final String COMP_FIRST_NAME = "LBL_FIRST_NAME";
	private static final String COMP_LBL_LAST_NAME = "LBL_LAST_NAME";
	private static final String COMP_LBL_PASSWORD = "LBL_PASSWORD";
	private static final String COMP_LBL_USER_ROLE = "LBL_USER_ROLE";
	private static final String COMP_LBL_STATUS = "LBL_STATUS";
	private static final String COMP_LBL_ACCESS_RIGHTS = "LBL_ACCESS_RIGHTS";
	
	private static final String COMP_TEXT_USER_NAME = "TXT_USERNAME";
	private static final String COMP_TEXT_FIRST_NAME = "TXT_FIRST_NAME";
	private static final String COMP_TEXT_LAST_NAME = "TXT_LAST_NAME";
	private static final String COMP_TEXT_PASSWORD = "TXT_PASSWORD";
	private static final String COMP_TEXT_USER_ROLE = "TXT_USER_ROLE";
	private static final String COMP_CHK_STATUS = "CHK_STATUS";
//	private static final String COMP_LBL_ACCESS_RIGHTS = "LBL_ACCESS_RIGHTS";
	
	private static final String COMP_TABLE_SELECTED_RIGHTS = "COMP_TABLE_SELECTED_RIGHTS";
	private static final String COMP_TABLE_AVAILABLE_RIGHTS = "COMP_TABLE_AVAILABLE_RIGHTS";
	
	private static final String BUTTON_ADD_OPTIONS = "BUTTON_ADD_OPTIONS";
	private static final String BUTTON_REMOVE_OPTIONS = "BUTTON_REMOVE_OPTIONS";
	private static final String BUTTON_SAVE = "BUTTON_SAVE";
	private static final String BUTTON_CANCEL = "BUTTON_CANCEL";
	
	
	
	private User user  = new User(); // for data binding
	private OneHashTableModel selectedRightsModel;
	private OneHashTableModel availableRightsModel;
	public UserEditPanel(OneHashGui mainFrame) {super(mainFrame);}
	
	
	@Override
	protected void init() {
		user = new User();

		super.registerComponent(COMP_LBL_USERNAME , FactoryComponent.createLabel("Username", new PositionScalar(20, 23, 100, 14)));
		super.registerComponent(COMP_FIRST_NAME , FactoryComponent.createLabel("First Name", new PositionScalar(20, 48, 100, 14)));
		super.registerComponent(COMP_LBL_LAST_NAME , FactoryComponent.createLabel("Last Name", new PositionScalar(20, 77, 100, 14)));
		super.registerComponent(COMP_LBL_PASSWORD , FactoryComponent.createLabel("Password", new PositionScalar(20, 102, 67, 14)));
		super.registerComponent(COMP_LBL_USER_ROLE , FactoryComponent.createLabel("Role", new PositionScalar(20, 130, 100, 14)));
		super.registerComponent(COMP_LBL_STATUS , FactoryComponent.createLabel("Activated", new PositionScalar(20, 158, 100, 14)));
		super.registerComponent(COMP_LBL_ACCESS_RIGHTS , FactoryComponent.createLabel("Rights", new PositionScalar(20, 186, 100, 14)));
		
		
		super.registerComponent(COMP_TEXT_USER_NAME , 
				FactoryComponent.createTextField( 
							new TextFieldAttributeScalar(136, 23, 100, 20,10 , new OneHashTextFieldListener(this,"userName",String.class)) ));
		
		super.registerComponent(COMP_TEXT_FIRST_NAME , 
				FactoryComponent.createTextField( 
							new TextFieldAttributeScalar(136, 48, 150, 20,10 , new OneHashTextFieldListener(this,"firstName",String.class)) ));

		super.registerComponent(COMP_TEXT_LAST_NAME , 
				FactoryComponent.createTextField( 
							new TextFieldAttributeScalar(136, 77, 150, 20,10 , new OneHashTextFieldListener(this,"lastName",String.class)) ));
		
		super.registerComponent(COMP_TEXT_PASSWORD , 
				FactoryComponent.createTextField( 
							new TextFieldAttributeScalar(136, 102, 150, 20,10 , new OneHashTextFieldListener(this,"password",String.class)) ));
		
		super.registerComponent(COMP_TEXT_USER_ROLE , 
				FactoryComponent.createTextField( 
							new TextFieldAttributeScalar(136, 130, 150, 20,10 , new OneHashTextFieldListener(this,"userRole",String.class)) ));
		
		super.registerComponent(COMP_CHK_STATUS , 
				FactoryComponent.createCheckBox("", 
							new CheckboxAttributeScalar(136, 158, 100, 20, new BooleanCheckBoxListener(this,"status")) ));
		
		
		// start access rights table selection
		
		JTable table = new JTable();
		selectedRightsModel = new OneHashTableModel(this.getSelectedRightsColumn() , this.getSelectedRightsData());
		
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(selectedRightsModel);
        table.removeColumn(table.getColumnModel().getColumn(0));
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(136,186, 200,100);
		super.registerComponent(COMP_TABLE_SELECTED_RIGHTS, scrollPane);
		
		
		super.registerComponent(BUTTON_ADD_OPTIONS , FactoryComponent.createButton("<<", new ButtonAttributeScalar(346, 210, 50, 23 , new ButtonActionListener(this,"addOptions"))));
		super.registerComponent(BUTTON_REMOVE_OPTIONS , FactoryComponent.createButton(">>", new ButtonAttributeScalar(346, 240, 50, 23 , new ButtonActionListener(this,"removeOptions"))));
	
		table = new JTable();
		availableRightsModel = new OneHashTableModel(this.getAvailableRightsColumn() , this.getAvailableRightsData());
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        table.setModel(availableRightsModel);
        table.removeColumn(table.getColumnModel().getColumn(0));
        
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(406,186, 200,100);
		super.registerComponent(COMP_TABLE_AVAILABLE_RIGHTS, scrollPane);
		
		
		super.registerComponent(BUTTON_SAVE , FactoryComponent.createButton("Save", new ButtonAttributeScalar(136, 300, 100, 23 , new ButtonActionListener(this,"saveUser"))));
		super.registerComponent(BUTTON_CANCEL , FactoryComponent.createButton("Back", new ButtonAttributeScalar(256, 300, 100, 23 , new ButtonActionListener(this,"cancel"))));
		
	}
	
	
	@Override
	protected String getScreenTitle() {return "User Maintenance";}

	@Override
	public BaseEntity getSelectedEntity() {return this.user;}
	@Override
	public void setSelectedEntity(BaseEntity baseEntity) {this.user = (User) baseEntity;}
	
	//
	
	@PostCreate
	public void postCreate(List<String> parameters){
		
		if(parameters == null)
			throw new IllegalArgumentException("UserEditPanel.postCreate parameter is required");
		
		if(ConstantAction.ADD.equals(parameters.get(0)))
			this.user = new User();
		else{
			this.user = OneHashDataCache.getInstance().getUserByUserName(parameters.get(1));
			super.getTextFieldComponent(COMP_TEXT_USER_NAME).setText(this.user.getUserName());
			super.getTextFieldComponent(COMP_TEXT_FIRST_NAME).setText(this.user.getFirstName());
			super.getTextFieldComponent(COMP_TEXT_LAST_NAME).setText(this.user.getLastName());
			super.getTextFieldComponent(COMP_TEXT_PASSWORD).setText(this.user.getPassword());
			super.getTextFieldComponent(COMP_TEXT_USER_ROLE).setText(this.user.getUserRole());
			for(EnumUserAccess userAccess : this.user.getUserAccesses()){
				Object data[] = new Object[2];
				data[0] = userAccess.getCode();
				data[1] = userAccess.getDescription();
				selectedRightsModel.addRow(data);
				availableRightsModel.removeRow( availableRightsModel.getRowIndexByName(0, userAccess.getCode()));
			}
			// porting user access
		}
		
		super.getCheckboxComponent(COMP_CHK_STATUS).setSelected(this.user.isActivated());
	}
	
	
	/***************************************** BUTTON LISTENER *****************************************************/
	public void cancel(){
		this.getMainFrame().doLoadScreen(UserListPanel.class);
	}
	
	public void saveUser() throws Exception{
		
		
		try{
			if(OneHashStringUtil.isEmpty(this.user.getUserName()))
				throw new InsufficientInputParameterException("Username Name is required");
			
			if(OneHashStringUtil.isEmpty(this.user.getPassword()))
				throw new InsufficientInputParameterException("User Password is required");
			
			if(OneHashStringUtil.isEmpty(this.user.getFirstName()))
				throw new InsufficientInputParameterException("First Name is required");
			
			if(OneHashStringUtil.isEmpty(this.user.getLastName()))
				throw new InsufficientInputParameterException("Last Name number is required");
			
			if(OneHashStringUtil.isEmpty(this.user.getUserRole()))
				throw new InsufficientInputParameterException("User Role is required");
			
			
			Object[][] datas = selectedRightsModel.getData();
			List<EnumUserAccess> userAccesses = new ArrayList<EnumUserAccess>();
			for(Object[] obj : datas){
				userAccesses.add(EnumUserAccess.getEnumByCode(obj[0].toString()));
			}
			
			if(userAccesses.isEmpty())
				throw new InsufficientInputParameterException("User Rights is required");
			
			this.user.setUserAccesses(userAccesses);
			
			OneHashDataCache.getInstance().saveUser(user);
			JOptionPane.showMessageDialog(this, "User Successfully Saved");
			this.cancel();
		}catch(Exception e){
			if(e instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
			throw e;
		}
	}
	
	/*************************** ACCESS RIGHTS TABLE UTIL *********************************/
	
	public String[] getSelectedRightsColumn(){return new String[]{"Rights Code","Selected Rights",};} 
	public Object[][] getSelectedRightsData() {
		Object[][] rowData = new String[0][2];
		return rowData;
	}
	
	public String[] getAvailableRightsColumn(){return new String[]{"Rights Code","Available Rights",};} 
	public Object[][] getAvailableRightsData() {
		EnumUserAccess[] userAccesses = EnumUserAccess.values();
		Object[][] rowData = new String[userAccesses.length][2];
		for(int i = 0 ; i < userAccesses.length ; i++){
			EnumUserAccess enumUserAccess = userAccesses[i];
			rowData[i][0] = enumUserAccess.getCode();
			rowData[i][1] = enumUserAccess.getDescription();
		}
		return rowData;
	}
	
	public void addOptions(){
		this.switchOptions(COMP_TABLE_AVAILABLE_RIGHTS, availableRightsModel, selectedRightsModel);
	}
	
	public void removeOptions(){
		this.switchOptions(COMP_TABLE_SELECTED_RIGHTS, selectedRightsModel, availableRightsModel);
	}
	
	private void switchOptions(String componentName, OneHashTableModel fromModel, OneHashTableModel toModel){
		JScrollPane optionsScrollPane = (JScrollPane)super.getComponent(componentName);
		JTable optionsJTable = (JTable)optionsScrollPane.getViewport().getView();
		if(optionsJTable.getSelectedRow() == -1 )
			return;
		
		toModel.addRow( fromModel.getRowByIndex(optionsJTable.getSelectedRow()));
		fromModel.removeRow(optionsJTable.getSelectedRow());
	}


	@Override
	protected void initiateAccessRights() {
		if(!OneHashDataCache.getInstance().getCurrentUser().hasRights(EnumUserAccess.USER_UPDATE)){
			super.disableComponent(BUTTON_CANCEL);
			
			/*super.getButtonComponent(BUTTON_SAVE).setEnabled(false);
			super.getButtonComponent(BUTTON_ADD_OPTIONS).setEnabled(false);
			super.getButtonComponent(BUTTON_REMOVE_OPTIONS).setEnabled(false);
			
			super.getTextFieldComponent(COMP_TEXT_USER_NAME).setEnabled(false);
			super.getTextFieldComponent(COMP_TEXT_FIRST_NAME).setEnabled(false);
			super.getTextFieldComponent(COMP_TEXT_LAST_NAME).setEnabled(false);
			super.getTextFieldComponent(COMP_TEXT_PASSWORD).setEnabled(false);
			super.getTextFieldComponent(COMP_TEXT_USER_ROLE).setEnabled(false);
			super.getTextFieldComponent(COMP_CHK_STATUS).setEnabled(false);*/
			
		}
	}
	
	

}
