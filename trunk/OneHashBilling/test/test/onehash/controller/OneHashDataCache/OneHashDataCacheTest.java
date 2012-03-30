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
 * 													
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.controller.OneHashDataCache;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.onehash.constant.ConstantPrefix;
import com.onehash.controller.OneHashDataCache;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.utility.OneHashDateUtil;
import com.onehash.utility.OneHashStringUtil;

public class OneHashDataCacheTest {
	
	/****************************************** COMPLAINT RELATED TEST **************************************************/
	private Customer cus1 = null;
	private ComplaintLog complaintLog = null;
	OneHashDataCache oneHashDataCache = null;
	
	ComplaintLog compLog1 = null;

	@Before
	public void setUp() {
		cus1 = new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68");
		complaintLog = new ComplaintLog();
		oneHashDataCache = OneHashDataCache.getInstance();
		complaintLog.setIssueNo("IS-0000-00-01");
		complaintLog.setIssueDescription("Complain about the service");
		cus1.addComplaintLog(complaintLog);
	}

	@Test
	public void testCreateComplaintLog() {
		assertEquals(complaintLog.getIssueNo(), "IS-0000-00-01");
		assertEquals(complaintLog.getIssueDescription(), "Complain about the service");
	}

	@Test
	public void testCustomerHasComplaintLog() {
		assertTrue(cus1.getComplaintLogs().size() > 0);
	}

	@Test
	public void testRetrieveComplaintLog() throws Exception {
		compLog1 = oneHashDataCache.getComplaintLog(cus1, "IS-0000-00-01");
		assertEquals(compLog1.getIssueNo(), "IS-0000-00-01");
		assertEquals(compLog1.getIssueDescription(), "Complain about the service");
	}


}
