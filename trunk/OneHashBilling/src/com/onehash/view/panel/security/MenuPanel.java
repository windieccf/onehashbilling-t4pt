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
 * 11 March 2012    Robin Foe	    0.1				Class Creation
 * 12 March 2012	Chen Changfeng	0.2				Menu creating												
 * 01 April 2012    Robin Foe	    0.3				Add in user access rights and logout confirmation
 * 
 */

package com.onehash.view.panel.security;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.onehash.controller.OneHashDataCache;
import com.onehash.enumeration.EnumUserAccess;
import com.onehash.model.scalar.MenuScalar;
import com.onehash.model.user.User;
import com.onehash.view.OneHashGui;
import com.onehash.view.panel.bill.BillListPanel;
import com.onehash.view.panel.bill.BillReportPanel;
import com.onehash.view.panel.complaint.ComplaintListPanel;
import com.onehash.view.panel.customer.CustomerListPanel;
import com.onehash.view.panel.master.subscriptionplan.ServicePlanListPanel;
import com.onehash.view.panel.payment.PaymentPanel;
import com.onehash.view.panel.user.UserListPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel{
	
	private OneHashGui mainFrame;
	public OneHashGui getMainFrame() {return mainFrame;}

	public MenuPanel(OneHashGui mainFrame){
		this.mainFrame = mainFrame;
		this.init();
	}
	
	public void init(){
		DefaultMutableTreeNode root =	new DefaultMutableTreeNode("One Hash");
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
		User currUser = OneHashDataCache.getInstance().getCurrentUser();
		
		if(currUser.hasRights(EnumUserAccess.USER_VIEW, EnumUserAccess.MASTER_SERVICE_PLAN_VIEW)){
			MenuScalar masterSetupMenu = MenuScalar.createParentMenu("Setup");
			rootMenu.getChildMenus().add(masterSetupMenu);
			masterSetupMenu.getChildMenus().add(MenuScalar.createChildMenu("User",UserListPanel.class));
			masterSetupMenu.getChildMenus().add(MenuScalar.createChildMenu("Service Plan",ServicePlanListPanel.class));
		}
		
		/******************* CUSTOMER MENU ***********************/
		if(currUser.hasRights(EnumUserAccess.CUSTOMER_VIEW , EnumUserAccess.COMPLAINT_VIEW) ){
			MenuScalar customerParentMenu = MenuScalar.createParentMenu("Customer");
			rootMenu.getChildMenus().add(customerParentMenu);
			
			if(currUser.hasRights(EnumUserAccess.CUSTOMER_VIEW))
				customerParentMenu.getChildMenus().add(MenuScalar.createChildMenu("Customer",CustomerListPanel.class));
			
			if(currUser.hasRights(EnumUserAccess.COMPLAINT_VIEW))
				customerParentMenu.getChildMenus().add(MenuScalar.createChildMenu("Complaint",ComplaintListPanel.class));
				
		}
			
		
		/******************* BILL MENU ***********************/
		if(currUser.hasRights(EnumUserAccess.BILL_VIEW , EnumUserAccess.REPORT_VIEW) ){
			MenuScalar BillMenu = MenuScalar.createParentMenu("Bill");
			rootMenu.getChildMenus().add(BillMenu);
			
			if(currUser.hasRights(EnumUserAccess.BILL_VIEW))
				BillMenu.getChildMenus().add( MenuScalar.createChildMenu("View Bill", BillListPanel.class) );
			
			if(currUser.hasRights(EnumUserAccess.REPORT_VIEW))
				BillMenu.getChildMenus().add( MenuScalar.createChildMenu("Monthly Report", BillReportPanel.class) );
		}
		
		
		/******************* PAYMENT MENU ***********************/
		if(currUser.hasRights(EnumUserAccess.PAYMENT_VIEW) ){
			MenuScalar paymentMenu = MenuScalar.createParentMenu("Payment");
			rootMenu.getChildMenus().add(paymentMenu);
			
			paymentMenu.getChildMenus().add( MenuScalar.createChildMenu("Payment", PaymentPanel.class) );
		}
		
		/******************* LOG OUT MENU ***********************/
		MenuScalar LogOutMenu = MenuScalar.createParentMenu("Access");
		rootMenu.getChildMenus().add(LogOutMenu);
		LogOutMenu.getChildMenus().add( MenuScalar.createChildMenu("Log Out", AuthenticationPanel.class) );
		
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
		    
		    if(! (selectedNode.getUserObject() instanceof String) ){
		    	MenuScalar menuScalar = (MenuScalar)selectedNode.getUserObject();
			    if(menuScalar.isAccessible()){
			    	if(AuthenticationPanel.class.equals(menuScalar.getKlass())){
			    		if(0 == JOptionPane.showConfirmDialog(this.menuPanel.getMainFrame(),"Do you wish to logout?","Alert",JOptionPane.YES_NO_OPTION)){
			    			OneHashDataCache.getInstance().logout();
					    	this.menuPanel.getMainFrame().doLoadLoginScreen();
			    		}
			    	}else
			    		this.menuPanel.getMainFrame().doLoadScreen(menuScalar.getKlass());
			    }
		    }
		    
		}
		
	}
	
	
}
