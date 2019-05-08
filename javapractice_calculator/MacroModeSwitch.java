package javapractice_calculator;

import java.awt.Color;

class MacroModeSwitch extends CalcButton {

    boolean forSaveMode;//flag for checking this is switching to save or use

    MacroModeSwitch(String text, boolean isSave) {
        super(text);
        forSaveMode = isSave;
        this.setBackground(Color.RED);
    }
}