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
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.model.bill.*;
import com.onehash.model.service.plan.*;
import com.onehash.model.user.User;
import com.onehash.utility.OneHashDateUtil;


public class OneHashDataCacheTest extends TestCase{
	OneHashDataCache oneHashDataCache = null;
	Calendar billRequestCalendar = null;
	Date billRequestDate = null;
	
	List<ServicePlan> servicePlan = null;
	List<ServiceRate> serviceRates = null;
	SubscriptionRate subRate = null;
	private MobileVoicePlan mVoicePlan = null;
	
	private Customer cus1 = null;
	private Customer cus2 = null;
	
	Bill bill = null;
	private ComplaintLog complaintLog1 = null;
	private ComplaintLog complaintLog2 = null; 
	
	private List<User> users = null;
	private User user1 = null;
	
	private List<Customer> listCus;

	@Before
	public void setUp() {
		oneHashDataCache = OneHashDataCache.getInstance();
		
		billRequestCalendar = Calendar.getInstance();;
		billRequestCalendar.set(Calendar.DATE, 28);
		billRequestCalendar.set(Calendar.MONTH, 1);
		billRequestCalendar.set(Calendar.YEAR, 2011);
		
		billRequestDate = billRequestCalendar.getTime();
		
		/* Customer */
	    listCus = new ArrayList<Customer>();
		
		cus1 = new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68");
		cus2 = new Customer("Acton Dennis", "S7277979X", "6159 West East St. Louis Ln.", "95164061" ,"SA-0314-69-44");
		listCus.add(cus1);
		
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
		
		/* User */
		users = new ArrayList<User>();
		user1 = new User();
		
		user1.setUserId(new Long(1));
		user1.setUserName("admin");
		user1.setFirstName("PT 4 Admin");
		user1.setLastName("PT 4");
		user1.setPassword("password");
		user1.setUserRole("admin");
		user1.setStatus(true);
		users.add(user1);
		oneHashDataCache.setUsers(users);
	}

	@After
	public void tearDown() throws Exception {
		oneHashDataCache = null;
		billRequestCalendar = null;
		billRequestDate = null;
		servicePlan = null;
		serviceRates = null;
		subRate = null;
		mVoicePlan = null;
		bill = null;
		cus1 = null;
		cus2 = null;
		
		complaintLog1 = null;
		complaintLog2 = null; 
	}
	

	@Test
	public void testGetInstance(){
		assertNotNull(oneHashDataCache);
	}
	
	@Test
	public void testCalculateBill(){
		assertNotNull(bill);
		assertEquals(bill.getBillDate(), billRequestDate);
		assertEquals(bill.getGstRate(), new BigDecimal(7));
		assertEquals(bill.getCarryForward(), new BigDecimal(0));
		assertEquals(bill.getCurrentBill(), new BigDecimal(100));
		assertEquals(bill.getTotalBill(), new BigDecimal(100));
	}
	
	@Test
	public void testGetCustomers(){
		assertNotNull(listCus);
		assertEquals(listCus.get(0).getName(),"Colby Mosley");
		assertEquals(listCus.get(0).getNric(),"S4136327D");
		assertEquals(listCus.get(0).getAddress(),"17784 South Guyana Blvd.");
		assertEquals(listCus.get(0).getPhoneNumber(),"87529248");
		assertEquals(listCus.get(0).getAccountNumber(),"SA-0055-36-68");
	}
	
	/***** USER RELATED OPERATION ******/
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
			oneHashDataCache.saveUser(user1);
		}
		catch(Exception e){
			throw new Exception ("Save user unsuccessfully");
		}
		
	}
    /***** COMPLAINT RELATED OPERATION *******/
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
