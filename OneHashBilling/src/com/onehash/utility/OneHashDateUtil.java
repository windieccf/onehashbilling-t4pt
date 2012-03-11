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
 * 10 March 2012    Chen Changfeng	0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.utility;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;

public class OneHashDateUtil {
    private static long milliSecPerDay = 24 * 60 * 60 * 1000;

    private static String dateFormat = "dd/MM/yyyy";
    private static String time12HrFormat = "hh:mm aa";
    private static String time24HrFormat = "HH:mm";
    private static String timezone = "Asia/Singapore";

    private OneHashDateUtil () {
    }

    /**
     * Compute the age returning an array of integers, for year, month, and day respectively.
     * <p>
     * @param  birthdate The start date to start the age computation.
     * @param  asOf      The end date for the age computation
     * @return The age in years, months, days in the 0, 1, 2 indices respectively.
     */
    public static int[] age ( Date birthdate, Date asOf ) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime( birthdate );
        to.setTime( asOf );

        int birthYYYY = from.get( Calendar.YEAR );
        int birthMM = from.get( Calendar.MONTH );
        int birthDD = from.get( Calendar.DAY_OF_MONTH );

        int asofYYYY = to.get( Calendar.YEAR );
        int asofMM = to.get( Calendar.MONTH );
        int asofDD = to.get( Calendar.DAY_OF_MONTH );

        int ageInYears = asofYYYY - birthYYYY;
        int ageInMonths = asofMM - birthMM;
        int ageInDays = asofDD - birthDD;

        if ( ageInDays < 0 ) {
            // Guaranteed after this single treatment, ageInDays will be >= 0.
            // i.e. ageInDays = asofDD - birthDD + daysInBirthMM.
            ageInDays += from.getActualMaximum( Calendar.DAY_OF_MONTH );
            ageInMonths--;
        }

        if ( ageInDays == to.getActualMaximum( Calendar.DAY_OF_MONTH ) ) {
            ageInDays = 0;
            ageInMonths++;
        }

        if ( ageInMonths < 0 ) {
            ageInMonths += 12;
            ageInYears--;
        }
        if ( birthYYYY < 0 && asofYYYY > 0 ) ageInYears--;

        if ( ageInYears < 0 ) {
            ageInYears = 0;
            ageInMonths = 0;
            ageInDays = 0;
        }

