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
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.controller.OneHashDataCache;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.bill.*;
import com.onehash.utility.OneHashDateUtil;


public class OneHashDataCacheTest extends TestCase{
	OneHashDataCache oneHashDataCache = null;
	Calendar billRequestCalendar = null;
	Date billRequestDate = null;
	
	private Customer cus1 = null;
	private Customer cus2 = null;
	
	private Bill bill = null;
	
	private ComplaintLog complaintLog1 = null;
	private ComplaintLog complaintLog2 = null; 
	
	//ComplaintLog compLog1 = null;

	@Before
	public void setUp() {
		oneHashDataCache = OneHashDataCache.getInstance();
		
		billRequestCalendar = Calendar.getInstance();;
		billRequestCalendar.set(Calendar.DATE, 30);
		billRequestCalendar.set(Calendar.MONTH, 03);
		billRequestCalendar.set(Calendar.YEAR, 2012);
		
		billRequestDate = billRequestCalendar.getTime();
		
		cus1 = new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68");
		cus2 = new Customer("Acton Dennis", "S7277979X", "6159 West East St. Louis Ln.", "95164061" ,"SA-0314-69-44");
		
		bill = new Bill();
		bill.setBillDate(OneHashDateUtil.getDate(2012,3,30));
		bill.setCarryForward(new BigDecimal(0));
		bill.setCurrentBill(new BigDecimal(100));
		bill.setGstRate(new BigDecimal(7));
		bill.setTotalBill(new BigDecimal(107.00));
		//bill.setBillDetails(billDetails);
		//bill.setBillSummaryMap(billSummaryMap);
		//bill.setPaymentDetails(paymentDetails);
		
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
		
		cus1 = null;
		cus2 = null;
		
		complaintLog1 = null;
		complaintLog2 = null; 
	}
	

	@Test
	public void testGetInstance(){
		assertNotNull(OneHashDataCache.getInstance());
	}
	
	public void testCalculateBill(){
		
	}
	
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
