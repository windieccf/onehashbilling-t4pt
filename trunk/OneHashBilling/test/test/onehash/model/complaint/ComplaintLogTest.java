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
 * 10 March 2012    Chen Changfeng	0.1				Class creating
 * 													
 * 													
 * 													
 * 													
 * 
 */

package test.onehash.model.complaint;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.onehash.model.complaint.ComplaintLog;
import com.onehash.utility.OneHashDateUtil;

public class ComplaintLogTest {
	ArrayList<String> issueNoList = new ArrayList<String>();
	ArrayList<String> issueNoListPreSetting = new ArrayList<String>();
	ComplaintLog compLog1 = null;
	ComplaintLog compLog2 = null;
	ComplaintLog compLog3 = null;
	ComplaintLog compLog4 = null;

	@Before
	public void createComplaintLogissueNoList() {
		issueNoListPreSetting.add("COMP201203100000");

		compLog1 = new ComplaintLog("Complain 1", OneHashDateUtil.getDate(),
				issueNoListPreSetting);
		issueNoListPreSetting.add(compLog1.getIssueNo());
		compLog2 = new ComplaintLog("Complain 2", OneHashDateUtil.getDate(),
				issueNoListPreSetting);
		issueNoListPreSetting.add(compLog2.getIssueNo());
		compLog3 = new ComplaintLog("Complain 3", OneHashDateUtil.getDate(),
				issueNoListPreSetting);
		issueNoListPreSetting.add(compLog3.getIssueNo());
		compLog4 = new ComplaintLog("Complain 4", OneHashDateUtil.getDate(),
				issueNoListPreSetting);
		issueNoListPreSetting.add(compLog4.getIssueNo());

		issueNoList.add(compLog1.getIssueNo());
		issueNoList.add(compLog2.getIssueNo());
		issueNoList.add(compLog3.getIssueNo());
		issueNoList.add(compLog4.getIssueNo());

		for (String issueNo : issueNoList) {
			//TODO later need to remove
			System.out.println(issueNo);

		}
	}

	@Test
	public void testIssueNoListIsNotEmpty() {
		assertTrue(issueNoList.size() > 0);
	}

	@Test
	public void testMaxIssueNo() {
		assertEquals(compLog4.getIssueNo(), "COMP201203100004");
	}

	@Test
	public void testNextGeneratedIssueNo() {
		ComplaintLog compLog5 = new ComplaintLog("Complain 5",
				OneHashDateUtil.getDate(), issueNoListPreSetting);
		assertEquals(compLog5.getIssueNo(), "COMP201203100005");
	}
	
	@Test
	public void testIssueWithCorrectDateAndDescription() {
		//description equals
		assertEquals(compLog4.getIssueDescription(), "Complain 4");
		//date equals
		assertEquals(compLog4.getComplaintDate(), OneHashDateUtil.getDate());
	}

}
