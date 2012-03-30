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
 * 													
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.model.usage;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.model.usage.MonthlyUsage;
import com.onehash.model.usage.TalkTimeUsage;
import com.onehash.utility.OneHashDateUtil;

public class MonthlyUsageTest extends TestCase {
    private MonthlyUsage mUsage1 = null;
	
	@Before
	public void setUp() throws Exception {
		List<TalkTimeUsage> tUsageList = new ArrayList<TalkTimeUsage>();
		TalkTimeUsage tUsage1 = new TalkTimeUsage();
		tUsage1.setCallNumber("84553321");
		tUsage1.setCallTime(OneHashDateUtil.getDate(2012, 3, 13));
		tUsage1.setUsageDuration(new Long(911));
		tUsage1.setUsageType("Second");
		tUsageList.add(tUsage1);
		
		TalkTimeUsage tUsage2 = new TalkTimeUsage();
		tUsage2 = new TalkTimeUsage();
		tUsage2.setCallNumber("85342345");
		tUsage2.setCallTime(OneHashDateUtil.getDate(2012, 3, 25));
		tUsage2.setUsageDuration(new Long(443));
		tUsage2.setUsageType("Second");
		tUsageList.add(tUsage2);
		
		mUsage1 = new MonthlyUsage();
		mUsage1.setUsageYearMonth("201203");
		mUsage1.setTalkTimeUsages(tUsageList);
	}
	
	@After
	public void tearDown() throws Exception {
		mUsage1 = null;
	}
	
	@Test
	public void testGetUsageYearMonth(){
		assertNotNull(mUsage1);
		assertEquals(mUsage1.getUsageYearMonth(),"201203");
	}
	
	@Test
	public void testGetTalkTimeUsages(){
		assertEquals(mUsage1.getTalkTimeUsages().get(0).getCallNumber(),"84553321");
		assertEquals(mUsage1.getTalkTimeUsages().get(0).getCallTime(),OneHashDateUtil.getDate(2012, 3, 13));
		assertEquals(mUsage1.getTalkTimeUsages().get(0).getUsageDuration(),new Long(911));
		assertEquals(mUsage1.getTalkTimeUsages().get(0).getUsageType(),"Second");
		assertEquals(mUsage1.getTalkTimeUsages().get(1).getCallNumber(),"85342345");
		assertEquals(mUsage1.getTalkTimeUsages().get(1).getCallTime(),OneHashDateUtil.getDate(2012, 3, 25));
		assertEquals(mUsage1.getTalkTimeUsages().get(1).getUsageDuration(),new Long(443));
		assertEquals(mUsage1.getTalkTimeUsages().get(1).getUsageType(),"Second");
	}
	
	@Test
	public void testGetCallUsages(){
		assertEquals(mUsage1.getCallUsages("Second"),new Long(1354));
		assertFalse(mUsage1.getCallUsages("Second").equals(new Long(1200)));
	}
	
	@Test
	public void testGetUsages(){
		assertEquals(mUsage1.getUsages("Second").get(0).getCallNumber(),"84553321");
		assertEquals(mUsage1.getUsages("Second").get(0).getCallTime(),OneHashDateUtil.getDate(2012, 3, 13));
		assertEquals(mUsage1.getUsages("Second").get(0).getUsageDuration(),new Long(911));
		assertEquals(mUsage1.getUsages("Second").get(0).getUsageType(),"Second");
		assertEquals(mUsage1.getUsages("Second").get(1).getCallNumber(),"85342345");
		assertEquals(mUsage1.getUsages("Second").get(1).getCallTime(),OneHashDateUtil.getDate(2012, 3, 25));
		assertEquals(mUsage1.getUsages("Second").get(1).getUsageDuration(),new Long(443));
		assertEquals(mUsage1.getUsages("Second").get(1).getUsageType(),"Second");
	}

}
