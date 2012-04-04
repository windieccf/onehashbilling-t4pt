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
 * 30 March 2012    Chen Changfeng	0.1				Class creating
 * 30 March 2012	Song Lei		0.2				Add testGetInstance, tearDown and testCalculateBill(uncompleted)
 * 													
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.controller.OneHashDataCache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.constant.ConstantStatus;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.bill.Bill;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.service.plan.MobileVoicePlan;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.model.user.User;
import com.onehash.utility.OneHashDateUtil;


public class OneHashDataCacheTest extends TestCase{
	OneHashDataCache oneHashDataCache = null;
	Calendar billRequestCalendar = null;
	Date billRequestDate = null;
	
	private List<User> users = null;
	private User curUser = null;
	private User newUser = null;
	
	private List<Customer> listCus;
	private Customer cus1 = null;
	private Customer cus2 = null;
	
	private List<ServicePlan> servicePlan = null;
	private List<ServiceRate> serviceRates = null;
	private SubscriptionRate subRate = null;
	private MobileVoicePlan mVoicePlan = null;
	
	private Bill bill = null;
	
	private ComplaintLog complaintLog1 = null;
	private ComplaintLog complaintLog2 = null; 

	@Before
	public void setUp() {
		oneHashDataCache = OneHashDataCache.getInstance();
		
		billRequestCalendar = Calendar.getInstance();;
		billRequestCalendar.set(Calendar.DATE, 28);
		billRequestCalendar.set(Calendar.MONTH, 1);
		billRequestCalendar.set(Calendar.YEAR, 2011);
		
		/* User */
		users = new ArrayList<User>();
		curUser = new User();
		newUser = new User();
		
		curUser.setUserId(new Long(1));
		curUser.setUserName("admin");
		curUser.setFirstName("PT 4 Admin");
		curUser.setLastName("PT 4");
		curUser.setPassword("password");
		curUser.setUserRole("admin");
		curUser.setStatus(true);
		oneHashDataCache.setCurrentUser(curUser);
		users.add(curUser);
		oneHashDataCache.setUsers(users);
		
		newUser.setUserName("agent");
		newUser.setFirstName("PT 4 Agent");
		newUser.setLastName("PT 4");
		newUser.setPassword("password");
		newUser.setUserRole("agent");
		newUser.setStatus(true);
		
		/* Customer */
	    listCus = new ArrayList<Customer>();
		
		cus1 = new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68");
		cus2 = new Customer("Acton Dennis", "S7277979X", "6159 West East St. Louis Ln.", "95164061" ,"SA-0314-69-44");
		listCus.add(cus1);
		listCus.add(cus2);
		
		oneHashDataCache.setCustomers(listCus);
		
		/* Service Plan */
		billRequestDate = billRequestCalendar.getTime();
		
		servicePlan = new ArrayList<ServicePlan>();
		serviceRates = new ArrayList<ServiceRate>();
	
		subRate = new SubscriptionRate();
		subRate.setRateCode("MV-S");
		subRate.setRateDescription("Mobile Voice Subscription");
		subRate.setRatePrice(new BigDecimal(100.00));
		subRate.setPriority(3);
		subRate.setFreeCharge(false);
		serviceRates.add(subRate);
		
		mVoicePlan = new MobileVoicePlan();
		mVoicePlan.setPlanId("MVCode1");
		mVoicePlan.setPlanCode("MVCode1");
		mVoicePlan.setStatus(ConstantStatus.SERVICEPLAN_ACTIVE);
		mVoicePlan.setPlanName("Mobile Voice Plan 1");
		mVoicePlan.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		mVoicePlan.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		mVoicePlan.setServiceRates(serviceRates);
		mVoicePlan.setSim("sim3"); 
		mVoicePlan.setRegisteredPhoneNumber("82781903");
		
		servicePlan.add(mVoicePlan);
		
		cus1.setServicePlans(servicePlan);
		
		bill = oneHashDataCache.calculateBill(cus1, billRequestDate);
		
		/* complaint Log */
		complaintLog1 = new ComplaintLog();
		complaintLog1.setIssueNo("IS-0000-00-01");
		complaintLog1.setIssueDescription("Complain about the service 1");
		
		complaintLog2 = new ComplaintLog();
		complaintLog2.setIssueNo("IS-0000-00-02");
		complaintLog2.setIssueDescription("Complain about the service 2");
		
		cus1.addComplaintLog(complaintLog1);
		cus2.addComplaintLog(complaintLog2);
	}

	@After
	public void tearDown() throws Exception {
		oneHashDataCache = null;
		billRequestCalendar = null;
		billRequestDate = null;
		
		users = null;
		curUser = null;
		newUser = null;
		
		listCus = null;
		cus1 = null;
		cus2 = null;
		
		servicePlan = null;
		serviceRates = null;
		subRate = null;
		mVoicePlan = null;
		
		bill = null;
	
		complaintLog1 = null;
		complaintLog2 = null; 
	}
	

	@Test
	public void testGetInstance(){
		assertNotNull(oneHashDataCache);
	}
	
	
	/***** Current Login User *****/
	@Test 
	public void testGetCurrentUser(){
		assertNotNull(oneHashDataCache.getCurrentUser());
		assertEquals(oneHashDataCache.getCurrentUser().getUserName(),"admin");
		assertEquals(oneHashDataCache.getCurrentUser().getFirstName(),"PT 4 Admin");
		assertEquals(oneHashDataCache.getCurrentUser().getLastName(),"PT 4");
		assertEquals(oneHashDataCache.getCurrentUser().getPassword(),"password");
		assertEquals(oneHashDataCache.getCurrentUser().getUserRole(),"admin");
	}
	
