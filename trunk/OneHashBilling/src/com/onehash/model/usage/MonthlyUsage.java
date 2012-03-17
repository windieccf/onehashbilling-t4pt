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
 * 13 March 2012    Aman Sharma	    0.1				Class creating												
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.usage;

import java.math.BigDecimal;
import java.util.List;

import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public abstract class MonthlyUsage extends BaseEntity{
	
	private String usageYearMonth;
	
	private List<TalkTimeUsage> talkTimeUsages;

	public String getUsageYearMonth() {
		return usageYearMonth;
	}

	public void setUsageYearMonth(String usageYearMonth) {
		this.usageYearMonth = usageYearMonth;
	}

	public List<TalkTimeUsage> getTalkTimeUsages() {
		return talkTimeUsages;
	}

	public void setTalkTimeUsages(List<TalkTimeUsage> talkTimeUsages) {
		this.talkTimeUsages = talkTimeUsages;
	}
	
	public BigDecimal getTotalLocalCallUsage(){
		try{
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return null;
	}
	
	public BigDecimal getTotalddUsage(){
		try{
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return null;
	}
	
	public BigDecimal getTotalRoamingUsage(){
		try{
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return null;
	}
	
	public List<TalkTimeUsage> getLocalUsages(){
		try{
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return null;
	}
	
	public List<TalkTimeUsage> getIddUsages(){
		try{
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return null;
	}
	
	public List<TalkTimeUsage> getRoamingusages(){
		try{
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return null;
	}
}
