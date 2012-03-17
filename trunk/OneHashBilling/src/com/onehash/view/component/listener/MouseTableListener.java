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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;

import javax.swing.JTable;

import com.onehash.view.panel.base.BasePanel;

public class MouseTableListener extends MouseAdapter{
	
	private BasePanel basePanel;
	private String actionMethod;

	public MouseTableListener(BasePanel basePanel){ this.basePanel = basePanel;}
	public MouseTableListener(BasePanel basePanel,String actionMethod){ 
		this.basePanel = basePanel;
		this.actionMethod = actionMethod;
	}
	
	
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
	         JTable target = (JTable)event.getSource();
	         String value = (String) target.getValueAt(target.getSelectedRow(), 0);
	         try {
				Method mtd = basePanel.getClass().getMethod(actionMethod,String.class);
				mtd.invoke(basePanel, value);
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
	         
	         
		}
	}   
		

}
