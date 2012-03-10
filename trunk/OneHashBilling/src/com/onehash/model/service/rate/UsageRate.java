package com.onehash.model.service.rate;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class UsageRate extends ServiceRate{

	private long rateUnit;
	public long getRateUnit() {return rateUnit;}
	public void setRateUnit(long rateUnit) {this.rateUnit = rateUnit;}

	@Override
	public BigDecimal calculateMonthlyRate() {
		return null;
	}

}
