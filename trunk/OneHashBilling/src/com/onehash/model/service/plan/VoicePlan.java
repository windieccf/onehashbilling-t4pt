package com.onehash.model.service.plan;

@SuppressWarnings("serial")
public abstract class VoicePlan extends ServicePlan{

	private String sim;
	public String getSim() {return sim;}
	public void setSim(String sim) {this.sim = sim;}

	private String registeredPhoneNumber;
	public String getRegisteredPhoneNumber() {return registeredPhoneNumber;}
	public void setRegisteredPhoneNumber(String registeredPhoneNumber) {this.registeredPhoneNumber = registeredPhoneNumber;}
	
}
