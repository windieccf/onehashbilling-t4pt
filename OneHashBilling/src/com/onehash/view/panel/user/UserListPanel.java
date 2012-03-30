package com.onehash.view.panel.user;

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
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TableFilterScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.model.user.User;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.listener.OneHashTabelFilterListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class UserListPanel extends BasePanel {
	
	private static final String COMP_LBL_USERNAME = "LBL_USERNAME";
	private static final String COMP_FIRST_NAME = "LBL_FIRST_NAME";
	private static final String COMP_LBL_LAST_NAME = "LBL_LAST_NAME";
	
	private static final String COMP_TEXT_USER_NAME = "TXT_USERNAME";
	private static final String COMP_TEXT_FIRST_NAME = "TXT_FIRST_NAME";
	private static final String COMP_TEXT_LAST_NAME = "TXT_LAST_NAME";
	
	private static final String COMP_TABLE = "TABLE";
	private static final String COMP_BUTTON_CREATE = "CREATE_BUTTON";
	
	public UserListPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}	

	@Override
	protected void init() {
		
		super.registerComponent(COMP_LBL_USERNAME , FactoryComponent.createLabel("Username", new PositionScalar(20, 23, 100, 14)));
		super.registerComponent(COMP_FIRST_NAME , FactoryComponent.createLabel("First Name", new PositionScalar(20, 50, 150, 14)));
		super.registerComponent(COMP_LBL_LAST_NAME , FactoryComponent.createLabel("Last Name", new PositionScalar(20, 77, 150, 14)));
		
		super.registerComponent(COMP_TEXT_USER_NAME , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 23, 150, 20,10) ));
		super.registerComponent(COMP_TEXT_FIRST_NAME , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 50, 150, 20,10) ));
		super.registerComponent(COMP_TEXT_LAST_NAME , FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 77, 150, 20,10 ) ));
		
		
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
        filters.add(new TableFilterScalar(super.getTextFieldComponent(COMP_TEXT_USER_NAME) , 0));
        filters.add(new TableFilterScalar(super.getTextFieldComponent(COMP_TEXT_FIRST_NAME) , 1));
        filters.add(new TableFilterScalar(super.getTextFieldComponent(COMP_TEXT_LAST_NAME) , 2));
        
        OneHashTabelFilterListener filterListener = new OneHashTabelFilterListener(sorter, filters);
        super.getTextFieldComponent(COMP_TEXT_USER_NAME).getDocument().addDocumentListener(filterListener);
        super.getTextFieldComponent(COMP_TEXT_FIRST_NAME).getDocument().addDocumentListener(filterListener);
        super.getTextFieldComponent(COMP_TEXT_LAST_NAME).getDocument().addDocumentListener(filterListener);
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15,120,700,200);
		super.registerComponent(COMP_TABLE, scrollPane);
		
		JButton loginButton = FactoryComponent.createButton("Create", new ButtonAttributeScalar(615, 340, 100, 23 , new ButtonActionListener(this,"loadAddScreen")));
		super.registerComponent(COMP_BUTTON_CREATE , loginButton);
		
	}
	
	@Override
	protected String getScreenTitle() {return "User List";}
	
	
	/**************************** REFLECTION UTILITY **********************************/
	public void loadAddScreen(){
		this.getMainFrame().doLoadScreen(UserEditPanel.class, ConstantAction.ADD);
	}
	
	public void loadEditScreen(String parameter){
		this.getMainFrame().doLoadScreen(UserEditPanel.class, ConstantAction.EDIT,parameter);
	}
	
	
	/******************************** TABLE UTILITY******************************************/
	public Object[][] getData(){
		Object[][] rowData = new String[1][5];
		List<User> users = OneHashDataCache.getInstance().getUsers();
		if(users.isEmpty()){
			rowData = new Object[0][6];
		}else{
			rowData = new Object[users.size()][6];
			for(int i = 0 ; i < users.size(); i++){
				User user = users.get(i);
				rowData[i][0] = user.getUserName();
				rowData[i][1] = user.getFirstName();
				rowData[i][2] = user.getLastName();
				rowData[i][3] = user.getUserRole();
				rowData[i][4] = user.isActivated();
				rowData[i][5] = user.getUserAccessAsString();
			}
		}
		
		return rowData;
	}
	
	public String[] getTableColumnNames(){
		return new String[]{"Username", "First Name","Last Name" , "Role","Activated","Rights"};
	}
	
	
	
}
