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
 * 16 March 2012    Robin Foe		0.2				Add in Document listener support							
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.model.scalar;

import com.onehash.view.component.listener.OneHashTextFieldListener;


public class TextFieldAttributeScalar extends PositionScalar{
	
	private int columns;
	public int getColumns() {return columns;}
	public void setColumns(int columns) {this.columns = columns;}
	
	private OneHashTextFieldListener documentListener;
	public OneHashTextFieldListener getDocumentListener() {return documentListener;}
	public void setDocumentListener(OneHashTextFieldListener documentListener) {this.documentListener = documentListener;}
	
	public TextFieldAttributeScalar(int posX, int posY, int  width, int height, int columns){
		super(posX, posY, width, height);
		this.columns = columns;
	}
	
	
	public TextFieldAttributeScalar(int posX, int posY, int  width, int height, int columns, OneHashTextFieldListener documentListener){
		this(posX, posY, width, height, columns);
		this.documentListener =documentListener;
	}

}
