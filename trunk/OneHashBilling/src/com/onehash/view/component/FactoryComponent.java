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
 * 11 March 2012    Robin Foe	    0.1				Class creating
 * 16 March 2012    Robin Foe		0.2				Add in Document listener support 													
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.view.component;

import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.CheckboxAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.view.component.comboboxitem.ComboBoxItem;

public class FactoryComponent {
	
	public static JLabel createLabel(String labelName, PositionScalar pos){
		JLabel jLabel = new JLabel(labelName);
		jLabel.setBounds(pos.getPosX(), pos.getPosY(), pos.getWidth(), pos.getHeight());
		return jLabel;
	}
	
	public static JTextField createTextField(TextFieldAttributeScalar scalar){
		JTextField textField = new JTextField();
		textField.setBounds(scalar.getPosX(), scalar.getPosY(), scalar.getWidth(), scalar.getHeight());
		if(scalar.getColumns() > 0)
			textField.setColumns(scalar.getColumns());
		
		if(scalar.getDocumentListener()!=null)
			textField.getDocument().addDocumentListener(scalar.getDocumentListener());
		
		return textField;
	}
	
	public static JPasswordField createPasswordField(TextFieldAttributeScalar scalar){
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(scalar.getPosX(), scalar.getPosY(), scalar.getWidth(), scalar.getHeight());
		if(scalar.getColumns() > 0)
			passwordField.setColumns(scalar.getColumns());
		
		return passwordField;
	}
	
	public static JButton createButton(String labelName , ButtonAttributeScalar scalar){
		JButton jButton = new JButton(labelName);
		jButton.setBounds(scalar.getPosX(), scalar.getPosY(), scalar.getWidth(), scalar.getHeight());
		if(scalar.getActionListener()!=null)
			jButton.addActionListener(scalar.getActionListener());
		
		return jButton;
	}
	
	public static JTextArea createTextArea( TextFieldAttributeScalar scalar){
		JTextArea textArea = new JTextArea();
		textArea.setBounds(scalar.getPosX(), scalar.getPosY(), scalar.getWidth(), scalar.getHeight());
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		if(scalar.getColumns() > 0)
			textArea.setColumns(scalar.getColumns());
		
		if(scalar.getDocumentListener()!=null)
			textArea.getDocument().addDocumentListener(scalar.getDocumentListener());
		
		return textArea;
	}
	
	public static JCheckBox createCheckBox(String labelName , CheckboxAttributeScalar scalar){
		JCheckBox chckbxNewCheckBox = new JCheckBox(labelName);
		chckbxNewCheckBox.setBounds(scalar.getPosX(), scalar.getPosY(), scalar.getWidth(), scalar.getHeight());
		chckbxNewCheckBox.setBackground(Color.WHITE);
		if(scalar.getItemListener()!=null)
			chckbxNewCheckBox.addItemListener(scalar.getItemListener());
			
		return chckbxNewCheckBox;
	}
	
	public static JComboBox createComboBox(Vector<ComboBoxItem> comboBoxes , ButtonAttributeScalar scalar){
		JComboBox jComboBox = new JComboBox(comboBoxes);
		jComboBox.setBounds(scalar.getPosX(), scalar.getPosY(), scalar.getWidth(), scalar.getHeight());
		if(scalar.getActionListener()!=null)
			jComboBox.addActionListener(scalar.getActionListener());
		
		return jComboBox;
	}

}
