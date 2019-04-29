package javapractice_calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javapractice_calculator.*;

class CalcV2{
    
    String operationString="";
    String macro1String="";
    String macro2String="";
    boolean isMacroSaveMode=true;

    /* operation String handling methods */
    public void addTerm(String newTerm) {//add Term to operationString
            operationString += newTerm;
    }
    public void deleteTerm() {//delete Term from operationString
        operationString = operationString.substring(0, operationString.length()-1);
    }
    public void clearString() {//clear the operationString
        operationString = "";
    }
    /* operation String handling methods */

    /* Macro handling methods */
    public void macro1Save(String currentStr) {//save current operationString to macro1String
        macro1String = currentStr;
    }

    public void macro2Save(String currentStr) {//save current operationString to macro2String
        macro2String = currentStr;
    }

    public void macro1Use() {//add macro1String to operationString
        addTerm(macro1String);
    }
    
    public void macro2Use() {//add macro2String to operationString
        addTerm(macro2String);
    }
    /* Macro handling methods */

    public void initializeGUI() {
        /* initializing Logic */
        Logic logic = new Logic();
        /* initializing Logic */

        /* for frequently used buttonActionListener Type */
        CalcButton [] idAddingButtonCollector = new CalcButton[20];//maximum num of collector = 20
        int numOfCollectedButton=0;
        /* for frequently used buttonActionListener Type */

        /**frame**/
        JFrame frame = new JFrame("My Calculator");//making Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 800);//set Bound to Frame
        /**frame**/

        /**pane**/
        Container pane = frame.getContentPane();//get pane from frame
        pane.setLayout( new BorderLayout());
        /**pane**/

        /**textField**/
        JTextField textInterface = new JTextField("0");//making textField to Interact
        textInterface.setFont(textInterface.getFont().deriveFont(25.0F));
        textInterface.setHorizontalAlignment(JLabel.RIGHT);
        pane.add(textInterface, "North");//add textField to uppermost position of pane
        textInterface.setPreferredSize(new Dimension(1,80));//set relative height of textField
        /**textField**/

        /**button Container**/
        Container buttonContainer = new Container();//making container for all buttons
        buttonContainer.setLayout( new BorderLayout());
        pane.add(buttonContainer, "Center");//add ButtonContainer to Center position of pane
        /**button Container**/

        /**upper Container**/
        Container upperContainer = new Container();
        upperContainer.setLayout( new GridLayout(0,4));
        buttonContainer.add(upperContainer, "North");//add upperContainer to uppermost position of buttonContainer
        upperContainer.setPreferredSize(new Dimension(1,80));//set relative height of upperContainer

        OperatorButton clearButton = new OperatorButton("C");//creating buttons for uppercontainer

        /* ActionListener For clearButton */
        clearButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                clearString();
                textInterface.setText(operationString);
            }
        });
        /* ActionListener For clearButton */

        ParenthesisButton parenOpenButton = new ParenthesisButton("(", true);

        /* ActionListener For parenOpenButton */
        idAddingButtonCollector[numOfCollectedButton++] = parenOpenButton;//collected to idAddingButtonCollector
        /* ActionListener For parenOpenButton */

        ParenthesisButton parenCloseButton = new ParenthesisButton(")", false);

        /* ActionListener For parenCloseButton */
        idAddingButtonCollector[numOfCollectedButton++] = parenCloseButton;//collected to idAddingButtonCollector
        /* ActionListener For parenCloseButton */

        OperatorButton delButton = new OperatorButton("DEL");

        /* ActionListener For delButton */
        delButton.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTerm();
                textInterface.setText(operationString);
            }
        });
        /* ActionListener For delButton */

        upperContainer.add(clearButton);//adding buttons to uppercontainer
        upperContainer.add(parenOpenButton);
        upperContainer.add(parenCloseButton);
        upperContainer.add(delButton);
        /**upper Container**/

        /**center Container**/
        Container centerContainer= new Container();
        centerContainer.setLayout( new GridLayout(0,3));
        buttonContainer.add(centerContainer, "Center");//add centerContainer to center position of buttonContainer

        CalcButton [] numberButtons = new CalcButton[10];//creating number buttons: index is number
        for(int i=0; i<10; i++) {
            numberButtons[i] = new NumButton(Integer.toString(i));

            /* ActionListener For NumberButtons */
            idAddingButtonCollector[numOfCollectedButton++] = numberButtons[i];//collected to idAddingButtonCollector
            /* ActionListener For NumberButtons */

        }
        CalcButton [] negAndPoint = new CalcButton[2];//creating negate and point button
        negAndPoint[0] = new SpecialOperand("-");

        /* ActionListener For negateButton */
        idAddingButtonCollector[numOfCollectedButton++] = negAndPoint[0];//collected to idAddingButtonCollector
        /* ActionListener For negateButton */

        negAndPoint[1] = new SpecialOperand(".");

        /* ActionListener For pointButton */
        idAddingButtonCollector[numOfCollectedButton++] = negAndPoint[1];//collected to idAddingButtonCollector
        /* ActionListener For pointButton */
        
        for(int i=0; i<3; i++) {//adding buttons to centerContainer
            for(int j=0; j<3; j++) {
                centerContainer.add(numberButtons[7+j-3*i]);//add Number buttons, sequence: 789456123
            }
        }
        centerContainer.add(negAndPoint[0]);//add Negative button
        centerContainer.add(numberButtons[0]);//add Zero button
        centerContainer.add(negAndPoint[1]);//add Point button
        /**center Container**/

        /**west Container**/
        Container westContainer = new Container();
        westContainer.setLayout( new GridLayout(4,0));
        buttonContainer.add(westContainer, "West");//add westContainer to west position of buttonContainer

        CalcButton [] macroButtons = new CalcButton[4];//creating macro buttons : index is id of macro button

        macroButtons[0] = new MacroButton("0");//create macro button

        /* ActionListener For macroButton1 */
        macroButtons[0].addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if(isMacroSaveMode) {//macro save mode
                    macro1Save(textInterface.getText());
                }
                else{                //macro use mode
                    macro1Use();
                    textInterface.setText(operationString);
                }
            }
        });
        /* ActionListener For macroButton1 */

        macroButtons[1] = new MacroButton("1");

        /* ActionListener For macroButton2 */
        macroButtons[1].addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {

                if(isMacroSaveMode) {//macro save mode
                    macro2Save(textInterface.getText());
                }
                else{                //macro use mode
                    macro2Use();
                    textInterface.setText(operationString);
                }
            }
        });
        /* ActionListener For macroButton2 */

        macroButtons[2] = new MacroModeSwitch("Save Mode", true);//create macro save switch

        /* ActionListener For macro save switch */
        macroButtons[2].addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {

                isMacroSaveMode=true;
            }
        });
        /* ActionListener For macro save switch */

        macroButtons[3] = new MacroModeSwitch("Use Mode", false);//create macro save switch

        /* ActionListener For macro use switch */
        macroButtons[3].addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {

                isMacroSaveMode=false;
            }
        });
        /* ActionListener For macro use switch */

        for(int i=0 ; i<4 ; i++) {
            westContainer.add(macroButtons[i]);
        }
        westContainer.setPreferredSize(new Dimension(200,1));//set relative size of westContainer
        /**west Container**/

        /**east Container**/
        Container eastContainer= new Container();
        eastContainer.setLayout( new GridLayout(4,0));
        buttonContainer.add(eastContainer, "East");//add eastContainer to east position of buttonContainer

        CalcButton [] divMultSubAddButtons = new CalcButton[4];//creating divide,multiply,subtract,add buttons
        divMultSubAddButtons[0] = new OperatorButton("/");
        divMultSubAddButtons[1] = new OperatorButton("*");
        divMultSubAddButtons[2] = new OperatorButton("-");
        divMultSubAddButtons[3] = new OperatorButton("+");

        for(int i=0; i<4; i++) {

            /* ActionListener For divMultSubAddButtons */
            idAddingButtonCollector[numOfCollectedButton++] = divMultSubAddButtons[i];//collected to idAddingButtonCollector
            /* ActionListener For divMultSubAddButtons */

        }
        
        eastContainer.add(divMultSubAddButtons[0]);//adding buttons to eastContainer
        eastContainer.add(divMultSubAddButtons[1]);
        eastContainer.add(divMultSubAddButtons[2]);
        eastContainer.add(divMultSubAddButtons[3]);
        eastContainer.setPreferredSize(new Dimension(130,1));//set relative size of eastContainer
        /**east Container**/
        
        /**Result Dialogue**/

        JDialog resultDialog = new JDialog(frame, "Result History");
        resultDialog.setBounds(850, 0, 700, 800);

        /**Result Panel**/
        JPanel resultPanel = new JPanel();
        resultDialog.add(resultPanel);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        /**Result Panel**/

        /**Result Dialogue**/

        /**equal Button**/
        JButton equalButton = new OperatorButton("=");//creating equal button

        /* ActionListener For equal button */
        equalButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = operationString + " = ";
                boolean noError = logic.checker(operationString);
                if(noError) {
                    logic.preprocess();
                    logic.stringToPostfix();
                    result += Double.toString(logic.postfixToResult());
                }
                else {
                    result = "error";
                }
                textInterface.setText(result);//show result
                operationString="";//clear the input
                resultPanel.add(new resultLabel(result));
                resultPanel.revalidate();
            }
        });
        /* ActionListener For equal button */

        buttonContainer.add(equalButton, "South");//add equal button to south position of buttonContainer
        equalButton.setPreferredSize(new Dimension(1,100));//set relative height of equalButton
        /**equal Button**/

        /**frequently used ActionListener for adding ID to text**/
        class IDAddActionListener implements ActionListener{//frequently used action : add id to operationString
            @Override
            public void actionPerformed(ActionEvent e) {
                addTerm(((CalcButton) e.getSource()).getId());//get Id of button and add to operationString
                    textInterface.setText(operationString);//update textField
            }
        }
        /**frequently used ActionListener for adding text**/
        
        /* attach IDAddActionListener to collected buttons */
        for(int i=0 ; i<numOfCollectedButton ; i++) {
            idAddingButtonCollector[i].addActionListener(new IDAddActionListener());
        }
        /* attach IDAddActionListener to collected buttons */

        /**set visible**/
        frame.setVisible(true);//set frame visible
        resultDialog.setVisible(true);//set dialogue visible
        /**set visible**/
    }

    public static void main(String[] args) {
        CalcV2 calculator = new CalcV2();
        calculator.initializeGUI();
    }
}