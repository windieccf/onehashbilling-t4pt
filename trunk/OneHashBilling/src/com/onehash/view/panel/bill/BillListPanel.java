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
 * 13 March 2012	Aman Sharma		0.2				Added Bill Functionalities
 * 													Search/History/Reset																									
 * 													
 * 													
 * 
 */
package com.onehash.view.panel.bill;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class BillListPanel extends BasePanel{

	private static final String COMP_LBL_ACCOUNTNUMBER = "LBL_USER_ACCOUNTNUMBER";
	private static final String COMP_LBL_BILLDATE = "LBL_USER_BILLDATE";
	private static final String COMP_LBL_BILLMONTH = "LBL_USER_BILLMONTH";
	private static final String COMP_LBL_BILLYEAR = "LBL_USER_BILLYEAR";
	private static final String COMP_TXT_ACCOUNTNUMBER = "COMP_TXT_ACCOUNTNUMBER";
	private static final String COMP_LBL_NRIC = "COMP_LBL_NRIC";
	private static final String COMP_TXT_NRIC = "COMP_TXT_NRIC";
	private static final String COMP_LBL_OR = "COMP_LBL_OR";
	
	private static final String COMP_DATE_BILLMONTH = "DATE_BILL_MONTH";
	private static final String COMP_DATE_BILYEAR = "DATE_BILL_YEAR";
	private static final String COMP_BUTTON_SEARCH = "BTN_SEARCH";
	private static final String COMP_BUTTON_RESET = "BTN_RESET";
	private static final String COMP_BUTTON_SEARCHHISTORY = "BTN_SEARCHHISTORY";
	private static final String COMP_BILL_TABLE = "BILL_TABLE";
	
	private static final String COMP_LBL_NAME = "COMP_LBL_NAME";
	private static final String COMP_LBL_AMOUNT = "COMP_LBL_AMOUNT";
	private static final String COMP_LBL_TOTAL = "COMP_LBL_TOTAL";
	
	private static final String COMP_LBL_SPD = "COMP_LBL_SPD";
	private static final String COMP_LBL_PR = "COMP_LBL_PR";
	private static final String COMP_LBL_SCC = "COMP_LBL_SCC";
	private static final String COMP_TEXT_SPD = "COMP_TEXT_SPD";
	private static final String COMP_TEXT_PR = "COMP_TEXT_PR";
	
	private static final String COMP_LBL_DV = "COMP_LBL_DV";
	private static final String COMP_LBL_DVS = "COMP_LBL_DVS";
	private static final String COMP_LBL_DVU = "COMP_LBL_DVU";
	private static final String COMP_TEXT_DVS = "COMP_TEXT_DVS";
	private static final String COMP_TEXT_DVU = "COMP_TEXT_DVU";
	
	private static final String COMP_LBL_MV = "COMP_LBL_MV";
	private static final String COMP_LBL_MVS = "COMP_LBL_MVS";
	private static final String COMP_LBL_MVU = "COMP_LBL_MVU";
	private static final String COMP_TEXT_MVS = "COMP_TEXT_MVS";
	private static final String COMP_TEXT_MVU = "COMP_TEXT_MVU";
	
	private static final String COMP_LBL_CT = "COMP_LBL_CT";
	private static final String COMP_LBL_CS = "COMP_LBL_CS";
	private static final String COMP_LBL_CU = "COMP_LBL_CU";
	private static final String COMP_TEXT_CS = "COMP_TEXT_CS";
	private static final String COMP_TEXT_CU = "COMP_TEXT_CU";
	
	private static final String COMP_LBL_GST = "COMP_LBL_GST";
	private static final String COMP_LBL_TCC = "COMP_LBL_TCC";
	private static final String COMP_TEXT_GST = "COMP_TEXT_GST";
	private static final String COMP_TEXT_TCC = "COMP_TEXT_TCC";
	
	private Calendar chosenDate;
	
	public BillListPanel(OneHashGui mainFrame) {
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
		super.registerComponent(COMP_BUTTON_SEARCHHISTORY , FactoryComponent.createButton("Bill History", new ButtonAttributeScalar(140, 100, 96, 23 , new ButtonActionListener(this,"viewBillHistory"))));
		super.registerComponent(COMP_BUTTON_RESET , FactoryComponent.createButton("Reset", new ButtonAttributeScalar(260, 100, 96, 23 , new ButtonActionListener(this,"resetSearchCriteria"))));
		
		//Bill History Panel
		Object[][] rowData = new String[0][2];
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(100, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , rowData));
        table.addMouseListener(new MouseTableListener(this,"viewBillDetail"));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,140,335,270);
		super.registerComponent(COMP_BILL_TABLE, scrollPane);
		
		//View Bill Details - Header
		super.registerComponent(COMP_LBL_NAME , FactoryComponent.createLabel("One#", new PositionScalar(370,20,50,20)));
		super.registerComponent(COMP_LBL_AMOUNT , FactoryComponent.createLabel("Amount (S$)", new PositionScalar(650,20,70,20)));
		super.registerComponent(COMP_LBL_TOTAL , FactoryComponent.createLabel("Total (S$)", new PositionScalar(750,20,70,20)));
		
		//Summary Payment Details
		super.registerComponent(COMP_LBL_SPD , FactoryComponent.createLabel("Summary - Payment Details", new PositionScalar(370,50,300,20)));
		super.registerComponent(COMP_LBL_PR , FactoryComponent.createLabel("Payment received", new PositionScalar(370,70,200,20)));
		super.registerComponent(COMP_TEXT_SPD , FactoryComponent.createLabel("",new PositionScalar(650, 70,100,20)));
		super.registerComponent(COMP_TEXT_PR , FactoryComponent.createLabel("",new PositionScalar(750, 70,100,20)));
		
		//Summary Current Charges
		super.registerComponent(COMP_LBL_SCC , FactoryComponent.createLabel("Summary Current Charges", new PositionScalar(370,100,300,20)));
		
		//Summary Current Charges - Digital Voice
		super.registerComponent(COMP_LBL_DV , FactoryComponent.createLabel("Digital Voice", new PositionScalar(370,120,100,20)));	
		super.registerComponent(COMP_LBL_DVS , FactoryComponent.createLabel("Subscription charges", new PositionScalar(390,140,200,20)));
		super.registerComponent(COMP_LBL_DVU , FactoryComponent.createLabel("Usage charges", new PositionScalar(390,160,150,20)));
		super.registerComponent(COMP_TEXT_DVS , FactoryComponent.createLabel("",new PositionScalar(650, 140,100,20)));
		super.registerComponent(COMP_TEXT_DVU , FactoryComponent.createLabel("",new PositionScalar(650, 160,100,20)));
		
		//Summary Current Charges - Mobile Voice
		super.registerComponent(COMP_LBL_MV , FactoryComponent.createLabel("Mobile Voice", new PositionScalar(370,190,100,20)));
		super.registerComponent(COMP_LBL_MVS , FactoryComponent.createLabel("Subscription charges", new PositionScalar(390,210,200,20)));
		super.registerComponent(COMP_LBL_MVU , FactoryComponent.createLabel("Usage charges", new PositionScalar(390,230,150,20)));
		super.registerComponent(COMP_TEXT_MVS , FactoryComponent.createLabel("",new PositionScalar(650, 210,100,20)));
		super.registerComponent(COMP_TEXT_MVU , FactoryComponent.createLabel("",new PositionScalar(650, 230,100,20)));
		
		//Summary Current Charges - Cable TV
		super.registerComponent(COMP_LBL_CT , FactoryComponent.createLabel("Cable TV", new PositionScalar(370,260,100,20)));
		super.registerComponent(COMP_LBL_CS , FactoryComponent.createLabel("Subscription charges", new PositionScalar(390,280,200,20)));
		super.registerComponent(COMP_LBL_CU , FactoryComponent.createLabel("Add. Channel charges", new PositionScalar(390,300,300,20)));
		super.registerComponent(COMP_TEXT_CS , FactoryComponent.createLabel("",new PositionScalar(650, 280,100,20)));
		super.registerComponent(COMP_TEXT_CU , FactoryComponent.createLabel("",new PositionScalar(650, 300,100,20)));
		
		//GST - TOTAL CHARGES.
		super.registerComponent(COMP_LBL_GST , FactoryComponent.createLabel("Total GST", new PositionScalar(370,330,300,20)));
		super.registerComponent(COMP_LBL_TCC , FactoryComponent.createLabel("Total Current Charges", new PositionScalar(370,350,300,20)));
		super.registerComponent(COMP_TEXT_GST , FactoryComponent.createLabel("",new PositionScalar(750, 330,100,20)));
		super.registerComponent(COMP_TEXT_TCC , FactoryComponent.createLabel("",new PositionScalar(750, 350,100,20)));
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
					populateBillDetailsToView(bill);
					if(customer.getBill()==null || customer.getBill().size()==0){
						List<Bill> billList = new ArrayList<Bill>();
						billList.add(bill);
					}
					customer.getBill().add(bill);
					OneHashDataCache.getInstance().saveCustomer(customer);
				}
				
			}else
				throw new InsufficientInputParameterException("Customer detials not found");
			
		}catch(Exception exp){
			if(exp instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, exp.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			exp.printStackTrace();
		}
	}
	
	public void viewBillDetail(String parameters) throws Exception {
		try{
			JTextField accountComponent = (JTextField)super.getComponent(COMP_TXT_ACCOUNTNUMBER);
			String accountNumber = (String)accountComponent.getText();
			
			Customer customer = OneHashDataCache.getInstance().getCustomerByAccountNumber(accountNumber);
			if(customer!=null){
				Bill bill = checkPreviousBillDetails(customer, parserDate(parameters));
				if(bill!=null){
					populateBillDetailsToView(bill);
				}
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	public void populateBillDetailsToView(Bill bill) {
		try{
			List<PaymentDetail> paymentDetails = bill.getPaymentDetails();
			BigDecimal totalPayment = new BigDecimal(0);
			if(paymentDetails!=null && paymentDetails.size()>0){
				for(PaymentDetail _paymentDetail : paymentDetails){
					totalPayment.add(_paymentDetail.getAmount());
				}
			}
			super.getLabelComponent(COMP_TEXT_PR).setText(totalPayment.toString());
			super.getLabelComponent(COMP_TEXT_SPD).setText(totalPayment.toString());
			
			BigDecimal tvSC = new BigDecimal(0);
			BigDecimal tvAC = new BigDecimal(0);
			BigDecimal dvSC = new BigDecimal(0);
			BigDecimal dvUC = new BigDecimal(0);
			BigDecimal mvSC = new BigDecimal(0);
			BigDecimal mvUC = new BigDecimal(0);
			
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
									|| _billSummary.getDescription().equalsIgnoreCase(ConstantSummary.CallTransfer))
								dvSC = dvSC.add(_billSummary.getTotal());
							if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Usagecharges))
								dvUC = dvUC.add(_billSummary.getTotal());
						}
					}else if(_key.startsWith("MV-")){
						List<BillSummary> mvSummaryList = billSummaryMap.get(_key);
						if(mvSummaryList!=null && mvSummaryList.size()>0)
						for(BillSummary _billSummary : mvSummaryList){
							if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges)
									|| _billSummary.getDescription().equalsIgnoreCase(ConstantSummary.DataServices))
								mvSC = mvSC.add(_billSummary.getTotal());
							if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Usagecharges))
								mvUC = mvUC.add(_billSummary.getTotal());
						}
					}
				}
				super.getLabelComponent(COMP_TEXT_CS).setText(tvSC.toString());
				super.getLabelComponent(COMP_TEXT_CU).setText(tvAC.toString());
				super.getLabelComponent(COMP_TEXT_DVS).setText(dvSC.toString());
				super.getLabelComponent(COMP_TEXT_DVU).setText(dvUC.toString());
				super.getLabelComponent(COMP_TEXT_MVS).setText(mvSC.toString());
				super.getLabelComponent(COMP_TEXT_MVU).setText(mvUC.toString());
			}
			if(bill.getGstRate()!=null)
				super.getLabelComponent(COMP_TEXT_TCC).setText(bill.getGstRate().toString());
			super.getLabelComponent(COMP_TEXT_TCC).setText(bill.getTotalBill().toString());
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	public Object[][] viewBillHistory() {
		Object[][] rowData = new String[0][2];
		try{
			JTextField accountComponent = (JTextField)super.getComponent(COMP_TXT_ACCOUNTNUMBER);
			String accountNumber = (String)accountComponent.getText();
			
			JTextField nricComponent = (JTextField)super.getComponent(COMP_TXT_NRIC);
			String nric = (String)nricComponent.getText();
			
			if(OneHashStringUtil.isEmpty(accountNumber)
					&& OneHashStringUtil.isEmpty(nric)){
				throw new InsufficientInputParameterException("Accout Number or NRIC is required");
			}
			
			Customer customer = null;
			if(!OneHashStringUtil.isEmpty(accountNumber))
				customer = OneHashDataCache.getInstance().getCustomerByAccountNumber(accountNumber);
			else if(!OneHashStringUtil.isEmpty(nric))
				customer = OneHashDataCache.getInstance().getCustomerByNric(nric);
			
			if(customer!=null){
				super.getTextFieldComponent(COMP_TXT_ACCOUNTNUMBER).setText(customer.getAccountNumber());
				super.getTextFieldComponent(COMP_TXT_NRIC).setText(customer.getNric());
				
				if(customer.getBill()!=null && customer.getBill().size()>0){
					List<Bill> billList = customer.getBill();
					rowData = new Object[billList.size()][2];
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					for(int i = 0 ; i < billList.size(); i++){
						Bill _bill = billList.get(i);
						rowData[i][0] = sdf.format(_bill.getBillDate());
						rowData[i][1] = _bill.getTotalBill();
					}
				}
				
				JTable table = new JTable();
		        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
		        table.setFillsViewportHeight(true);
		        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		        table.setModel(new OneHashTableModel(this.getTableColumnNames() , rowData));
		        table.addMouseListener(new MouseTableListener(this,"viewBillDetail"));
				JScrollPane scrollPane = (JScrollPane) super.getComponent(COMP_BILL_TABLE);
				scrollPane.setViewportView(table);
			}
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return rowData;
	}
	
	public Bill checkPreviousBillDetails(Customer customer, Date billRequestDate) {
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
	
	public Date parserDate(String strDate){
		String[] arryDate = strDate.split("/");
		int day = new Integer(arryDate[0]).intValue();
		int month = new Integer(arryDate[1]).intValue();
		int year = new Integer(arryDate[2]).intValue();
		
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.set(Calendar.DATE, day);
		calendarDate.set(Calendar.MONTH, (month-1));
		calendarDate.set(Calendar.YEAR, year);
		
		return calendarDate.getTime();
	}
	
	public void resetSearchCriteria() throws Exception {
		try{
			Object[][] rowData = new String[0][2];
			JTable table = new JTable();
	        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
	        table.setFillsViewportHeight(true);
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table.setModel(new OneHashTableModel(this.getTableColumnNames() , rowData));
	        table.addMouseListener(new MouseTableListener(this,"viewBillDetail"));
			JScrollPane scrollPane = (JScrollPane) super.getComponent(COMP_BILL_TABLE);
			scrollPane.setViewportView(table);
			
			super.getTextFieldComponent(COMP_TXT_ACCOUNTNUMBER).setText(null);
			super.getTextFieldComponent(COMP_TXT_NRIC).setText(null);
			super.getLabelComponent(COMP_TEXT_SPD).setText(null);
			super.getLabelComponent(COMP_TEXT_PR).setText(null);
			super.getLabelComponent(COMP_TEXT_DVS).setText(null);
			super.getLabelComponent(COMP_TEXT_DVU).setText(null);
			super.getLabelComponent(COMP_TEXT_MVS).setText(null);
			super.getLabelComponent(COMP_TEXT_MVU).setText(null);
			super.getLabelComponent(COMP_TEXT_CS).setText(null);
			super.getLabelComponent(COMP_TEXT_CU).setText(null);
			super.getLabelComponent(COMP_TEXT_GST).setText(null);
			super.getLabelComponent(COMP_TEXT_TCC).setText(null);
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	public String[] getTableColumnNames(){
		return new String[]{"Bill Date" , "Bill Amount"};
	}
	
	@Override
	protected String getScreenTitle() {return "Bill Details";}
}
