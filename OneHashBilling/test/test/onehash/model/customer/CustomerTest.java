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

package test.onehash.model.customer;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.onehash.model.customer.Customer;

public class CustomerTest extends TestCase {
    private Customer cus1 = null;
	private Customer cus2 = null;
	
	@Before
	public void setUp() throws Exception {
		cus1 = new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68");
		cus2 = new Customer("Clio York","S1026829Y","98056 East Comoros Ave.","92758240","SA-0220-48-6");
	}
	
	@After
	public void tearDown() throws Exception {
		cus1 = null;
		cus2 = null;
	}
	
	@Test
	public void testCustomer() {
		assertNotNull(cus1);
		assertNotNull(cus2);
		assertEquals(cus1,new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68"));
		
		//Check individual attribute 
		assertEquals(cus1.getName(),"Colby Mosley");
		assertEquals(cus1.getNric(),"S4136327D");
		assertEquals(cus1.getAddress(),"17784 South Guyana Blvd.");
		assertEquals(cus1.getPhoneNumber(),"87529248");
		assertEquals(cus1.getAccountNumber(),"SA-0055-36-68");
	}
	
	@Test
	public void testgetName(){
		assertEquals(cus2.getName(),"Clio York");
	}
	
	@Test
	public void testgetNric(){
		assertEquals(cus2.getNric(),"S1026829Y");
	}
	
	@Test
	public void testgetAddress(){
		assertEquals(cus2.getAddress(),"98056 East Comoros Ave.");
	}
	
	@Test
	public void testgetPhoneNumber(){
	    assertEquals(cus2.getPhoneNumber(),"92758240");
	}
	
	@Test
	public void testgetAccountNumber(){
		assertEquals(cus2.getAccountNumber(),"SA-0220-48-6");
	}
	
	@Test
	public void testisActivated(){
		assertEquals(cus2.isActivated(),true);
	}
}
