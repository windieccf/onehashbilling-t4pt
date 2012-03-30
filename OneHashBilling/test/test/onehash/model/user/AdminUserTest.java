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
 * 29 March 2012    Yue Yang	    0.2			    User constant for users' status
 * 																										
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.model.user;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.model.user.AdminUser;
import com.onehash.constant.ConstantStatus;

public class AdminUserTest extends TestCase {
    private AdminUser admin1 = null;
	private AdminUser admin2 = null;
	
	@Before
	public void setUp() throws Exception {
		admin1 = new AdminUser("admin1","Jolin","Wong","23479013",ConstantStatus.ACTIVE);
		admin2 = new AdminUser("admin2","Meiqi","Chen","56895467",ConstantStatus.DEACTIVATE);
	}
	
	@After
	public void tearDown() throws Exception {
		admin1 = null;
		admin2 = null;
	}
	
	@Test
	public void testAgentUser() {
		assertNotNull(admin1);
		assertNotNull(admin2);
		assertNotSame(admin1,new AdminUser("admin1","Jolin","Wong","23479013",ConstantStatus.ACTIVE));
		
		//Check individual attribute 
		assertEquals(admin1.getUserid(),"admin1");
		assertEquals(admin1.getFirstName(),"Jolin");
		assertEquals(admin1.getLastName(),"Wong");
		assertEquals(admin1.getPassword(),"23479013");
		assertEquals(admin1.getMemberOf(),"Admin");
		assertEquals(admin1.getStatus(),ConstantStatus.ACTIVE);
		assertTrue(admin1.getMemberOf().equals(admin2.getMemberOf()));
	}
	
	@Test
	public void testGetUserid(){
		assertEquals(admin2.getUserid(),"admin2");
	}
	
	@Test
	public void testGetFirstName(){
		assertEquals(admin2.getFirstName(),"Meiqi");
	}
	
	@Test
	public void testGetLastName(){
		assertEquals(admin2.getLastName(),"Chen");
	}
	
	@Test
	public void testGetPassword(){
	    assertEquals(admin2.getPassword(),"56895467");
	}
	
	@Test
	public void testGetMemberOf(){
		assertEquals(admin2.getMemberOf(),"Admin");
	}
	
	@Test
	public void testGetStatus(){
		assertEquals(admin2.getStatus(),ConstantStatus.DEACTIVATE);
	}
	
	@Test
	public void testGetAvailableMenu(){
		assertEquals(admin2.getAvailableMenu().size(),11);
		assertEquals(admin2.getAvailableMenu().get(0),"Add Customer");
		assertEquals(admin2.getAvailableMenu().get(1),"Update Customer");
		assertEquals(admin2.getAvailableMenu().get(2),"Delete Customer");
		assertEquals(admin2.getAvailableMenu().get(3),"Add Subscription Plan");
		assertEquals(admin2.getAvailableMenu().get(4),"Update Subscription Plan");
		assertEquals(admin2.getAvailableMenu().get(5),"Delete Subscription Plan");
		assertEquals(admin2.getAvailableMenu().get(6),"Add Subscription Plan For Customer");
		assertEquals(admin2.getAvailableMenu().get(7),"Update Subscription Plan For Customer");
		assertEquals(admin2.getAvailableMenu().get(8),"Delete Subscription Plan For Customer");
		assertEquals(admin2.getAvailableMenu().get(9),"Add Complaint");
		assertEquals(admin2.getAvailableMenu().get(10),"Update Complaint");
		
	}
	
	@Test
	public void testIsValidPassword(){
		assertEquals(admin2.isValidPassword("56895467"),true);
		assertEquals(admin2.isValidPassword("23459045"),false);
	}
}
