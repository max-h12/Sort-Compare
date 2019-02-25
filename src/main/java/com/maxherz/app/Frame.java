package com.maxherz.app;

import javax.swing.*;

public class Frame {

    String input;
    boolean isRunning;

    public Frame(){
         input ="";
         isRunning=true;
    }

    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                input = JOptionPane.showInputDialog(null, "Number of trials?");
                System.out.println("Trials: " + input);
            }
        });
    }

    public int trials(){
        isRunning = false;
        return Integer.parseInt(input);
    }

    public boolean running(){
        return isRunning;
    }
}