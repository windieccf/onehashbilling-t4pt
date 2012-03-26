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
 * 11 March 2012	Robin Foe		0.2				Add in file read and write													
 * 16 March 2012	Robin Foe		0.3				Add in Key Scalar and Customer related operation												
 * 17 March 2012	Aman Sharma		0.4				Add bill related methods.													
 * 													
 * 
 */
package com.onehash.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onehash.constant.ConstantFilePath;
import com.onehash.constant.ConstantSummary;
import com.onehash.constant.ConstantUsageType;
import com.onehash.exception.BusinessLogicException;
import com.onehash.model.bill.Bill;
import com.onehash.model.bill.BillSummary;
import com.onehash.model.bill.PaymentDetail;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.KeyScalar;
import com.onehash.model.service.plan.CableTvPlan;
import com.onehash.model.service.plan.DigitalVoicePlan;
import com.onehash.model.service.plan.MobileVoicePlan;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.model.service.rate.UsageRate;
import com.onehash.model.usage.MonthlyUsage;
import com.onehash.utility.OneHashDateUtil;
import com.onehash.utility.OneHashStringUtil;

/**
 * Data cache for the application
 * 
 */
public class OneHashDataCache {

	/************************* SINGLETON DESIGN PATTERN ***************************/
	private static OneHashDataCache instance;
	private OneHashDataCache(){/** to ensure singleton **/}
	public static OneHashDataCache getInstance(){
		if(instance == null){
			instance = new OneHashDataCache();
			instance.init();
		}
		return instance;
	}
	
	/************************************ DATA CACHE ************************************************/
	private KeyScalar keyScalar = new KeyScalar();
	private List<Customer> customers = new ArrayList<Customer>();
	public List<Customer> getCustomers() {return customers;}
	public void setCustomers(List<Customer> customers) {this.customers = customers;}
	
	private List<ServicePlan> availableServicePlan;
	public List<ServicePlan> getAvailableServicePlan() {return availableServicePlan;}
	public void setAvailableServicePlan(List<ServicePlan> availableServicePlan) {this.availableServicePlan = availableServicePlan;}
	
	private List<ServiceRate> availableServiceRate;
	public List<ServiceRate> getAvailableServiceRate() {return availableServiceRate;}
	public void setAvailableServiceRate(List<ServiceRate> availableServiceRate) {this.availableServiceRate = availableServiceRate;}

	/************************************ FILE PROCESSING ************************************************/
	
	/**
     * Initialise the class file
     * @return void 
     */
	private void init(){
		this.restoreCache();
	}
	
