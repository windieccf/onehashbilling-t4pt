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

package test.onehash.model.usage;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.model.usage.TalkTimeUsage;
import com.onehash.utility.OneHashDateUtil;

public class TalkTimeUsageTest extends TestCase {
    private TalkTimeUsage tUsage1 = null;
	private TalkTimeUsage tUsage2 = null;
	
	@Before
	public void setUp() throws Exception {
		tUsage1 = new TalkTimeUsage();
		tUsage1.setCallNumber("84553321");
		tUsage1.setCallTime(OneHashDateUtil.getDate(2012, 3, 13));
		tUsage1.setUsageDuration(new Long(911));
		tUsage1.setUsageType("Second");
		
		tUsage2 = new TalkTimeUsage();
		tUsage2.setCallNumber("85342345");
		tUsage2.setCallTime(OneHashDateUtil.getDate(2012, 3, 25));
		tUsage2.setUsageDuration(new Long(443));
		tUsage2.setUsageType("Second");
	}
	
	@After
	public void tearDown() throws Exception {
		tUsage1 = null;
		tUsage2 = null;
	}
	
	@Test
	public void testgetCallTime(){
		assertNotNull(tUsage1);
		assertNotNull(tUsage2);
		assertEquals(tUsage1.getCallTime(),OneHashDateUtil.getDate(2012, 3, 13));
		assertEquals(tUsage2.getCallTime(),OneHashDateUtil.getDate(2012, 3, 25));
	}
	
	@Test
	public void testgetUsageDuration(){
		assertEquals(tUsage1.getUsageDuration(),new Long(911));
		assertEquals(tUsage2.getUsageDuration(),new Long(443));
	}
	
	@Test
	public void testgetCallNumber(){
		assertEquals(tUsage1.getCallNumber(),"84553321");
		assertEquals(tUsage2.getCallNumber(),"85342345");
	}
	
	@Test
	public void testgetUsageType(){
		assertEquals(tUsage1.getUsageType(),"Second");
		assertEquals(tUsage2.getUsageType(),"Second");
	}
}
