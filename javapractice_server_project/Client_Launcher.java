import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Client_Launcher {

    public static void main(String[] args) {// 메인 메소드
        Client_Launcher launcher = new Client_Launcher();
        launcher.GUI_initialize();
    }

    /*** inner class에서 접근해야 하는 object들은 여기에서 instance variable로 선언해서 관리한다. ***/

    Chatting_Window chatting_window;// 채팅창
    Chatting_Out text_out;// 댓글창
    BufferedReader reader;// 서버에서 읽어오는 역할 chain stream
    PrintWriter writer;// 서버로 쓰는 역할 chain stream
    Socket sock;// 소켓

    /*** inner class에서 접근해야 하는 object들은 여기에서 instance variable로 선언해서 관리한다. ***/

    public class Chatting_Window extends JTextArea {// 채팅이 표시되는 채팅창

        private static final long serialVersionUID = 1L;

        Chatting_Window() {
            super(15, 50);
            this.setLineWrap(true);
            this.setWrapStyleWord(true);
            this.setEditable(false);
        }
    }

    public class Chatting_Scroller extends JScrollPane {// 스크롤러로 채팅창을 감싼다.

        private static final long serialVersionUID = 1L;

        Chatting_Scroller(JTextArea textBox) {
            super(textBox);
            this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);// 수직 방향으로만 스크롤되게 한다.
            this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        }
    }

    public class Chatting_Out extends JTextField implements KeyListener{// 채팅을 입력하는 공간

        private static final long serialVersionUID = 1L;

        Chatting_Out() {
            super(20);
            this.addKeyListener(this);
        }
        @Override
        public void keyPressed(KeyEvent e) {// 엔터 키를 누르면 채팅을 서버로 전송한다.
            if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                try {
                    writer.println(text_out.getText());
                    writer.flush();
                    text_out.setText("");
                    text_out.requestFocus();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
           }
        }

    }

    public class SendButton extends JButton implements ActionListener {// 채팅을 전송하는 버튼

        private static final long serialVersionUID = 1L;

        SendButton() {
            super("Send");
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {// 버튼을 누르면 채팅을 서버로 전송한다.
            try {
                writer.println(text_out.getText());
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                text_out.setText("");
                text_out.requestFocus();
            }
        }
    }

    public class Main_Frame extends JFrame {// 프레임

        private static final long serialVersionUID = 1L;

        Main_Frame() {
            super("Mafia Game");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setBounds(300, 300, 600, 500);// set Bound to Frame
        }

    }

    public class Main_Panel extends JPanel {// 패널

        private static final long serialVersionUID = 1L;

        Main_Panel() {
            super();
        }
    }

    public class IncomingReader implements Runnable {//Runnable

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message); 
                    chatting_window.append(message + "\n"); 
                }  
            } catch(Exception ex) {
                ex.printStackTrace();
            }      
        }
    }

    public void GUI_initialize() {

        Main_Frame frame = new Main_Frame();
        Main_Panel panel = new Main_Panel();
        frame.getContentPane().add(panel);
        chatting_window = new Chatting_Window();
        Chatting_Scroller scroller = new Chatting_Scroller(chatting_window);
        text_out = new Chatting_Out();
        SendButton sendButton = new SendButton();
        make_Connection();
        panel.add(scroller);
        panel.add(text_out);
        panel.add(sendButton);

        Thread readerThread = new Thread(new IncomingReader());//서버에서 새로운 채팅을 계속 읽어들이고 update하는 역할
        readerThread.start();

        frame.setVisible(true);

    }

    public void make_Connection() {
        try {
            sock = new Socket("127.0.0.1", 9999);// 서버 ip, 소켓 번호를 입력해서 서버와 연결한다.
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());//InputStreamReader와 BufferedReader 사이의 bridge역할
            reader = new BufferedReader(streamReader);//서버에서 읽어오는 역할
            writer = new PrintWriter(sock.getOutputStream());//서버에 입력하는역할
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}