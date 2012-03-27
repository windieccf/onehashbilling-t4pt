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

import javax.swing.JTable;

public class MouseTableListener extends MouseAdapter{
	
	private Component basePanel;
	private String actionMethod;
	private int targetValueIndex = 0;

	public MouseTableListener(Component basePanel){ this.basePanel = basePanel;}
	public MouseTableListener(Component basePanel,String actionMethod){ 
		this.basePanel = basePanel;
		this.actionMethod = actionMethod;
	}
	public MouseTableListener(Component basePanel,String actionMethod, int targetValueIndex){ 
		this.basePanel = basePanel;
		this.actionMethod = actionMethod;
		this.targetValueIndex = targetValueIndex;
	}
	
	
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
	         JTable target = (JTable)event.getSource();
	         String value = (String) target.getValueAt(target.getSelectedRow(), targetValueIndex);
	         try {
				Method mtd = basePanel.getClass().getMethod(actionMethod,String.class);
				mtd.invoke(basePanel, value);
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
	         
	         
		}
	}   
		

}
