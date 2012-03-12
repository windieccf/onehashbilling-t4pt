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

package com.onehash.model.scalar;

import java.util.ArrayList;
import java.util.List;

import com.onehash.view.panel.base.BasePanel;

public class MenuScalar {
	
	private String menuName;
	public String getMenuName() {return menuName;}
	public void setMenuName(String menuName) {this.menuName = menuName;}

	private Class<? extends BasePanel> klass;
	public Class<? extends BasePanel> getKlass() {return klass;}
	public void setKlass(Class<? extends BasePanel> klass) {this.klass = klass;}

	private List<MenuScalar> childMenus = new ArrayList<MenuScalar>();
	public List<MenuScalar> getChildMenus() {return childMenus;}
	public void setChildMenus(List<MenuScalar> childMenus) {this.childMenus = childMenus;}

	private MenuScalar(){}
	private MenuScalar(String menuName){this(menuName,null);}
	private MenuScalar(String menuName, Class<? extends BasePanel> klass){
		this.menuName = menuName;
		this.klass = klass;
	}
	

	/******************************* UTILITY *************************************/
	public boolean isAccessible(){return (klass!=null);}
	
	public MenuScalar cloneAttribute(){return new MenuScalar(menuName, klass);}
	
	public String toString(){return this.getMenuName();}

	
	/********************************************* CREATION OF MENU SCALAR *****************************************************/
	public static MenuScalar createParentMenu(String menuName){return new MenuScalar(menuName);}
	public static MenuScalar createChildMenu(String menuName,Class<? extends BasePanel> klass){return new MenuScalar(menuName,klass);}
}
