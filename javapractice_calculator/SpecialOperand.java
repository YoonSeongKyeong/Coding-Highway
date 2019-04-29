package javapractice_calculator;
import java.awt.*;

class SpecialOperand extends OperandButton {// negative , point

    SpecialOperand(String text) {
        super(text);
        this.setFont(new Font("Arial", Font.PLAIN, 30));
    }
}
