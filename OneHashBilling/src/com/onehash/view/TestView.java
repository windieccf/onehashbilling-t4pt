package com.onehash.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

@SuppressWarnings("serial")
public class TestView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestView frame = new TestView();
					frame.setVisible(true);
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JTextPane textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.NORTH);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 434, 262);
		contentPane.add(splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		
		JTree tree = new JTree();
		panel_1.add(tree);
	}
}
