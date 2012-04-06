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
 * 16 March 2012	Robin Foe		0.4				Add in User Related Information										
 * 17 March 2012	Aman Sharma		0.5				Add bill related methods.													
 * 25 March 2012	Yue Yang		0.6				Add in Restore 
 * 06 April 2012	Kenny Hartono	0.7				Add in Load Service Plan, Service Rate, Monthly Usage, TalkTimeUsage from CSV
 * 
 */
package com.onehash.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import java.util.StringTokenizer;

import com.onehash.constant.ConstantFilePath;
import com.onehash.constant.ConstantPrefix;
import com.onehash.constant.ConstantSummary;
import com.onehash.enumeration.EnumUserAccess;
import com.onehash.exception.BusinessLogicException;
import com.onehash.exception.DataDuplicationException;
import com.onehash.exception.InsufficientInputParameterException;
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
import com.onehash.model.usage.TalkTimeUsage;
import com.onehash.model.user.User;
import com.onehash.utility.OneHashBeanUtil;
import com.onehash.utility.OneHashBillUtil;
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
			instance.restoreFromFile();
		}
		return instance;
	}
	
	/************************************ SESSION CACHE FOR CURRENT LOGGED IN USER************************************************/
	private User currentUser;
	public User getCurrentUser() {return currentUser;}
	public void setCurrentUser(User currentUser) {this.currentUser = currentUser;}

	/************************************ DATA CACHE ************************************************/
	private KeyScalar keyScalar = new KeyScalar();

	private List<User> users = new ArrayList<User>();
	public List<User> getUsers() {return users;}
	public void setUsers(List<User> users) {this.users = users;}

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
		try{
			FileInputStream fin = new FileInputStream(ConstantFilePath.ONE_HASH_DATA_SECURITY);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.setUsers((List<User>) ois.readObject());
			ois.close();
		}catch(Exception e){/*IGNORED*/}
		
		try{
			FileInputStream fin = new FileInputStream(ConstantFilePath.ONE_HASH_DATA);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.setCustomers((List<Customer>) ois.readObject());
			ois.close();
			
			fin = new FileInputStream(ConstantFilePath.ONE_HASH_KEY_DATA);
			ois = new ObjectInputStream(fin);
			this.keyScalar= (KeyScalar) ois.readObject();
			ois.close();
			
			fin = new FileInputStream(ConstantFilePath.ONE_HASH_DATA_PLAN);
			ois = new ObjectInputStream(fin);
			this.availableServicePlan= (List<ServicePlan>) ois.readObject();
			ois.close();
			
			fin = new FileInputStream(ConstantFilePath.ONE_HASH_DATA_RATE);
			ois = new ObjectInputStream(fin);
			this.availableServiceRate= (List<ServiceRate>) ois.readObject();
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
			
			fout = new FileOutputStream(ConstantFilePath.ONE_HASH_DATA_SECURITY);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this.users);
			oos.close();
			
			fout = new FileOutputStream(ConstantFilePath.ONE_HASH_DATA_PLAN);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this.availableServicePlan);
			oos.close();
			
			fout = new FileOutputStream(ConstantFilePath.ONE_HASH_DATA_RATE);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this.availableServiceRate);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/************************************ UTILITY ************************************************/
	
	/****************************************** USER RELATED OPERATION **************************************************/
	
	public User getUserByUserName(String userName) {
		return retrieveUserByUserName(userName, false);
	}
	
	private User getCachedUserByUserName(String userName){
		return retrieveUserByUserName(userName, true);
	}
	
	// retrieve user by username
	private User retrieveUserByUserName(String userName, boolean isReference){
		for(User user : this.getUsers()){
			if(user.getUserName().equalsIgnoreCase(userName))
				return (isReference) ? user : (User) user.clone();;
		}
		return null;
	}
	
	public void saveUser(User user) throws Exception{
		if(user.getUserId() == 0){
			// means you are new customer, we just assign the account number and append new record into the list
			// validate username
			if(this.isUsernameExist(user.getUserName()))
				throw new DataDuplicationException("Username is taken");
			
			user.setUserId(this.keyScalar.getNextUserID());
			this.getUsers().add(user);
		}else{
			//update mode
			User cachedUser = getCachedUserByUserName(user.getUserName());
			OneHashBeanUtil.copyProperties(cachedUser, user, "userId","userName", "userAccesses" );
			cachedUser.setUserAccesses(user.getUserAccesses());
			
		}
	}
	
	private boolean isUsernameExist(String userName){
		return (this.getUserByUserName(userName) != null);
	}
	
	public void authenticate(User user) throws Exception{	
		if(user == null)
			throw new InsufficientInputParameterException("User is required");
		
		User cachedUser = this.retrieveUserByUserName(user.getUserName(), false);
		if(cachedUser == null)
			throw new BusinessLogicException("Incorrect Credentials");
		
		if(!cachedUser.getPassword().equals(user.getPassword()))
			throw new BusinessLogicException("Incorrect Credentials");

		// storing current user to the cache
		this.currentUser = cachedUser;
	}
	
	public void logout(){
		this.flushCache();
		this.currentUser = null;
	}

	/****************************************** CUSTOMER RELATED OPERATION **************************************************/
	public Customer getCustomerByAccountNumber(String accountNumber) {
		return retrieveCustomerByAccountNumber(accountNumber, false);
	}
	
	private Customer getCachedCustomerByAccountNumber(String accountNumber){
		return retrieveCustomerByAccountNumber(accountNumber, true);
	}
	
	private Customer retrieveCustomerByAccountNumber(String accountNumber, boolean isReference){
		for(Customer customer : this.getCustomers()){
			if(customer.getAccountNumber().equalsIgnoreCase(accountNumber)){
				return (isReference) ? customer : (Customer) customer.clone();
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
				throw new DataDuplicationException("NRIC exist");
			
			String accountNumber = OneHashStringUtil.generateReferenceNumberNumber(ConstantPrefix.ACCOUNT_NUMBER, this.keyScalar.getNextAccountNumber());
			customer.setAccountNumber(accountNumber);
			this.getCustomers().add(customer);
		}else{
			// UPDATE 
			Customer cachedCustomer = getCachedCustomerByAccountNumber(customer.getAccountNumber());
			OneHashBeanUtil.copyProperties(cachedCustomer, customer, "accountNumber","servicePlans", "complaintLogs" , "bill");
			cachedCustomer.setServicePlans(customer.getServicePlans());
			cachedCustomer.setBill(customer.getBill());
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
					currentBill = currentBill.add(OneHashBillUtil.calculateTVBill(_servicePlan, billSummaryMap));
				}
				
				//Check plan type is Digital Voice
				if(_servicePlan instanceof DigitalVoicePlan){
					currentBill = currentBill.add(OneHashBillUtil.calculateDVBill(_servicePlan, billSummaryMap, yearMonth));
				}

				//Check plan type is Mobile Voice
				if(_servicePlan instanceof MobileVoicePlan){
					currentBill = currentBill.add(OneHashBillUtil.calculateMVBill(_servicePlan, billSummaryMap, yearMonth));
				}
			}
			
			if(billSummaryMap!=null && billSummaryMap.size()>0){
				bill.setBillSummaryMap(billSummaryMap);
				bill.setCurrentBill(currentBill);
				
				BigDecimal carryForwardAmount = checkCarryForward(customer, yearMonth);
				bill.setCarryForward(carryForwardAmount);
				
				Calendar calBillDate = Calendar.getInstance();
				calBillDate.setTime(yearMonth);
				calBillDate.set(Calendar.DATE, 28);
				bill.setBillDate(calBillDate.getTime());
				
				bill.setGstRate(new BigDecimal(ConstantFilePath.GST));
				currentBill = currentBill.add(carryForwardAmount);
				bill.setTotalBill(currentBill);
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
					bill = OneHashBillUtil.getBillForMonth(cachedCustomer,previousMonthBill.getTime());
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
	
	/****************************************** BILL RELATED OPERATION - END **************************************************/
	
	/****************************************** COMPLAINT RELATED OPERATION **************************************************/
	private ComplaintLog getCachedComplaintLog(Customer customer, String issueNumber) throws Exception {
		return retrieveComplaintLog(customer,issueNumber, true);
	}
	
	public ComplaintLog getComplaintLog(Customer customer, String issueNumber) throws Exception {
		return retrieveComplaintLog(customer,issueNumber, false);
	}
	
	private ComplaintLog retrieveComplaintLog(Customer customer, String issueNumber, boolean isReference) throws Exception {
		for(ComplaintLog complaintLog : customer.getComplaintLogs()){
			if(complaintLog.getIssueNo().equals(issueNumber))
				return (isReference) ? complaintLog : (ComplaintLog)complaintLog.clone();
		}
		return null;
	}
	
	public void saveComplaintLog(Customer customer, ComplaintLog complaintLog) throws Exception {
		if(OneHashStringUtil.isEmpty(complaintLog.getIssueNo())){
			// need to generate issue number
			String issueNumber = OneHashStringUtil.generateReferenceNumberNumber(ConstantPrefix.ISSUE_NUMBER, this.keyScalar.getNextComplaintNumber());
			complaintLog.setIssueNo(issueNumber);
			Customer cachedcustomer = this.getCachedCustomerByAccountNumber(customer.getAccountNumber());
			cachedcustomer.getComplaintLogs().add(complaintLog);
			
		}else{
			// look up the issue number and perform bean copier
			Customer cachedcustomer = this.getCachedCustomerByAccountNumber(customer.getAccountNumber());
			ComplaintLog cachedComplaintLog = this.getCachedComplaintLog(cachedcustomer, complaintLog.getIssueNo());
			
			// perform update
			OneHashBeanUtil.copyProperties(cachedComplaintLog, complaintLog);
		}
	}
	
	// for data restoration back to the cache (Restore team 4 factory setting)
	private void restoreFromFile(){
		try{
			restoreServiceRates();
			restoreCustomer();
			restoreCustomerPlan();
			restoreUserAccess();
			
			this.flushCache();
			// update the keys
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	private void restoreCustomer(){
		
		try{
			// restoring customer related information
			FileInputStream fstream = new FileInputStream(ConstantFilePath.ONE_HASH_RESTORE_CUSTOMER);
			java.io.DataInputStream in = new java.io.DataInputStream(fstream);
			java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(in));
			List<Customer> customers = new ArrayList<Customer>();
			String strLine;
			long lastKey = 1L;
			while ((strLine = br.readLine()) != null)   {
				String[] split = strLine.split("\\|");
				Customer customer = new Customer();
				customer.setName(split[0]);
				customer.setNric(split[1]);
				customer.setAddress(split[2]);
				customer.setPhoneNumber(split[3]);
				customer.setAccountNumber(split[4]);
				customers.add(customer);
				
				String[] keySplit = split[4].split("-");
				long currKey = Long.parseLong(keySplit[1] + keySplit[2] + keySplit[3]);
				lastKey = ( lastKey < currKey) ? currKey : lastKey;
			}
			in.close();
			this.setCustomers(customers);
			
			// adjusting key scalar
			Long key = 1L;
			do{
				key  = this.keyScalar.getNextAccountNumber();
			}while(key < lastKey);
			


			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void restoreUserAccess(){

		try{
			// basic user credentials
			User adminUser = new User();
			adminUser.setUserId(this.keyScalar.getNextUserID());
			adminUser.setUserName("admin");
			adminUser.setFirstName("PT 4 Admin");
			adminUser.setLastName("PT 4");
			adminUser.setPassword("password");
			adminUser.setUserRole("admin");
			// admin will have all rights
			for(EnumUserAccess availableAccess : EnumUserAccess.values()){
				adminUser.getUserAccesses().add(availableAccess);
			}
			this.users.add(adminUser);
			
			User agentUser = new User();
			agentUser.setUserId(this.keyScalar.getNextUserID());
			agentUser.setUserName("agent");
			agentUser.setFirstName("PT 4 Agent");
			agentUser.setLastName("PT 4");
			agentUser.setUserRole("agent");
			agentUser.setPassword("password");
			
			// agent only has specific rights
			agentUser.getUserAccesses().add(EnumUserAccess.CUSTOMER_VIEW);
			agentUser.getUserAccesses().add(EnumUserAccess.COMPLAINT_UPDATE);
			agentUser.getUserAccesses().add(EnumUserAccess.COMPLAINT_VIEW);
			agentUser.getUserAccesses().add(EnumUserAccess.SERVICE_PLAN_VIEW);
			agentUser.getUserAccesses().add(EnumUserAccess.BILL_VIEW);
			this.users.add(agentUser);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void restoreServiceRates(){

		try{
			
			int priority = 0;
			String rateFile = "";
			ArrayList<ServiceRate> services = new ArrayList<ServiceRate>();
			
			
			// load Rates.csv
			try {
				rateFile = ConstantFilePath.ONE_HASH_RESTORE_RATES;
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
				rateFile = ConstantFilePath.ONE_HASH_RESTORE_TV_CHANNELS;
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
				rateFile = ConstantFilePath.ONE_HASH_RESTORE_VOICE_FEATURES;
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
			this.setAvailableServiceRate(services);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}
	
	private void restoreCustomerPlan(){
		
		List<ServicePlan> masterServicePlans = this.getAvailableServicePlan();
		if (masterServicePlans == null) {
			masterServicePlans = new ArrayList<ServicePlan>();
			
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
			
			this.setAvailableServicePlan(masterServicePlans);
		}

		try {
			
			HashMap<String,HashMap<String,MonthlyUsage>> mapAccMonthlyDVUsages = restoreTransactionDetails("DV");
			HashMap<String,HashMap<String,MonthlyUsage>> mapAccMonthlyMVUsages = restoreTransactionDetails("DV");
			
			// loading the service plan for the customer
			String[] availablePath = {ConstantFilePath.ONE_HASH_RESTORE_DV_SUBSCRIPTIONS,
					ConstantFilePath.ONE_HASH_RESTORE_MV_SUBSCRIPTIONS,
					ConstantFilePath.ONE_HASH_RESTORE_TV_SUBSCRIPTIONS};
			String filePath;
			for (int i=0; i<3; i++) {
				filePath = availablePath[i];
				// LOAD SERVICE PLAN
				FileInputStream fstream2 = new FileInputStream(filePath);
				java.io.DataInputStream in2 = new java.io.DataInputStream(fstream2);
				java.io.BufferedReader br2 = new java.io.BufferedReader(new java.io.InputStreamReader(in2));
				List<ServicePlan> servicePlans = new ArrayList<ServicePlan>();
				br2.readLine(); // remove the header line
				String strLine;
				while ((strLine = br2.readLine()) != null)   {
					String[] split = strLine.split(",");
					if (split.length < 4) continue;
					Customer customer1 = this.getCachedCustomerByAccountNumber(split[0]);
					servicePlans = customer1.getServicePlans();
					if (servicePlans == null) {
						servicePlans = new ArrayList<ServicePlan>();
					}
					ServicePlan servicePlan = new MobileVoicePlan();
					List<ServiceRate> serviceRates = new ArrayList<ServiceRate>();
					ServiceRate serviceRate = new SubscriptionRate();
					
					if (filePath.equals(ConstantFilePath.ONE_HASH_RESTORE_DV_SUBSCRIPTIONS)) {
						for (ServicePlan servicePlan2:OneHashDataCache.getInstance().getAvailableServicePlan()) {
							if (servicePlan2 instanceof DigitalVoicePlan) {
								servicePlan = (DigitalVoicePlan)servicePlan2.clone();
								servicePlan.setPlanId(ServiceRate.PREFIX_DIGITAL_VOICE+(servicePlans.size()+1));
								((DigitalVoicePlan)servicePlan).setRegisteredPhoneNumber(split[4]);
								
								// add basic subscription rate
								for(ServiceRate serviceRate2:OneHashDataCache.getInstance().getAvailableServiceRate()) {
									if (serviceRate2.getRateCode().equals("DV-S")) {
										serviceRates.add((ServiceRate)serviceRate.clone());
										break;
									}
								}
								break;
							}
						}
						//Setting monthly usage
						HashMap<String,MonthlyUsage> monthlyUsages = mapAccMonthlyDVUsages.get(customer1.getAccountNumber());
						if(servicePlan.getMonthlyUsages()==null || servicePlan.getMonthlyUsages().size()==0){
							List<MonthlyUsage> monthlyUsageList = new ArrayList<MonthlyUsage>();
							servicePlan.setMonthlyUsages(monthlyUsageList);
						}
						for(MonthlyUsage _monthlyUsage : monthlyUsages.values()){
							servicePlan.getMonthlyUsages().add(_monthlyUsage);
						}
						
					}
					else if (filePath.equals(ConstantFilePath.ONE_HASH_RESTORE_MV_SUBSCRIPTIONS)) {
						for (ServicePlan servicePlan2:OneHashDataCache.getInstance().getAvailableServicePlan()){
							if (servicePlan2 instanceof MobileVoicePlan) {
								servicePlan = (MobileVoicePlan)servicePlan2.clone();
								servicePlan.setPlanId(ServiceRate.PREFIX_MOBILE_VOICE+(servicePlans.size()+1));
								((MobileVoicePlan)servicePlan).setRegisteredPhoneNumber(split[4]);
								
								// add basic subscription rate
								for(ServiceRate serviceRate2:OneHashDataCache.getInstance().getAvailableServiceRate()) {
									if (serviceRate2.getRateCode().equals("MV-S")) {
										serviceRates.add((ServiceRate)serviceRate.clone());
										break;
									}
								}
								break;
							}
						}
						//Setting monthly usage
						HashMap<String,MonthlyUsage> monthlyUsages = mapAccMonthlyMVUsages.get(customer1.getAccountNumber());
						if(servicePlan.getMonthlyUsages()==null || servicePlan.getMonthlyUsages().size()==0){
							List<MonthlyUsage> monthlyUsageList = new ArrayList<MonthlyUsage>();
							servicePlan.setMonthlyUsages(monthlyUsageList);
						}
						for(MonthlyUsage _monthlyUsage : monthlyUsages.values()){
							servicePlan.getMonthlyUsages().add(_monthlyUsage);
						}
					}
					else if (filePath.equals(ConstantFilePath.ONE_HASH_RESTORE_TV_SUBSCRIPTIONS)) {
						for (ServicePlan servicePlan2:OneHashDataCache.getInstance().getAvailableServicePlan())
							if (servicePlan2 instanceof CableTvPlan) {
								servicePlan = (CableTvPlan)servicePlan2.clone();
								servicePlan.setPlanId(ServiceRate.PREFIX_CABLE_TV+(servicePlans.size()+1));
								
								// add basic subscription rate
								for(ServiceRate serviceRate2:OneHashDataCache.getInstance().getAvailableServiceRate()) {
									if (serviceRate2.getRateCode().equals("TV-S")) {
										serviceRates.add((ServiceRate)serviceRate.clone());
										break;
									}
								}
								break;
							}
					}
	
					Calendar calendar;
					String[] splitDate;
	
					calendar = Calendar.getInstance();
					splitDate = split[2].split("/");
					calendar.set(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));
					servicePlan.setStartDate(calendar.getTime());
	
					calendar = Calendar.getInstance();
					splitDate = split[3].split("/");
					calendar.set(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));
					servicePlan.setEndDate(calendar.getTime());
					
					servicePlans.add(servicePlan);
					customer1.setServicePlans(servicePlans);
				}
				in2.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		// Try to load service rate
		ArrayList<ArrayList<String>> allServiceRates = new ArrayList<ArrayList<String>>();
		try {
			// loading the service rate for the customer
			String[] availablePath = {ConstantFilePath.ONE_HASH_RESTORE_DV_OPT_SUBSCRIPTION,
					ConstantFilePath.ONE_HASH_RESTORE_MV_OPT_SUBSCRIPTION,
					ConstantFilePath.ONE_HASH_RESTORE_TV_OPT_SUBSCRIPTION};
			String[] planCodes = {"DV", "MV", "TV"};
			String filePath;
			for (int i=0; i<3; i++) {
				filePath = availablePath[i];
				// LOAD SERVICE RATE
				FileInputStream fstream2 = new FileInputStream(filePath);
				java.io.DataInputStream in2 = new java.io.DataInputStream(fstream2);
				java.io.BufferedReader br2 = new java.io.BufferedReader(new java.io.InputStreamReader(in2));
				br2.readLine(); // remove the header line
				String strLine;
				while ((strLine = br2.readLine()) != null)   {
					String[] store = strLine.split(",");
					ArrayList<String> lineList = new ArrayList<String>();

					// Matching serviceRate to Master Service Rate
					String planCode = planCodes[i];
					for(ServiceRate serviceRate_temp:this.getAvailableServiceRate()) {
						if (serviceRate_temp.getRateCode().indexOf(planCode) >= 0) { // plan type must match the subscription
							String serviceRateDescription_temp = serviceRate_temp.getRateDescription().replaceAll("IDD", "International"); // "IDD Calls" <> "International Calls" in the master set up
							String[] serviceRateDescription = serviceRateDescription_temp.split(" ");
							if (serviceRateDescription.length < 4) continue; // Avoid Subscription Rate
							if (store[1].indexOf("- ") >= 0) { // TV Channel
								if (serviceRate_temp.getRateCode().equals("TV-C")) {
									lineList.add("TV-C");
									lineList.add(store[1]);
									break;
								}
							}
							else if ((serviceRateDescription[2]+" "+serviceRateDescription[3]).equals(store[1])) {
								lineList.add(serviceRate_temp.getRateCode());
								lineList.add(serviceRate_temp.getRateDescription());
								break;
							}
						}
					}
					
					if (lineList.size() == 0) { // Service Rate not found in the Master set up, or different name
						continue;
					}
					
					for (int j=0; j<store.length; j++) {
						if (j==1) continue;
						lineList.add(store[j]);
					}
					//System.out.println(lineList);
					allServiceRates.add(lineList);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		/*
		// generate bill for every month + add/delete service rate
		//[Customer][ServicePlan][ServiceRate][year,month]
		HashMap<String, HashMap<String, HashMap<String, List<Integer>>>> customerServiceRateDate= new HashMap<String, HashMap<String, HashMap<String, List<Integer>>>>();
		for (int year1=2011; year1<=2012; year1++) {
			for (int month1=1; month1<=12; month1++) {
				for(ArrayList<String> line:allServiceRates) {
					// take only service plan within the year && month
					if (line.get(3).indexOf("/") < 0) continue;
					String[] start = line.get(3).split("/");
					String[] end = line.get(4).split("/");
					int num_start = Integer.parseInt(start[2])*12 + Integer.parseInt(start[1]);
					int num_end = Integer.parseInt(end[2])*12 + Integer.parseInt(end[1]);
					if (!(num_start <= year1*12 + month1 && num_end >= year1*12 + month1))
						continue;
					
					// Try to add/remove service rate and generate monthly usage + talk time usage + monthly bill
					Customer customer1 = this.getCachedCustomerByAccountNumber(line.get(2));
					List<ServicePlan> servicePlans = customer1.getServicePlans();
					
					for(ServicePlan servicePlan:servicePlans) {
						if (line.get(0).substring(0, 2).equals(servicePlan.getPlanId().substring(0,2))) {
							List<ServiceRate> serviceRates = servicePlan.getServiceRates();
							
							// ignore if the servicerate is available
							Boolean checkAvailable = false;
							for(ServiceRate serviceRate:serviceRates) {
								if (serviceRate.getRateCode().equals(line.get(0))) {
									checkAvailable = true;
								}
							}
							if (checkAvailable.equals(false)) {
								for(ServiceRate serviceRate:this.getAvailableServiceRate()) {
									if (serviceRate.getRateCode().equals(line.get(0))) {
										serviceRates.add(serviceRate);
										System.out.println("adding service rate");
										
										// capture the expiration date of this service rate
										List<Integer> yearMonthExpired = new ArrayList<Integer>();
										String[] date = line.get(4).split("/");
										yearMonthExpired.add(Integer.parseInt(date[2]));
										yearMonthExpired.add(Integer.parseInt(date[1]));
										HashMap<String, List<Integer>> activeServiceRate = new HashMap<String, List<Integer>>();
										activeServiceRate.put(line.get(0), yearMonthExpired);
										HashMap<String, HashMap<String, List<Integer>>> activeServicePlan = new HashMap<String, HashMap<String, List<Integer>>>();
										activeServicePlan.put(servicePlan.getPlanId(), activeServiceRate);
										
										customerServiceRateDate.put(line.get(2), activeServicePlan);
										break;
									}
								}
							}
							
						}
					}
				}
				
				// check for expired service rate at every month for all customers
				for(Customer customer:this.getCustomers()) {
					for(ServicePlan servicePlan:customer.getServicePlans()) {
						List<ServiceRate> activeServiceRates = new ArrayList<ServiceRate>();
						for (ServiceRate serviceRate:servicePlan.getServiceRates()) {
							HashMap<String, HashMap<String, List<Integer>>> activeServicePlan;
							activeServicePlan = customerServiceRateDate.get(customer.getAccountNumber());
							//if (activeServicePlan == null) continue;

							List<Integer> yearDate;
							
							HashMap<String, List<Integer>> activeServiceRate = new HashMap<String, List<Integer>>();
							activeServiceRate = activeServicePlan.get(servicePlan.getPlanId());
							yearDate = activeServiceRate.get(serviceRate);
							
							// remove the service rate, because it is expired
							if (year1*12 + month1 > yearDate.get(0)*12 + yearDate.get(1)) {
								continue;
							}
							
							// add the active service rate
							activeServiceRates.add(serviceRate);
						}
						servicePlan.setServiceRates(activeServiceRates);
					}
				}
			
				// generate Monthly Bill HERE
			}
		}
		*/
		
	}
	
	private HashMap<String,HashMap<String,MonthlyUsage>> restoreTransactionDetails(String plantype){
		String transactionFile = new String();
		if(plantype.equalsIgnoreCase("DV"))
			transactionFile = ConstantFilePath.ONE_HASH_RESTORE_DV_TRANSACTIONS;
		if(plantype.equalsIgnoreCase("MV"))
			transactionFile = ConstantFilePath.ONE_HASH_RESTORE_MV_TRANSACTIONS;
		
		HashMap<String,HashMap<String,MonthlyUsage>> mapAccMonthlyUsages = new HashMap<String, HashMap<String,MonthlyUsage>>();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(transactionFile));
			StringTokenizer st;
			String line;
			int count = 0;

			
			while ((line = br.readLine()) != null) {
				if(count==0){
					count = 1;
					continue;
				}
				
				st = new StringTokenizer(line, ",");
				
				int column =0;
				String accountNumber = new String();
				
				HashMap<String,MonthlyUsage> mapMonthlyUsage = new HashMap<String,MonthlyUsage>();
				MonthlyUsage monthlyUsage = new MonthlyUsage();
				TalkTimeUsage talkTimeUsage = new TalkTimeUsage();
				
				while (st.hasMoreTokens()) {
					String columnValue = st.nextToken();
					//Check if account number exists
					if(column==0){
						if(mapAccMonthlyUsages.containsKey(columnValue)){
							mapMonthlyUsage = (HashMap<String,MonthlyUsage>)mapAccMonthlyUsages.get(columnValue);
						}
						accountNumber = columnValue;
						talkTimeUsage = new TalkTimeUsage();
						
						column++;
						continue;
						
						//Check if month year exists	
					}if(column==1){
						String[] dateTime = columnValue.split(" ");
						String dateOfCall = dateTime[0];
						String[] dateSplit = dateOfCall.split("/");
						String monthYear = dateSplit[1]+""+dateSplit[2];
						if(mapMonthlyUsage.containsKey(monthYear)){
							monthlyUsage = mapMonthlyUsage.get(monthYear);
						}else{
							monthlyUsage.setUsageYearMonth(monthYear);
							List<TalkTimeUsage> talkTimeUsages = new ArrayList<TalkTimeUsage>();
							monthlyUsage.setTalkTimeUsages(talkTimeUsages);
						}
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.DATE, new Integer(dateSplit[0]));
						cal.set(Calendar.MONTH, new Integer(dateSplit[1]));
						cal.set(Calendar.YEAR, new Integer(dateSplit[2]));
						talkTimeUsage.setCallTime(cal.getTime());
						
						column++;
						continue;
						//Setting call type
					}if(column==2){
						talkTimeUsage.setUsageType(columnValue);
						column++;
						continue;
						//Setting call number
					}if(column==3){
						talkTimeUsage.setCallNumber(columnValue);
						column++;
						continue;
						//Setting call duration	
					}if(column==4){
						talkTimeUsage.setUsageDuration(new Long(columnValue));
						monthlyUsage.getTalkTimeUsages().add(talkTimeUsage);
						mapMonthlyUsage.put(monthlyUsage.getUsageYearMonth(), monthlyUsage);
						mapAccMonthlyUsages.put(accountNumber, mapMonthlyUsage);
					}
				}
			}
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		System.out.println("mapAccMonthlyUsages.size() : "+mapAccMonthlyUsages.size());
		
		return mapAccMonthlyUsages;
	}
	
	public static void main(String...args){
		(new OneHashDataCache()).restoreFromFile();
		//(new OneHashDataCache()).restoreTransactionDetails();	
	}
}
