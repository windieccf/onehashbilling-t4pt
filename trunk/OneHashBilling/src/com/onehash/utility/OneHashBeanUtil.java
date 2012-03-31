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
 * 25 March 2012    Robin Foe	    0.2				Add in bean util copy properties and describe properties 													
 * 													
 * 
 */

package com.onehash.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneHashBeanUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getSetterMethod(Class klass, String fieldName, Class... argumentsType) throws SecurityException, NoSuchMethodException{
		String setter = "set" + OneHashStringUtil.capitalize(fieldName);
		Method mtd = klass.getMethod(setter, argumentsType);
		return mtd;
	}
	
	public static void copyProperties(Object destinationBean, Object sourceBean, String... excludedPropertyName){
		try{
			Map<String,Object> fieldMap = OneHashBeanUtil.describe(destinationBean, sourceBean);
			
			for(String exclude : excludedPropertyName)
				fieldMap.remove(exclude);

			// perform copy here
			@SuppressWarnings("rawtypes")
			Class currentKlass = destinationBean.getClass();
			
			for(String fieldName : fieldMap.keySet()){
				Field field = currentKlass.getDeclaredField(fieldName);
				
				// we will skip final and static modifier
				if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()))
					continue;
				
				field.setAccessible(true);
				field.set(destinationBean, fieldMap.get(fieldName));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static Map<String,Object> describe(Object destinationBean,Object sourceBean)throws Exception{
		Map<String,Object> map  = new HashMap<String,Object>();
		List<Field> fields = new ArrayList<Field>();
		
		@SuppressWarnings("rawtypes")
		Class currentKlass = destinationBean.getClass();
		while(true){
			fields.addAll(Arrays.asList(currentKlass.getDeclaredFields()));
			currentKlass = currentKlass.getSuperclass();
			if(currentKlass == null || "java.lang.Object".equals(currentKlass.getName()))
				break;
		}
		
		for(Field field : fields){
			try{
				field.setAccessible(true);
				map.put(field.getName(), field.get(sourceBean));
			}catch(Exception e){/*IGNORED*/}
		}
		
		return map;
	}

}
