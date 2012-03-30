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

import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.panel.MainScreenPanel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class AuthenticationPanel extends BasePanel {
	
	private static final String COMP_LBL_USERNAME = "LBL_USER_NAME";
	private static final String COMP_LBL_PASSWORD = "LBL_PASSWORD";
	
	private static final String COMP_TXT_USERNAME = "TXT_USER_NAME";
	private static final String COMP_TXT_PASSWORD = "TXT_PASSWORD";
	private static final String COMP_BUTTON_LOGIN = "BTN_LOGIN";
	
	public AuthenticationPanel(OneHashGui mainFrame) {super(mainFrame);}

	@Override
	protected void init() {
		super.registerComponent(COMP_LBL_USERNAME , FactoryComponent.createLabel("Username", new PositionScalar(38,26,79,14)));
		super.registerComponent(COMP_LBL_PASSWORD , FactoryComponent.createLabel("Password", new PositionScalar(38,51,79,14)));
		super.registerComponent(COMP_TXT_USERNAME, FactoryComponent.createTextField( new TextFieldAttributeScalar(146, 23, 126, 20,10) ));
		super.registerComponent(COMP_TXT_PASSWORD , FactoryComponent.createPasswordField( new TextFieldAttributeScalar(146, 48, 126, 20,0) ));
		
		
		super.registerComponent(COMP_BUTTON_LOGIN , FactoryComponent.createButton("Login", new ButtonAttributeScalar(178, 79, 96, 23 , new ButtonActionListener(this,"doLogin"))) );
	//	JButton loginButton = FactoryComponent.createButton("Login", new ButtonAttributeScalar(178, 79, 96, 23));
	//	loginButton.addActionListener(new AuthenticationPanel.LoginButtonListener(this));
	//	super.registerComponent(COMP_BUTTON_LOGIN , loginButton);
		
	}
	
	public void doLogin(){
		
		
		
		super.getMainFrame().doLoadScreen(MainScreenPanel.class);
	}
	
	/*public static class LoginButtonListener implements ActionListener {
		
		private BasePanel basePanel;
		public LoginButtonListener(BasePanel basePanel){ this.basePanel = basePanel;}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			basePanel.getMainFrame().doLoadScreen(MainScreenPanel.class);
        }
    }*/
	
	@Override
	protected String getScreenTitle() {return "Login";}
	
	@Override
	protected boolean isEnableHeader(){return false;}
	
	
}
