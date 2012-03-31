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
 * 20 March 2012    Robin Foe	    0.1				Initial Creation
 * 													
 * 													
 * 													
 * 
 */

package com.onehash.enumeration;

import com.onehash.view.panel.bill.BillListPanel;
import com.onehash.view.panel.bill.BillReportPanel;
import com.onehash.view.panel.complaint.ComplaintListPanel;
import com.onehash.view.panel.customer.CustomerEditPanel;
import com.onehash.view.panel.customer.CustomerListPanel;
import com.onehash.view.panel.payment.PaymentPanel;
import com.onehash.view.panel.subscription.SubscriptionPanel;
import com.onehash.view.panel.user.UserEditPanel;
import com.onehash.view.panel.user.UserListPanel;

public enum EnumUserAccess {
	USER_VIEW("USERV", "User View",UserListPanel.class),
	USER_UPDATE("USERU", "User Update",UserEditPanel.class),
	
	
	CUSTOMER_VIEW("CUSTV", "Customer View",CustomerListPanel.class),
	CUSTOMER_UPDATE("CUSTU", "Customer Update",CustomerEditPanel.class),
	
	SERVICE_PLAN_VIEW("SVPLV", "Service Plan View",SubscriptionPanel.class),
	SERVICE_PLAN_UPDATE("SVPLU", "Service Plan Update",SubscriptionPanel.class),
	
	COMPLAINT_VIEW("COMPV", "Complaint View",ComplaintListPanel.class),
	COMPLAINT_UPDATE("COMPU" , "Complaint Update", null),
	
	BILL_VIEW("BILLV", "Bill View",BillListPanel.class),
	BILL_GENERATE("BILLG", "Bill Generate",null),
	REPORT_VIEW("REPTV" , "Report View", BillReportPanel.class),
	
	PAYMENT_VIEW("PAYV", "Payment View",PaymentPanel.class),
	PAYMENT_ADD("PAYA", "Payment Add",PaymentPanel.class),
	
	MASTER_SERVICE_PLAN_VIEW("MSVPLV", "Master Service Plan View",SubscriptionPanel.class),
	MASTER_SERVICE_PLAN_UPDATE("MSVPLU", "Master Service Plan Update",SubscriptionPanel.class);
	
	
	private String code;
	public String getCode() {return code;}
	
	private String description;
	public String getDescription() {return description;}
	
	private Class<?> klass;
	public Class<?> getKlass() {return klass;}

	private EnumUserAccess(String code, String description, Class<?> klass){
		this.code = code;
		this.description = description;
		this.klass = klass;
	}
	
	public static EnumUserAccess getEnumByCode(String code){
		for(EnumUserAccess enumUserAccess : EnumUserAccess.values()){
			if(enumUserAccess.getCode().equalsIgnoreCase(code))
				return enumUserAccess;
		}
		return null;
	}
	
	public static EnumUserAccess getEnumByDescription(String description){
		for(EnumUserAccess enumUserAccess : EnumUserAccess.values()){
			if(enumUserAccess.getDescription().equalsIgnoreCase(description))
				return enumUserAccess;
		}
		return null;
	}
	

}
