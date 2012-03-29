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
 * 29 March 2012    Yue Yang	    0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.model.service.rate;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.onehash.model.service.rate.SubscriptionRate;

public class SubscriptionRateTest extends TestCase {
    private SubscriptionRate subRate1 = null;
	private SubscriptionRate subRate2 = null;
	
	@Before
	public void setUp() throws Exception {
		subRate1 = new SubscriptionRate();
		subRate1.setRateCode("DV-S");
		subRate1.setRateDescription("Digital Voice Subscription");
		subRate1.setRatePrice(new BigDecimal(10.00));
		subRate1.setPriority(1);
		subRate1.setFreeCharge(true);
		
		subRate2 = new SubscriptionRate();
		subRate2.setRateCode("MV-S");
		subRate2.setRateDescription("Mobile Voice Subscription");
		subRate2.setRatePrice(new BigDecimal(45.00));
		subRate2.setPriority(2);
		subRate2.setFreeCharge(false);
	}
	
	@After
	public void tearDown() throws Exception {
		subRate1 = null;
		subRate2 = null;
	}
	
	@Test
	public void testgetRateCode(){
		assertNotNull(subRate1);
		assertNotNull(subRate2);
		assertEquals(subRate1.getRateCode(),"DV-S");
		assertEquals(subRate2.getRateCode(),"MV-S");
	}
	
	@Test
	public void testgetRateDescription(){
		assertEquals(subRate1.getRateDescription(),"Digital Voice Subscription");
		assertEquals(subRate2.getRateDescription(),"Mobile Voice Subscription");
	}
	
	@Test
	public void testgetRatePrice(){
		assertEquals(subRate1.getRatePrice(),new BigDecimal(10.00));
		assertEquals(subRate2.getRatePrice(),new BigDecimal(45.00));
	}
	
	@Test
	public void testgetPriority(){
		assertEquals(subRate1.getPriority(),1);
		assertEquals(subRate2.getPriority(),2);
	}
	
	@Test
	public void testisFreeCharge(){
		assertEquals(subRate1.isFreeCharge(),true);
		assertEquals(subRate2.isFreeCharge(),false);
	}
	
	@Test
	public void testcalculateMonthlyRate(){
		assertEquals(subRate1.calculateMonthlyRate(),new BigDecimal(0.00));
		assertEquals(subRate2.calculateMonthlyRate(),new BigDecimal(45.00));
	}
}
