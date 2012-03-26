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
 * 26 March 2012    Chen Changfeng	0.1				Class creating 													
 * 													
 * 													
 * 													
 * 
 */
package com.onehash.view.component.dialogUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class OkCancelDialog extends JDialog {

//    public OkCancelDialog (JFrame parent, String title) {
//        super (parent, title);
//        add ("Center", createFormPanel());
//        add ("South",  createButtonPanel());
//    }

    public OkCancelDialog (JFrame parent) {
        this (parent, "", "", "");
    }
    
    public OkCancelDialog (JFrame parent, String title, String issueDes, String status) {
        super (parent, title);
        add ("Center", createFormPanel(issueDes, status));
        add ("South",  createButtonPanel());
    }

    private JPanel createButtonPanel () {
        JPanel p = new JPanel ();

        JButton b;
        ActionListener l;

        b = new JButton ("OK");
        l = new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                boolean success = performOkAction ();
                if (success) {
                    destroyDialog ();
                }
            }
        };
        b.addActionListener (l);
        p.add (b);

        b = new JButton ("Cancel");
        l = new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                destroyDialog ();
            }
        };
        b.addActionListener (l);
        p.add (b);

        return p;
    }

    private void destroyDialog () {
        setVisible (false);
        dispose();
    }

//    protected abstract JPanel createFormPanel () ;
    
    protected abstract JPanel createFormPanel (String issueDes, String status) ;

    protected abstract boolean performOkAction () ;

}