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

package test.onehash.model.service.plan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.constant.ConstantStatus;
import com.onehash.model.service.plan.DigitalVoicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.utility.OneHashDateUtil;

public class DigitalVoicePlanTest extends TestCase {
    private DigitalVoicePlan dVoicePlan1 = null;
	private DigitalVoicePlan dVoicePlan2 = null;
	
	@Before
	public void setUp() throws Exception {
		
		List<ServiceRate> serviceRates1 = new ArrayList<ServiceRate>();
		List<ServiceRate> serviceRates2 = new ArrayList<ServiceRate>();
		
		SubscriptionRate subRate1 = new SubscriptionRate();
		subRate1 = new SubscriptionRate();
		subRate1.setRateCode("DV-S");
		subRate1.setRateDescription("Digital Voice Subscription");
		subRate1.setRatePrice(new BigDecimal(10.00));
		subRate1.setPriority(1);
		subRate1.setFreeCharge(false);
		serviceRates1.add(subRate1);
		serviceRates2.add(subRate1);
		
		SubscriptionRate subRate2 = new SubscriptionRate();
		subRate2 = new SubscriptionRate();
		subRate2.setRateCode("DV-C");
		subRate2.setRateDescription("Digital Voice Call Transfer");
		subRate2.setRatePrice(new BigDecimal(5.00));
		subRate2.setPriority(2);
		subRate2.setFreeCharge(false);
		serviceRates2.add(subRate2);
		
		dVoicePlan1 = new DigitalVoicePlan();
		dVoicePlan1.setPlanId("DVCode1");
		dVoicePlan1.setPlanCode("DVCode1");
		dVoicePlan1.setStatus(ConstantStatus.SERVICEPLAN_ACTIVE);
		dVoicePlan1.setPlanName("Digital Voice Plan 1");
		dVoicePlan1.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		dVoicePlan1.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		dVoicePlan1.setServiceRates(serviceRates1);
		dVoicePlan1.setSim("sim1"); 
		dVoicePlan1.setRegisteredPhoneNumber("62673901");
		
		dVoicePlan2 = new DigitalVoicePlan();
		dVoicePlan2.setPlanId("DVCode2");
		dVoicePlan2.setPlanCode("DVCode2");
		dVoicePlan2.setStatus(ConstantStatus.SERVICEPLAN_ACTIVE);
		dVoicePlan2.setPlanName("Digital Voice Plan 2");
		dVoicePlan2.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		dVoicePlan2.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		dVoicePlan2.setServiceRates(serviceRates2);
		dVoicePlan2.setSim("sim2");
		dVoicePlan2.setRegisteredPhoneNumber("612983013");
	}
	
	@After
	public void tearDown() throws Exception {
		dVoicePlan1 = null;
		dVoicePlan2 = null;
	}
	
		
	@Test
	public void testgetPlanId(){
		assertNotNull(dVoicePlan1);
		assertNotNull(dVoicePlan2);
		assertEquals(dVoicePlan1.getPlanId(),"DVCode1");
		assertEquals(dVoicePlan2.getPlanId(),"DVCode2");
	}

	
	@Test
	public void testgetPlanCode(){
		assertEquals(dVoicePlan1.getPlanCode(),"DVCode1");
		assertEquals(dVoicePlan2.getPlanCode(),"DVCode2");
	}
	
	@Test
	public void testgetStatus(){
		assertEquals(dVoicePlan1.getStatus(),ConstantStatus.SERVICEPLAN_ACTIVE);
		assertEquals(dVoicePlan2.getStatus(),ConstantStatus.SERVICEPLAN_ACTIVE);
	}
	
	@Test
	public void testgetPlanName(){
		assertEquals(dVoicePlan1.getPlanName(),"Digital Voice Plan 1");
		assertEquals(dVoicePlan2.getPlanName(),"Digital Voice Plan 2");
	}
	
	@Test
	public void testgetStartDate(){
		assertEquals(dVoicePlan1.getStartDate(),OneHashDateUtil.getDate(2011, 1, 1));
		assertEquals(dVoicePlan2.getStartDate(),OneHashDateUtil.getDate(2011, 1, 1));
	}
	
	@Test
	public void testgetEndDate(){
		assertEquals(dVoicePlan1.getEndDate(),OneHashDateUtil.getDate(2012, 12, 31));
		assertEquals(dVoicePlan2.getEndDate(),OneHashDateUtil.getDate(2012, 12, 31));
	}
	
	@Test
	public void testgetDeletedDate(){
		assertEquals(dVoicePlan2.getDeletedDate(),new Date());
	}
	
	@Test 
	public void testgetSim(){
		assertEquals(dVoicePlan1.getSim(),"sim1");
		assertEquals(dVoicePlan2.getSim(),"sim2");
	}
	
	@Test 
	public void testgetRegisteredPhoneNumber(){
		assertEquals(dVoicePlan1.getRegisteredPhoneNumber(),"62673901");
		assertEquals(dVoicePlan2.getRegisteredPhoneNumber(),"612983013");
	}
	
	@Test
	public void testgetServiceRates(){
		assertEquals(dVoicePlan2.getServiceRates().size(),2);
		assertEquals(dVoicePlan2.getServiceRates().get(0).getRateCode(),"DV-S");
		assertEquals(dVoicePlan2.getServiceRates().get(0).getRateDescription(),"Digital Voice Subscription");
		assertEquals(dVoicePlan2.getServiceRates().get(0).getRatePrice(),new BigDecimal(10.00));
		assertEquals(dVoicePlan2.getServiceRates().get(0).getPriority(),1);
		assertEquals(dVoicePlan2.getServiceRates().get(1).getRateCode(),"DV-C");
		assertEquals(dVoicePlan2.getServiceRates().get(1).getRateDescription(),"Digital Voice Call Transfer");
		assertEquals(dVoicePlan2.getServiceRates().get(1).getRatePrice(),new BigDecimal(5.00));
		assertEquals(dVoicePlan2.getServiceRates().get(1).getPriority(),2);
		
		assertTrue(dVoicePlan2.getServiceRates().get(0).getRateCode().equals(dVoicePlan1.getServiceRates().get(0).getRateCode()));
		assertTrue(dVoicePlan2.getServiceRates().get(0).getRateDescription().equals(dVoicePlan1.getServiceRates().get(0).getRateDescription()));
		assertTrue(dVoicePlan2.getServiceRates().get(0).getRatePrice().equals(dVoicePlan1.getServiceRates().get(0).getRatePrice()));
   }
}
