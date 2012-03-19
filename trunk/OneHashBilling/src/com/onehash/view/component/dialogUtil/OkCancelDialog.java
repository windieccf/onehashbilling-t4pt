package com.onehash.view.component.dialogUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class OkCancelDialog extends JDialog {

    public OkCancelDialog (JFrame parent, String title) {
        super (parent, title);
        add ("Center", createFormPanel());
        add ("South",  createButtonPanel());
    }

    public OkCancelDialog (JFrame parent) {
        this (parent, "");
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

    protected abstract JPanel createFormPanel () ;

    protected abstract boolean performOkAction () ;

}