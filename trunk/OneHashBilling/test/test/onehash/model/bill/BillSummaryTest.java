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

import com.onehash.model.bill.BillSummary;

public class BillSummaryTest extends TestCase {
    private BillSummary bSummary1 = null;
	private BillSummary bSummary2= null;
	
	@Before
	public void setUp() throws Exception {
		bSummary1 = new BillSummary("MV-L",new BigDecimal(53.50));
		bSummary2 = new BillSummary("MV-R",new BigDecimal(100.00));
	}
	
	@After
	public void tearDown() throws Exception {
		bSummary1 = null;
		bSummary2 = null;
	}
	
	@Test
	public void testBillDetail() {
		assertNotNull(bSummary1);
		assertNotNull(bSummary2);
		assertNotSame(bSummary1,new BillSummary("MV-L",new BigDecimal(53.50)));
		
		//Check individual attribute 
		assertEquals(bSummary1.getDescription(),"MV-L");
		assertEquals(bSummary1.getTotal(),new BigDecimal(53.50));
	}
	
	@Test
	public void testGetDescription(){
		assertEquals(bSummary2.getDescription(),"MV-R");
	}
	
	@Test
	public void testGetTotal(){
		assertEquals(bSummary2.getTotal(),new BigDecimal(100.00));
	}
}
