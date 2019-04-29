package javapractice_calculator;

class NumButton extends OperandButton{
    int value;

    NumButton(String num) {
        super(num);
        value = Integer.parseInt(num);
    }

    int getVal() {
        return value;
    }
}