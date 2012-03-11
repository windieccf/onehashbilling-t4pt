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

public class TextFieldAttributeScalar extends PositionScalar{
	
	private int columns;
	public int getColumns() {return columns;}
	public void setColumns(int columns) {this.columns = columns;}
	
	public TextFieldAttributeScalar(int posX, int posY, int  width, int height, int columns){
		super(posX, posY, width, height);
		this.columns = columns;
	}

}
