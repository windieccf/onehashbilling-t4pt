package com.onehash.model.service.rate;

import java.math.BigDecimal;

import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public abstract class ServiceRate extends BaseEntity{
	
	private String rateCode;
	public String getRateCode() {return rateCode;}
	public void setRateCode(String rateCode) {this.rateCode = rateCode;}
	
	private String rateDescription;
	public String getRateDescription() {return rateDescription;}
	public void setRateDescription(String rateDescription) {this.rateDescription = rateDescription;}
	
	private BigDecimal ratePrice;
	public BigDecimal getRatePrice() {return ratePrice;}
	public void setRatePrice(BigDecimal ratePrice) {this.ratePrice = ratePrice;}
	
	private int priority;
	public int getPriority() {return priority;}
	public void setPriority(int priority) {this.priority = priority;}
	
	private boolean freeCharge = false;
	public boolean isFreeCharge() {return freeCharge;}
	public void setFreeCharge(boolean freeCharge) {this.freeCharge = freeCharge;}
	
	public abstract BigDecimal calculateMonthlyRate();

}