	/**
     * Restoring file from cache
     * @return void 
     */
	@SuppressWarnings("unchecked")
	private void restoreCache(){
		ServiceRate.loadServiceRate();
		try{
			FileInputStream fin = new FileInputStream(ConstantFilePath.ONE_HASH_DATA);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.setCustomers((List<Customer>) ois.readObject());
			ois.close();
			
			fin = new FileInputStream(ConstantFilePath.ONE_HASH_KEY_DATA);
			ois = new ObjectInputStream(fin);
			this.keyScalar= (KeyScalar) ois.readObject();
			ois.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
     * Flush cache to file in BYTE format
     * @return void
     */
	public void flushCache(){
		try{
			FileOutputStream fout = new FileOutputStream(ConstantFilePath.ONE_HASH_DATA);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this.getCustomers());
			oos.close();
			
			fout = new FileOutputStream(ConstantFilePath.ONE_HASH_KEY_DATA);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this.keyScalar);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/************************************ UTILITY ************************************************/

	/****************************************** CUSTOMER RELATED OPERATION **************************************************/
	public Customer getCustomerByAccountNumber(String accountNumber) {
		for(Customer customer : this.getCustomers()){
			if(customer.getAccountNumber().equalsIgnoreCase(accountNumber)){
				// will create a replicate of the customer
				return (Customer) customer.clone();
			}
				
		}
		return null;
	}
	
	public Customer getCustomerByNric(String nric) {
		for(Customer customer : this.getCustomers()){
			if(customer.getNric().equalsIgnoreCase(nric)){
				// will create a replicate of the customer
				return (Customer) customer.clone();
			}
		}
		return null;
	}
	
	public void saveCustomer(Customer customer) throws Exception{
		if(OneHashStringUtil.isEmpty(customer.getAccountNumber())){
			// means you are new customer, we just assign the account number and append new record into the list
			// validate NRIC
			if(this.isNricExist(customer.getNric()))
				throw new BusinessLogicException("NRIC exist");
			
			String accountNumber = OneHashStringUtil.maskAccountNumber(this.keyScalar.getNextAccountNumber());
			customer.setAccountNumber(accountNumber);
			this.getCustomers().add(customer);
		}else{
			
			// UPDATE 
			for(Customer cachedCustomer : this.getCustomers()){
				if(customer.getAccountNumber().equalsIgnoreCase(cachedCustomer.getAccountNumber())){
					cachedCustomer.setName(customer.getName());
					cachedCustomer.setNric(customer.getNric());
					cachedCustomer.setPhoneNumber(customer.getPhoneNumber());
					cachedCustomer.setAddress(customer.getAddress());
					cachedCustomer.setStatus(customer.isActivated());
					cachedCustomer.setServicePlans(customer.getServicePlans());
				}
			}
		}
	}
	
	private boolean isNricExist(String nric){
		for(Customer customer : this.getCustomers()){
			if(customer.getNric().equalsIgnoreCase(nric))
				return true;
		}
		
		return false;
	}
	
	
	/****************************************** BILL RELATED OPERATION - START **************************************************/
	
	/**
     * Calculate monthly bill
     * @param Customer, Date
     * @return Bill
     */
	public Bill calculateBill(Customer customer, Date yearMonth) {
		Bill bill = new Bill();
		Map<String,List<BillSummary>> billSummaryMap = new HashMap<String,List<BillSummary>>();
		BigDecimal currentBill = new BigDecimal(0);
		try{
			
			//Get Service Plan for the customer
			if(customer.getServicePlans()!=null && customer.getServicePlans().size()>0)
			for(ServicePlan _servicePlan : customer.getServicePlans()){
				
				if(_servicePlan.getEndDate().before(yearMonth)){
					SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
					SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
					
					Integer yearSP = new Integer(sdfYear.format(_servicePlan.getEndDate()));
					Integer monthSP = new Integer(sdfMonth.format(_servicePlan.getEndDate()));
					
					Integer yearRD = new Integer(sdfYear.format(yearMonth));
					Integer mothRD = new Integer(sdfMonth.format(yearMonth));

					if(yearSP<yearRD)
						continue;
					else if(monthSP<mothRD)
						continue;
				}
					
				
				//Check plan type is cable TV
				if(_servicePlan instanceof CableTvPlan){
					
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
				}
				
				//Check plan type is Digital Voice
				if(_servicePlan instanceof DigitalVoicePlan){
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
					usageRateLocal = usageRateLocal.multiply(BigDecimal.valueOf(dvLocal));
					usageRateIDD = usageRateIDD.multiply(BigDecimal.valueOf(dvIDD));
					totalUsage = usageRateLocal.add(usageRateIDD);
					
					BillSummary billSummaryDVLUsage = new BillSummary(ConstantSummary.UsagechargesLocal,usageRateLocal);
					BillSummary billSummaryDVIUsage = new BillSummary(ConstantSummary.UsagechargesIDD,usageRateIDD);
					BillSummary billSummaryDVUsage = new BillSummary(ConstantSummary.Usagecharges,totalUsage);
					
					billSummaryList.add(billSummaryDVLUsage);
					billSummaryList.add(billSummaryDVIUsage);
					billSummaryList.add(billSummaryDVUsage);
					
					billSummaryMap.put(_servicePlan.getPlanId(),billSummaryList);
					currentBill = currentBill.add(totalUsage);
				}

				//Check plan type is Mobile Voice
				if(_servicePlan instanceof MobileVoicePlan){
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
					usageRateLocal = usageRateLocal.multiply(BigDecimal.valueOf(mvLocal));
					usageRateIDD = 	usageRateIDD.multiply(BigDecimal.valueOf(mvIDD));
					usageRateRoaming = usageRateRoaming.multiply(BigDecimal.valueOf(mvRoaming));
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
				}
			}
			
			if(billSummaryMap!=null && billSummaryMap.size()>0){
				bill.setBillSummaryMap(billSummaryMap);
				bill.setCurrentBill(currentBill);
				BigDecimal carryForwardAmount = checkCarryForward(customer, yearMonth);
				bill.setCarryForward(carryForwardAmount);
				bill.setTotalBill(currentBill.add(carryForwardAmount));
				
				Calendar calBillDate = Calendar.getInstance();
				calBillDate.set(Calendar.DATE, 28);
				bill.setBillDate(calBillDate.getTime());
			}else
				return null;
			
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
		return bill;
	}
	
	private BigDecimal checkCarryForward(Customer customer, Date yearMonth) {
		Bill bill = new Bill();
		BigDecimal carryForwardAmount = new BigDecimal(0);
		Calendar previousMonthBill = Calendar.getInstance();
		previousMonthBill.set(Calendar.MONTH, -1);
		
		try{
			for(Customer cachedCustomer : this.getCustomers()){
				if(customer.getAccountNumber().equalsIgnoreCase(cachedCustomer.getAccountNumber())){
					bill = getBillForMonth(cachedCustomer,previousMonthBill.getTime());
					break;
				}
			}
			
			if(bill!=null){
				BigDecimal totalBill = bill.getTotalBill();
				BigDecimal paymentMade = new BigDecimal(0);
				
				for(PaymentDetail _paymentDetail : bill.getPaymentDetails()){
					paymentMade.add(_paymentDetail.getAmount());
				}
				carryForwardAmount = totalBill.subtract(paymentMade);
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return carryForwardAmount;
	}
	
	/**
     * Method to get the requested monthly bill for a customer
     * @param Customer, Date
     * @return Bill
     */
	private Bill getBillForMonth(Customer customer, Date yearMonth) {
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
	
	/****************************************** BILL RELATED OPERATION - END **************************************************/
	
	/****************************************** COMPLAINT RELATED OPERATION **************************************************/
	
	/**
	 * Follow up field will be mandatory only when status is set to (F)Followup
	 * Closed date will be the date issue set to closed status
	 * @param complaintLog
	 * @param status
	 * @param followup
	 * @throws BusinessLogicException
	 */
	public void updateComplaintLog(ComplaintLog complaintLog, String status, String followup) throws BusinessLogicException {
		if (status.equals("F") && followup.isEmpty()) throw new BusinessLogicException("Followup field is mandatory when status is 'Follow up'");
		if (status.equals("C")) complaintLog.setClosedDate(OneHashDateUtil.getDate());
		if (!followup.isEmpty()) complaintLog.setFollowUp(followup);
		complaintLog.setStatus(status);
	}
	
	/**
	 * Default complaint date will be sysdate
	 * Default issue status will be A(active)
	 * @param customer
	 * @param issueDescription
	 * @param allIssueNoList
	 * @throws Exception
	 */
	public void createComplaint(Customer customer, String issueDescription, ArrayList<String> allIssueNoList) throws BusinessLogicException{
		if (issueDescription.isEmpty()) throw new BusinessLogicException("Description field is mandatory.");
		ComplaintLog complaintLog = new ComplaintLog(issueDescription,allIssueNoList);
		System.out.println("com" + complaintLog.getIssueNo());
		customer.addComplaintLog(complaintLog);
	}
	
	/**
	 * Retrieve complaintLog object by issue number
	 * @param issueNo
	 * @return
	 * @throws Exception
	 */
	public ComplaintLog getComplaintLogByIssueNo(String issueNo, ArrayList<ComplaintLog> complaintLogs) throws Exception{
		for(ComplaintLog complaintLog : complaintLogs){
			if(complaintLog.getIssueNo().equalsIgnoreCase(issueNo)){
				// will create a replicate of the complaintLog
				return (ComplaintLog) complaintLog.clone();
			}
				
		}
		return null;
	}
}
