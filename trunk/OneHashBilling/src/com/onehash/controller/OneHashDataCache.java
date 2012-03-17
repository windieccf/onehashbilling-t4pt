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
 * 													
 * 													
 * 
 */
package com.onehash.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.onehash.constant.ConstantFilePath;
import com.onehash.exception.BusinessLogicException;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.KeyScalar;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
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
	
	private List<ComplaintLog> complaintLog;
	public List<ComplaintLog> ComplaintLog() {return complaintLog;}
	public void setComplaintLog(List<ComplaintLog> complaintLog) {this.complaintLog = complaintLog;}
	
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
	
	
}
