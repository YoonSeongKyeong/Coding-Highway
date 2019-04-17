package javapractice_helloworld;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.util.Random;

public class ButtonGame implements ActionListener{ 
    private static String leftLabel = "Number of button clicks : ";
    private int numClicks = 0;
    private int buttonLocationX;
    private int buttonLocationY;
    private JLabel label = new JLabel(leftLabel + "0  ");
    // private Timer timer;

    Color[] labelColorArr = {new Color(255,0,0),new Color(255,255,255),new Color(0,0,255)};
    JButton button = new JButton("Swing Button!");//want to put it into go-method, find how to replace button into this.caller
    //for access to button from actionPerformer-method
    public void go (String title) {

        JFrame frame = new JFrame(title);
        button.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(button);
        panel.add(label);
        panel.setLayout(null);
        label.setBounds(600, 20, 200, 50);//because pannel don't have a size
        button.setBounds(700, 250, 130, 50);//because pannel don't have a size
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 700);
        frame.setVisible(true);

        // timer = new Timer(10000, new ActionListener() {//make timeout
        //     public void actionPerformed(ActionEvent evt) {
                
        //     }
        // });
    }
    public void actionPerformed(ActionEvent event) {
        label.setText(leftLabel + ++numClicks);
        int i = numClicks % 3;
        Random random = new Random();
        buttonLocationX = random.nextInt(1200)+100;
        buttonLocationY = random.nextInt(350)+100;
        button.setBackground(labelColorArr[ i ]);
        //this.caller.setBackground(labelColorArr[ i ]); - want to replace to this code
        button.setLocation(buttonLocationX, buttonLocationY);
    } 

    public static void main(String[] args) {
        ButtonGame button = new ButtonGame();
        button.go("Event handling");
    }
}
