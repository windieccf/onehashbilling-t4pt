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
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.user;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.onehash.constant.ConstantStatus;

public class UserTest {

	ArrayList<User> userList = new ArrayList<User>();
	User invalidUser = null;
	
	public UserTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void prepareUsers() {
		AdminUser adm1 = new AdminUser("admin1", "Aman", "Sharma", "password1", ConstantStatus.ACTIVE);
		AdminUser adm2 = new AdminUser("admin2", "Robin", "Foe", "password2", ConstantStatus.ACTIVE);
		AdminUser adm3 = new AdminUser("admin3", "Chang", "Feng", "password3", ConstantStatus.ACTIVE);
		
		AgentUser agnt1 = new AgentUser("agent1", "Mansoor", "M I", "password1", ConstantStatus.ACTIVE);
		AgentUser agnt2 = new AgentUser("agent2", "Srinivas", "A", "password2", ConstantStatus.ACTIVE);
		AgentUser agnt3 = new AgentUser("agent3", "Kenny", "Hartono", "password3", ConstantStatus.ACTIVE);
		AgentUser agnt4 = new AgentUser("agent4", "Becky", "Yue Yang", "password4", ConstantStatus.ACTIVE);
		AgentUser agnt5 = new AgentUser("agent5", "Tristone", "Song", "password5", ConstantStatus.ACTIVE);
		AgentUser agnt6 = new AgentUser("agent6", "Invalid", "Not Valid", "password123", ConstantStatus.DEACTIVATE);
		
		userList.add(adm1);
		userList.add(adm2);
		userList.add(adm3);
		userList.add(agnt1);
		userList.add(agnt2);
		userList.add(agnt3);
		userList.add(agnt4);
		userList.add(agnt5);
		//userList.add(agnt6);
		
		invalidUser = agnt6;
	}
	
	@Test
	public void testUserAvailableMenuIsNotEmpty() {
		
		System.out.println("running... testUserAvailableMenuIsNotEmpty()!");
		
		for (User user : userList) {

			//TODO later need to remove
			System.out.println(user);
			assertTrue("Menus are available!", user.getAvailableMenu().size() > 0);
		}
	}
	
	@Test
	public void testValidateUserPassword() {
		
		System.out.println("running... testValidateUserPassword()!");
		
		for (User user : userList) {

			//TODO later need to remove
			assertTrue(user.isValidPassword(user.getPassword()));
		}
	}

	@Test
	public void testValidateUser() {
		
		System.out.println("running... testValidateUser()!");
		
		for (User user : userList) {

			//TODO later need to remove
			assertTrue(user.getFullName() + " is Active?", user.isActive());
		}
		
		System.out.println(invalidUser);
		assertTrue(!invalidUser.isActive());
	}
}
