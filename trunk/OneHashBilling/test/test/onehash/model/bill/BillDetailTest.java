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

import com.onehash.model.bill.BillDetail;
import com.onehash.utility.OneHashDateUtil;

public class BillDetailTest extends TestCase {
    private BillDetail bDetail1 = null;
	private BillDetail bDetail2 = null;
	
	@Before
	public void setUp() throws Exception {
		bDetail1 = new BillDetail();
		bDetail1.setPlanName("MV-L");
		bDetail1.setRate(new BigDecimal(0.5));
		bDetail1.setRateName("MV-L Rate");
		bDetail1.setUsageDuration(new Long(911));
		bDetail1.setUsageTime(OneHashDateUtil.getDate(2012, 2, 10));
        
        bDetail2 = new BillDetail();
        bDetail2.setPlanName("MV-R");
        bDetail2.setRate(new BigDecimal(0.5));
        bDetail2.setRateName("MV-R Rate");
        bDetail2.setUsageDuration(new Long(233));
        bDetail2.setUsageTime(OneHashDateUtil.getDate(2012, 3, 27));
	}
	
	@After
	public void tearDown() throws Exception {
		bDetail1 = null;
		bDetail2 = null;
	}
	
	@Test
	public void testgetPalnName(){
		assertNotNull(bDetail1);
		assertNotNull(bDetail2);
		assertNotSame(bDetail1,new BillDetail());
		assertEquals(bDetail1.getPalnName(),"MV-L");
		assertEquals(bDetail2.getPalnName(),"MV-R");
	}
	
	@Test
	public void testgetRate(){
		assertEquals(bDetail1.getRate(),new BigDecimal(0.5));
		assertEquals(bDetail2.getRate(),new BigDecimal(0.5));
	}
	
	@Test
	public void testgetRateName(){
		assertEquals(bDetail1.getRateName(),"MV-L Rate");
		assertEquals(bDetail2.getRateName(),"MV-R Rate");
	}
	
	@Test
	public void testgetUsageDuration(){
		assertEquals(bDetail1.getUsageDuration(),new Long(911));
	    assertEquals(bDetail2.getUsageDuration(),new Long(233));
	}
	
	@Test
	public void testgetUsageTime(){
		assertEquals(bDetail1.getUsageTime(),OneHashDateUtil.getDate(2012, 3, 10));
		assertEquals(bDetail2.getUsageTime(),OneHashDateUtil.getDate(2012, 3, 14));
	}
}
