package javapractice_calculator;

class MacroModeSwitch extends CalcButton{

    boolean forSaveMode;//flag for checking this is switching to save or use

    MacroModeSwitch(String text, boolean isSave) {
        super(text);
        forSaveMode = isSave;
    }
}