package com.utils;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MyWindowListener implements WindowListener {
    JFrame jFrame=null;
    public MyWindowListener(JFrame jFrame) {
        this.jFrame=jFrame;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println(1);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.jFrame.setVisible(true);
        System.out.println(2);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println(3);
        this.jFrame.setVisible(true);
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println(4);
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println(5);
    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println(6);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println(7);

    }
}
