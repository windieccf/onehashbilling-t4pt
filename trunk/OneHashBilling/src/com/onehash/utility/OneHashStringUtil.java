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
 * 10 March 2012    Robin Foe	    0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.utility;

/**
 *Perform basic string utility function 
 */

public class OneHashStringUtil {
	
	public static boolean isEmpty(String... texts){
		for(String text : texts){
			if(text == null || "".equals(text.trim()))
				continue;
			else
				return false;
		}
		return true;
	}
	
	public static String getEmptyIfNull(String text){
		return OneHashStringUtil.isEmpty(text) ? "" : text;
	}

}
