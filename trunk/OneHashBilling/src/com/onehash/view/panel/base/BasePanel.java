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
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.onehash.constant.ConstantGUIAttribute;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;

/**
 * @author robin.foe
 * base panel serve the purpose of
 * 	-	draw the component
 * 	-	keep the component object
 * 	-	define the single source of styling
 * 	-	disabling component
 */

@SuppressWarnings("serial")
public abstract class BasePanel extends JPanel implements Runnable{
	
	private Map<String,Component> componentMap = new HashMap<String, Component>();
	
	private OneHashGui mainFrame;
	public OneHashGui getMainFrame() {return mainFrame;}

	public BasePanel(OneHashGui mainFrame){
		this.mainFrame = mainFrame;
		this.draw();
	}
	
	/**
	 * @author robin.foe
     * Drawing the component into panel
     */
	@Override
	public void run(){
		JPanel panel = this;
		if(this.isEnableHeader()){
			JLabel titleLabel = FactoryComponent.createLabel(this.getScreenTitle(), new PositionScalar(20,20,300,25));
			titleLabel.setFont(new Font(this.getScreenTitle(), Font.BOLD, 15) );
			panel.add(titleLabel);
			
			panel.add(FactoryComponent.createLabel("Logged in as : " + OneHashDataCache.getInstance().getCurrentUser().getUserName(), new PositionScalar(650,20,400,25)));
			
			JSeparator separator = new JSeparator();
			separator.setBounds(ConstantGUIAttribute.HEADER_SEPERATOR);
			panel.add(separator);
		}

		double maxWidth = 0;
		double maxHeight = 0;
		
		for(String key : componentMap.keySet()){
			Component comp = componentMap.get(key);
			Rectangle rect = comp.getBounds();
			if(this.isEnableHeader()){
				rect.y = rect.y + 40;
				comp.setBounds(rect);
			}
			maxWidth = ((rect.getX() + rect.getWidth()) > maxWidth ) ?  rect.getX() + rect.getWidth() : maxWidth;
			maxHeight = ((rect.getY() + rect.getHeight()) > maxHeight ) ?  rect.getY() + rect.getHeight() : maxHeight;
			panel.add(comp);
		}

		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension( (new BigDecimal(maxWidth).setScale(0, BigDecimal.ROUND_UP).intValue()) ,(new BigDecimal(maxHeight).setScale(0,BigDecimal.ROUND_UP).intValue()) ));
		this.initiateAccessRights();
	}
	
	public void draw(){
		this.init();
		EventQueue.invokeLater(this);
	}
	
	
	/**
	 * @author robin.foe
     * Registering java swing component
     */
	public final void registerComponent(String componentName , Component component){
		if(component == null) throw new IllegalArgumentException("component must not be null");
		if(OneHashStringUtil.isEmpty(componentName)) throw new IllegalArgumentException("componentName is required");
		if(componentMap.containsKey(componentName)) throw new IllegalArgumentException("Component name " + componentName + " already exist");
		componentMap.put(componentName, component);
	}
	
	/**
	 * @author robin.foe
     * Retrieving java swing component
     */
	public final Component getComponent(String componentName) {
		if(componentName == null) throw new IllegalArgumentException("component must not be null");
		if(OneHashStringUtil.isEmpty(componentName)) throw new IllegalArgumentException("componentName is required");
		if(componentMap.containsKey(componentName)) {
			return componentMap.get(componentName);
		}
		throw new IllegalArgumentException("Component name " + componentName + " is not exist");
	}
	
	
	/**
	 * @author robin.foe
     * Disabling java swing component, but will exclude the name in the parameters
     */
	public final void disableComponent(String... excludedComponentName){
		List<String> excludedList = new ArrayList<String>();
		if(excludedComponentName != null){
			excludedList = Arrays.asList(excludedComponentName);
		}
		
		for(String key : componentMap.keySet()){
			if(excludedList.contains(key)) continue;
			
			componentMap.get(key).setEnabled(false);			
		}
	}
	
	/**
	 * @author robin.foe
     * Short hand method to retrieve component
     */
	public final JTextField getTextFieldComponent(String componentName){return (JTextField)componentMap.get(componentName);}
	public final JTextArea getTextAreaComponent(String componentName){return (JTextArea)componentMap.get(componentName);}
	public final JLabel getLabelComponent(String componentName){return (JLabel)componentMap.get(componentName);}
	public final JCheckBox getCheckboxComponent(String componentName){return (JCheckBox)componentMap.get(componentName);}
	public final JComboBox getComboBoxComponent(String componentName){return (JComboBox)componentMap.get(componentName);}
	public final JButton getButtonComponent(String componentName){return (JButton)componentMap.get(componentName);}
	
	protected boolean isEnableHeader(){return true;}

	abstract protected void init();
	abstract protected String getScreenTitle();
	protected void initiateAccessRights(){/*IGNORED*/}

}
