package com.onehash.enumeration;

import com.onehash.view.panel.complaint.ComplaintListPanel;
import com.onehash.view.panel.customer.CustomerEditPanel;
import com.onehash.view.panel.customer.CustomerListPanel;

public enum EnumUserAccess {
	CUSTOMER_VIEW("CUSTV", "Customer View",CustomerListPanel.class),
	CUSTOMER_UPDATE("CUSTU", "Customer Update",CustomerEditPanel.class),
	
	SERVICE_PLAN_VIEW("SVPLV", "Service Plan View",null),
	SERVICE_PLAN_UPDATE("SVPLU", "Service Plan Update",null),
	
	COMPLAINT_VIEW("COMPV", "Complaint View",ComplaintListPanel.class),
	COMPLAINT_UPDATE("COMPU" , "Complaint Update", null);
	
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
