package javapractice_calculator;

import javax.swing.*;
import java.awt.*;

public class CalcButton extends JButton { 
    public String id;
    CalcButton(String text) {
        super(text);
        id = text;
        this.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    public String getId() {
        return id;
    }
}



