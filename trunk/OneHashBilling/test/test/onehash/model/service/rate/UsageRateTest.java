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

import com.onehash.model.service.rate.UsageRate;

public class UsageRateTest extends TestCase {
    private UsageRate usagRate1 = null;
	private UsageRate usagRate2 = null;
	
	@Before
	public void setUp() throws Exception {
		usagRate1 = new UsageRate(1);
		usagRate1.setRateCode("DV-L");
		usagRate1.setRateDescription("Digital Voice Local Calls");
		usagRate1.setRatePrice(new BigDecimal(0.30));
		usagRate1.setPriority(3);
		usagRate1.setFreeCharge(false);
		
		usagRate2 = new UsageRate(2);
		usagRate2.setRateCode("MV-L");
		usagRate2.setRateDescription("Mobile Voice Local Calls");
		usagRate2.setRatePrice(new BigDecimal(0.50));
		usagRate2.setPriority(4);
		usagRate2.setFreeCharge(true);
	}
	
	@After
	public void tearDown() throws Exception {
		usagRate1 = null;
		usagRate2 = null;
	}
	
	@Test
	public void testGetRateCode(){
		assertNotNull(usagRate1);
		assertNotNull(usagRate2);
		assertEquals(usagRate1.getRateCode(),"DV-L");
		assertEquals(usagRate2.getRateCode(),"MV-L");
	}
	
	@Test
	public void testGetRateDescription(){
		assertEquals(usagRate1.getRateDescription(),"Digital Voice Local Calls");
		assertEquals(usagRate2.getRateDescription(),"Mobile Voice Local Calls");
	}
	
	@Test
	public void testGetRatePrice(){
		assertEquals(usagRate1.getRatePrice(),new BigDecimal(0.30));
		assertEquals(usagRate2.getRatePrice(),new BigDecimal(0.50));
	}
	
	@Test
	public void testGetPriority(){
		assertEquals(usagRate1.getPriority(),3);
		assertEquals(usagRate2.getPriority(),4);
	}
	
	@Test
	public void testIsFreeCharge(){
		assertEquals(usagRate1.isFreeCharge(),false);
		assertEquals(usagRate2.isFreeCharge(),true);
	}
}
