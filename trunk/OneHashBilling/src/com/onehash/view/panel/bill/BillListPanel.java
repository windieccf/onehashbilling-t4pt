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
 * 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.view.panel.bill;

import java.awt.Dimension;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
import com.onehash.model.customer.Customer;
import com.onehash.model.scalar.ButtonAttributeScalar;
import com.onehash.model.scalar.PositionScalar;
import com.onehash.model.scalar.TextFieldAttributeScalar;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.component.listener.ButtonActionListener;
import com.onehash.view.component.listener.MouseTableListener;
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class BillListPanel extends BasePanel {

	private static final String COMP_LBL_ACCOUNTNUMBER = "LBL_USER_ACCOUNTNUMBER";
	private static final String COMP_LBL_BILLDATE = "LBL_USER_BILLDATE";
	private static final String COMP_LBL_BILLMONTH = "LBL_USER_BILLMONTH";
	private static final String COMP_LBL_BILLYEAR = "LBL_USER_BILLYEAR";
	private static final String COMP_TXT_ACCOUNTNUMBER = "TXT_ACCOUNT_NUMBER";
	private static final String COMP_DATE_BILLMONTH = "DATE_BILL_MONTH";
	private static final String COMP_DATE_BILYEAR = "DATE_BILL_YEAR";
	private static final String COMP_BUTTON_SEARCH = "BTN_SEARCH";
	private static final String COMP_BUTTON_RESET = "BTN_RESET";
	private static final String COMP_BUTTON_SEARCHHISTORY = "BTN_SEARCHHISTORY";
	private static final String COMP_BILL_TABLE = "BILL_TABLE";
	
	private static final String COMP_LBL_DV = "COMP_LBL_DV";
	private static final String COMP_LBL_MV = "COMP_LBL_MV";
	private static final String COMP_LBL_CT = "COMP_LBL_CT";
	private static final String COMP_LBL_DVS = "COMP_LBL_DVS";
	private static final String COMP_LBL_DVU = "COMP_LBL_DVU";
	private static final String COMP_LBL_MVS = "COMP_LBL_MVS";
	private static final String COMP_LBL_MVU = "COMP_LBL_MVU";
	private static final String COMP_LBL_CS = "COMP_LBL_CS";
	private static final String COMP_LBL_CU = "COMP_LBL_CU";
	
	private Calendar chosenDate;
	
	public BillListPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	
	@Override
	protected void init() {
		disableBillDetailsToView();
		super.registerComponent(COMP_LBL_ACCOUNTNUMBER , FactoryComponent.createLabel("Account No.", new PositionScalar(38,26,79,14)));
		super.registerComponent(COMP_LBL_BILLDATE , FactoryComponent.createLabel("Bill Date", new PositionScalar(38,51,79,14)));
		super.registerComponent(COMP_TXT_ACCOUNTNUMBER, FactoryComponent.createTextField( new TextFieldAttributeScalar(160, 23, 126, 20,10) ));
		
		final String[] months = new String[12];
        for(int i=0;i<months.length;i++){
        	months[i] = ""+(i+1);
        }
        
        super.registerComponent(COMP_LBL_BILLMONTH , FactoryComponent.createLabel("Month : ", new PositionScalar(160,48,50,20)));
        chosenDate = Calendar.getInstance();
        
        JComboBox monthSelector = new JComboBox(months);
        monthSelector.setSelectedIndex(chosenDate.get(Calendar.MONTH));
        monthSelector.setBounds(210, 48, 50, 20);
        monthSelector.setUI(new BasicComboBoxUI() { 
        	@Override 
     	    protected JButton createArrowButton() { 
     	        return new BasicArrowButton(BasicArrowButton.SOUTH); 
     	    } 
        }); 
        super.registerComponent(COMP_DATE_BILLMONTH , monthSelector);
        
        super.registerComponent(COMP_LBL_BILLYEAR , FactoryComponent.createLabel("Year : ", new PositionScalar(300, 48, 40, 20)));
        JComboBox yearSelector = new JComboBox();
        final Integer[] years = getYears(chosenDate.get(Calendar.YEAR));
        for (int i = 0; i < years.length; i++) {
        	yearSelector.addItem(years[i]);
        }
        yearSelector.setSelectedItem(new Integer(chosenDate.get(Calendar.YEAR)));
        yearSelector.setBounds(340, 48, 60, 20);
        yearSelector.setUI(new BasicComboBoxUI() { 
        	@Override 
     	    protected JButton createArrowButton() { 
     	        return new BasicArrowButton(BasicArrowButton.SOUTH); 
     	    } 
        }); 
        super.registerComponent(COMP_DATE_BILYEAR , yearSelector);
        
		super.registerComponent(COMP_BUTTON_SEARCH , FactoryComponent.createButton("Search", new ButtonAttributeScalar(160, 100, 96, 23 , new ButtonActionListener(this,"searchCusomerBill"))));
		super.registerComponent(COMP_BUTTON_RESET , FactoryComponent.createButton("Reset", new ButtonAttributeScalar(280, 100, 96, 23 , new ButtonActionListener(this,"resetSearchCriteria"))));
		super.registerComponent(COMP_BUTTON_SEARCHHISTORY , FactoryComponent.createButton("Bill History", new ButtonAttributeScalar(400, 100, 100, 23 , new ButtonActionListener(this,"viewBillHistory"))));
		
		//Create fields to display bill details
		
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
	
	public void resetSearchCriteria() throws Exception {
		try{
			disableBillDetailsToView();
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	public void searchCusomerBill() throws Exception {
		try{
			
			JTextField accountComponent = (JTextField)super.getComponent(COMP_TXT_ACCOUNTNUMBER);
			String accountNumber = (String)accountComponent.getText();
			if(OneHashStringUtil.isEmpty(accountNumber))
				throw new InsufficientInputParameterException("Accout Number is required");
			
			Customer customer = OneHashDataCache.getInstance().getCustomerByAccountNumber(accountNumber);
			if(customer!=null){
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
				
				Bill bill = OneHashDataCache.getInstance().getMonthlyBill(customer, billRequestDate.getTime());
				if(bill!=null){
					populateBillDetailsToView(bill);
				}
			}else
				throw new InsufficientInputParameterException("Accout Number is not valid");
			
		}catch(Exception exp){
			if(exp instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, exp.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	public void populateBillDetailsToView(Bill bill) {
		enableBillDetailsToView();
		
		Map<String,List<BillSummary>> billSummaryMap = bill.getBillSummaryMap();
		List<BillSummary> tvSummaryList = billSummaryMap.get(ConstantSummary.CableTV);
		for(BillSummary _billSummary : tvSummaryList){
			if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges))
				super.getTextFieldComponent(COMP_LBL_CS).setText(_billSummary.getTotal().toString());
			if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Usagecharges))
				super.getTextAreaComponent(COMP_LBL_CU).setText(_billSummary.getTotal().toString());
		}
		
		List<BillSummary> dvSummaryList = billSummaryMap.get(ConstantSummary.DigitalVoice);
		for(BillSummary _billSummary : dvSummaryList){
			if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges))
				super.getTextFieldComponent(COMP_LBL_DVS).setText(_billSummary.getTotal().toString());
			if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Usagecharges))
				super.getTextAreaComponent(COMP_LBL_DVU).setText(_billSummary.getTotal().toString());
		}
		
		List<BillSummary> mvSummaryList = billSummaryMap.get(ConstantSummary.DigitalVoice);
		for(BillSummary _billSummary : mvSummaryList){
			if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Subscriptioncharges))
				super.getTextFieldComponent(COMP_LBL_MVS).setText(_billSummary.getTotal().toString());
			if(_billSummary.getDescription().equalsIgnoreCase(ConstantSummary.Usagecharges))
				super.getTextAreaComponent(COMP_LBL_MVU).setText(_billSummary.getTotal().toString());
		}
	}
	
	public void viewBillHistory() {
		try{
			
			JTextField accountComponent = (JTextField)super.getComponent(COMP_TXT_ACCOUNTNUMBER);
			String accountNumber = (String)accountComponent.getText();
			if(OneHashStringUtil.isEmpty(accountNumber))
				throw new InsufficientInputParameterException("Accout Number is required");
			
			Customer customer = OneHashDataCache.getInstance().getCustomerByAccountNumber(accountNumber);
			if(customer!=null){
				Object[][] rowData = new String[1][4];
				if(customer.getBill()!=null && customer.getBill().size()>0){
					List<Bill> billList = customer.getBill();
					rowData = new Object[billList.size()][4];
					for(int i = 0 ; i < billList.size(); i++){
						Bill _bill = billList.get(i);
						rowData[i][0] = customer.getAccountNumber();
						rowData[i][1] = customer.getName();
						rowData[i][2] = _bill.getBillDate();
						rowData[i][3] = _bill.getTotalBill();
					}
				}
				
				JTable table = new JTable();
		        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		        table.setFillsViewportHeight(true);
		        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		        table.setModel(new OneHashTableModel(this.getTableColumnNames() , rowData));
		        table.addMouseListener(new MouseTableListener(this,"searchCusomerBill"));
		        
		        JScrollPane scrollPane = new JScrollPane(table);
		        scrollPane.setBounds(15,150,700,200);
		        
				super.registerComponent(COMP_BILL_TABLE, scrollPane);
				super.getComponent(COMP_BILL_TABLE).setVisible(true);
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	public void enableBillDetailsToView() {
		super.getComponent(COMP_LBL_DV).setVisible(true);
		super.getComponent(COMP_LBL_MV).setVisible(true);
		super.getComponent(COMP_LBL_CT).setVisible(true);
		super.getComponent(COMP_LBL_DVS).setVisible(true);
		super.getComponent(COMP_LBL_DVU).setVisible(true);
		super.getComponent(COMP_LBL_MVS).setVisible(true);
		super.getComponent(COMP_LBL_MVU).setVisible(true);
		super.getComponent(COMP_LBL_CS).setVisible(true);
		super.getComponent(COMP_LBL_CU).setVisible(true);
	}
	
	public void disableBillDetailsToView() {
		try{
			super.getComponent(COMP_LBL_DV).setVisible(false);
			super.getComponent(COMP_LBL_MV).setVisible(false);
			super.getComponent(COMP_LBL_CT).setVisible(false);
			super.getComponent(COMP_LBL_DVS).setVisible(false);
			super.getComponent(COMP_LBL_DVU).setVisible(false);
			super.getComponent(COMP_LBL_MVS).setVisible(false);
			super.getComponent(COMP_LBL_MVU).setVisible(false);
			super.getComponent(COMP_LBL_CS).setVisible(false);
			super.getComponent(COMP_LBL_CU).setVisible(false);
			super.getComponent(COMP_BILL_TABLE).setVisible(false);
		}catch(Exception exp){}
	}
	
	public String[] getTableColumnNames(){
		return new String[]{"Account Number", "Name","Bill Date" , "Amount"};
	}
	
	@Override
	protected String getScreenTitle() {return "Bill Details";}
}
