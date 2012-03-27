package com.onehash.view.component.dialog.search;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TableFilterScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.listener.OneHashTabelFilterListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.CustomerOperationImpl;

@SuppressWarnings("serial")
public class CustomerLookupDialog extends JDialog {
	
	private CustomerOperationImpl panel;
	public CustomerLookupDialog(JFrame frame, CustomerOperationImpl panel) {
		super(frame, true);
		this.panel = panel;
		this.setResizable(false);
		this.setTitle("Customer Look Up");
		this.setSize(750, 400);
		init();
		setLocationRelativeTo(frame);
		setVisible(true);
		this.setBackground(Color.WHITE);
	}
	
	private void init(){
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel.add(FactoryComponent.createLabel("Account Number", new PositionScalar(20, 23, 100, 14)));
		panel.add(FactoryComponent.createLabel("Name", new PositionScalar(20, 50, 46, 14)));
		panel.add(FactoryComponent.createLabel("NRIC.", new PositionScalar(20, 77, 46, 14)));
		
		JTextField accountTextField = FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 23, 150, 20,10) );
		JTextField nameTextField = FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 50, 150, 20,10) );
		JTextField nricTextField = FactoryComponent.createTextField( new TextFieldAttributeScalar(136, 77, 150, 20,10 ) );
		
		panel.add(accountTextField);
		panel.add(nameTextField);
		panel.add(nricTextField);
		

		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , this.getData()));
        table.addMouseListener(new MouseTableListener(this,"submitSelection"));
        
        TableRowSorter<OneHashTableModel> sorter = new TableRowSorter<OneHashTableModel>( (OneHashTableModel) table.getModel());
        List<TableFilterScalar> filters = new ArrayList<TableFilterScalar>();
        filters.add(new TableFilterScalar(accountTextField , 0));
        filters.add(new TableFilterScalar(nameTextField , 1));
        filters.add(new TableFilterScalar(nricTextField , 2));
        
        OneHashTabelFilterListener filterListener = new OneHashTabelFilterListener(sorter, filters);
        accountTextField.getDocument().addDocumentListener(filterListener);
        nameTextField.getDocument().addDocumentListener(filterListener);
        nricTextField.getDocument().addDocumentListener(filterListener);
        
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15,120,700,200);
        panel.add(scrollPane);
	}
	
	
	private Object[][] getData(){
		Object[][] rowData = new String[1][3];
		List<Customer> customers = OneHashDataCache.getInstance().getCustomers();
		if(customers.isEmpty()){
			rowData = new Object[0][3];
		}else{
			rowData = new Object[customers.size()][3];
			for(int i = 0 ; i < customers.size(); i++){
				Customer customer = customers.get(i);
				rowData[i][0] = customer.getAccountNumber();
				rowData[i][1] = customer.getName();
				rowData[i][2] = customer.getNric();
			}
		}
		
		return rowData;
	}
	
	private String[] getTableColumnNames(){
		return new String[]{"Account Number", "Name","NRIC" };
	}
	
	public void submitSelection(String parameter){
		panel.setSelectedCustomer(OneHashDataCache.getInstance().getCustomerByAccountNumber(parameter));
		this.dispose();
	}

}
