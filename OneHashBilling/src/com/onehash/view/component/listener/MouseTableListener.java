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

package com.onehash.view.component.listener;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import com.onehash.constant.ConstantPrefix;
import com.onehash.utility.OneHashStringUtil;

/**
 * @author robin.foe
 * Mouse double click listener for table
 */
public class MouseTableListener extends MouseAdapter{
	
	private Component basePanel;
	private String actionMethod;
	private int[] targetValueIndex;

	public MouseTableListener(Component basePanel){this.basePanel = basePanel;}
	
	public MouseTableListener(Component basePanel,String actionMethod){ 
		this.basePanel = basePanel;
		this.actionMethod = actionMethod;
	}
	public MouseTableListener(Component basePanel,String actionMethod, int... targetValueIndex){ 
		this.basePanel = basePanel;
		this.actionMethod = actionMethod;
		this.targetValueIndex = targetValueIndex;
	}
	
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
	         JTable target = (JTable)event.getSource();
	         List<String> values = new ArrayList<String>();
	         
	         if(targetValueIndex == null){
	        	 targetValueIndex = new int[1];
	        	 targetValueIndex[0] = 0;
	         }
	         
	         for(int index : targetValueIndex){
	        	 values.add((String) target.getValueAt(target.getSelectedRow(), index));
	         }
	        	 
	         String value = OneHashStringUtil.join(values, ConstantPrefix.SEPERATOR_PIPE);
	         try {
				Method mtd = basePanel.getClass().getMethod(actionMethod,String.class);
				mtd.invoke(basePanel, value);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getMessage());
			}
	         
	         
		}
	}   
		

}
