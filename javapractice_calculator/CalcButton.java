package javapractice_calculator;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class CalcButton extends JButton { 
    public String id;
    CalcButton(String text) {
        super(text);
        id = text;
        this.setFont(new Font("Arial", Font.BOLD, 28));
        this.setBackground(Color.black);
        this.setForeground(Color.white);
        this.setBorder(new LineBorder(Color.white, 3));

    }

    public String getId() {
        return id;
    }
}



