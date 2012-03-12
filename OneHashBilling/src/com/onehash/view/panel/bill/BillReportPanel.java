package com.onehash.view.panel.bill;

import com.onehash.model.scalar.PositionScalar;
import com.onehash.view.OneHashGui;
import com.onehash.view.component.FactoryComponent;
import com.onehash.view.panel.base.BasePanel;

@SuppressWarnings("serial")
public class BillReportPanel extends BasePanel {
	private static final String COMP_LBL_TITLE = "TITLE_PANEL";
	
	public BillReportPanel(OneHashGui mainFrame) {
		super(mainFrame);
	}
	
	@Override
	protected void init() {
		super.registerComponent(COMP_LBL_TITLE , FactoryComponent.createLabel("Report Listing", new PositionScalar(38,26,79,14)));
	}
}
