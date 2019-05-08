package javapractice_calculator;

import java.awt.Color;
import java.awt.font.TextLayout;

import javax.swing.JLabel;

public class resultLabel extends JLabel {
    resultLabel(String text) {
        super(text);
        this.setFont(this.getFont().deriveFont(30.0F));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setSize(700, 100);
        this.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.setForeground(Color.ORANGE);
    }
}
