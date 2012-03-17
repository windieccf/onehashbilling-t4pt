package com.onehash.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
//import com.jgoodies.forms.factories.FormFactory;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JScrollBar;

@SuppressWarnings("serial")
public class TestView extends JFrame {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestView frame = new TestView();
					frame.setVisible(true);
					frame.getContentPane().setLayout(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 412);
		getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 434, 262);
		mainPanel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 553, 376);
		mainPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 39, 414, 2);
		panel_1.add(separator);
		
		table = new JTable();
		table.setBounds(10, 188, 414, 48);
		panel_1.add(table);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(20, 52, 89, 23);
		panel_1.add(btnNewButton);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(220, 157, 204, 20);
		panel_1.add(textPane);
	}
}
