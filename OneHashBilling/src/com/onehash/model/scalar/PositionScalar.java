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

public class PositionScalar {

	private int posX;
	public int getPosX() {return posX;}
	public void setPosX(int posX) {this.posX = posX;}

	private int posY;
	public int getPosY() {return posY;}
	public void setPosY(int posY) {this.posY = posY;}

	private int width;
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}

	private int height;
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	
	public PositionScalar(){}
	
	public PositionScalar(int posX, int posY, int  width, int height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
}
