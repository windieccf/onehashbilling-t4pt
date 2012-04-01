package com.onehash.view.panel.payment;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import com.onehash.view.component.tablemodel.OneHashTableModel;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class PaymentPanel extends BasePanel{
	
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
	private static final String COMP_BILL_TABLE = "BILL_TABLE";
	
	private static final String COMP_LBL_NAME = "COMP_LBL_NAME";
	private static final String COMP_LBL_AMOUNT = "COMP_LBL_AMOUNT";
	private static final String COMP_LBL_TOTAL = "COMP_LBL_TOTAL";
	
	private static final String COMP_LBL_SCC = "COMP_LBL_SCC";
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
	
	private static final String COMP_LBL_PAYMENT = "COMP_LBL_PAYMENT";
	private static final String COMP_LBL_PAY = "COMP_LBL_PAY";
	private static final String COMP_TXT_PAYAMOUNT = "COMP_TXT_PAYAMOUNT";
	private static final String COMP_BUTTON_SAVEPAY = "COMP_BUTTON_SAVEPAY";
	
	private static final String COMP_LBL_PAID = "COMP_LBL_PAID";
	//private static final String COMP_LBL_DUE = "COMP_LBL_DUE";
	private static final String COMP_TXT_PAID = "COMP_TXT_PAID";
	//private static final String COMP_TXT_DUE = "COMP_TXT_DUE";
	
	private Calendar chosenDate;
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public PaymentPanel(OneHashGui mainFrame) {
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
		super.registerComponent(COMP_BUTTON_SEARCH , FactoryComponent.createButton("Search", new ButtonAttributeScalar(120, 100, 96, 23 , new ButtonActionListener(this,"searchCusomerBill"))));
		super.registerComponent(COMP_BUTTON_RESET , FactoryComponent.createButton("Reset", new ButtonAttributeScalar(240, 100, 100, 23 , new ButtonActionListener(this,"resetSearchCriteria"))));
		
		//Bill History Panel
		Object[][] rowData = new String[0][2];
		JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(100, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new OneHashTableModel(this.getTableColumnNames() , rowData));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,140,320,110);
		super.registerComponent(COMP_BILL_TABLE, scrollPane);
		
		//View Bill Details - Header
		super.registerComponent(COMP_LBL_NAME , FactoryComponent.createLabel("One#", new PositionScalar(370,20,50,20)));
		super.registerComponent(COMP_LBL_AMOUNT , FactoryComponent.createLabel("Amount (S$)", new PositionScalar(650,20,70,20)));
		super.registerComponent(COMP_LBL_TOTAL , FactoryComponent.createLabel("Total (S$)", new PositionScalar(750,20,70,20)));
		
		//Summary Current Charges
		super.registerComponent(COMP_LBL_SCC , FactoryComponent.createLabel("Summary Current Charges", new PositionScalar(370,60,300,20)));
		
		//Summary Current Charges - Digital Voice
		super.registerComponent(COMP_LBL_DV , FactoryComponent.createLabel("Digital Voice", new PositionScalar(370,80,100,20)));	
		super.registerComponent(COMP_LBL_DVS , FactoryComponent.createLabel("Subscription charges", new PositionScalar(390,100,200,20)));
		super.registerComponent(COMP_LBL_DVU , FactoryComponent.createLabel("Usage charges", new PositionScalar(390,120,150,20)));
		super.registerComponent(COMP_TEXT_DVS , FactoryComponent.createLabel("",new PositionScalar(650, 100,100,20)));
		super.registerComponent(COMP_TEXT_DVU , FactoryComponent.createLabel("",new PositionScalar(650, 120,100,20)));
		
		//Summary Current Charges - Mobile Voice
		super.registerComponent(COMP_LBL_MV , FactoryComponent.createLabel("Mobile Voice", new PositionScalar(370,150,100,20)));
		super.registerComponent(COMP_LBL_MVS , FactoryComponent.createLabel("Subscription charges", new PositionScalar(390,170,200,20)));
		super.registerComponent(COMP_LBL_MVU , FactoryComponent.createLabel("Usage charges", new PositionScalar(390,190,150,20)));
		super.registerComponent(COMP_TEXT_MVS , FactoryComponent.createLabel("",new PositionScalar(650, 170,100,20)));
		super.registerComponent(COMP_TEXT_MVU , FactoryComponent.createLabel("",new PositionScalar(650, 190,100,20)));
		
		//Summary Current Charges - Cable TV
		super.registerComponent(COMP_LBL_CT , FactoryComponent.createLabel("Cable TV", new PositionScalar(370,220,100,20)));
		super.registerComponent(COMP_LBL_CS , FactoryComponent.createLabel("Subscription charges", new PositionScalar(390,240,200,20)));
		super.registerComponent(COMP_LBL_CU , FactoryComponent.createLabel("Add. Channel charges", new PositionScalar(390,260,300,20)));
		super.registerComponent(COMP_TEXT_CS , FactoryComponent.createLabel("",new PositionScalar(650, 240,100,20)));
		super.registerComponent(COMP_TEXT_CU , FactoryComponent.createLabel("",new PositionScalar(650, 260,100,20)));
		
		//GST - TOTAL CHARGES.
		super.registerComponent(COMP_LBL_GST , FactoryComponent.createLabel("Total GST", new PositionScalar(370,290,300,20)));
		super.registerComponent(COMP_LBL_TCC , FactoryComponent.createLabel("Total Current Charges", new PositionScalar(370,310,300,20)));
		super.registerComponent(COMP_TEXT_GST , FactoryComponent.createLabel("",new PositionScalar(750, 290,100,20)));
		super.registerComponent(COMP_TEXT_TCC , FactoryComponent.createLabel("",new PositionScalar(750, 310,100,20)));
		
		//Payment Details
		super.registerComponent(COMP_LBL_PAID , FactoryComponent.createLabel("Total amount paid : ", new PositionScalar(30,220,335,110)));
		//super.registerComponent(COMP_LBL_DUE , FactoryComponent.createLabel("Due amount : ",new PositionScalar(65,240,335,110)));
		super.registerComponent(COMP_TXT_PAID , FactoryComponent.createLabel("", new PositionScalar(170,220,335,110)));
		//super.registerComponent(COMP_TXT_DUE , FactoryComponent.createLabel("",new PositionScalar(170,240,335,110)));
		
		super.registerComponent(COMP_LBL_PAYMENT , FactoryComponent.createLabel("Make Payment", new PositionScalar(20,260,335,110)));
		super.registerComponent(COMP_LBL_PAY , FactoryComponent.createLabel("Enter Amount : ",new PositionScalar(60,280,335,110)));
		super.registerComponent(COMP_TXT_PAYAMOUNT, FactoryComponent.createTextField( new TextFieldAttributeScalar(170, 330, 80, 23,10) ));
		super.registerComponent(COMP_BUTTON_SAVEPAY , FactoryComponent.createButton("Save", new ButtonAttributeScalar(260, 330, 80, 23 , new ButtonActionListener(this,"savePayment"))));
		
		super.getComponent(COMP_LBL_PAYMENT).setVisible(false);
		super.getComponent(COMP_LBL_PAY).setVisible(false);
		super.getComponent(COMP_TXT_PAYAMOUNT).setVisible(false);
		super.getComponent(COMP_BUTTON_SAVEPAY).setVisible(false);
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
				this.setCustomer(customer);
				super.getTextFieldComponent(COMP_TXT_ACCOUNTNUMBER).setText(customer.getAccountNumber());
				super.getTextFieldComponent(COMP_TXT_NRIC).setText(customer.getNric());

				Bill bill = checkPreviousBillDetails(customer, billRequestDate.getTime());
				if(bill!=null){
					populateBillDetailsToView(bill);
					viewPaymentHistory(bill);
				}else{
					resetSearchCriteria();
					throw new InsufficientInputParameterException("Customer Bill for the requested month not found");
				}
			}else{
				resetSearchCriteria();
				throw new InsufficientInputParameterException("Customer detials not found");
			}
		}catch(Exception exp){
			if(exp instanceof BusinessLogicException)
				JOptionPane.showMessageDialog(this, exp.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			exp.printStackTrace();
		}
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
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
		return null;
	}
	
	public void populateBillDetailsToView(Bill bill) {
		try{
			
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
			BigDecimal gstAmount = new BigDecimal(0);
			if(bill.getGstRate()!=null){
				DecimalFormat df = new DecimalFormat("#.##");
				double gst = bill.getGstRate().doubleValue()/100;
				double totalBillDec = bill.getTotalBill().doubleValue()*gst;
				gstAmount = new BigDecimal(df.format(totalBillDec));
			}
			super.getLabelComponent(COMP_TEXT_GST).setText(gstAmount.toString());
			super.getLabelComponent(COMP_TEXT_TCC).setText(bill.getTotalBill().add(gstAmount).toString());
			
			super.getComponent(COMP_LBL_PAYMENT).setVisible(true);
			super.getComponent(COMP_LBL_PAY).setVisible(true);
			super.getComponent(COMP_TXT_PAYAMOUNT).setVisible(true);
			super.getComponent(COMP_BUTTON_SAVEPAY).setVisible(true);
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	public Object[][] viewPaymentHistory(Bill bill) {
		Object[][] rowData = new String[0][2];
		BigDecimal totalAmountPaid = new BigDecimal(0);
		try{
			
			List<PaymentDetail> paymentDetailList = bill.getPaymentDetails();
			rowData = new Object[paymentDetailList.size()][2];
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for(int i = 0 ; i < paymentDetailList.size(); i++){
				PaymentDetail _paymentDetail = paymentDetailList.get(i);
				rowData[i][0] = sdf.format(_paymentDetail.getPaymentDate());
				rowData[i][1] = _paymentDetail.getAmount();
				totalAmountPaid = totalAmountPaid.add(_paymentDetail.getAmount());
			}
			
			JTable table = new JTable();
	        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
	        table.setFillsViewportHeight(true);
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table.setModel(new OneHashTableModel(this.getTableColumnNames() , rowData));
			JScrollPane scrollPane = (JScrollPane) super.getComponent(COMP_BILL_TABLE);
			scrollPane.setViewportView(table);		
			super.getLabelComponent(COMP_TXT_PAID).setText(totalAmountPaid.toString());
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return rowData;
	}
	
	public void resetSearchCriteria() throws Exception {
		try{
			Object[][] rowData = new String[0][2];
			JTable table = new JTable();
	        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
	        table.setFillsViewportHeight(true);
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table.setModel(new OneHashTableModel(this.getTableColumnNames() , rowData));
			JScrollPane scrollPane = (JScrollPane) super.getComponent(COMP_BILL_TABLE);
			scrollPane.setViewportView(table);
			
			super.getTextFieldComponent(COMP_TXT_ACCOUNTNUMBER).setText(null);
			super.getTextFieldComponent(COMP_TXT_NRIC).setText(null);
			super.getLabelComponent(COMP_TEXT_DVS).setText(null);
			super.getLabelComponent(COMP_TEXT_DVU).setText(null);
			super.getLabelComponent(COMP_TEXT_MVS).setText(null);
			super.getLabelComponent(COMP_TEXT_MVU).setText(null);
			super.getLabelComponent(COMP_TEXT_CS).setText(null);
			super.getLabelComponent(COMP_TEXT_CU).setText(null);
			super.getLabelComponent(COMP_TEXT_GST).setText(null);
			super.getLabelComponent(COMP_TEXT_TCC).setText(null);
			
			super.getComponent(COMP_LBL_PAYMENT).setVisible(false);
			super.getComponent(COMP_LBL_PAY).setVisible(false);
			super.getComponent(COMP_TXT_PAYAMOUNT).setVisible(false);
			super.getComponent(COMP_BUTTON_SAVEPAY).setVisible(false);
			super.getComponent(COMP_TXT_PAID).setVisible(false);
			//super.getComponent(COMP_TXT_DUE).setVisible(false);
			
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}	
	
	
	public void savePayment() throws Exception {
		JComboBox monthComponent = (JComboBox)super.getComponent(COMP_DATE_BILLMONTH);
		String monthTxt = (String)monthComponent.getSelectedItem();
		int month = new Integer(monthTxt).intValue();
		
		JComboBox yearComponent = (JComboBox)super.getComponent(COMP_DATE_BILYEAR);
		Integer yearTxt = (Integer)yearComponent.getSelectedItem();
		int year = yearTxt.intValue();
		
		JTextField payAmount = (JTextField)super.getComponent(COMP_TXT_PAYAMOUNT);
		String amount = (String)payAmount.getText();
		
		Calendar billRequestDate = Calendar.getInstance();
		billRequestDate.set(Calendar.DATE, 28);
		billRequestDate.set(Calendar.MONTH, month-1);
		billRequestDate.set(Calendar.YEAR, year);
		
		try{
			if(this.getCustomer()!=null){
				if(this.getCustomer().getBill()!=null){
					int bill = 0;
					for(Bill _bill : this.getCustomer().getBill()){
						if(OneHashDateUtil.isMonthYearOfBill(_bill.getBillDate(),billRequestDate.getTime())){
							PaymentDetail paymentDetail = new PaymentDetail(new Date(),new BigDecimal(amount));
							if(this.getCustomer().getBill().get(bill).getPaymentDetails()==null || this.getCustomer().getBill().get(bill).getPaymentDetails().size()==0){
								List<PaymentDetail> paymentDetailsList = new ArrayList<PaymentDetail>(); 
								this.getCustomer().getBill().get(bill).setPaymentDetails(paymentDetailsList);
							}
							this.getCustomer().getBill().get(bill).getPaymentDetails().add(paymentDetail);
							viewPaymentHistory(this.getCustomer().getBill().get(bill));
							break;
						}
						bill++;
					}
					OneHashDataCache.getInstance().saveCustomer(this.getCustomer());
				}
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}
		super.getTextFieldComponent(COMP_TXT_PAYAMOUNT).setText(null);
	}
	public String[] getTableColumnNames(){
		return new String[]{"Payment Date" , "Payment Amount"};
	}
	
	@Override
	protected String getScreenTitle() {return "Payment";}

}
