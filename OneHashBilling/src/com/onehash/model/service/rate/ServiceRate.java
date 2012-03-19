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
 * 17 March 2012	Kenny Hartono	0.2				Added loadServiceRate()														
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.service.rate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public abstract class ServiceRate extends BaseEntity{
	
	public static final String SUBSCRPTION_CABLE_TV = "TV-S";
	public static final String SUBSCRPTION_MOBILE_VOICE = "MV-S";
	public static final String SUBSCRPTION_DIGITAL_VOICE = "DV-S";
	
	public static final String PREFIX_CABLE_TV = "TV-";
	public static final String PREFIX_MOBILE_VOICE = "MV-";
	public static final String PREFIX_DIGITAL_VOICE = "DV-";
	
	
	private String rateCode;
	public String getRateCode() {return rateCode;}
	public void setRateCode(String rateCode) {this.rateCode = rateCode;}
	
	private String rateDescription;
	public String getRateDescription() {return rateDescription;}
	public void setRateDescription(String rateDescription) {this.rateDescription = rateDescription;}
	
	private BigDecimal ratePrice;
	public BigDecimal getRatePrice() {return ratePrice;}
	public void setRatePrice(BigDecimal ratePrice) {this.ratePrice = ratePrice;}
	
	private int priority;
	public int getPriority() {return priority;}
	public void setPriority(int priority) {this.priority = priority;}
	
	private boolean freeCharge = false;
	public boolean isFreeCharge() {return freeCharge;}
	public void setFreeCharge(boolean freeCharge) {this.freeCharge = freeCharge;}
	
	public abstract BigDecimal calculateMonthlyRate();
	
	public static void loadServiceRate() {
		/**
		 * Load ServiceRate CSV File
		 */
		int priority = 0;
		String rateFile = "";
		ArrayList<ServiceRate> services = new ArrayList<ServiceRate>();
		// load Rates.csv
		try {
			rateFile = "data/Rates.csv";
			BufferedReader br = new BufferedReader(new FileReader(rateFile));
			StringTokenizer st;
			String line;
			HashMap<Integer, String> header = new HashMap<Integer, String>();
			HashMap<String, String> value = new HashMap<String, String>();
	
			// read header
			line = br.readLine();
			st = new StringTokenizer(line, ",");
			while (st.hasMoreTokens()) {
				header.put(header.size(), st.nextToken());
			}
			
			ServiceRate temp;
			// read data
			while ((line = br.readLine()) != null) {
				value.clear();
				st = new StringTokenizer(line, ",");
				int index = 0;
				while (st.hasMoreTokens()) {
					String columnValue = st.nextToken();
					String columnName = header.get(index);
					value.put(columnName, columnValue);
					index++;
				}
				
				if (value.get("Desc").indexOf("Subscription") >= 0) {
					temp = new SubscriptionRate();
				} else {
					temp = new UsageRate(0);
				}
				temp.setRateCode(value.get("RateCode"));
				temp.setRateDescription(value.get("Desc"));
				temp.setRatePrice(new BigDecimal(value.get("Rate")));
				temp.setPriority(priority);
				priority++;
				services.add(temp);
			}
		}
		catch (Exception e) {
			System.out.println(rateFile+" "+e);
		}
		
		// load TVChannel-Basic.csv
		try {
			rateFile = "data/TVChannel-Basic.txt";
			BufferedReader br = new BufferedReader(new FileReader(rateFile));
			String line;

			ServiceRate temp;
			// read data
			while ((line = br.readLine()) != null) {
				temp = new SubscriptionRate();
				temp.setRateCode("TV-C");
				temp.setRateDescription(line);
				temp.setRatePrice(new BigDecimal(5));
				temp.setPriority(priority);
				priority++;
				services.add(temp);
			}
		}
		catch (Exception e) {
			System.out.println(rateFile+" "+e);
		}


		// load VoiceFeatures.txt
		try {
			rateFile = "data/VoiceFeatures.txt";
			BufferedReader br = new BufferedReader(new FileReader(rateFile));
			String line;

			ServiceRate temp;
			// read data
			while ((line = br.readLine()) != null) {
				temp = new SubscriptionRate(); // voice features has SubscriptionRate
				temp.setRateCode(line);
				temp.setRateDescription(line);
				temp.setRatePrice(new BigDecimal(5));
				temp.setPriority(priority);
				priority++;
				services.add(temp);

				temp = new UsageRate(0); // voice features has UsageRate
				temp.setRateCode(line);
				temp.setRateDescription(line);
				temp.setRatePrice(new BigDecimal(5));
				temp.setPriority(priority);
				priority++;
				services.add(temp);
			}
		}
		catch (Exception e) {
			System.out.println(rateFile+" "+e);
		}
		
		// Set all ServiceRate
		OneHashDataCache.getInstance().setAvailableServiceRate(services);
	}
}
