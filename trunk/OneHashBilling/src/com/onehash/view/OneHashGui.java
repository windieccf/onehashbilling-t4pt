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
 * 16 March 2012    Robin Foe	    0.2				Add in dynamic class loading that sense @PostCreate annotation 													
 * 													
 * 													
 * 
 */

package com.onehash.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.onehash.annotation.PostCreate;
import com.onehash.constant.ConstantGUIAttribute;
import com.onehash.controller.OneHashDataCache;
import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.security.AuthenticationPanel;
import com.onehash.view.panel.security.MenuPanel;

@SuppressWarnings("serial")
public class OneHashGui extends JFrame {
	
	public OneHashGui(){this.init();}
	private JSplitPane splitPanel;
	
	
	private void init(){
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		    	OneHashDataCache.getInstance().flushCache();
		        System.exit(0); 
		    }
		});
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		doLoadLoginScreen();
		//setBounds(100, 100, 768, 416);
	}
	
	public void doLoadLoginScreen(){
		this.setContentPane(new AuthenticationPanel(this));
		this.setSize(ConstantGUIAttribute.GUI_LOGON_WIDTH, ConstantGUIAttribute.GUI_LOGON_HEIGHT);
		this.setResizable( false );
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void doLoadScreen(Class<? extends BasePanel> klass,String... parameters){
		final OneHashGui frame = this;
		final Class<? extends BasePanel> clazz = klass;
		final String[] finalParam = parameters;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try{
					
					if(splitPanel == null){
						frame.setVisible(false);
						splitPanel = new JSplitPane();
						frame.setSize(ConstantGUIAttribute.GUI_MAIN_WIDTH,ConstantGUIAttribute.GUI_MAIN_HEIGHT);
						frame.setResizable( false );
						splitPanel.setDividerSize(5);
						splitPanel.setDividerLocation(155);
						splitPanel.setEnabled(false);
						splitPanel.setLeftComponent(new MenuPanel(frame));
						
						frame.setContentPane(splitPanel);
					}
					
					Constructor constructor = clazz.getConstructor(OneHashGui.class);
					Object panelComponent = (Object) constructor.newInstance(frame);
					JScrollPane scrollPane = new JScrollPane((Component)panelComponent);

					//scrollPane.setPreferredSize(new Dimension(300,300));
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					splitPanel.setRightComponent(scrollPane);
					//splitPanel.setRightComponent((Component)panelComponent);
					//check on method invocation with annotation post create
					Method[] methods = clazz.getMethods();
					for(Method method : methods){
						if(method.getAnnotation(PostCreate.class)!=null){ 
							method.getParameterTypes();
							method.invoke(panelComponent,Arrays.asList(finalParam));
						}
					}
					
					
					frame.setVisible(true);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		/*if(splitPanel == null){
			splitPanel = new JSplitPane();
			this.setSize(ConstantGUIAttribute.GUI_MAIN_WIDTH,ConstantGUIAttribute.GUI_MAIN_HEIGHT);
			this.setResizable( false );
			splitPanel.setDividerSize(5);
			splitPanel.setDividerLocation(155);
			splitPanel.setEnabled(false);
			splitPanel.setLeftComponent(new MenuPanel(this));
			
			this.setContentPane(splitPanel);
		}
		
		Constructor constructor = klass.getConstructor(OneHashGui.class);
		splitPanel.setRightComponent((Component) constructor.newInstance(this));*/
		
		
	}
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OneHashGui frame = new OneHashGui();
					//frame.setSize(800, 500);
					frame.setVisible(true);
					frame.setTitle("Team 4 One Hash Billing System");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
