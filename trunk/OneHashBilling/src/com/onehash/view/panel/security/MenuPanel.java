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
 * 12 March 2012	Chen Changfeng	0.1				Menu creating												
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.view.panel.security;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.onehash.model.scalar.MenuScalar;
import com.onehash.view.OneHashGui;
import com.onehash.view.panel.bill.BillListPanel;
import com.onehash.view.panel.bill.BillReportPanel;
import com.onehash.view.panel.complaint.ComplaintListPanel;
import com.onehash.view.panel.customer.CustomerListPanel;

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
		// expanding all menu 
		for (int i = 0; i < menuTree.getRowCount(); i++) {
			menuTree.expandRow(i);
		}
		
		this.bindEvent(menuTree);
		JScrollPane scrollPane = new JScrollPane(menuTree);
		
		// removing borders
		scrollPane.setBorder(null);
		
		this.add(scrollPane, BorderLayout.EAST);
		this.setBackground(Color.WHITE);
	}
	
	private void bindEvent(JTree menuTree){
		menuTree.addTreeSelectionListener(new MenuPanel.MenuPanelSelectionListener(this));
	}
	
	private void generateNodes(DefaultMutableTreeNode root, List<MenuScalar> menus){
		for(MenuScalar menu : menus){
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(menu.cloneAttribute());
			if(!menu.getChildMenus().isEmpty())
				generateNodes(childNode, menu.getChildMenus());
				
			root.add(childNode);
		}
	}
	
	
	

	public MenuScalar getMenus(){
		MenuScalar rootMenu = MenuScalar.createParentMenu("One Hash Billing");
		
		/******************* CUSTOMER MENU ***********************/
		MenuScalar customerMenu = MenuScalar.createChildMenu("Customer",CustomerListPanel.class);
		rootMenu.getChildMenus().add(customerMenu);
		
		/******************* BILL MENU ***********************/
		MenuScalar BillMenu = MenuScalar.createParentMenu("Bill");
		rootMenu.getChildMenus().add(BillMenu);
		
		BillMenu.getChildMenus().add( MenuScalar.createChildMenu("View Bill", BillListPanel.class) );
		BillMenu.getChildMenus().add( MenuScalar.createChildMenu("Monthly Report", BillReportPanel.class) );
		
		/******************* COMPLAINT MENU ***********************/
		MenuScalar ComplaintlMenu = MenuScalar.createParentMenu("Complaint");
		rootMenu.getChildMenus().add(ComplaintlMenu);
		
		ComplaintlMenu.getChildMenus().add( MenuScalar.createChildMenu("View Complaint", ComplaintListPanel.class) );
		
		
		return rootMenu;
	}

	
	/******************** LISTENER IMPLEMENTATION **************/
	private static class MenuPanelSelectionListener implements  TreeSelectionListener{
		private MenuPanel menuPanel;
		public MenuPanelSelectionListener(MenuPanel menuPanel){ this.menuPanel = menuPanel;}
		
		@Override
		public void valueChanged(TreeSelectionEvent selectionEvent) {
			JTree tree = (JTree) selectionEvent.getSource();
		    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		    MenuScalar menuScalar = (MenuScalar)selectedNode.getUserObject();
		    if(menuScalar.isAccessible())
		    	this.menuPanel.getMainFrame().doLoadScreen(menuScalar.getKlass());
			
		}
		
	}
	
	
}
