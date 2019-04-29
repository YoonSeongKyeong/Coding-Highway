package javapractice_calculator;

class MacroButton extends CalcButton {

    private String savedMacro;

    MacroButton(String text) {
        super("Macro "+text);
        savedMacro="";
    }

    public void setMacro(String text) {
        savedMacro = text;
    }

    public String getMacro() {
        return savedMacro;
    }
}