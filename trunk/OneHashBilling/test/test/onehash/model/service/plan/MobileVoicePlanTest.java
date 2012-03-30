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
 * 30 March 2012    Yue Yang	    0.1				Class creating
 * 30 March 2012    Yue Yang	    0.2			    Modify 	testgetDeletedDate Class													
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.model.service.plan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.constant.ConstantStatus;
import com.onehash.model.service.plan.MobileVoicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.utility.OneHashDateUtil;

public class MobileVoicePlanTest extends TestCase {
    private MobileVoicePlan mVoicePlan1 = null;
	private MobileVoicePlan mVoicePlan2 = null;
	
	@Before
	public void setUp() throws Exception {
		
		List<ServiceRate> serviceRates1 = new ArrayList<ServiceRate>();
		List<ServiceRate> serviceRates2 = new ArrayList<ServiceRate>();
		
		SubscriptionRate subRate1 = new SubscriptionRate();
		subRate1 = new SubscriptionRate();
		subRate1.setRateCode("MV-S");
		subRate1.setRateDescription("Mobile Voice Subscription");
		subRate1.setRatePrice(new BigDecimal(45.00));
		subRate1.setPriority(3);
		subRate1.setFreeCharge(false);
		serviceRates1.add(subRate1);
		serviceRates2.add(subRate1);
		
		SubscriptionRate subRate2 = new SubscriptionRate();
		subRate2 = new SubscriptionRate();
		subRate2.setRateCode("MV-D");
		subRate2.setRateDescription("Mobile Voice Data Service");
		subRate2.setRatePrice(new BigDecimal(10.00));
		subRate2.setPriority(4);
		subRate2.setFreeCharge(false);
		serviceRates2.add(subRate2);
		
		mVoicePlan1 = new MobileVoicePlan();
		mVoicePlan1.setPlanId("MVCode1");
		mVoicePlan1.setPlanCode("MVCode1");
		mVoicePlan1.setStatus(ConstantStatus.SERVICEPLAN_ACTIVE);
		mVoicePlan1.setPlanName("Mobile Voice Plan 1");
		mVoicePlan1.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		mVoicePlan1.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		mVoicePlan1.setServiceRates(serviceRates1);
		mVoicePlan1.setSim("sim3"); 
		mVoicePlan1.setRegisteredPhoneNumber("82781903");
		
		mVoicePlan2 = new MobileVoicePlan();
		mVoicePlan2.setPlanId("MVCode2");
		mVoicePlan2.setPlanCode("MVCode2");
		mVoicePlan2.setStatus(ConstantStatus.SERVICEPLAN_ACTIVE);
		mVoicePlan2.setPlanName("Mobile Voice Plan 2");
		mVoicePlan2.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		mVoicePlan2.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		mVoicePlan2.setServiceRates(serviceRates2);
		mVoicePlan2.setSim("sim4");
		mVoicePlan2.setRegisteredPhoneNumber("82539011");
	}
	
	@After
	public void tearDown() throws Exception {
		mVoicePlan1 = null;
		mVoicePlan2 = null;
	}
	
		
	@Test
	public void testgetPlanId(){
		assertNotNull(mVoicePlan1);
		assertNotNull(mVoicePlan2);
		assertEquals(mVoicePlan1.getPlanId(),"MVCode1");
		assertEquals(mVoicePlan2.getPlanId(),"MVCode2");
	}

	
	@Test
	public void testgetPlanCode(){
		assertEquals(mVoicePlan1.getPlanCode(),"MVCode1");
		assertEquals(mVoicePlan2.getPlanCode(),"MVCode2");
	}
	
	@Test
	public void testgetStatus(){
		assertEquals(mVoicePlan1.getStatus(),ConstantStatus.SERVICEPLAN_ACTIVE);
		assertEquals(mVoicePlan2.getStatus(),ConstantStatus.SERVICEPLAN_ACTIVE);
	}
	
	@Test
	public void testgetPlanName(){
		assertEquals(mVoicePlan1.getPlanName(),"Mobile Voice Plan 1");
		assertEquals(mVoicePlan2.getPlanName(),"Mobile Voice Plan 2");
	}
	
	@Test
	public void testgetStartDate(){
		assertEquals(mVoicePlan1.getStartDate(),OneHashDateUtil.getDate(2011, 1, 1));
		assertEquals(mVoicePlan2.getStartDate(),OneHashDateUtil.getDate(2011, 1, 1));
	}
	
	@Test
	public void testgetEndDate(){
		assertEquals(mVoicePlan1.getEndDate(),OneHashDateUtil.getDate(2012, 12, 31));
		assertEquals(mVoicePlan2.getEndDate(),OneHashDateUtil.getDate(2012, 12, 31));
	}
	
	@Test
	public void testgetDeletedDate(){
		assertEquals(mVoicePlan1.getDeletedDate(),OneHashDateUtil.getDate());
		assertEquals(mVoicePlan2.getDeletedDate(),OneHashDateUtil.getDate());
	}
	
	@Test 
	public void testgetSim(){
		assertEquals(mVoicePlan1.getSim(),"sim3");
		assertEquals(mVoicePlan2.getSim(),"sim4");
	}
	
	@Test 
	public void testgetRegisteredPhoneNumber(){
		assertEquals(mVoicePlan1.getRegisteredPhoneNumber(),"82781903");
		assertEquals(mVoicePlan2.getRegisteredPhoneNumber(),"82539011");
	}
	
	@Test
	public void testgetServiceRates(){
		assertEquals(mVoicePlan2.getServiceRates().size(),2);
		assertEquals(mVoicePlan2.getServiceRates().get(0).getRateCode(),"MV-S");
		assertEquals(mVoicePlan2.getServiceRates().get(0).getRateDescription(),"Mobile Voice Subscription");
		assertEquals(mVoicePlan2.getServiceRates().get(0).getRatePrice(),new BigDecimal(45.00));
		assertEquals(mVoicePlan2.getServiceRates().get(0).getPriority(),3);
		assertEquals(mVoicePlan2.getServiceRates().get(1).getRateCode(),"MV-D");
		assertEquals(mVoicePlan2.getServiceRates().get(1).getRateDescription(),"Mobile Voice Data Service");
		assertEquals(mVoicePlan2.getServiceRates().get(1).getRatePrice(),new BigDecimal(10.00));
		assertEquals(mVoicePlan2.getServiceRates().get(1).getPriority(),4);
		
		assertTrue(mVoicePlan2.getServiceRates().get(0).getRateCode().equals(mVoicePlan1.getServiceRates().get(0).getRateCode()));
		assertTrue(mVoicePlan2.getServiceRates().get(0).getRateDescription().equals(mVoicePlan1.getServiceRates().get(0).getRateDescription()));
		assertTrue(mVoicePlan2.getServiceRates().get(0).getRatePrice().equals(mVoicePlan1.getServiceRates().get(0).getRatePrice()));
   }
}
