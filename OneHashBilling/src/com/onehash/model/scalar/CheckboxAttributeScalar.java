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

import com.onehash.view.component.listener.BooleanCheckBoxListener;

public class CheckboxAttributeScalar extends PositionScalar{
	
	private BooleanCheckBoxListener itemListener;
	public BooleanCheckBoxListener getItemListener() {return itemListener;}
	public void setItemListener(BooleanCheckBoxListener itemListener) {this.itemListener = itemListener;}

	public CheckboxAttributeScalar(int posX, int posY, int  width, int height){
		super(posX, posY, width, height);
	}
	public CheckboxAttributeScalar(int posX, int posY, int  width, int height, BooleanCheckBoxListener itemListener){
		this(posX, posY, width, height);
		this.itemListener =itemListener;
	}

}
