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
 * 15 March 2012    Robin Foe	    0.1				Initial creating
 * 													
 * 													
 * 
 */

package com.onehash.model.scalar;

import com.onehash.view.component.listener.ButtonActionListener;


public class ButtonAttributeScalar extends PositionScalar{
	
	private ButtonActionListener actionListener;
	public ButtonActionListener getActionListener() {return actionListener;}
	public void setActionListener(ButtonActionListener actionListener) {this.actionListener = actionListener;}
	
	
	public ButtonAttributeScalar(int posX, int posY, int  width, int height){
		super(posX, posY, width, height);
	}
	
	public ButtonAttributeScalar(int posX, int posY, int  width, int height, ButtonActionListener actionListener){
		this(posX, posY, width, height);
		this.actionListener = actionListener;
	}
	
	

}
