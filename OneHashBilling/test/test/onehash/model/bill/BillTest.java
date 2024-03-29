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
 * 28 March 2012    Yue Yang	    0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.model.bill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.model.bill.*;
import com.onehash.utility.OneHashDateUtil;

public class BillTest extends TestCase {
    private Bill b1 = null;

	
	@Before
	public void setUp() throws Exception {
        
        //Set up BillSummary
        Map<String,List<BillSummary>> billSummaryMap = new HashMap<String,List<BillSummary>>();
        List<BillSummary> billSummary = new ArrayList<BillSummary>();
        BillSummary bSummary1 = new BillSummary("MV-L",new BigDecimal(80.00));
        billSummary.add(bSummary1);
        
        BillSummary bSummary2 = new BillSummary("MV-R",new BigDecimal(20.00));
        billSummary.add(bSummary2);
        billSummaryMap.put("20120301", billSummary);
        
        //Set up Payment Details
        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
        PaymentDetail bPayment1 = new PaymentDetail(OneHashDateUtil.getDate(2012,3,2),new BigDecimal(60.00));
        paymentDetails.add(bPayment1);
        
        PaymentDetail bPayment2 = new PaymentDetail(OneHashDateUtil.getDate(2012,3,10),new BigDecimal(40.00));
        paymentDetails.add(bPayment2);
        
        b1 = new Bill();
        b1.setBillDate(OneHashDateUtil.getDate(2012,3,1));
        b1.setCarryForward(new BigDecimal(0));
        b1.setCurrentBill(new BigDecimal(100));
        b1.setGstRate(new BigDecimal(7));
        b1.setTotalBill(new BigDecimal(107.00));
        b1.setBillSummaryMap(billSummaryMap);
        b1.setPaymentDetails(paymentDetails);
	}
	
	@After
	public void tearDown() throws Exception {
		b1 = null;
	}
	
	
	@Test
	public void testGetBillDate(){
		assertNotNull(b1);
		assertEquals(b1.getBillDate(),OneHashDateUtil.getDate(2012,3,1));
	}
	
	@Test
	public void testGetCarryForward(){
		assertEquals(b1.getCarryForward(),new BigDecimal(0));
	}
	
	@Test
	public void testGetCurrentBill(){
	    assertEquals(b1.getCurrentBill(),new BigDecimal(100));
	}
	
	@Test
	public void testGetGstRate(){
		assertEquals(b1.getGstRate(),new BigDecimal(7));
	}
	
	@Test
	public void testGetTotalBill(){
		assertEquals(b1.getTotalBill(),new BigDecimal(107.00));
	}
	
	@Test
	public void testGetBillSummaryMap(){
		assertEquals(b1.getBillSummaryMap().size(),1);
		assertEquals(b1.getBillSummaryMap().get("20120301").get(0).getDescription(),"MV-L");
		assertEquals(b1.getBillSummaryMap().get("20120301").get(0).getTotal(),new BigDecimal(80.00));
		assertEquals(b1.getBillSummaryMap().get("20120301").get(1).getDescription(),"MV-R");
		assertEquals(b1.getBillSummaryMap().get("20120301").get(1).getTotal(),new BigDecimal(20.00));
	}
	
	@Test
	public void testGetPaymentDetails(){
		assertEquals(b1.getPaymentDetails().size(),2);
		assertEquals(b1.getPaymentDetails().get(0).getPaymentDate(),OneHashDateUtil.getDate(2012,3,2));
		assertEquals(b1.getPaymentDetails().get(0).getAmount(),new BigDecimal(60.00));
		assertEquals(b1.getPaymentDetails().get(1).getPaymentDate(),OneHashDateUtil.getDate(2012,3,10));
		assertEquals(b1.getPaymentDetails().get(1).getAmount(),new BigDecimal(40.00));
	}
}
