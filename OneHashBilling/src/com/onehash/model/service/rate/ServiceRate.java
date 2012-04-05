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
 * 05 April 2012	Kenny Hartono	0.3				Generating random customer service plan													
 * 													
 * 													
 * 
 */
package com.onehash.model.service.rate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.onehash.constant.ConstantSummary;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.customer.Customer;
import com.onehash.model.service.plan.CableTvPlan;
import com.onehash.model.service.plan.DigitalVoicePlan;
import com.onehash.model.service.plan.MobileVoicePlan;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.usage.MonthlyUsage;
import com.onehash.model.usage.TalkTimeUsage;

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
				
				if (value.get("Desc").indexOf("Subscription") >= 0 || value.get("RateUnit").equals("Month")) {
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
	
	public static void generateRandomServicePlanForCustomer() {
		// checking master set up
		ArrayList<Customer> customers = new ArrayList<Customer>(OneHashDataCache.getInstance().getCustomers());
		ArrayList<ServicePlan> masterServicePlans;
		if (OneHashDataCache.getInstance().getAvailableServicePlan() == null) {
			masterServicePlans = new ArrayList<ServicePlan>();
		} else {
			masterServicePlans = new ArrayList<ServicePlan>(OneHashDataCache.getInstance().getAvailableServicePlan());
		}
		if (masterServicePlans.isEmpty()) {
			ServicePlan servicePlan;
			servicePlan = new DigitalVoicePlan();
			servicePlan.setPlanName(ConstantSummary.DigitalVoice);
			servicePlan.setPlanCode(ServiceRate.PREFIX_DIGITAL_VOICE);
			masterServicePlans.add(servicePlan);
			
			servicePlan = new MobileVoicePlan();
			servicePlan.setPlanName(ConstantSummary.MobileVoice);
			servicePlan.setPlanCode(ServiceRate.PREFIX_MOBILE_VOICE);
			masterServicePlans.add(servicePlan);
			
			servicePlan = new CableTvPlan();
			servicePlan.setPlanName(ConstantSummary.CableTV);
			servicePlan.setPlanCode(ServiceRate.PREFIX_CABLE_TV);
			masterServicePlans.add(servicePlan);
			
			OneHashDataCache.getInstance().setAvailableServicePlan(masterServicePlans);
		}

		// Inserting random customer database & transactions 
		ArrayList<ServiceRate> masterServiceRates = new ArrayList<ServiceRate>(OneHashDataCache.getInstance().getAvailableServiceRate());
		ArrayList<ServicePlan> servicePlans = new ArrayList<ServicePlan>();
		for(Customer customer:customers) {
			servicePlans = new ArrayList<ServicePlan>();
			int noOfPlan = (int)(Math.random()*10)%3 + 1; // minimum 1 subscription
			int mobile_flag = 0, digital_flag = 0, tv_flag = 0;
			for (int i_plan=0; i_plan<noOfPlan; i_plan++) {
				ServicePlan servicePlan = new CableTvPlan();
				if ((int)(Math.random()*10)%3==0 && digital_flag == 0) {
					digital_flag = 1;
					servicePlan = new DigitalVoicePlan();
					servicePlan.setPlanName(ConstantSummary.DigitalVoice);
					servicePlan.setPlanCode(ServiceRate.PREFIX_DIGITAL_VOICE);
					servicePlan.setPlanId(ServiceRate.PREFIX_DIGITAL_VOICE+(servicePlans.size()+1));
				}
				else if ((int)(Math.random()*10)%3==1 && mobile_flag == 0) {
					mobile_flag = 1;
					servicePlan = new MobileVoicePlan();
					servicePlan.setPlanName(ConstantSummary.MobileVoice);
					servicePlan.setPlanCode(ServiceRate.PREFIX_MOBILE_VOICE);
					servicePlan.setPlanId(ServiceRate.PREFIX_MOBILE_VOICE+(servicePlans.size()+1));
				}
				else if (tv_flag == 0){
					tv_flag = 1;
					servicePlan = new CableTvPlan();
					servicePlan.setPlanName(ConstantSummary.CableTV);
					servicePlan.setPlanCode(ServiceRate.PREFIX_CABLE_TV);
					servicePlan.setPlanId(ServiceRate.PREFIX_CABLE_TV+(servicePlans.size()+1));
				}
				else {
					continue;
				}
				ArrayList<ServiceRate> serviceRates = new ArrayList<ServiceRate>();
				
				// random delete
				if (servicePlan.getServiceRates() != null) {
					if (servicePlan.getServiceRates().size() > 0) {
						// random delete if available
						serviceRates = new ArrayList<ServiceRate>(servicePlan.getServiceRates());
						int maxi_delete = (int)(Math.random()*100) % serviceRates.size();
						for (int i=0; i<maxi_delete; i++)
							serviceRates.remove((int)(Math.random()*100) % serviceRates.size());
					}
					else {
						serviceRates = new ArrayList<ServiceRate>();
						servicePlan.setServiceRates(serviceRates);
					}
				}
				
				if (serviceRates.size() == 0) {
					for (ServiceRate serviceRate:masterServiceRates) {
						if (serviceRate.getRateCode().startsWith(servicePlan.getPlanCode())) {
							// subscription must be included or RANDOM
							if ((int)(Math.random()*100 % 2) == 1 || serviceRate.getRateDescription().indexOf("Local")>-1 || serviceRate.getRateCode().startsWith(servicePlan.getPlanCode()+"S")) {
								serviceRates.add(serviceRate);
							}
						}
					}
				}
				servicePlan.setServiceRates(serviceRates);
				Calendar calendar = Calendar.getInstance();
				int year = 2007+(int)(Math.random()*100)%4;
				int month = 0+(int)(Math.random()*100)%12;
				int curMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
				int curYear = Calendar.getInstance().get(Calendar.YEAR);
				calendar.set(year, month, 1);
				servicePlan.setStartDate(calendar.getTime()); // setting the servicePlan starting date
				ArrayList<MonthlyUsage> monthlyUsages = new ArrayList<MonthlyUsage>();
				for (int year1=year; year1<=curYear; year1++) { // generate data based on the servicePlan starting date
					for (int month1=1; month1<=12; month1++) {
						if (year1 == curYear && month1 > curMonth) break; // transactions is only for past dates
						
						if(!(servicePlan instanceof CableTvPlan)){
							MonthlyUsage monthlyUsage = new MonthlyUsage();
							monthlyUsage.setUsageYearMonth(Integer.toString(month1) + Integer.toString(year1));
						
							for (ServiceRate serviceRate:serviceRates) { // fill in the usage to all serviceRate
								if (serviceRate instanceof SubscriptionRate || serviceRate.getRateCode().indexOf("TV-") >= 0) continue; // must be non TV rate, and non subscription
	
								String usageCode = "";
								usageCode = serviceRate.getRateCode().replaceAll("-", ""); // MVL, DVL ,... 3 letters code
								ArrayList<TalkTimeUsage> talkTimeUsages = new ArrayList<TalkTimeUsage>();
								TalkTimeUsage talkTimeUsage = new TalkTimeUsage();
								talkTimeUsage.setUsageType(usageCode);
								
								talkTimeUsage.setCallNumber(Integer.toString(80000000 + (int)(Math.random()*10000000))); // 8 digit number
								talkTimeUsage.setUsageDuration((long)(Math.random()*1200)); // 20 minutes duration, random
								
								Calendar randomTalkTime = Calendar.getInstance();
								randomTalkTime.set(year1, month1, 1+(int)(Math.random()*27), ((int)(Math.random()*23))%24, 1+(int)(Math.random()*59), 1+(int)(Math.random()*59)); // random date & time
								
								talkTimeUsage.setCallTime(randomTalkTime.getTime());
								talkTimeUsages.add(talkTimeUsage);
								monthlyUsage.setTalkTimeUsages(talkTimeUsages);
								monthlyUsages.add(monthlyUsage);
							}
						}
					}
				}
				servicePlan.setMonthlyUsages(monthlyUsages);
				servicePlans.add(servicePlan);
			}
			customer.setServicePlans(servicePlans);
		}
	}
}
