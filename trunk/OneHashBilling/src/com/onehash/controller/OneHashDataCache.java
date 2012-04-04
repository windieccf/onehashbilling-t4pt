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
import com.onehash.constant.ConstantPrefix;
import com.onehash.enumeration.EnumUserAccess;
import com.onehash.exception.BusinessLogicException;
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
		}catch(Exception e){
			
		}
		
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
		ServiceRate.loadServiceRate();
		ServiceRate.generateRandomServicePlanForCustomer();
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
				throw new BusinessLogicException("Username is taken");
			
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
				throw new BusinessLogicException("NRIC exist");
			
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
			
			// restoring customer related information
			FileInputStream fstream = new FileInputStream("./data/CustomerData.txt");
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
			
			this.flushCache();
			// update the keys
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String...args){
		(new OneHashDataCache()).restoreFromFile();
		
	}
}
