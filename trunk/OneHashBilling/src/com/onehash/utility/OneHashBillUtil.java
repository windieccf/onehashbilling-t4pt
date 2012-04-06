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
 * 1 April 2012     Aman Sharma	    0.1				Initial Creation											
 * 													
 * 
 */

package com.onehash.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.onehash.constant.ConstantSummary;
import com.onehash.constant.ConstantUsageType;
import com.onehash.model.bill.Bill;
import com.onehash.model.bill.BillSummary;
import com.onehash.model.customer.Customer;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.model.service.rate.UsageRate;
import com.onehash.model.usage.MonthlyUsage;


public class OneHashBillUtil {
	
	public static BigDecimal calculateTVBill(ServicePlan _servicePlan,Map<String,List<BillSummary>> billSummaryMap){
		BigDecimal currentBill = new BigDecimal(0);
		try{
			BigDecimal cableTVSubscriptionRate = new BigDecimal(0);
			BigDecimal cableTVAddChannelCharge = new BigDecimal(0);
			List<BillSummary> billSummaryList = new ArrayList <BillSummary>();

			for(ServiceRate _serviceRate : _servicePlan.getServiceRates()){
				if (_serviceRate.isFreeCharge()) 
					continue;
				
				if(_serviceRate instanceof SubscriptionRate){
					if(_serviceRate.getRateCode().equalsIgnoreCase("TV-S"))
						cableTVSubscriptionRate = _serviceRate.getRatePrice();
					if(_serviceRate.getRateCode().equalsIgnoreCase("TV-C")){
						cableTVAddChannelCharge  = cableTVAddChannelCharge.add(_serviceRate.getRatePrice());
					}
				}
			}
			
			BillSummary billSummaryCableTV = new BillSummary(ConstantSummary.Subscriptioncharges,cableTVSubscriptionRate);
			BillSummary addChannelChargeCableTV = new BillSummary(ConstantSummary.AddChannelcharges,cableTVAddChannelCharge);
			billSummaryList.add(billSummaryCableTV);
			billSummaryList.add(addChannelChargeCableTV);
			
			billSummaryMap.put(_servicePlan.getPlanId(),billSummaryList);
			currentBill = currentBill.add(cableTVSubscriptionRate.add(cableTVAddChannelCharge));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return currentBill;
	}
	
	public static BigDecimal calculateDVBill(ServicePlan _servicePlan,Map<String,List<BillSummary>> billSummaryMap,Date yearMonth){
		BigDecimal currentBill = new BigDecimal(0);
		try{
			//Subscription
			BigDecimal dvSubscriptionRate = new BigDecimal(0);
			BigDecimal dvCallTransferRate = new BigDecimal(0);
			
			//Usage
			BigDecimal usageRateLocal = new BigDecimal(0);
			BigDecimal usageRateIDD = new BigDecimal(0);
			BigDecimal totalUsage = new BigDecimal(0);
			
			//Get service rates
			List<BillSummary> billSummaryList = new ArrayList <BillSummary>();
			for(ServiceRate _serviceRate : _servicePlan.getServiceRates()){
				//Get service rates - Subscription
				if(_serviceRate instanceof SubscriptionRate){
					if(_serviceRate.getRateCode().equalsIgnoreCase("DV-S"))
						dvSubscriptionRate = _serviceRate.getRatePrice();
					if(_serviceRate.getRateCode().equalsIgnoreCase("DV-C"))
						dvCallTransferRate = _serviceRate.getRatePrice();
				}
				//Get service rates - Usage
				if(_serviceRate instanceof UsageRate){
					if(_serviceRate.getRateCode().equalsIgnoreCase("DV-L"))
						usageRateLocal = _serviceRate.getRatePrice();
					if(_serviceRate.getRateCode().equalsIgnoreCase("DV-I"))
						usageRateIDD = _serviceRate.getRatePrice();
				}
			}
			
			//Set Summary Rates
			currentBill = currentBill.add(dvSubscriptionRate).add(dvCallTransferRate);
			BillSummary billSummaryDVSummary = new BillSummary(ConstantSummary.Subscriptioncharges,dvSubscriptionRate);
			BillSummary billSummaryDVCallTransfer = new BillSummary(ConstantSummary.CallTransfer,dvCallTransferRate);
			billSummaryList.add(billSummaryDVSummary);
			billSummaryList.add(billSummaryDVCallTransfer);
			
			//Get usage for Digital Voice Plan -Local and IDD
			Long dvLocal = new Long(0);
			Long dvIDD = new Long(0);
			for(MonthlyUsage _monthlyUsage : _servicePlan.getMonthlyUsages()){
				if(OneHashDateUtil.checkMonthYear(_monthlyUsage.getUsageYearMonth(),yearMonth)){
					dvLocal = _monthlyUsage.getCallUsages(ConstantUsageType.DVL);
					dvIDD = _monthlyUsage.getCallUsages(ConstantUsageType.DVI);
				}
			}
			
			//Setting usages to BillSummary
			BigDecimal valueDVLocal = new BigDecimal(dvLocal.toString());
			usageRateLocal = usageRateLocal.multiply(valueDVLocal);
			BigDecimal valueDVIDD = new BigDecimal(dvIDD.toString());
			usageRateIDD = usageRateIDD.multiply(valueDVIDD);
			totalUsage = usageRateLocal.add(usageRateIDD);
			
			BillSummary billSummaryDVLUsage = new BillSummary(ConstantSummary.UsagechargesLocal,usageRateLocal);
			BillSummary billSummaryDVIUsage = new BillSummary(ConstantSummary.UsagechargesIDD,usageRateIDD);
			BillSummary billSummaryDVUsage = new BillSummary(ConstantSummary.Usagecharges,totalUsage);
			
			billSummaryList.add(billSummaryDVLUsage);
			billSummaryList.add(billSummaryDVIUsage);
			billSummaryList.add(billSummaryDVUsage);
			
			billSummaryMap.put(_servicePlan.getPlanId(),billSummaryList);
			currentBill = currentBill.add(totalUsage);
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return currentBill;
	}
	
	public static BigDecimal calculateMVBill(ServicePlan _servicePlan,Map<String,List<BillSummary>> billSummaryMap,Date yearMonth){
		BigDecimal currentBill = new BigDecimal(0);
		try{

			//Subscription
			BigDecimal mvSubscriptionRate = new BigDecimal(0);
			BigDecimal mvDataServiceRate = new BigDecimal(0);
			
			//Usage
			BigDecimal usageRateLocal = new BigDecimal(0);
			BigDecimal usageRateIDD = new BigDecimal(0);
			BigDecimal usageRateRoaming = new BigDecimal(0);
			BigDecimal totalUsage = new BigDecimal(0);
			
			//Get service rates
			List<BillSummary> billSummaryList = new ArrayList <BillSummary>();
			for(ServiceRate _serviceRate : _servicePlan.getServiceRates()){
				//Get service rates - Subscription
				if(_serviceRate instanceof SubscriptionRate){
					if(_serviceRate.getRateCode().equalsIgnoreCase("MV-S"))
						mvSubscriptionRate = _serviceRate.getRatePrice();
					if(_serviceRate.getRateCode().equalsIgnoreCase("MV-D"))
						mvDataServiceRate = _serviceRate.getRatePrice();
				}
				//Get service rates - Usage
				if(_serviceRate instanceof UsageRate){
					if(_serviceRate.getRateCode().equalsIgnoreCase("MV-L"))
						usageRateLocal = _serviceRate.getRatePrice();
					if(_serviceRate.getRateCode().equalsIgnoreCase("MV-I"))
						usageRateIDD = _serviceRate.getRatePrice();
					if(_serviceRate.getRateCode().equalsIgnoreCase("MV-R"))
						usageRateRoaming = _serviceRate.getRatePrice();
				}
			}
			
			//Set Summary Rates
			currentBill = currentBill.add(mvSubscriptionRate).add(mvDataServiceRate);
			BillSummary billSummaryDVSummary = new BillSummary(ConstantSummary.Subscriptioncharges,mvSubscriptionRate);
			BillSummary billSummaryDVDataService = new BillSummary(ConstantSummary.DataServices,mvDataServiceRate);
			billSummaryList.add(billSummaryDVSummary);
			billSummaryList.add(billSummaryDVDataService);
			
			//Get usage for Digital Voice Plan
			Long mvLocal = new Long(0);
			Long mvIDD = new Long(0);
			Long mvRoaming = new Long(0);
			for(MonthlyUsage _monthlyUsage : _servicePlan.getMonthlyUsages()){
				if(OneHashDateUtil.checkMonthYear(_monthlyUsage.getUsageYearMonth(),yearMonth)){
					mvLocal = _monthlyUsage.getCallUsages(ConstantUsageType.MVL);
					mvIDD = _monthlyUsage.getCallUsages(ConstantUsageType.MVI);
					mvRoaming = _monthlyUsage.getCallUsages(ConstantUsageType.MVR);
				}
			}
			
			//Setting usages to BillSummary
			BigDecimal valuemvLocal = new BigDecimal(mvLocal.toString());
			usageRateLocal = usageRateLocal.multiply(valuemvLocal);
			
			BigDecimal valuemvIDD = new BigDecimal(mvIDD.toString());
			usageRateIDD = 	usageRateIDD.multiply(valuemvIDD);
			
			BigDecimal valuemvRoaming = new BigDecimal(mvRoaming.toString());
			usageRateRoaming = usageRateRoaming.multiply(valuemvRoaming);
			totalUsage = usageRateLocal.add(usageRateIDD).add(usageRateRoaming);
			
			BillSummary billSummaryMVLUsage = new BillSummary(ConstantSummary.UsagechargesLocal,usageRateLocal);
			BillSummary billSummaryMVIUsage = new BillSummary(ConstantSummary.UsagechargesIDD,usageRateIDD);
			BillSummary billSummaryMVRUsage = new BillSummary(ConstantSummary.UsagechargesRoamin,usageRateRoaming);
			BillSummary billSummaryMVUsage = new BillSummary(ConstantSummary.Usagecharges,totalUsage);
			
			billSummaryList.add(billSummaryMVLUsage);
			billSummaryList.add(billSummaryMVIUsage);
			billSummaryList.add(billSummaryMVRUsage);
			billSummaryList.add(billSummaryMVUsage);
			
			billSummaryMap.put(_servicePlan.getPlanId(),billSummaryList);
			currentBill = currentBill.add(totalUsage);
		
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return currentBill;
	}
	
	/**
     * Method to get the requested monthly bill for a customer
     * @param Customer, Date
     * @return Bill
     */
	public static Bill getBillForMonth(Customer customer, Date yearMonth) {
		try{
			for(Bill _bill : customer.getBill()){
				if(OneHashDateUtil.isMonthYearOfBill(_bill.getBillDate(),yearMonth))
					return _bill;
			}
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
		return null;
	}
}
