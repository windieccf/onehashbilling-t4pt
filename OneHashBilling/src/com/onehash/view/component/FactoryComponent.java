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
 * 													
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.view.component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;

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
		
		return textField;
	}
	
	public static JPasswordField createPasswordField(TextFieldAttributeScalar scalar){
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(scalar.getPosX(), scalar.getPosY(), scalar.getWidth(), scalar.getHeight());
		if(scalar.getColumns() > 0)
			passwordField.setColumns(scalar.getColumns());
		
		return passwordField;
	}
	
	public static JButton createButton(String labelName , PositionScalar pos){
		JButton jButton = new JButton(labelName);
		jButton.setBounds(pos.getPosX(), pos.getPosY(), pos.getWidth(), pos.getHeight());
		return jButton;
	}
	
	public static JTextArea createTextArea(String labelName , PositionScalar pos){
		JTextArea textArea = new JTextArea();
		textArea.setBounds(pos.getPosX(), pos.getPosY(), pos.getWidth(), pos.getHeight());
		return textArea;
	}
	
	public static JCheckBox createCheckBox(String labelName , PositionScalar pos){
		JCheckBox chckbxNewCheckBox = new JCheckBox(labelName);
		chckbxNewCheckBox.setBounds(pos.getPosX(), pos.getPosY(), pos.getWidth(), pos.getHeight());
		return chckbxNewCheckBox;
	}
	
	public static JComboBox createCheckBox(PositionScalar pos){
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(pos.getPosX(), pos.getPosY(), pos.getWidth(), pos.getHeight());
		return comboBox;
	}
	

}
