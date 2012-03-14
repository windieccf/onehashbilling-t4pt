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
 * 14 March 2012    Mansoor M I	    0.1				Class creating												
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.user;

import java.util.List;

import com.onehash.constant.ConstantStatus;

public abstract class User {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String password;
	private String memberOf;
	private ConstantStatus status;
	
	public String getUserid() {
		return userId;
	}
	
	public void setUserid(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ConstantStatus getStatus() {
		return status;
	}
	
	public void setStatus(ConstantStatus status) {
		this.status = status;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastname(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }
    
//    public List<String> getAvailableMenu() {
//    	
//    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("User");
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", memberOf='").append(memberOf).append('\'');
        sb.append('}');
        return sb.toString();
    }    
}