        int[] result = new int[3];
        result[0] = ageInYears;
        result[1] = ageInMonths;
        result[2] = ageInDays;
        return result;
    }

    /**
     * Calculate the duration in number of days. If the start date is later than the
     * end date, a negative value will be returned.
     * <p>
     * @param  startDate The start date of the duration.
     * @param  endDate   The end date of the duration.
     * @return The difference in number of days between the start date and end date.
     */
    public static long computeDuration ( Date startDate, Date endDate ) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime( startDate );
        to.setTime( endDate );

        // Set both from and to to the same time so that the number of
        // days calcualted will be accurate.
        from.set( Calendar.HOUR_OF_DAY, 0 );
        from.set( Calendar.MINUTE, 0 );
        from.set( Calendar.SECOND, 0 );
        from.set( Calendar.MILLISECOND, 0 );

        to.set( Calendar.HOUR_OF_DAY, 0 );
        to.set( Calendar.MINUTE, 0 );
        to.set( Calendar.SECOND, 0 );
        to.set( Calendar.MILLISECOND, 0 );

        long diff = to.getTime().getTime() - from.getTime().getTime();
        long days = diff / milliSecPerDay;

        return days;
    }

    /**
     * Get the formatted string of the given date instance based on the
     * date format provided.
     * <p>
     * @param  date   The date that needs to be formatted.
     * @param  format The format to be applied to the date.
     * @return The formatted Date String.
     */
    public static String format ( Date date, String format ) {
        if ( date == null || format == null ) return null;

        SimpleDateFormat sdf = new SimpleDateFormat( format );

        return sdf.format( date );
    }

    /**
     * Retrieves the value of the field in the Date.
     * Some common fields that is likely to be used include :
     * <p>
     * <li>Calendar.YEAR - retrieves the year value
     * <li>Calendar.MONTH - retrieves the month value ( 1 - 12 )
     * <li>Calendar.DAY_OF_MONTH - retrieve the day value ( 1 - 31 )
     * <li>Calendar.HOUR - retrieves the hours value in 12 hour format ( 1 - 12 )
     * <li>Calendar.HOUR_OF_DAY - retrieves the hours value in 24 hour format ( 0 - 23 )
     * <li>Calendar.MINUTE - retrieves the minutes value ( 0 - 59 )
     * <li>Calendar.AM_PM - retrieves the am/pm value ( 0 = am; 1 = pm )
     * <p>
     * @param  date  The Date object to extract value from.
     * @param  field A Calendar constant to retrieve the field value from the Date
     * object.
     * @return The value of the field that is requested.
     * @throws ArrayIndexOutOfBoundsException - if specified field is out of
     * range (<code>field</code> &lt; 0 || <code>field</code> &gt;= <code>Calendar.FIELD_COUNT</code>).
     * @see java.util.Calendar
     */
    public static int get ( Date date, int field ) {

        Calendar cal = Calendar.getInstance();

        cal.setTime( date );

        int value = cal.get( field );

        // Add 1 if the field is Calendar.MONTH since Calendar returns
        // the month value starting from 0.
        if ( Calendar.MONTH == field )
            value += 1;

        // If it is 12 am/pm, the value will be 0. Need to change it to 12 for ease of display.
        if ( Calendar.HOUR == field && value == 0 )
            value = 12;

        return value;
    }

    /**
     * Returns the date instance based on the current system date
     * <p>
     * @return Current System date.
     */
    public static Date getDate () {
        TimeZone tz = TimeZone.getTimeZone( timezone );
        return Calendar.getInstance( tz ).getTime();
    }
    
    /**
     * Returns the date instance based on the current system date
     * <p>
     * @return One day earlier date.
     */
    public static Date getPreviousDate () {
        TimeZone tz = TimeZone.getTimeZone( timezone );
        //SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HH:mm:ss");
		Calendar c = Calendar.getInstance(tz); 
		c.add(Calendar.DATE, -1); // number of days to subtract
		//String dt = sdf.format(c.getTime());
        
        return c.getTime();
    }

    /**
     * Return the Date instance with the provided year,
     * month ( 1 – 12 ), and day ( 1 - 31 ) values.
     * <p>
     * The date value will roll over when given a value that is greater
     * than the max possible. Eg. when getDate( 2002, 10, 32 )
     * is provided, the Date instance will be 1st Nov 2002.
     * <p>
     * @param  year  Year
     * @param  month Month ( 1 - 12 )
     * @param  day   Day( 1 - 31 )
     * @return The Date instance created.
     */
    public static Date getDate ( int year, int month, int day ) {
        Calendar cal = Calendar.getInstance();

        // Clear all fields first
        cal.clear();

        cal.set( year, month - 1, day );

        return cal.getTime();
    }

    /**
     * Return the Timestamp instance based on the current server date.
     *
     * @return the current server timestamp
     */
    public static Timestamp getTimestamp () {
        return new Timestamp(getDate().getTime());
    }

    /**
     * Return the Timestamp instance with the provided year,
     * month ( 1 – 12 ), and day ( 1 - 31 ) values.
     * <p>
     * The date value will roll over when given a value that is greater
     * than the max possible. Eg. when getDate( 2002, 10, 32 )
     * is provided, the Date instance will be 1st Nov 2002.
     * <p>
     * @param  year  Year
     * @param  month Month ( 1 - 12 )
     * @param  day   Day( 1 - 31 )
     * @return The Date instance created.
     */

    public static Timestamp getTimestamp ( int year, int month, int day ) {
        return new Timestamp ( getDate( year, month, day).getTime() );
    }

    /**
     * Tests if the input year is a leap year. Overriding method
     * that accepts either an integer representing the year, or a Date object.
     * <p>
     * @param  year The year to check if it is a leap year.
     * @return True if the year is a leap year; False otherwise.
     */
    public static boolean isLeap ( int year ) {
        GregorianCalendar cal = new GregorianCalendar();

        return cal.isLeapYear( year );
    }

    /**
     * Tests if the input year is a leap year. Overriding method
     * that accepts either an integer representing the year, or a Date object.
     * <p>
     * @param  date The Date instance to check if it falls on a leap year.
     * @return True if the date falls on a leap year; False otherwise.
     */
    public static boolean isLeap ( Date date ) {

        if ( date == null ) return false;

        int year = get( date, Calendar.YEAR );

        return isLeap( year );
    }

    /**
     * Tests the input date to check if the date falls on a Saturday.
     * <p>
     * @param  date The date instance.
     * @return True if the date falls on a Saturday; False otherwise
     */
    public static boolean isSaturday ( Date date ) {

        if ( date == null ) return false;

        if ( get( date, Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY )
            return true;
        else
            return false;
    }

    /**
     * Tests the input date to check if the date falls on a Sunday.
     * <p>
     * @param  date The date instance.
     * @return True if the date falls on a Sunday; False otherwise
     */
    public static boolean isSunday ( Date date ) {

        if ( date == null ) return false;

        if ( get( date, Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY )
            return true;
        else
            return false;
    }

    /**
     * Tests the input value to ensure that a valid Date instance can be created from it.
     * Roll over dates are not allowed here and will return a false value.
     * Eg. isValidDate(2002, 10, 32) will return false.
     * <p>
     * @param  year  The year value.
     * @param  month The month value. ( 1 - 12 )
     * @param  day   The day value. ( 1 - 31 )
     * @return True if all values can be used to create a single valid Date instance.
     */
    public static boolean isValidDate ( int year, int month, int day ) {

        if ( day <= 0 || month <= 0 || year <= 0 ) return false;
        if ( month > 12 || day > 31 ) return false;

        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.YEAR, year );
        cal.set( Calendar.MONTH, month - 1 );

        // Find the maximum field value possible for the day with the year and month.
        int maxDay = cal.getActualMaximum( Calendar.DAY_OF_MONTH );
        if ( day > maxDay ) return false;

        return true;
    }

    /**
     * Tests the input string to ensure that a valid Date instance can be created from it
     * according to the format provided.
     * <p>
     * @param  date   A date string.
     * @param  format Date format to conform to.
     * @return True if it conforms to the specified date format; False otherwise.
     */
    public static boolean isValidDate ( String date, String format ) {

        if ( date == null ) return false;

        try {
           parse( date, format );
        } catch ( java.text.ParseException e ) {
            return false;
        }
        return true;
    }

    /**
     * Tests the input string to ensure that a valid Date instance can be created
     * according to the date format specified in the System property.
     * <p>
     * If the properties file is not available or the dateformat property has
     * not been specified, the default format "dd/MM/yyyy" will be used.
     * <p>
     * @param  date A date string.
     * @return True if it conforms to the system date format; False otherwise.
     */
    public static boolean isValidDate ( String date ) {
        if ( date == null ) return false;

        try {
        	parse( date );
        } catch ( java.text.ParseException e ) {
            return false;
        }

        return true;
    }

    /**
     * Tests if the inputs are valid time. When the ampm parameter is true,
     * the input hour will be tested for 12-hour format ( 1 – 12 ).
     * When it is false, the input hour will be tested for 24-hour format ( 0 – 23 ).
     * <p>
     * @param  hour   The Hour value. ( 0 - 23 or 1 - 12 )
     * @param  minute The Minute value. ( 0 - 59 )
     * @param  ampm   If true, the time is in 12 hour format.
     * Otherwise, it is in 24 hour format.
     *
     * @return True if the time inputs can be used to create a valid time instance.
     */
    public static boolean isValidTime ( int hour, int minute, boolean ampm ) {

        if ( minute < 0 || minute > 59 )
            return false;

        if ( ampm && ( hour < 1 || hour > 12 ) )
            return false;

        else if ( hour < 0 || hour > 23 )
            return false;

        else
            return true;
    }

    /**
     * Tests the input string to ensure that a valid time can be created
     * according to the 12 Hr time format in the System property.
     * <p>
     * If the properties file is not available or the timeformat property has
     * not been specified, the default 12 Hr format "hh:mm aa" will be used.
     * <p>
     * @param  time The time string.
     * @return True if it conforms to the system time format; False otherwise.
     */
    public static boolean isValidTime ( String time ) {

        if ( time == null ) return false;

        String timeformat = null;
       
        // Use the default format of "hh:mm aa".
        timeformat = time12HrFormat;
       

        try {
            parse( time, timeformat );
        } catch ( java.text.ParseException e ) {
            return false;
        }

        return true;
    }

    /**
     * Tests the input string to ensure that a valid time can be created
     * according to the time format provided.
     * <p>
     * @param  time   The time string.
     * @param  format Time format to conform to.
     * @return True if it conforms to the specified time format; False otherwise.
     */
    public static boolean isValidTime ( String time, String format ) {

        if ( time == null || format == null ) return false;

        try {
            parse( time, format );
        } catch ( java.text.ParseException e ) {
            return false;
        }

        return true;
    }

    /**
     * Tests the input date to check if the date falls on a weekend.
     * Weekend falls on a Saturday or a Sunday.
     * <p>
     * @param date The date instance.
     * @return True if the date falls on a weekend; False otherwise.
     */
    public static boolean isWeekend ( Date date ) {

        if ( isSaturday( date ) || isSunday( date ) )
            return true;
        else
            return false;
    }

    /**
     * Returns a Date object instance from the input string.
     * The input string is parsed to return a date object based
     * on the date format specified in the System property.
     * <p>
     * @param  date The date string.
     * @return The date instance created.
     * @throws java.text.ParseException  - if the beginning of the specified string cannot be parsed.
     */
    public static Date parse ( String date ) throws java.text.ParseException {
        // Modified by Aris on 02/02/2009 for case 1296351
		//if ( date == null || date.length()==0) return null;
		if ( date == null ) return null;

        String dateformat = null;
        // Use the default format of "dd/MM/yyyy".
        dateformat = dateFormat;

        return parse( date, dateformat );
    }

    /**
     * Returns a Date object instance from the input string.
     * The input date string is parsed to return a date object
     * based on the format provided.
     * <p>
     * Eg., to parse the date string "01/01/2003 9:2 PM", use the
     * format "dd/MM/yyyy h:m a". The resultant data object will have
     * the value "Mar 11 2003 09:02", as displayed in 24 hr format.
     * <p>
     * @param  date   The date string.
     * @param  format The date format that the date string conforms to.
     * @return The date instance created.
     * @throws java.text.ParseException  - if the beginning of the specified string cannot be parsed.
     */
    public static Date parse ( String date, String format ) throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( format );
        sdf.setLenient( false );

        return sdf.parse( date );
    }

    /**
     * Returns a Date object instance from the input string.
     * This method is specifically for date string of the form "EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy"
     * <p>
     * For eg, "Thu Jun 30 15:00:54 GMT+08:00 2003"
     *
     * @param date  The date string of the format "EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy"
     * @return The date instance created.
     * @throws java.text.ParseException - if the date string is not of the given format.
     */
    public static Date parseLocale ( String date ) throws java.text.ParseException {

        String localeFormat = "EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy";

        return parse( date, localeFormat );
    }

    /**
     * Return the formatted string of the given date instance based on
     * the 12 hour format specified for time in the System property.
     * <p>
     * If the properties file is not available or the timeformat property has
     * not been specified, the default format "hh:mm aa" will be used.
     * <p>
     * @param  date The Date instance.
     * @return The Time String from the date instance in 24 hour format.
     */
    public static String to12HrTimeString ( Date date ) {

        if ( date == null ) return null;

        String timeformat = null;
        // Use the default format of "hh:mm aa".
        timeformat = time12HrFormat;

        return format( date, timeformat );
    }

    /**
     * Return the formatted string of the given date instance based on
     * the 24 hour format specified for time in the System property.
     * <p>
     * If the properties file is not available or the timeformat property has
     * not been specified, the default format "HH:mm" will be used.
     * <p>
     * @param  date The Date instance.
     * @return The Time String from the date instance in 24 hour format.
     */
    public static String to24HrTimeString ( Date date ) {

        if ( date == null ) return null;

        String timeformat = null;
        // Use the default format of "hh:mm aa".
        timeformat = time24HrFormat;

        return format( date, timeformat );
    }

    /**
     * Date Arithmetic function. Adds the specified (signed) amount of time to
     * the given time field, based on the calendar's rules.
     * <p>
     * For example, to subtract 5 days from a specific date, it can be achieved
     * by calling: <p>
     * DateUtil.add(date, Calendar.DATE, -5).
     * <p>
     * @param date   The date to perform the arithmetic function on
     * @param field  A Calendar constant to retrieve the field value from the Date
     * object. Same as for {@link #get get()}.
     * @param amount the amount of date or time to be added to the field
     * @return The date as a result of the execution of the arithmetic function.
     */
    public static Date add ( Date date, int field, int amount ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );
        cal.add( field, amount );

        return cal.getTime();
    }

    /**
     * Set the time component as the last seconds of the day.
     * <p>
     * The Time Component of the date returned will be set to
     * 23:59:59.
     * <p>
     * @param date The Date to get the last seconds
     * @return The date with the time component set to the last seconds
     * of the day.
     */
    public static Date getEndOfDay ( Date date ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );

        // Clear the time component
        cal.set( Calendar.HOUR_OF_DAY, cal.getActualMaximum( Calendar.HOUR_OF_DAY ) );
        cal.set( Calendar.MINUTE, cal.getActualMaximum( Calendar.MINUTE ) );
        cal.set( Calendar.SECOND, cal.getActualMaximum( Calendar.SECOND ) );
        cal.set( Calendar.MILLISECOND, cal.getActualMaximum( Calendar.MILLISECOND ) );

        // System.out.println( "cal.toString() = " + cal.toString() );

        return cal.getTime();
    }

    /**
     * Set the time component as the start of the day.
     * <p>
     * The Time Component of the date returned will be set to
     * 00:00:00.
     * <p>
     * @param date The Date to get the start of the day
     * @return The date with the time component reset to 00:00:00.
     */
    public static Date getStartOfDay ( Date date ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime( date );

        // Clear the time component
        int year = cal.get( Calendar.YEAR );
        int month = cal.get( Calendar.MONTH ) + 1;
        int day = cal.get( Calendar.DAY_OF_MONTH );

        Date start = getDate( year, month, day );
        return start;
    }

	/**
	 * @param date, time
	 * @return The date with the time component.
	 */
	public static Date getTimeStamp(Date date, String time) {

		if (date == null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = Integer.parseInt(time.substring(0, 2));
		int minute = Integer.parseInt(time.substring(3));
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	
	/**
	    * Parse Date to array of string
	    *
	    * @param 	date : date to be converted
	    * @return 	array of string where String[0] = dd, String[1] = mm, String[2] = yyyy
	    */
	  public static String[] parseDateToStr( Date date )
	  {
	    String strFormat = "ddMMyyyy";
	    SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
	    String[] arrDate = new String[3];
	    String strDate = null;
	    if (date == null)
	      return arrDate;

	    strDate = dateFormat.format(date);
	    arrDate[0] = strDate.substring(0, 2);
	    arrDate[1] = strDate.substring(2, 4);
	    arrDate[2] = strDate.substring(4);
	    return arrDate;
	  } 

}
