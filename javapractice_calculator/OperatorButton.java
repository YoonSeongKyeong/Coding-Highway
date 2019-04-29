package javapractice_calculator;
import java.awt.*;

class OperatorButton extends CalcButton {

    OperatorButton(String op) {
        super(op);
        this.setFont(new Font("Arial", Font.PLAIN, 30));
    }
}