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

import java.util.ArrayList;
import java.util.List;

import com.onehash.constant.ConstantStatus;
import com.onehash.enumeration.EnumUserAccess;
import com.onehash.model.base.BaseEntity;
import com.onehash.model.scalar.MenuScalar;
import com.onehash.utility.OneHashStringUtil;

@SuppressWarnings("serial")
public class User extends BaseEntity{
	
	private long userId = 0L;
	public long getUserId() {return userId;}
	public void setUserId(long userId) {this.userId = userId;}

	private String userName;
	public String getUserName() {return userName;}
	public void setUserName(String userName) {this.userName = userName;}

	private String firstName;
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	
	private String lastName;
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getFullName() {return firstName + " " + lastName;}
   
	private String password;
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	private String userRole;
	public String getUserRole() {return userRole;}
	public void setUserRole(String userRole) {this.userRole = userRole;}

	private String status = ConstantStatus.ACTIVE;
	public boolean isActivated(){return ConstantStatus.ACTIVE.equals(this.status);}
	public void setStatus(boolean status){this.status = (status) ? ConstantStatus.ACTIVE : ConstantStatus.DEACTIVATE;}
	
	private List<EnumUserAccess> userAccesses = new ArrayList<EnumUserAccess>();
	public List<EnumUserAccess> getUserAccesses() {return userAccesses;}
	public void setUserAccesses(List<EnumUserAccess> userAccesses) {this.userAccesses = userAccesses;}
	public String getUserAccessAsString(){
		List<String> accesses = new ArrayList<String>();
		for(EnumUserAccess enumUserAccess : userAccesses){
			accesses.add(enumUserAccess.getDescription());
		}
		return OneHashStringUtil.join(accesses, " , ");
	}
	public boolean hasRights(EnumUserAccess... accesses){
		if(accesses == null)
			throw new IllegalArgumentException("User.hasRights :: Accesses cant be null");
		
		for(EnumUserAccess access : accesses){
			if(this.getUserAccesses().contains(access))
				return true;
		}
		return false;
	}
	
	public User(){}
	
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("User {");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", userRole='").append(userRole).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
    
    //public boolean isValidPassword(String password) {return this.getPassword().equals(password); }
   // public boolean isActive() {return ConstantStatus.ACTIVE.equals(this.status);}
    
   public MenuScalar getAvailableMenu(){
	   return null;
   };
}
