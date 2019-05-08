package javapractice_calculator;

import java.awt.Color;
import java.awt.Font;

class NumButton extends OperandButton{
    int value;

    NumButton(String num) {
        super(num);
        value = Integer.parseInt(num);
        this.setFont(new Font("Arial", Font.BOLD, 35));
        this.setForeground(Color.white);
        this.setBackground(Color.black);
    }

    int getVal() {
        return value;
    }
}