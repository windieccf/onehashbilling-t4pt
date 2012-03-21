package com.onehash.view.component.dialog;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.view.component.dialogUtil.OkCancelDialog;
import com.onehash.view.panel.customer.CustomerEditPanel;

@SuppressWarnings("serial")
public class EditComplaintDialog extends OkCancelDialog {

	private OneHashDataCache dataCache;
	private CustomerEditPanel customerEdit;
	private ComplaintLog complaintLog;
    private JTextArea followUpField;

    public EditComplaintDialog (CustomerEditPanel customerEdit, ComplaintLog complaintLog) {
        super (customerEdit.getMainFrame(), "Edit Complaint");
        dataCache=OneHashDataCache.getInstance();
        this.customerEdit = customerEdit;
        this.complaintLog = complaintLog;
    }

    protected JPanel createFormPanel () {
        JPanel p = new JPanel ();
        p.setLayout (new GridLayout (0, 2));
        p.add (new JLabel ("Description"));

        p.add (new JLabel ("Description text"));
        
        p.add (new JLabel ("Follow up"));
        followUpField = new JTextArea(10, 15);
        p.add (followUpField);
        p.add (new JLabel ("Status"));
//        secondField = new JTextField (20);
//        p.add (secondField);
        return p;
    }

    protected boolean performOkAction () {
        String status = followUpField.getText();
        String followup = followUpField.getText();
        try {
			dataCache.updateComplaintLog(complaintLog, status, followup);
			System.out.println(followUpField.getText());
			Customer cus = (Customer)customerEdit.getSelectedEntity();
			System.out.println("2"+cus.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }

}
