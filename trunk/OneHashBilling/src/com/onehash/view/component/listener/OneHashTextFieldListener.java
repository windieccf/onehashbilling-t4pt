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


import java.lang.reflect.Method;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.onehash.utility.OneHashBeanUtil;
import com.onehash.view.panel.base.BaseOperationImpl;

/**
 * @author robin.foe
 * Dynamic text field listener, any changes in text field will automatically
 * updated to the respective object
 */
public class OneHashTextFieldListener implements DocumentListener{
	
	private BaseOperationImpl baseImpl;
	private Method setterMethod;
	public OneHashTextFieldListener(BaseOperationImpl baseImpl, String fieldName, Class<?>... argumentsType) {
		// check on the getter and setter
		try {
			this.baseImpl = baseImpl;
			setterMethod = OneHashBeanUtil.getSetterMethod(baseImpl.getSelectedEntity().getClass(), fieldName, argumentsType);
		} catch (Exception  e) {
			e.printStackTrace();
			throw new IllegalArgumentException("unable to find setter method for class "+ baseImpl.getSelectedEntity().getClass() + " with field name "+fieldName);
		}
		
	}
	

	@Override
	public void changedUpdate(DocumentEvent event) {
		eventOnChange(event);
	}

	@Override
	public void insertUpdate(DocumentEvent event) {
		eventOnChange(event);
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		eventOnChange(event);
	}
	
	private void eventOnChange(DocumentEvent event){
		try {
			setterMethod.invoke(baseImpl.getSelectedEntity(), event.getDocument().getText(0, event.getDocument().getLength()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