	/***** Attribute *****/
	@Test
	public void testGetCustomers(){
		assertNotNull(listCus);
		assertEquals(listCus.get(0).getName(),"Colby Mosley");
		assertEquals(listCus.get(0).getNric(),"S4136327D");
		assertEquals(listCus.get(0).getAddress(),"17784 South Guyana Blvd.");
		assertEquals(listCus.get(0).getPhoneNumber(),"87529248");
		assertEquals(listCus.get(0).getAccountNumber(),"SA-0055-36-68");
	}
	
	@Test
	public void testFlushCache(){
		oneHashDataCache.flushCache();
		assertNotNull(users);
		assertNotNull(listCus);
	}
	
	
	/***** USER RELATED Test Case*****/
	@Test
	public void testGetUserByUserName(){
		assertEquals(oneHashDataCache.getUserByUserName("admin").getFirstName(),"PT 4 Admin");
		assertEquals(oneHashDataCache.getUserByUserName("admin").getLastName(),"PT 4");
		assertEquals(oneHashDataCache.getUserByUserName("admin").getPassword(),"password");
		assertEquals(oneHashDataCache.getUserByUserName("admin").getUserRole(),"admin");
	}
	
	@Test
	public void testSaveUser() throws Exception{
		try{
			oneHashDataCache.saveUser(newUser);
			assertNotNull(oneHashDataCache.getUsers());
			assertEquals(oneHashDataCache.getUsers().size(),2);
			assertEquals(oneHashDataCache.getUsers().get(1).getUserId(),1);
			assertEquals(oneHashDataCache.getUsers().get(1).getUserName(),"agent");
		}
		catch(Exception e){
			throw new Exception ("Save user unsuccessfully");
		}
	}
	
	@Test
	public void tesLogout(){
		oneHashDataCache.logout();
		assertNull(curUser);
	}
	
	
	/**** Customer Related Test Case ****/
	@Test
	public void testGetCustomerByAccountNumber(){
		assertNotNull(oneHashDataCache.getCustomerByAccountNumber("SA-0314-69-44"));
		assertEquals(oneHashDataCache.getCustomerByAccountNumber("SA-0314-69-44").getName(),"Acton Dennis");
		assertEquals(oneHashDataCache.getCustomerByAccountNumber("SA-0314-69-44").getNric(),"S7277979X");
		assertEquals(oneHashDataCache.getCustomerByAccountNumber("SA-0314-69-44").getAccountNumber(),"SA-0314-69-44");
	}

	@Test
	public void testGetCustomerByNric(){
		assertNotNull(oneHashDataCache.getCustomerByNric("S7277979X"));
		assertEquals(oneHashDataCache.getCustomerByNric("S7277979X").getName(),"Acton Dennis");
		assertEquals(oneHashDataCache.getCustomerByNric("S7277979X").getNric(),"S7277979X");
		assertEquals(oneHashDataCache.getCustomerByNric("S7277979X").getAccountNumber(),"SA-0314-69-44");
	}
	
	@Test
	public void testSaveCustomer() throws Exception {
		Customer cus3 = new Customer();
		cus3.setName("Yasir Hamilton");
		cus3.setNric("S0668036Q");
		cus3.setAddress("80849  Cyprus Blvd.");
		cus3.setPhoneNumber("87020191");
		try{
			oneHashDataCache.saveCustomer(cus3);
			assertEquals(oneHashDataCache.getCustomers().size(),3);
			assertEquals(oneHashDataCache.getCustomers().get(2).getAccountNumber(),"SA-0000-00-01");
			assertEquals(oneHashDataCache.getCustomers().get(2).getNric(),"S0668036Q");
		}
		catch(Exception e){
			throw new Exception ("Save Customer unsuccessfully");
		}
	}
	
	
	/***** Bill Related Test Case *****/
	@Test
	public void testCalculateBill(){
		assertNotNull(bill);
		assertEquals(bill.getBillDate(), billRequestDate);
		assertEquals(bill.getGstRate(), new BigDecimal(7));
		assertEquals(bill.getCarryForward(), new BigDecimal(0));
		assertEquals(bill.getCurrentBill(), new BigDecimal(100));
		assertEquals(bill.getTotalBill(), new BigDecimal(100));
	}
	
	
    /***** Complaint RRelated Test Case *****/
	@Test
	public void testCreateComplaintLog() {
		assertEquals(complaintLog1.getIssueNo(), "IS-0000-00-01");
		assertEquals(complaintLog1.getIssueDescription(), "Complain about the service 1");
		
		assertEquals(complaintLog2.getIssueNo(), "IS-0000-00-02");
		assertEquals(complaintLog2.getIssueDescription(), "Complain about the service 2");
	}

	@Test
	public void testCustomerHasComplaintLog() {
		assertTrue(cus1.getComplaintLogs().size() > 0);
		assertTrue(cus2.getComplaintLogs().size() > 0);
	}

	@Test
	public void testRetrieveComplaintLog() throws Exception {
		complaintLog1 = oneHashDataCache.getComplaintLog(cus1, "IS-0000-00-01");
		assertEquals(complaintLog1.getIssueNo(), "IS-0000-00-01");
		assertEquals(complaintLog1.getIssueDescription(), "Complain about the service 1");
		
		complaintLog2 = oneHashDataCache.getComplaintLog(cus2, "IS-0000-00-02");
		assertEquals(complaintLog2.getIssueNo(), "IS-0000-00-02");
		assertEquals(complaintLog2.getIssueDescription(), "Complain about the service 2");
	}
}
