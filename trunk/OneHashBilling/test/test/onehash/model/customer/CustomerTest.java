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
 * 28 March 2012    Yue Yang	    0.1				Class creating
 * 29 March 2012    Yue Yang	    0.2			    Modify the class													
 * 02 April 2012    Yue Yang	    0.3				Add few more method												
 * 													
 * 													
 * 
 */

package test.onehash.model.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onehash.constant.ConstantStatus;
import com.onehash.model.bill.Bill;
import com.onehash.model.bill.BillDetail;
import com.onehash.model.bill.BillSummary;
import com.onehash.model.bill.PaymentDetail;
import com.onehash.model.complaint.ComplaintLog;
import com.onehash.model.customer.Customer;
import com.onehash.model.service.plan.CableTvPlan;
import com.onehash.model.service.plan.ServicePlan;
import com.onehash.model.service.rate.ServiceRate;
import com.onehash.model.service.rate.SubscriptionRate;
import com.onehash.utility.OneHashDateUtil;

public class CustomerTest extends TestCase {
    private Customer cus1 = null;
	private Customer cus2 = null;
	
	@Before
	public void setUp() throws Exception {
		cus1 = new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68");
		cus2 = new Customer("Clio York","S1026829Y","98056 East Comoros Ave.","92758240","SA-0220-48-6");
	}
	
	@After
	public void tearDown() throws Exception {
		cus1 = null;
		cus2 = null;
	}
	
	@Test
	public void testCustomer() {
		assertNotNull(cus1);
		assertNotNull(cus2);
		assertEquals(cus1,new Customer("Colby Mosley","S4136327D","17784 South Guyana Blvd.","87529248","SA-0055-36-68"));
		
		//Check individual attribute 
		assertEquals(cus1.getName(),"Colby Mosley");
		assertEquals(cus1.getNric(),"S4136327D");
		assertEquals(cus1.getAddress(),"17784 South Guyana Blvd.");
		assertEquals(cus1.getPhoneNumber(),"87529248");
		assertEquals(cus1.getAccountNumber(),"SA-0055-36-68");
	}
	
	@Test
	public void testGetName(){
		assertEquals(cus2.getName(),"Clio York");
	}
	
	@Test
	public void testGetNric(){
		assertEquals(cus2.getNric(),"S1026829Y");
	}
	
	@Test
	public void testGetAddress(){
		assertEquals(cus2.getAddress(),"98056 East Comoros Ave.");
	}
	
	@Test
	public void testGetPhoneNumber(){
	    assertEquals(cus2.getPhoneNumber(),"92758240");
	}
	
	@Test
	public void testGetAccountNumber(){
		assertEquals(cus2.getAccountNumber(),"SA-0220-48-6");
	}
	
	@Test
	public void testIsActivated(){
		assertEquals(cus2.isActivated(),true);
		cus2.setStatus(false);
		assertEquals(cus2.isActivated(),false);
	}
	
	@Test
	public void testGetServicePlan(){
		List<ServiceRate> serviceRates1 = new ArrayList<ServiceRate>();
		
		SubscriptionRate subRate1 = new SubscriptionRate();
		subRate1 = new SubscriptionRate();
		subRate1.setRateCode("TV-S");
		subRate1.setRateDescription("TV Subscription");
		subRate1.setRatePrice(new BigDecimal(60.00));
		subRate1.setPriority(1);
		subRate1.setFreeCharge(false);
		serviceRates1.add(subRate1);
	    
		CableTvPlan cTVPlan1 = new CableTvPlan();
		cTVPlan1.setPlanId("CTVCode1");
		cTVPlan1.setPlanCode("CTVCode1");
		cTVPlan1.setStatus(ConstantStatus.SERVICEPLAN_DELETED);
		cTVPlan1.setPlanName("Cabel TV Plan 1");
		cTVPlan1.setStartDate(OneHashDateUtil.getDate(2011, 1, 1));
		cTVPlan1.setEndDate(OneHashDateUtil.getDate(2012, 12, 31));
		cTVPlan1.setDeletedDate(OneHashDateUtil.getDate(2012, 10, 31));
		cTVPlan1.setServiceRates(serviceRates1);
		cTVPlan1.setWaiveCount(0);
		
		List<ServicePlan> servicePlans = new ArrayList<ServicePlan>();
		servicePlans.add(cTVPlan1);
		
		cus1.setServicePlans(servicePlans);
		
		assertEquals(cus1.getServicePlan("CTVCode1").getPlanName(),"Cabel TV Plan 1");
		assertEquals(cus1.getServicePlan("CTVCode1").getStatus(),ConstantStatus.SERVICEPLAN_DELETED);
		assertEquals(cus1.getServicePlan("CTVCode1").getStartDate(),OneHashDateUtil.getDate(2011, 1, 1));
		assertEquals(cus1.getServicePlan("CTVCode1").getEndDate(),OneHashDateUtil.getDate(2012, 12, 31));
		assertEquals(cus1.getServicePlan("CTVCode1").getDeletedDate(),OneHashDateUtil.getDate(2012, 10, 31));
		assertEquals(cus1.getServicePlan("CTVCode1").getServiceRates().get(0).getRateCode(),"TV-S");
		assertEquals(cus1.getServicePlan("CTVCode1").getServiceRates().get(0).getRateDescription(),"TV Subscription");
		assertEquals(cus1.getServicePlan("CTVCode1").getServiceRates().get(0).getRatePrice(),new BigDecimal(60.00));
		assertEquals(cus1.getServicePlan("CTVCode1").getServiceRates().get(0).isFreeCharge(),false);
	}
	
