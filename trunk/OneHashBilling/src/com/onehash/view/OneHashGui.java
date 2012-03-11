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

package com.onehash.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.lang.reflect.Constructor;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.onehash.view.panel.base.BasePanel;
import com.onehash.view.panel.security.AuthenticationPanel;
import com.onehash.view.panel.security.MenuPanel;

@SuppressWarnings("serial")
public class OneHashGui extends JFrame {
	
	public OneHashGui(){this.init();}
	private JSplitPane splitPanel;
	
	
	private void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		doLoadLoginScreen();
		//setBounds(100, 100, 768, 416);
	}
	
	public void doLoadLoginScreen(){
		this.setContentPane(new AuthenticationPanel(this));
		this.setSize(400, 150);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void doLoadScreen(Class<? extends BasePanel> klass){
		try{
			if(splitPanel == null){
				splitPanel = new JSplitPane();
				this.setSize(800,500);
				this.setContentPane(splitPanel);
				splitPanel.setLeftComponent(new MenuPanel(this));
			}
			
			Constructor constructor = klass.getConstructor(OneHashGui.class);
			splitPanel.setRightComponent((Component) constructor.newInstance(this));
			
		}catch(Exception e){
			e.printStackTrace();
		}
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
