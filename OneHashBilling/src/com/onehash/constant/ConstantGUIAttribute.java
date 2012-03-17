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

package com.onehash.constant;

import java.awt.Rectangle;

public class ConstantGUIAttribute {
	
	public static final int GUI_LOGON_WIDTH = 400;
	public static final int GUI_LOGON_HEIGHT = 150;
	
	public static final int GUI_MAIN_WIDTH = 990;
	public static final int GUI_MAIN_HEIGHT = 500;
	
	
	public static final Rectangle HEADER_SEPERATOR;
	static{
		HEADER_SEPERATOR = new Rectangle(10, 50, 800, 10);
	}

}