	@Test
	public void testGetComplaintLogs(){
		ComplaintLog complaintLog1 = new ComplaintLog();
		complaintLog1.setIssueNo("IS-0000-00-30");
		complaintLog1.setIssueDescription("Complain about the service 30");
		
		ComplaintLog complaintLog2 = new ComplaintLog();
		complaintLog2.setIssueNo("IS-0000-00-31");
		complaintLog2.setIssueDescription("Complain about the service 31");
		
		cus1.addComplaintLog(complaintLog1);
		cus1.addComplaintLog(complaintLog2);

		assertEquals(cus1.getComplaintLogs().size(),2);
		assertEquals(cus1.getComplaintLogs().get(0).getIssueNo(),"IS-0000-00-30");
		assertEquals(cus1.getComplaintLogs().get(0).getIssueDescription(),"Complain about the service 30");
		assertEquals(cus1.getComplaintLogs().get(1).getIssueNo(),"IS-0000-00-31");
		assertEquals(cus1.getComplaintLogs().get(1).getIssueDescription(),"Complain about the service 31");
	}
	
	@Test
	public void testGetBill(){
		List<BillDetail> billDetails = new ArrayList<BillDetail>();
    	BillDetail bDetail1 = new BillDetail();
    	
    	bDetail1.setPlanName("MV-L");
    	bDetail1.setRate(new BigDecimal(0.5));
    	bDetail1.setRateName("MV-L Rate");
    	bDetail1.setUsageDuration(new Long(911));
    	bDetail1.setUsageTime(OneHashDateUtil.getDate(2012, 2, 10));
    	billDetails.add(bDetail1);
        
        //Set up BillSummary
        Map<String,List<BillSummary>> billSummaryMap = new HashMap<String,List<BillSummary>>();
        List<BillSummary> billSummary = new ArrayList<BillSummary>();
        BillSummary bSummary1 = new BillSummary("MV-L",new BigDecimal(80.00));
        billSummary.add(bSummary1);
        billSummaryMap.put("20120301", billSummary);
        
        //Set up Payment Details
        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
        PaymentDetail bPayment1 = new PaymentDetail(OneHashDateUtil.getDate(2012,3,2),new BigDecimal(60.00));
        paymentDetails.add(bPayment1);
        
        List<Bill> bList = new ArrayList<Bill>();
        Bill b1 = new Bill();
        b1.setBillDate(OneHashDateUtil.getDate(2012,3,1));
        b1.setCarryForward(new BigDecimal(0));
        b1.setCurrentBill(new BigDecimal(80));
        b1.setGstRate(new BigDecimal(7));
        b1.setTotalBill(new BigDecimal(85.60));
        b1.setBillDetails(billDetails);
        b1.setBillSummaryMap(billSummaryMap);
        b1.setPaymentDetails(paymentDetails);
        bList.add(b1);
        
        cus1.setBill(bList);
        assertEquals(cus1.getBill().get(0).getBillDate(),OneHashDateUtil.getDate(2012,3,1));
        assertEquals(cus1.getBill().get(0).getCarryForward(),new BigDecimal(0));
        assertEquals(cus1.getBill().get(0).getCurrentBill(),new BigDecimal(80));
        assertEquals(cus1.getBill().get(0).getGstRate(),new BigDecimal(7));
        assertEquals(cus1.getBill().get(0).getTotalBill(),new BigDecimal(85.60));
        assertEquals(cus1.getBill().get(0).getBillDetails().get(0).getPalnName(),"MV-L");
        assertEquals(cus1.getBill().get(0).getBillDetails().get(0).getRate(),new BigDecimal(0.5));
        assertEquals(cus1.getBill().get(0).getBillSummaryMap().get("20120301").get(0).getDescription(),"MV-L");
        assertEquals(cus1.getBill().get(0).getBillSummaryMap().get("20120301").get(0).getTotal(),new BigDecimal(80.00));
        assertEquals(cus1.getBill().get(0).getPaymentDetails().get(0).getPaymentDate(),OneHashDateUtil.getDate(2012,3,2));
        assertEquals(cus1.getBill().get(0).getPaymentDetails().get(0).getAmount(),new BigDecimal(60.00));
	}
}
