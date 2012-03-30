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
 */

package test.onehash.model.user;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.model.user.AgentUser;
import com.onehash.constant.ConstantStatus;

public class AgentUserTest extends TestCase {
    private AgentUser agUser1 = null;
	private AgentUser agUser2 = null;
	
	@Before
	public void setUp() throws Exception {
		agUser1 = new AgentUser("agUser1","Cheryl","Lim","12345678",ConstantStatus.ACTIVE);
		agUser2 = new AgentUser("agUser2","Cindy","Tan","78901234",ConstantStatus.DEACTIVATE);
	}
	
	@After
	public void tearDown() throws Exception {
		agUser1 = null;
		agUser2 = null;
	}
	
	@Test
	public void testAgentUser() {
		assertNotNull(agUser1);
		assertNotNull(agUser2);
		assertNotSame(agUser1,new AgentUser("agUser1","Cheryl","Lim","12345678",ConstantStatus.ACTIVE));
		
		//Check individual attribute 
		assertEquals(agUser1.getUserid(),"agUser1");
		assertEquals(agUser1.getFirstName(),"Cheryl");
		assertEquals(agUser1.getLastName(),"Lim");
		assertEquals(agUser1.getPassword(),"12345678");
		assertEquals(agUser1.getMemberOf(),"Agent");
		assertEquals(agUser1.getStatus(),ConstantStatus.ACTIVE);
		assertTrue(agUser1.getMemberOf().equals(agUser2.getMemberOf()));
	}
	
	@Test
	public void testGetUserid(){
		assertEquals(agUser2.getUserid(),"agUser2");
	}
	
	@Test
	public void testGetFirstName(){
		assertEquals(agUser2.getFirstName(),"Cindy");
	}
	
	@Test
	public void testGetLastName(){
		assertEquals(agUser2.getLastName(),"Tan");
	}
	
	@Test
	public void testGetPassword(){
	    assertEquals(agUser2.getPassword(),"78901234");
	}
	
	@Test
	public void testGetMemberOf(){
		assertEquals(agUser2.getMemberOf(),"Agent");
	}
	
	@Test
	public void testGetStatus(){
		assertEquals(agUser2.getStatus(),ConstantStatus.DEACTIVATE);
	}
	
	@Test
	public void testGetAvailableMenu(){
		assertEquals(agUser2.getAvailableMenu().size(),3);
		assertEquals(agUser2.getAvailableMenu().get(0),"Add Complaint");
		assertEquals(agUser2.getAvailableMenu().get(1),"Update Complaint");
		assertEquals(agUser2.getAvailableMenu().get(2),"View Customer");
	}
	
	@Test
	public void testIsValidPassword(){
		assertEquals(agUser2.isValidPassword("78901234"),true);
		assertEquals(agUser2.isValidPassword("23459045"),false);
	}
}
