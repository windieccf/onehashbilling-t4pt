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

package com.onehash.view.panel.security;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.onehash.model.scalar.MenuScalar;
import com.onehash.view.OneHashGui;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel{
	
	private OneHashGui mainFrame;
	public OneHashGui getMainFrame() {return mainFrame;}

	public MenuPanel(OneHashGui mainFrame){
		this.mainFrame = mainFrame;
		this.init();
	}
	
	public void init(){
		DefaultMutableTreeNode root =	new DefaultMutableTreeNode("Root");
		MenuScalar menuScalar = getMenus();
		generateNodes(root, menuScalar.getChildMenus());
		
		JTree menuTree = new JTree(root);
		this.add(menuTree);
		this.setBackground(Color.WHITE);
	}
	
	public void generateNodes(DefaultMutableTreeNode root, List<MenuScalar> menus){
		for(MenuScalar menu : menus){
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(menu.cloneAttribute());
			if(!menu.getChildMenus().isEmpty())
				generateNodes(childNode, menu.getChildMenus());
				
			root.add(childNode);
		}
	}
	

	public MenuScalar getMenus(){
		MenuScalar rootMenu = new MenuScalar("Root Menu","");
		
		MenuScalar customerMenu = new MenuScalar("Customer","");
		rootMenu.getChildMenus().add(customerMenu);
			
		return rootMenu;
	}
	
	
}
