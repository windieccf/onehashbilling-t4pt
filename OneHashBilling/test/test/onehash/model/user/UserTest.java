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
 * 22 March 2012    Mansoor M I	    0.1				Class creating												
 * 29 March 2012	Yue Yang		0.2				Modify the import class						
 * 													
 * 													
 * 
 */
package test.onehash.model.user;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.model.user.User;

public class UserTest extends TestCase{
	User user1 = new User();
	User user2 = new User();
	
	@Before
	public void setUp() throws Exception {
		user1.setUserName("admin");
		user1.setFirstName("PT 4 Admin");
		user1.setLastName("PT 4");
		user1.setPassword("password");
		user1.setUserRole("admin");
		user1.setStatus(true);	
		
		user2.setUserName("agent");
		user2.setFirstName("PT 4 Agent");
		user2.setLastName("PT 4");
		user2.setPassword("password");
		user2.setUserRole("agent");
		user2.setStatus(true);
	}
	
	@After
	public void tearDown() throws Exception {
		user1 = null;
		user2 = null;
	}
	
	@Test
	public void testGetUserName(){
		assertEquals(user1.getUserName(),"admin");
		assertEquals(user2.getUserName(),"agent");
	}
	
	@Test
	public void testGetFirstName(){
		assertEquals(user1.getFirstName(),"PT 4 Admin");
		assertEquals(user2.getFirstName(),"PT 4 Agent");
	}
	
	@Test
	public void testGetLastName(){
		assertEquals(user1.getLastName(),"PT 4");
		assertEquals(user2.getLastName(),"PT 4");
	}
	
	@Test
	public void testGetFullName(){
		assertEquals(user1.getFullName(),"PT 4 Admin PT 4");
		assertEquals(user2.getFullName(),"PT 4 Agent PT 4");
	}
	
	@Test
	public void testGetPassworde(){
		assertEquals(user1.getPassword(),"password");
		assertEquals(user2.getPassword(),"password");
	}
	
	@Test
	public void testGetUserRole(){
		assertEquals(user1.getUserRole(),"admin");
		assertEquals(user2.getUserRole(),"agent");
	}
	
	@Test
	public void testIsActivated(){
		assertEquals(user1.isActivated(),true);
		assertEquals(user2.isActivated(),true);
	}
	
	@Test
	public void testToString(){
		assertEquals(user1.toString(),"User {userName='admin', firstName='PT 4 Admin', lastName='PT 4', password='password', userRole='admin', status='A'}");
		assertEquals(user2.toString(),"User {userName='agent', firstName='PT 4 Agent', lastName='PT 4', password='password', userRole='agent', status='A'}");
	}
}
