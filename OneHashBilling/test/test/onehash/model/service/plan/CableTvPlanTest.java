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
import com.onehash.model.service.plan.CableTvPlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.utility.OneHashDateUtil;

public class CableTvPlanTest extends TestCase {
    private CableTvPlan cTVPlan1 = null;
	private CableTvPlan cTVPlan2 = null;
	
	@Before
	public void setUp() throws Exception {
		
		List<ServiceRate> serviceRates1 = new ArrayList<ServiceRate>();
		List<ServiceRate> serviceRates2 = new ArrayList<ServiceRate>();
		
		SubscriptionRate subRate1 = new SubscriptionRate();
		subRate1 = new SubscriptionRate();
		subRate1.setRateCode("TV-S");
		subRate1.setRateDescription("TV Subscription");
		subRate1.setRatePrice(new BigDecimal(60.00));
		subRate1.setPriority(1);
		subRate1.setFreeCharge(false);
		serviceRates1.add(subRate1);
		serviceRates2.add(subRate1);
		
		SubscriptionRate subRate2 = new SubscriptionRate();
		subRate2 = new SubscriptionRate();
		subRate2.setRateCode("TV-C");
		subRate2.setRateDescription("TV Per Channel");
		subRate2.setRatePrice(new BigDecimal(20.00));
		subRate2.setPriority(2);
		subRate2.setFreeCharge(false);
		serviceRates2.add(subRate2);
		
		cTVPlan1 = new CableTvPlan();
		cTVPlan1.setPlanId("CTVCode1");
		cTVPlan1.setPlanCode("CTVCode1");
		cTVPlan1.setStatus(ConstantStatus.SERVICEPLAN_DELETED);
		cTVPlan1.setPlanName("Cabel TV Plan 1");
		cTVPlan1.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		cTVPlan1.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		cTVPlan1.setDeletedDate(OneHashDateUtil.getDate(2012, 10, 31));
		cTVPlan1.setServiceRates(serviceRates1);
		cTVPlan1.setWaiveCount(0);
		
		cTVPlan2 = new CableTvPlan();
		cTVPlan2.setPlanId("CTVCode2");
		cTVPlan2.setPlanCode("CTVCode2");
		cTVPlan2.setStatus(ConstantStatus.SERVICEPLAN_ACTIVE);
		cTVPlan2.setPlanName("Cabel TV Plan 2");
		cTVPlan2.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		cTVPlan2.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		cTVPlan2.setServiceRates(serviceRates2);
		cTVPlan2.setWaiveCount(1);
	}
	
	@After
	public void tearDown() throws Exception {
		cTVPlan1 = null;
		cTVPlan2 = null;
	}
	
		
	@Test
	public void testGetPlanId(){
		assertNotNull(cTVPlan1);
		assertNotNull(cTVPlan2);
		assertEquals(cTVPlan1.getPlanId(),"CTVCode1");
		assertEquals(cTVPlan2.getPlanId(),"CTVCode2");
	}

	
	@Test
	public void testGetPlanCode(){
		assertEquals(cTVPlan1.getPlanCode(),"CTVCode1");
		assertEquals(cTVPlan2.getPlanCode(),"CTVCode2");
	}
	
	@Test
	public void testGetStatus(){
		assertEquals(cTVPlan1.getStatus(),ConstantStatus.SERVICEPLAN_DELETED);
		assertEquals(cTVPlan2.getStatus(),ConstantStatus.SERVICEPLAN_ACTIVE);
	}
	
	@Test
	public void testGetPlanName(){
		assertEquals(cTVPlan1.getPlanName(),"Cabel TV Plan 1");
		assertEquals(cTVPlan2.getPlanName(),"Cabel TV Plan 2");
	}
	
	@Test
	public void testGetStartDate(){
		assertEquals(cTVPlan1.getStartDate(),OneHashDateUtil.getDate(2011, 1, 1));
		assertEquals(cTVPlan2.getStartDate(),OneHashDateUtil.getDate(2011, 1, 1));
	}
	
	@Test
	public void testGetEndDate(){
		assertEquals(cTVPlan1.getEndDate(),OneHashDateUtil.getDate(2012, 12, 31));
		assertEquals(cTVPlan2.getEndDate(),OneHashDateUtil.getDate(2012, 12, 31));
	}
	
	@Test
	public void testGetDeletedDate(){
		assertEquals(cTVPlan1.getDeletedDate(),OneHashDateUtil.getDate(2012, 10, 31));
		assertEquals(cTVPlan2.getDeletedDate(),OneHashDateUtil.getDate());
	}
	
	@Test
	public void testGetServiceRates(){
		assertEquals(cTVPlan2.getServiceRates().size(),2);
		assertEquals(cTVPlan2.getServiceRates().get(0).getRateCode(),"TV-S");
		assertEquals(cTVPlan2.getServiceRates().get(0).getRateDescription(),"TV Subscription");
		assertEquals(cTVPlan2.getServiceRates().get(0).getRatePrice(),new BigDecimal(60.00));
		assertEquals(cTVPlan2.getServiceRates().get(0).getPriority(),1);
		assertEquals(cTVPlan2.getServiceRates().get(1).getRateCode(),"TV-C");
		assertEquals(cTVPlan2.getServiceRates().get(1).getRateDescription(),"TV Per Channel");
		assertEquals(cTVPlan2.getServiceRates().get(1).getRatePrice(),new BigDecimal(20.00));
		assertEquals(cTVPlan2.getServiceRates().get(1).getPriority(),2);
		
		assertTrue(cTVPlan2.getServiceRates().get(0).getRateCode().equals(cTVPlan1.getServiceRates().get(0).getRateCode()));
		assertTrue(cTVPlan2.getServiceRates().get(0).getRateDescription().equals(cTVPlan1.getServiceRates().get(0).getRateDescription()));
		assertTrue(cTVPlan2.getServiceRates().get(0).getRatePrice().equals(cTVPlan1.getServiceRates().get(0).getRatePrice()));
   }
}
