package com.onehash.model.scalar;

import javax.swing.JTextField;

public class TableFilterScalar {
	
	private JTextField textField;
	public JTextField getTextField() {return textField;}
	public void setTextField(JTextField textField) {this.textField = textField;}
	
	private int index;
	public int getIndex() {return index;}
	public void setIndex(int index) {this.index = index;}
	
	public TableFilterScalar(JTextField textField, int index){
		this.textField = textField;
		this.index = index;
	}
	
}
