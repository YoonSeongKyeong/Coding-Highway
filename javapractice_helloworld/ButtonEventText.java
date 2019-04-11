package javapractice_helloworld;

import javax.swing.*;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.*;

public class ButtonEventText implements ActionListener{ 
    private static String leftLabel = "Number of button clicks : ";
    private int numClicks = 0;
    private JLabel label = new JLabel(leftLabel + "0  ");

    public void go (String title) {
        
        JFrame frame = new JFrame(title);
        JButton button = new JButton("Swing Button!");
        JPanel panel = new JPanel();
        button.addActionListener(this);
        panel.add(button);
        panel.add(label);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent event) {
        label.setText(leftLabel + ++numClicks);
    } 

    public static void main(String[] args) {
        ButtonEventText button = new ButtonEventText();
        button.go("Event handling");
    }
}
