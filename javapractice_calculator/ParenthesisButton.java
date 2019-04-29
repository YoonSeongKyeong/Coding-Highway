package javapractice_calculator;

class ParenthesisButton extends OperatorButton {

    private boolean open;// '(' : true , ')' : false

    ParenthesisButton(String op, boolean isOpen) {
        super(op);
        open = isOpen;
    }

    public boolean isopen() {
        return open;
    }
}