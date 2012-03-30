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

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.model.bill.PaymentDetail;
import com.onehash.utility.OneHashDateUtil;

public class PaymentDetailTest extends TestCase {
    private PaymentDetail bPayment1 = null;
	private PaymentDetail bPayment2= null;
	
	@Before
	public void setUp() throws Exception {
		bPayment1 = new PaymentDetail(OneHashDateUtil.getDate(2012,3,14),new BigDecimal(73.50));
		bPayment2 = new PaymentDetail(OneHashDateUtil.getDate(2012,4,3),new BigDecimal(85.60));
	}
	
	@After
	public void tearDown() throws Exception {
		bPayment1 = null;
		bPayment2 = null;
	}
	
	@Test
	public void testPaymentDetail() {
		assertNotNull(bPayment1);
		assertNotNull(bPayment2);
		assertNotSame(bPayment1,new PaymentDetail(OneHashDateUtil.getDate(2012,3,14),new BigDecimal(73.50)));
		
		//Check individual attribute 
		assertEquals(bPayment1.getPaymentDate(),OneHashDateUtil.getDate(2012,3,14));
		assertEquals(bPayment1.getAmount(),new BigDecimal(73.50));
	}
	
	@Test
	public void testGetPaymentDate(){
		assertEquals(bPayment2.getPaymentDate(),OneHashDateUtil.getDate(2012,4,3));
	}
	
	@Test
	public void testGetTotal(){
		assertEquals(bPayment2.getAmount(),new BigDecimal(85.60));
	}
}
