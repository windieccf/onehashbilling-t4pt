package com.onehash.model.service.plan;

@SuppressWarnings("serial")
public class CableTvPlan extends ServicePlan{

	private int waiveCount;
	public int getWaiveCount() {return waiveCount;}
	public void setWaiveCount(int waiveCount) {this.waiveCount = waiveCount;}

	@Override
	public void calculateBill() {
	}

}
