package javapractice_calculator;

import java.awt.Color;

class MacroButton extends CalcButton {

    private String savedMacro;

    MacroButton(String text) {
        super("Macro "+text);
        savedMacro="";
        this.setBackground(Color.LIGHT_GRAY);
    }

    public void setMacro(String text) {
        savedMacro = text;
    }

    public String getMacro() {
        return savedMacro;
    }
}