package com.onehash.enumeration;

public enum EnumComplaint {
	STS_ACTIVE("A","Active"),
	STS_CLOSED("C", "Closed"),
	STS_PENDING("P","Pending"),
	STS_FOLLOW_UP("F","Follow up");
	
	private String label;
	public String getLabel(){return this.label;}
	
	private String status;
	public String getStatus(){return this.status;}

	private EnumComplaint(String status, String label){
		this.status = status;
		this.label = label;
	}
	
	public static EnumComplaint getEnumByStatus(String status){
		for(EnumComplaint enumComplaint : EnumComplaint.values()){
			if(enumComplaint.getStatus().equalsIgnoreCase(status))
				return enumComplaint;
		}
		return null;
		
	}
	
}
