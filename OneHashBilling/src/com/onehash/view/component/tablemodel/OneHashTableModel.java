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
 * 15 March 2012    Robin Foe	    0.1				Class creating
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.view.component.tablemodel;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class OneHashTableModel extends AbstractTableModel{
	
	 private String[] columnNames;
	 private Object[][] data;
	 
	 public OneHashTableModel(String[] columnNames , Object[][] data){
		 this.columnNames = columnNames;
		 this.data = data;
	 }

	
	public String getColumnName(int col) {
        return columnNames[col].toString();
    }
    
	public int getRowCount() {return data.length;}
    public int getColumnCount() {return columnNames.length;}
    public Object getValueAt(int row, int col) {return data[row][col];}
    
    public boolean isCellEditable(int row, int col){ 
    	return false; 
    }
    


}
