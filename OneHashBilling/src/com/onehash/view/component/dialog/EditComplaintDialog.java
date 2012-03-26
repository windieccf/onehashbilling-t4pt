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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.view.component.dialogUtil.OkCancelDialog;
import com.onehash.view.panel.complaint.ComplaintEditPanel;

@SuppressWarnings("serial")
public class EditComplaintDialog extends OkCancelDialog {

	private OneHashDataCache dataCache;
	private ComplaintLog complaintLog;
    private JTextArea followUpField;
    private JComboBox comboBox;

	public EditComplaintDialog(ComplaintEditPanel complaintEdit,
			ComplaintLog complaintLog) {
		super(complaintEdit.getMainFrame(), "Edit Complaint", complaintLog
				.getIssueDescription(), complaintLog.getStatus().getLabel());
		dataCache = OneHashDataCache.getInstance();
		this.complaintLog = complaintLog;
	}

	protected JPanel createFormPanel(String issueDes, String status) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0, 2));
		p.add(new JLabel("Description"));

		p.add(new JLabel(issueDes));

		p.add(new JLabel("Follow up"));
		followUpField = new JTextArea(10, 15);
		p.add(followUpField);
		p.add(new JLabel("Status"));
		comboBox = new JComboBox();
		comboBox.addItem("Active");
		comboBox.addItem("Closed");
		comboBox.addItem("Pending");
		comboBox.addItem("Follow up");
		comboBox.setSelectedItem(status);
		p.add(comboBox);
		return p;
	}

    protected boolean performOkAction () {
        String status = (String) comboBox.getSelectedItem();
        System.out.println(status);
        String followup = followUpField.getText();
        try {
			dataCache.updateComplaintLog(complaintLog, status, followup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }

}
