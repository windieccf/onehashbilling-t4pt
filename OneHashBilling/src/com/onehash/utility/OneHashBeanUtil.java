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
 * 15 March 2012    Robin Foe	    0.1				Initial Creation
 * 													
 * 													
 * 
 */

package com.onehash.utility;

import java.lang.reflect.Method;

public class OneHashBeanUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getSetterMethod(Class klass, String fieldName, Class... argumentsType) throws SecurityException, NoSuchMethodException{
		String setter = "set" + OneHashStringUtil.capitalize(fieldName);
		Method mtd = klass.getMethod(setter, argumentsType);
		return mtd;
	}

}
