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

import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * @author robin.foe
 * Custom table mode to suits ONE HASH BILLING needs
 */
@SuppressWarnings("serial")
public class OneHashTableModel extends DefaultTableModel{
	
	 private String[] columnNames;
	 
	 public OneHashTableModel(String[] columnNames , Object[][] data){
		 super(data, columnNames);
		 this.columnNames = columnNames;
	 }

	
	public String getColumnName(int col) {
        return columnNames[col].toString();
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getRowIndexByName(int columnIndex, Object value) {
		int i = 0; 
		if(value == null) return -1;
		
		for(Iterator ite = super.getDataVector().iterator() ; ite.hasNext(); i++){
			Vector data = (Vector)ite.next();
			Object[] obj = new Object[data.size()];
			data.toArray(obj);
			if(value.equals(obj[columnIndex]))
				return i;
		}
		return -1;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[] getRowByIndex(int rowIndex){
		int i = 0;
		for(Iterator ite = super.getDataVector().iterator() ; ite.hasNext(); i++){
			Vector data = (Vector)ite.next();
			if(i == rowIndex){
				Object[] obj = new Object[data.size()];
				data.toArray(obj);
				return obj;
			}
				
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[][] getData(){
		Object obj[][] = new Object[super.getDataVector().size()] [this.columnNames.length];
		int i = 0;
		for(Iterator ite = super.getDataVector().iterator() ; ite.hasNext(); i++){
			Vector data = (Vector)ite.next();
			data.toArray(obj[i]);
		}
		return obj;
	}
	
    
    public boolean isCellEditable(int row, int col){ 
    	return false; 
    }
    
    
    
    
    


}
