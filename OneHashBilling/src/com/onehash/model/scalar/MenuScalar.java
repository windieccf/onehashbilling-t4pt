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

public class MenuScalar {
	
	private String menuName;
	public String getMenuName() {return menuName;}
	public void setMenuName(String menuName) {this.menuName = menuName;}

	private String klassName;
	public String getKlassName() {return klassName;}
	public void setKlassName(String klassName) {this.klassName = klassName;}
	
	private List<MenuScalar> childMenus = new ArrayList<MenuScalar>();
	public List<MenuScalar> getChildMenus() {return childMenus;}
	public void setChildMenus(List<MenuScalar> childMenus) {this.childMenus = childMenus;}
	
	public MenuScalar(){}
	public MenuScalar(String menuName, String klassName){
		this.menuName = menuName;
		this.klassName = klassName;
	}
	
	public MenuScalar cloneAttribute(){
		return new MenuScalar(menuName, klassName);
	}
	
	public String toString(){return this.getMenuName();}
	
}
