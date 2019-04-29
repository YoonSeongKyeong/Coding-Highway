package javapractice_calculator.oldversion;

import javax.swing.*;
import java.awt.*;

public class OldCalc{

    public static void main(String[] args) {
        JFrame frame = new JFrame("My Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel textInterface = new JLabel("330  ");
        textInterface.setFont(textInterface.getFont().deriveFont(25.0F));
        textInterface.setHorizontalAlignment(JLabel.RIGHT);
        Container pane = frame.getContentPane();
        Container buttons = new Container();
        pane.setLayout( new BorderLayout());
        buttons.setLayout( new GridLayout(0,4));
        pane.add(textInterface, "North");
        pane.add(buttons, "Center");
        frame.setBounds(0, 0, 500, 500);
        JButton num1 = new JButton("1");
        JButton num2 = new JButton("2");
        JButton num3 = new JButton("3");
        JButton num4 = new JButton("4");
        JButton num5 = new JButton("5");
        JButton num6 = new JButton("6");
        JButton num7 = new JButton("7");
        JButton num8 = new JButton("8");
        JButton num9 = new JButton("9");
        JButton num0 = new JButton("0");
        JButton bPlus = new JButton("+");
        JButton bMinus = new JButton("-");
        JButton bMult = new JButton("*");
        JButton bDiv = new JButton("/");
        JButton bDel = new JButton("DEL");
        JButton bDot = new JButton(".");
        JButton bEq = new JButton("=");
        num1.setFont(new Font("Arial", Font.PLAIN, 20));
        num2.setFont(new Font("Arial", Font.PLAIN, 20));
        num3.setFont(new Font("Arial", Font.PLAIN, 20));
        num4.setFont(new Font("Arial", Font.PLAIN, 20));
        num5.setFont(new Font("Arial", Font.PLAIN, 20));
        num6.setFont(new Font("Arial", Font.PLAIN, 20));
        num7.setFont(new Font("Arial", Font.PLAIN, 20));
        num8.setFont(new Font("Arial", Font.PLAIN, 20));
        num9.setFont(new Font("Arial", Font.PLAIN, 20));
        num0.setFont(new Font("Arial", Font.PLAIN, 20));
        bPlus.setFont(new Font("Arial", Font.PLAIN, 30));
        bMinus.setFont(new Font("Arial", Font.PLAIN, 30));
        bMult.setFont(new Font("Arial", Font.PLAIN, 30));
        bDiv.setFont(new Font("Arial", Font.PLAIN, 30));
        bDel.setFont(new Font("Arial", Font.PLAIN, 20));
        bDot.setFont(new Font("Arial", Font.PLAIN, 30));
        bEq.setFont(new Font("Arial", Font.PLAIN, 30));
        buttons.add(num1);
        buttons.add(num2);
        buttons.add(num3);
        buttons.add(bPlus);
        buttons.add(num4);
        buttons.add(num5);
        buttons.add(num6);
        buttons.add(bMinus);
        buttons.add(num7);
        buttons.add(num8);
        buttons.add(num9);
        buttons.add(bMult);
        buttons.add(bDot);
        buttons.add(num0);
        buttons.add(bDel);
        buttons.add(bDiv);
        pane.add(bEq, "South");
        textInterface.setPreferredSize(new Dimension(1,70));
        bEq.setPreferredSize(new Dimension(1,80));
        frame.setVisible(true);
    }

    public void go() {
    }
    //소숫점도 한번에 두개이상 못넣게 한다.(소숫점이 이미 있을때는 소숫점 버튼을 못넣게 한다.)
    //연산자를 한번에 두개 이상 못넣게 한다. (두번 이상 누르면 최근에 누른 연산자로 업데이트된다.)
    //모든 수식을 입력하고 = 버튼을 누르면 수식 = 값 으로 label을 업데이트한다.
    //나중에 한번에 계산해서 업데이트해준다. 나중에 '(', ')' 도 넣어줄 수 있다.
}