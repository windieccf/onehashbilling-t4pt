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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import com.onehash.view.panel.base.BasePanel;

/**
 * @author robin.foe
 * Action listener on button click, By passing BasePanel and action to call, 
 * the class will use reflection style to refer to specific method call
 */
public class ButtonActionListener implements ActionListener{
	
	private BasePanel basePanel;
	private String actionMethod;
	public ButtonActionListener(BasePanel basePanel){ this.basePanel = basePanel;}
	
	public ButtonActionListener(BasePanel basePanel,String actionMethod){ 
		this.basePanel = basePanel;
		this.actionMethod = actionMethod;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			Method mtd = basePanel.getClass().getMethod(actionMethod);
			mtd.invoke(basePanel);
		} catch (Exception e){/*IGNORED*/}
    }

}
