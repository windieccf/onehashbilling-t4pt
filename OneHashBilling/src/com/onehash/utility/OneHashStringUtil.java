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
 * 10 March 2012	Chen Changfeng	0.2				Adding new Util methods											
 * 15 March 2012	Robin Foe		0.3				Adding new Util methods											
 * 													
 * 													
 * 
 */
package com.onehash.utility;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

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
	
	public static String capitalize(String text){
		if(OneHashStringUtil.isEmpty(text)) return text;
		
		String capitalizeString = text.substring(0,1).toUpperCase();
		if(text.length() < 1)
			return capitalizeString;
		else{
			return capitalizeString + text.substring(1);
		}
	}
	
	/**
     * To pad the given string with spaces up to the given length. <br>
     * e.g. rPad("ABCD", 10, ' ') returns "ABCD      " which has a length of 10.
     *
     * This method has built-in 'intelligence' to handle cases where
     * calling method tries to be funny and supply the following<br>
     * - rPad("abc", 10, "123") it will return, "abc1231231"
     *
     * @param str String to be padded
     * @param length The required length of the resulted string
     * @param padString The required padding string.
     * @return The padded string.
     * If str already <I>longer</I> than <I>length</I>, return str itself.
     */
    public static String rPad ( String str, int length, String padString ) {
        int lOriginal = str.length();
        int lPadStr = padString.length();
        int times2Pad = 0;
        int lPadded = 0;

        if ( lOriginal >= length )
            return str;

        StringBuffer sb = new StringBuffer( str ); //add the original str first
        String padded;

        times2Pad = ( length - lOriginal ) / lPadStr;  //will give (1) if 3/2

        padded = duplicate( padString, times2Pad );
        lPadded = padded.length();
        sb.append( padded );        //pad in the repetitive characters

        //if still insufficient by the modulus e.g. 30/20 is 10
        if ( lOriginal + lPadded < length ) {
            int more = length - ( lOriginal + lPadded );

            //add in the difference which is less entire length of padStr
            sb.append( padString.substring( 0, more ) );
        }

        return sb.toString();
    }
    
    /**
     * To return a string which is filled with a specified string.
     * e.g. duplicate("*", 5) returns "*****", duplicate("OK", 3) returns "OKOKOK"
     * repeated for given number of times
     *
     * @param str String to be repeated/duplicated
     * @param times Number of time the string to be repeated/duplicated
     * @return The resulted string with <code>str</code> repeated the specified number of times.
     */
    public static String duplicate ( String str, int times ) {
        StringBuffer result = new StringBuffer();

        for ( int i = 0; i < times; i++ ) {
            result.append( str );
        }
        return ( result.toString() );
    }
    
    /**
     * To pad the given string with a user specified character on the left up to the
     * given length.
     * e.g. lPad("ABCD", 10, 'X') returns "XXXXXXABCD" which has a length of 10.
     * This method has built-in 'intelligence' to handle cases where calling method
     * If <I>str</I> already longer than <I>length</I>, return <I>str</I> itself.
     * tries to be funny and supply the following :
     * - lPad("abc", 10, "123") it will return, "1231231abc"
     *
     * @param str String to be padded
     * @param length he required length of the resulted string.
     * @param padString The required padding string
     * @return The padded string
     */
    public static String lPad ( String str, int length, String padString ) {
        int lOriginal = str.length();
        int lPadStr = padString.length();
        int times2Pad = 0;
        int lPadded = 0;

        if ( lOriginal >= length )
            return str;

        StringBuffer sb = new StringBuffer();
        String padded;

        times2Pad = ( length - lOriginal ) / lPadStr;  //will give (1) if 3/2

        padded = duplicate( padString, times2Pad );
        lPadded = padded.length();
        sb.append( padded );        //pad in the repetitive characters

        //if still insufficient by the modulus e.g. 30/20 is 10
        if ( lOriginal + lPadded < length ) {
            int more = length - ( lOriginal + lPadded );

            //add in the difference which is less entire length of padStr
            sb.append( padString.substring( 0, more ) );
        }

        sb.append( str ); //pad the original string behind

        return sb.toString();
    }
    
    /**
     * Pads the string with prevailing spaces.
     *
     * @param str String to be padded with spaces on the left.
     * @param len The number of spaces to pad to the left of the string.
     * @return The space-padded string.
     */
    public static String lPad ( String str, int len ) {
        return lPad( str, len, " " );
    }
    
    public static String maskAccountNumber(long accountNo) throws ParseException{
    	NumberFormat formatter = new DecimalFormat("000000000");
    	return formatter.format(accountNo);
    }

}
