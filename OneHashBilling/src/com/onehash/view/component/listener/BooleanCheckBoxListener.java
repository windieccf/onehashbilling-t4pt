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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;

import com.onehash.utility.OneHashBeanUtil;
import com.onehash.view.panel.base.BaseOperationImpl;

public class BooleanCheckBoxListener implements ItemListener{

	private BaseOperationImpl baseImpl;
	private Method setterMethod;
	public BooleanCheckBoxListener(BaseOperationImpl baseImpl, String fieldName) {
		// check on the getter and setter
		try {
			this.baseImpl = baseImpl;
			setterMethod = OneHashBeanUtil.getSetterMethod(baseImpl.getSelectedEntity().getClass(), fieldName, boolean.class);
		} catch (Exception  e) {
			e.printStackTrace();
			throw new IllegalArgumentException("unable to find setter method for class "+ baseImpl.getSelectedEntity().getClass() + " with field name "+fieldName);
		}
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent itemEvent) {
		try {
			setterMethod.invoke(baseImpl.getSelectedEntity(), itemEvent.getStateChange() == ItemEvent.SELECTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
