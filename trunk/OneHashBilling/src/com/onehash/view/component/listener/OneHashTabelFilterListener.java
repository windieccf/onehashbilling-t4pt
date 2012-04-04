package com.onehash.view.component.listener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.onehash.model.scalar.TableFilterScalar;
import com.onehash.utility.OneHashStringUtil;
import com.onehash.view.component.tablemodel.OneHashTableModel;

/**
 * @author robin.foe
 * Dynamic table filter listener.
 */
public class OneHashTabelFilterListener implements DocumentListener{

	private TableRowSorter<OneHashTableModel> sorter;
	private List<TableFilterScalar> filters;
	
	public OneHashTabelFilterListener(TableRowSorter<OneHashTableModel> sorter , List<TableFilterScalar> filters){
		this.sorter = sorter;
		this.filters = filters;
	}
	
	@Override
	public void changedUpdate(DocumentEvent event) {
		this.eventOnChange(event);
	}

	@Override
	public void insertUpdate(DocumentEvent event) {
		this.eventOnChange(event);
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		this.eventOnChange(event);
	}
	
	private void eventOnChange(DocumentEvent event){
		List<RowFilter<Object,Object>> rfs =  new ArrayList<RowFilter<Object,Object>>();
		for(TableFilterScalar filter : filters){
			String text = filter.getTextField().getText();
			if(!OneHashStringUtil.isEmpty(text))
				rfs.add(RowFilter.regexFilter("(?i)"+text.trim(), filter.getIndex()));
		}
		this.sorter.setRowFilter(RowFilter.andFilter(rfs));
	}

}
