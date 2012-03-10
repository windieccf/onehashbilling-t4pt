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
 * 10 March 2012    Robin Foe	    0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.model.complaint;

import java.util.ArrayList;
import java.util.Date;

import com.onehash.enumeration.EnumComplaint;
import com.onehash.model.base.BaseEntity;
import com.onehash.utility.OneHashDateUtil;
import com.onehash.utility.OneHashStringUtil;

@SuppressWarnings("serial")
public class ComplaintLog extends BaseEntity{
	
	public static final String COMPLAINT_PREFIX = "COMP";

	private String issueNo; // format as COMP201203100001
	private String issueDescription;
	private Date complaintDate;
	private Date closedDate;
	private EnumComplaint status;
	private String followUp;
	
	/**
	 * issueNo construct as COMPLAINT_PREFIX + complaintDateNo
	 * @param issueDescription
	 * @param complaintDate
	 * @param allIssueNoList
	 */
	public ComplaintLog(String issueDescription, Date complaintDate, ArrayList<String> allIssueNoList) {
		String complaintDateNo = null;
		String strDate = OneHashDateUtil.format(OneHashDateUtil.getDate(),
				"yyyyMMdd");
		long todayIssueNo = this.getLastComplaintNoUsedToday(strDate, allIssueNoList);

		complaintDateNo = getComplaintDateNo(complaintDateNo, strDate,
				todayIssueNo);

		this.setIssueNo(COMPLAINT_PREFIX + complaintDateNo);
		this.setComplaintDate(complaintDate);
		this.setIssueDescription(issueDescription);
	}
	
	public ComplaintLog () {
		//default constructor
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public EnumComplaint getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = EnumComplaint.getEnumByStatus(status);
	}

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}
	
	/**
	 * Retrieving today max issue number
	 * @param strDate
	 * @param AllIssueNoList
	 * @return current maximum issue number (last 4 digits)
	 */
	private long getLastComplaintNoUsedToday(String strDate, ArrayList<String> AllIssueNoList) {
		ArrayList<String> issueNoMaxList = new ArrayList<String>();
		if (AllIssueNoList != null && AllIssueNoList.size() > 0) {
			for (String issueNo : AllIssueNoList) {
				int issueNoIndex = issueNo.indexOf(strDate);
				if (issueNoIndex > 0) {
					issueNoMaxList.add(issueNo);
				}
			}
		}

		if (issueNoMaxList.size() == 0) {
			return -1;
		}

		long max = 0;

		for (String issueNoMax : issueNoMaxList) {
			String tempNum = issueNoMax.substring(12);
			if (max < Long.parseLong(tempNum)) {
				max = Long.parseLong(tempNum);
			}
		}
		return max;
	}

	/**
	 * Retrieving complaint Date Number
	 * @param complaintDateNo
	 * @param strDate
	 * @param todayIssueNo
	 * @return complaintDateNo, format as '201203100001'
	 */
	private String getComplaintDateNo(String complaintDateNo, String strDate,
			long todayIssueNo) {
		String[] strDateArr = null;
		if (todayIssueNo < 0) {
			strDateArr = OneHashDateUtil.parseDateToStr(OneHashDateUtil
					.getDate());

			if (strDateArr != null) {
				String sbDay = strDateArr[0];
				String sbMonth = strDateArr[1];
				String sbYear = strDateArr[2];

				complaintDateNo = OneHashStringUtil.rPad(strDate, 12, "0");
			}
		} else {
			long lTempNo = todayIssueNo + 1;
			String newTempNo = String.valueOf(lTempNo);
			newTempNo = OneHashStringUtil.lPad(newTempNo, 4, "0");
			complaintDateNo = strDate + newTempNo;
		}
		return complaintDateNo;
	}
	
}
