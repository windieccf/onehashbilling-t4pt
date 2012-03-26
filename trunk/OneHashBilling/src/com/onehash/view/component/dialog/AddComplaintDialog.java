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
 * 26 March 2012    Chen Changfeng	0.1				Class creating 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.view.component.dialog;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.customer.Customer;
import com.onehash.view.component.dialogUtil.OkCancelDialog;
import com.onehash.view.panel.complaint.ComplaintEditPanel;

@SuppressWarnings("serial")
public class AddComplaintDialog extends OkCancelDialog {

	private OneHashDataCache dataCache;
	private ComplaintEditPanel complaintEditPanel;
    private JTextArea descriptionField;

    public AddComplaintDialog (ComplaintEditPanel complaintEditPanel) {
        super (complaintEditPanel.getMainFrame(), "Add Complaint", "", "");
        dataCache=OneHashDataCache.getInstance();
        this.complaintEditPanel = complaintEditPanel;
    }

    protected JPanel createFormPanel (String issueDes, String status) {
        JPanel p = new JPanel ();
        p.setLayout (new GridLayout (0, 2));
        p.add (new JLabel ("Description"));
        descriptionField = new JTextArea(10, 15);
        p.add (descriptionField);
        return p;
    }

    protected boolean performOkAction () {
        String description = descriptionField.getText();
        try {
        	ArrayList<String> issueNoListPreSetting = new ArrayList<String>();
        	issueNoListPreSetting.add("COMP201203100000");
			dataCache.createComplaint((Customer)complaintEditPanel.getSelectedEntity(), description, issueNoListPreSetting);
			System.out.println(descriptionField.getText());
			Customer cus = (Customer)complaintEditPanel.getSelectedEntity();
			System.out.println("2"+cus.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }

}
