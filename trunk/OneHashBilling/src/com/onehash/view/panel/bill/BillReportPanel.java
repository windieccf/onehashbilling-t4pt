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
 * 12 March 2012    Robin Foe	    0.1				Class creating
 * 22 March 2012    Aman Sharma	    0.2				Generate Report Method													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.view.panel.bill;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.onehash.constant.ConstantSummary;
import com.onehash.controller.OneHashDataCache;
import com.onehash.exception.BusinessLogicException;
import com.onehash.exception.InsufficientInputParameterException;
import com.onehash.model.bill.Bill;
import com.onehash.model.bill.BillSummary;
import com.onehash.model.bill.PaymentDetail;
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.utility.OneHashDateUtil;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class BillReportPanel extends BasePanel {
	private static final String COMP_LBL_ACCOUNTNUMBER = "LBL_USER_ACCOUNTNUMBER";
	private static final String COMP_TXT_ACCOUNTNUMBER = "COMP_TXT_ACCOUNTNUMBER";
	private static final String COMP_LBL_NRIC = "COMP_LBL_NRIC";
	private static final String COMP_TXT_NRIC = "COMP_TXT_NRIC";
	private static final String COMP_LBL_OR = "COMP_LBL_OR";
	
	private static final String COMP_LBL_BILLDATE = "LBL_USER_BILLDATE";
	private static final String COMP_LBL_BILLMONTH = "LBL_USER_BILLMONTH";
	private static final String COMP_LBL_BILLYEAR = "LBL_USER_BILLYEAR";
	
	private static final String COMP_DATE_BILLMONTH = "DATE_BILL_MONTH";
	private static final String COMP_DATE_BILYEAR = "DATE_BILL_YEAR";
	private static final String COMP_BUTTON_SEARCH = "BTN_SEARCH";

	private Calendar chosenDate;
	
	public BillReportPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	
	@Override
	protected void init() {

		super.registerComponent(COMP_LBL_ACCOUNTNUMBER , FactoryComponent.createLabel("Account No.", new PositionScalar(20,26,79,14)));
		super.registerComponent(COMP_TXT_ACCOUNTNUMBER, FactoryComponent.createTextField( new TextFieldAttributeScalar(120, 23, 126, 20,10) ));
		super.registerComponent(COMP_LBL_OR , FactoryComponent.createLabel("OR", new PositionScalar(250,35,50,20)));
		super.registerComponent(COMP_LBL_NRIC , FactoryComponent.createLabel("NRIC ", new PositionScalar(20,46,79,14)));
		super.registerComponent(COMP_TXT_NRIC, FactoryComponent.createTextField( new TextFieldAttributeScalar(120, 43, 126, 20,10) ));
		
		super.registerComponent(COMP_LBL_BILLDATE , FactoryComponent.createLabel("Bill Date", new PositionScalar(20,71,79,14)));
		final String[] months = new String[12];
        for(int i=0;i<months.length;i++){
        	months[i] = ""+(i+1);
        } 
        super.registerComponent(COMP_LBL_BILLMONTH , FactoryComponent.createLabel("Month :", new PositionScalar(120,71,50,20)));
        chosenDate = Calendar.getInstance();
        
        JComboBox monthSelector = new JComboBox(months);
        monthSelector.setSelectedIndex(chosenDate.get(Calendar.MONTH));
        monthSelector.setBounds(170, 71, 50, 20);
        monthSelector.setUI(new BasicComboBoxUI() { 
        	@Override 
     	    protected JButton createArrowButton() { 
     	        return new BasicArrowButton(BasicArrowButton.SOUTH); 
     	    } 
        }); 
        super.registerComponent(COMP_DATE_BILLMONTH , monthSelector);
        
        super.registerComponent(COMP_LBL_BILLYEAR , FactoryComponent.createLabel("Year :", new PositionScalar(250, 71, 50, 20)));
        JComboBox yearSelector = new JComboBox();
        final Integer[] years = getYears(chosenDate.get(Calendar.YEAR));
        for (int i = 0; i < years.length; i++) {
        	yearSelector.addItem(years[i]);
        }
        yearSelector.setSelectedItem(new Integer(chosenDate.get(Calendar.YEAR)));
        yearSelector.setBounds(290, 71, 50, 20);
        yearSelector.setUI(new BasicComboBoxUI() { 
        	@Override 
     	    protected JButton createArrowButton() { 
     	        return new BasicArrowButton(BasicArrowButton.SOUTH); 
     	    } 
        }); 
        super.registerComponent(COMP_DATE_BILYEAR , yearSelector);
        
        //Registering Search/History/Refresh Button
		super.registerComponent(COMP_BUTTON_SEARCH , FactoryComponent.createButton("Search", new ButtonAttributeScalar(20, 100, 96, 23 , new ButtonActionListener(this,"searchCusomerBill"))));
	}
	
	private Integer[] getYears(int chosenYear) {
        final int size = 20 * 2 + 1;
        final int start = chosenYear - 20;

        final Integer[] years = new Integer[size];
        for (int i = 0; i < size; i++) {
            years[i] = new Integer(i + start);
        }
        return years;
    }
	
	@Override
	protected String getScreenTitle() {return "Report Listing";}
	
	public void searchCusomerBill() throws Exception {
		try{
			
			JTextField accountComponent = (JTextField)super.getComponent(COMP_TXT_ACCOUNTNUMBER);
			String accountNumber = (String)accountComponent.getText();
			
			JTextField nricComponent = (JTextField)super.getComponent(COMP_TXT_NRIC);
			String nric = (String)nricComponent.getText();
			
			if(OneHashStringUtil.isEmpty(accountNumber)
					&& OneHashStringUtil.isEmpty(nric)){
				throw new InsufficientInputParameterException("Accout Number or NRIC is required");
			}

			JComboBox monthComponent = (JComboBox)super.getComponent(COMP_DATE_BILLMONTH);
			String monthTxt = (String)monthComponent.getSelectedItem();
			int month = new Integer(monthTxt).intValue();
			
			JComboBox yearComponent = (JComboBox)super.getComponent(COMP_DATE_BILYEAR);
			Integer yearTxt = (Integer)yearComponent.getSelectedItem();
			int year = yearTxt.intValue();
			
			Calendar billRequestDate = Calendar.getInstance();
			billRequestDate.set(Calendar.DATE, 28);
			billRequestDate.set(Calendar.MONTH, month-1);
			billRequestDate.set(Calendar.YEAR, year);
			
			//Check if the bill requested is for future month
			if(OneHashDateUtil.isFutureDate(billRequestDate.getTime()))
				throw new BusinessLogicException("Select a valid date for bill");
			
			Customer customer = null;
			if(!OneHashStringUtil.isEmpty(accountNumber))
				customer = OneHashDataCache.getInstance().getCustomerByAccountNumber(accountNumber);
			else if(!OneHashStringUtil.isEmpty(nric))
				customer = OneHashDataCache.getInstance().getCustomerByNric(nric);
			
			if(customer!=null){
				super.getTextFieldComponent(COMP_TXT_ACCOUNTNUMBER).setText(customer.getAccountNumber());
				super.getTextFieldComponent(COMP_TXT_NRIC).setText(customer.getNric());

				Bill bill = checkPreviousBillDetails(customer, billRequestDate.getTime());
				if(bill!=null){
					populateBillDetailsToView(customer,bill);
				}
				
			}else
				throw new InsufficientInputParameterException("Customer detials not found");
			
		}catch(Exception exp){
			if(exp instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, exp.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			exp.printStackTrace();
		}
	}
	
	private Bill checkPreviousBillDetails(Customer customer, Date billRequestDate) {
		try{
			if(customer.getBill()!=null && customer.getBill().size()>0){
				for(Bill _bill : customer.getBill()){
					if(OneHashDateUtil.isMonthYearOfBill(_bill.getBillDate(),billRequestDate)){
						return _bill;
					}
				}
			}
			return OneHashDataCache.getInstance().calculateBill(customer, billRequestDate);
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
	}
	
	public static void populateBillDetailsToView(Customer customer,Bill bill) {
		
		//FIRST GET ALL PLAN DETAILS AND CONVERT XML TO DATA
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(bill.getBillDate());
		
		Hashtable<String,Object> ht = new Hashtable<String,Object>();
		ht.put("NAME",customer.getName());
		ht.put("ADDRESS",customer.getAddress());
		ht.put("ACCNO",customer.getAccountNumber());
		ht.put("BILLDT",sdf.format(cal.getTime()));
		
		cal.set(Calendar.DATE, 15);
		cal.add(Calendar.MONTH, 1);
		ht.put("DUEDATE",sdf.format(cal.getTime()));
		
		//DEATILS OF LAST PAYMENT
		BigDecimal payment = new BigDecimal(0);
		Date paymentDate = new Date();
		if(bill.getPaymentDetails()!=null && bill.getPaymentDetails().size()>0){
			for(PaymentDetail _paymentDetail : bill.getPaymentDetails()){
				payment.add(_paymentDetail.getAmount());
				paymentDate = _paymentDetail.getPaymentDate();
			}
		}
		ht.put("PREVIOUSBILL",payment.toString());
		ht.put("PAYMENT",payment.toString());
		
		//CURRENT BILL
		ht.put("CURRENTCHARGES",bill.getCurrentBill().toString());
		ht.put("PAY",bill.getTotalBill().toString());
		
		ht.put("PYTRCVDT",sdf.format(paymentDate));
		ht.put("PYTAMT",payment);
		
		BigDecimal tvSC = new BigDecimal(0);
		BigDecimal tvAC = new BigDecimal(0);
		BigDecimal dvSC = new BigDecimal(0);
		BigDecimal dvUC = new BigDecimal(0);
		BigDecimal mvSC = new BigDecimal(0);
		BigDecimal mvUC = new BigDecimal(0);
		
		//Subscription
		BigDecimal dvSubscriptionRate = new BigDecimal(0);
		BigDecimal dvCallTransferRate = new BigDecimal(0);
		BigDecimal mvSubscriptionRate = new BigDecimal(0);
		BigDecimal mvDataServiceRate = new BigDecimal(0);
		
		//Usage
		BigDecimal dvUsageRateLocal = new BigDecimal(0);
		BigDecimal dvUsageRateIDD = new BigDecimal(0);
		
		BigDecimal usageRateLocal = new BigDecimal(0);
		BigDecimal usageRateIDD = new BigDecimal(0);
		BigDecimal usageRateRoaming = new BigDecimal(0);
		
		if(bill.getBillSummaryMap()!=null && bill.getBillSummaryMap().size()>0){
			Map<String,List<BillSummary>> billSummaryMap = bill.getBillSummaryMap();
			Set<String> keySet = billSummaryMap.keySet();
			for(String _key : keySet){
				if(_key.startsWith("TV-")){
					List<BillSummary> tvSummaryList = billSummaryMap.get(_key);
					if(tvSummaryList!=null && tvSummaryList.size()>0)
					for(BillSummary _billSummary : tvSummaryList){
						if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges))
							tvSC = tvSC.add(_billSummary.getTotal());
						if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.AddChannelcharges))
							tvAC = tvAC.add(_billSummary.getTotal());
					}
				}else if(_key.startsWith("DV-")){
					List<BillSummary> dvSummaryList = billSummaryMap.get(_key);
					if(dvSummaryList!=null && dvSummaryList.size()>0)
					for(BillSummary _billSummary : dvSummaryList){
						if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges)
								|| _billSummary.getDescription().equalsIgnoreCase(ConstantSummary.CallTransfer)){
							dvSC = dvSC.add(_billSummary.getTotal());
							if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges))
								dvSubscriptionRate = _billSummary.getTotal();
							else
								dvCallTransferRate = _billSummary.getTotal();
						}if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Usagecharges)){
							dvUC = dvUC.add(_billSummary.getTotal());
						}if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.UsagechargesLocal)){
							dvUsageRateLocal = dvUsageRateLocal.add(_billSummary.getTotal());
						}if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.UsagechargesIDD)){
							dvUsageRateIDD = dvUsageRateIDD.add(_billSummary.getTotal());
						}
					}
				}else if(_key.startsWith("MV-")){
					List<BillSummary> mvSummaryList = billSummaryMap.get(_key);
					if(mvSummaryList!=null && mvSummaryList.size()>0)
					for(BillSummary _billSummary : mvSummaryList){
						if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges)
								|| _billSummary.getDescription().equalsIgnoreCase(ConstantSummary.DataServices)){
							mvSC = mvSC.add(_billSummary.getTotal());
							if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges))
								mvSubscriptionRate = _billSummary.getTotal();
							else
								mvDataServiceRate = _billSummary.getTotal();
						}if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Usagecharges)){
							mvUC = mvUC.add(_billSummary.getTotal());
						}if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.UsagechargesLocal)){
							usageRateLocal = usageRateLocal.add(_billSummary.getTotal());
						}if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.UsagechargesIDD)){
							usageRateIDD = usageRateIDD.add(_billSummary.getTotal());
						}if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.UsagechargesRoamin)){
							usageRateRoaming = usageRateRoaming.add(_billSummary.getTotal());
						}
					}
				}
			}
		}
		
		ht.put("DVSL",dvSubscriptionRate);
		ht.put("DVSCCT",dvCallTransferRate);
		ht.put("MVSCM",mvSubscriptionRate);
		ht.put("MVSCD",mvDataServiceRate);

		ht.put("DVSC",dvSC);ht.put("DVUCL",dvUsageRateLocal);
		ht.put("DVSC",dvSC);ht.put("DVUCIDD",dvUsageRateIDD);
		
		ht.put("DVSC",dvSC);ht.put("MVUCL",usageRateLocal);
		ht.put("DVSC",dvSC);ht.put("MVUCIDD",usageRateIDD);
		ht.put("DVSC",dvSC);ht.put("MVUCR",usageRateRoaming);
		
		ht.put("DVSC",dvSC);
		ht.put("DVUC",dvUC);
		ht.put("TTLDV",dvUC.add(dvSC));
		
		ht.put("MVSC",mvSC);
		ht.put("MVUC",mvUC);
		ht.put("TTLMV",mvUC.add(mvSC));
		
		ht.put("CTVSC",tvSC);
		ht.put("CTVUC",tvAC);
		ht.put("TTLCTV",tvAC.add(tvSC));
		
		ht.put("TTLBILL",dvUC.add(dvSC).add(mvUC.add(mvSC)).add(tvAC.add(tvSC)));

		generateWordDoc(ht, "template/Bill.xml", "template/temp/Bill("+customer.getAccountNumber()+"-"+bill.getBillDate().getTime()+").xml");
	}
	
	public static void generateWordDoc(Hashtable<String,Object> ht, String templatePathFilename, String outputPathFilename) {	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(templatePathFilename));
			
			File destination = new File(outputPathFilename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
			
			String thisLine;
			int i = 0;
			
			while ((thisLine = reader.readLine()) != null) {
				System.out.println(i);
				
				for (Enumeration<String> e = ht.keys(); e.hasMoreElements();) {
					String name = (String) e.nextElement();
					String value = ht.get(name).toString();
					// Use this if we need to XML-encode the string in hashtable value...
					thisLine = thisLine.replaceAll("#" + name.toUpperCase() + "#", XmlEncode(value));
			    }
				System.out.println("THIS LINE : "+thisLine);
				writer.write(thisLine);
				writer.newLine();
				i++;
			}
			writer.close();
			System.out.println("done");
		}
		catch (Exception e) {
			System.out.println("exception!=" + e);
		}
	}

	private static String XmlEncode(String text) {
		int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
		for(int i = 0; i < charsRequiringEncoding.length - 1; i++) {
			text = text.replaceAll(String.valueOf((char)charsRequiringEncoding[i]),"&#"+charsRequiringEncoding[i]+";");
		}
		return text; 
	}
}
