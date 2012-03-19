package com.onehash.view.component.dialog;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.customer.Customer;
import com.onehash.view.component.dialogUtil.OkCancelDialog;
import com.onehash.view.panel.customer.CustomerEditPanel;

@SuppressWarnings("serial")
public class AddComplaintDialog extends OkCancelDialog {

	private OneHashDataCache dataCache;
	private CustomerEditPanel customerEdit;
//    private JTextField firstField;
//    private JTextField secondField;
    private JTextField descriptionField;

    public AddComplaintDialog (CustomerEditPanel customerEdit) {
        super (customerEdit.getMainFrame(), "Add Complaint");
        dataCache=OneHashDataCache.getInstance();
        this.customerEdit = customerEdit;
    }

    protected JPanel createFormPanel () {
        JPanel p = new JPanel ();
        p.setLayout (new GridLayout (0, 2));
        p.add (new JLabel ("Surname"));
        descriptionField = new JTextField (20);
        p.add (descriptionField);
//        p.add (new JLabel ("First name"));
//        firstField = new JTextField (20);
//        p.add (firstField);
//        p.add (new JLabel ("Second name"));
//        secondField = new JTextField (20);
//        p.add (secondField);
        return p;
    }

    protected boolean performOkAction () {
        String surname = descriptionField.getText();
//        String first = firstField.getText();
//        if ((surname.length() == 0) || (first.length() == 0)) {
//            return false;
//        }
//        String second = secondField.getText();
//        if (second.length() == 0) {
//            second = null;
//        }
        try {
        	ArrayList<String> issueNoListPreSetting = new ArrayList<String>();
        	issueNoListPreSetting.add("COMP201203100000");
			dataCache.createComplaint((Customer)customerEdit.getSelectedEntity(), surname, issueNoListPreSetting);
			System.out.println(descriptionField.getText());
			Customer cus = (Customer)customerEdit.getSelectedEntity();
			System.out.println("2"+cus.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }

}
