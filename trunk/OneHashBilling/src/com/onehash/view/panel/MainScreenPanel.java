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
package com.onehash.view.panel;


import com.onehash.model.scalar.PositionScalar;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class MainScreenPanel extends BasePanel {
	private static final String COMP_LBL_WELCOME = "WELCOME_SPLASHER";
	public MainScreenPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	

	@Override
	protected void init() {
		super.registerComponent(COMP_LBL_WELCOME , FactoryComponent.createLabel("Please use the menu on the left side to navigate", new PositionScalar(38,26,300,200)));
	}
	@Override
	protected String getScreenTitle() {return COMP_LBL_WELCOME;}

}
