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

package com.onehash.view.panel.base;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;

@SuppressWarnings("serial")
public abstract class BasePanel extends JPanel{
	
	private Map<String,Component> componentMap = new HashMap<String, Component>();
	
	private OneHashGui mainFrame;
	public OneHashGui getMainFrame() {return mainFrame;}

	public BasePanel(OneHashGui mainFrame){
		this.draw();
		this.mainFrame = mainFrame;
	}
	
	public void draw(){
		this.init();
		for(String key : componentMap.keySet()){
			this.add(componentMap.get(key));
		}
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}
	
	public void registerComponent(String componentName , Component component){
		if(component == null) throw new IllegalArgumentException("component must not be null");
		if(OneHashStringUtil.isEmpty(componentName)) throw new IllegalArgumentException("componentName is required");
		if(componentMap.containsKey(componentName)) throw new IllegalArgumentException("Component name " + componentName + " already exist");
		componentMap.put(componentName, component);
		
	}
	
	abstract protected void init();
	

}
