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

import java.util.ArrayList;
import java.util.List;

import com.onehash.model.base.BaseEntity;

@SuppressWarnings("serial")
public class MonthlyUsage extends BaseEntity{
	
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
	
	/************************************ UTILITY ************************************************/
	
	public Long getCallUsages(String usageType){
		Long totalCallUasges = new Long(0);
		try{
			for(TalkTimeUsage _talkTimeUsage : this.getTalkTimeUsages()){
				if(_talkTimeUsage.getUsageType().equalsIgnoreCase(usageType))
					totalCallUasges = totalCallUasges + _talkTimeUsage.getUsageDuration();
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return totalCallUasges;
	}
		
	public List<TalkTimeUsage> getUsages(String usageType){
		List<TalkTimeUsage> talkTimeUsages = new ArrayList<TalkTimeUsage>();
		try{
			
			for(TalkTimeUsage _talkTimeUsage : this.getTalkTimeUsages()){
				if(_talkTimeUsage.getUsageType().equalsIgnoreCase(usageType))
					talkTimeUsages.add(_talkTimeUsage);
			}
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return talkTimeUsages;
	}
}
