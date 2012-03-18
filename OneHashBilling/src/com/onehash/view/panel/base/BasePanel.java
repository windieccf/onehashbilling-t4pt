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
 * 14 March 2012    Robin Foe	    0.2				Add short hand method to get component
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.view.panel.base;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.SwingUtilities;

import com.onehash.constant.ConstantGUIAttribute;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;

@SuppressWarnings("serial")
public abstract class BasePanel extends JPanel implements Runnable{
	
	private Map<String,Component> componentMap = new HashMap<String, Component>();
	
	private OneHashGui mainFrame;
	public OneHashGui getMainFrame() {return mainFrame;}

	public BasePanel(OneHashGui mainFrame){
		this.draw();
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void run(){
		JPanel panel = this;
		if(this.isEnableHeader()){
			JLabel titleLabel = FactoryComponent.createLabel(this.getScreenTitle(), new PositionScalar(20,20,300,25));
			titleLabel.setFont(new Font(this.getScreenTitle(), Font.BOLD, 15) );
			panel.add(titleLabel);
			JSeparator separator = new JSeparator();
			separator.setBounds(ConstantGUIAttribute.HEADER_SEPERATOR);
			panel.add(separator);
			
		}
		
		for(String key : componentMap.keySet()){
			Component comp = componentMap.get(key);
			if(this.isEnableHeader()){
				Rectangle rect = comp.getBounds();
				rect.y = rect.y + 40;
				comp.setBounds(rect);
			}
			//comp.setBounds(50 +  comp.getBounds().getX(), 50 +  comp.getBounds().getY(), comp.getBounds().getWidth(),  comp.getBounds().getHeight());
			panel.add(comp);
		}
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}
	
	public void draw(){
		this.init();
		EventQueue.invokeLater(this);
	}
	
	public final void registerComponent(String componentName , Component component){
		if(component == null) throw new IllegalArgumentException("component must not be null");
		if(OneHashStringUtil.isEmpty(componentName)) throw new IllegalArgumentException("componentName is required");
		if(componentMap.containsKey(componentName)) throw new IllegalArgumentException("Component name " + componentName + " already exist");
		componentMap.put(componentName, component);
	}
	
	public final Component getComponent(String componentName) {
		if(componentName == null) throw new IllegalArgumentException("component must not be null");
		if(OneHashStringUtil.isEmpty(componentName)) throw new IllegalArgumentException("componentName is required");
		if(componentMap.containsKey(componentName)) {
			return componentMap.get(componentName);
		}
		throw new IllegalArgumentException("Component name " + componentName + " is not exist");
	}
	
	public final void setComponent(String componentName , Component component) {
		if(component == null) throw new IllegalArgumentException("component must not be null");
		if(OneHashStringUtil.isEmpty(componentName)) throw new IllegalArgumentException("componentName is required");
		if (componentMap.containsKey(componentName))
			componentMap.remove(componentName);
		componentMap.put(componentName, component);
	}
	
	public final JTextField getTextFieldComponent(String componentName){return (JTextField)componentMap.get(componentName);}
	public final JTextArea getTextAreaComponent(String componentName){return (JTextArea)componentMap.get(componentName);}
	public final JLabel getLabelComponent(String componentName){return (JLabel)componentMap.get(componentName);}
	public final JCheckBox getCheckboxComponent(String componentName){return (JCheckBox)componentMap.get(componentName);}
	
	
	
	protected boolean isEnableHeader(){return true;}
	
	abstract protected void init();
	abstract protected String getScreenTitle();
	

}
