package com.maxherz.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame extends JFrame implements ActionListener {

    static Scanner sc = new Scanner(System.in);
    static String s;
    static JTextField textfield;
    static JButton jButton;

    public void showFrame() {
        JFrame jf = new JFrame();
        textfield = new JTextField("", 10);
        jButton = new JButton("Enter");
        jButton.addActionListener(new Frame());
        JPanel panel = new JPanel();
        JLabel jl = new JLabel("Options");
        jf.setSize(200, 200);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(panel);
        panel.add(jl);
        panel.add(textfield);
        panel.add(jButton);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        s = textfield.getText();
    }

}