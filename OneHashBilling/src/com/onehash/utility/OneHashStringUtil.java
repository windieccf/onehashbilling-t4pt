package com.onehash.utility;

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
