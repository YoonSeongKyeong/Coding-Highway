import java.awt.GridLayout;
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
    int flag = -1;// 현재 client의 상태와 권한을 나타낸다.
    // (-1): [게임 시작 전+이름을 등록하지 않은 상태]
    // (0): [게임 시작 전+채팅], (1): (낮) [채팅+투표], (2): (밤, 시민) [] (3): (밤, 마피아) [마피아
    // 채팅+마피아 투표]
    // (4): (밤, 경찰) [경찰 투표], (5): (밤, 의사) [의사 투표], (6): (죽은 사람 or 관전) []
    String []names = new String[8];// 8개의 id를 array에 저장해서 관리한다.
    int myId;// 내 id의 index 번호를 저장
    int myJob;// 내 직업의 번호를 저장 (2): [시민], (3): [마피아], (4): [경찰], (5): [의사], (6): [죽은 사람 or 관전]
    NameButton []nameButtons = new NameButton[8];// 8개의 nameButton을 array에 저장해서 관리한다.
    boolean isVotable;// 버튼이 각 턴마다 한번만 동작해야 하므로 투표 가능한지 여부를 관리한다.

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

    public class Chatting_Out extends JTextField implements KeyListener {// 채팅을 입력하는 공간

        private static final long serialVersionUID = 1L;

        Chatting_Out() {
            super(20);
            this.addKeyListener(this);
        }

        @Override
        public void keyPressed(KeyEvent e) {// 엔터 키를 누르면 채팅을 서버로 전송한다.
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String form = "";
                switch (flag) {
                    case -1:// 게임 시작 전 + 이름을 등록하지 않은 상태
                        form = "k";// 맨 처음 이름을 보내는 프로토콜의 format
                        flag=0;//이제 flag를 이름을 등록한 상태로 바꿔준다.
                        break;
                    case 0: // 게임 시작 전 - 일반 채팅
                    case 1: // 낮 - 일반 채팅
                        form = "l";// 일반 채팅을 보내는 프로토콜의 format
                        break;
                    case 2: // 밤 - 일반 시민
                    case 4: // 밤 - 경찰
                    case 5: // 밤 - 의사
                        return;// 아무것도 하지 않는다.
                    case 3: // 밤 - 마피아
                        form = "n";// 마피아 채팅을 보내는 프로토콜의 format
                        break;
                    case 6: // 유령 or 관전자 채팅
                        form = "r";
                        break;
                    default:
                        form = "z";// error sign when form is z
                        break;
                }
                try {
                    writer.println(form + "/" + text_out.getText());// format된 string을 서버로 전송한다.
                    writer.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    text_out.setText("");
                    text_out.requestFocus();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

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
            String form = "";
            switch (flag) {
                case -1:// 게임 시작 전 + 이름을 등록하지 않은 상태
                    form = "k";// 맨 처음 이름을 보내는 프로토콜의 format
                    flag=0;//이제 flag를 이름을 등록한 상태로 바꿔준다.
                    break;
                case 0: // 게임 시작 전 - 일반 채팅
                case 1: // 낮 - 일반 채팅
                    form = "l";// 일반 채팅을 보내는 프로토콜의 format
                    break;
                case 2: // 밤 - 일반 시민
                case 4: // 밤 - 경찰
                case 5: // 밤 - 의사
                    return;// 아무것도 하지 않는다.
                case 3: // 밤 - 마피아
                    form = "n";// 마피아 채팅을 보내는 프로토콜의 format
                    break;
                case 6: // 유령 or 관전자 채팅
                    form = "r";
                    break;
                default:
                    form = "z";// error sign when form is z
                    break;
            }
            try {
                writer.println(form + "/" + text_out.getText());// format된 string을 서버로 전송한다.
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                text_out.setText("");
                text_out.requestFocus();
            }
        }
    }

    public class NameButton extends JButton implements ActionListener {//투표할 때 사용하는 버튼이다.

        private static final long serialVersionUID = 1L;
        
        int indexOfUser;// 해당 버튼에 해당하는 유저의 index를 관리한다.
        boolean isDied;// 해당 버튼이 비활성화되었는지(유저가 죽어서) 여부를 관리한다.

        NameButton(int id) {
            super("waiting");
            this.addActionListener(this);
            isDied = false;
            indexOfUser = id;
        }
    
        void setId(String name) {// 서버에서 해당 버튼에 해당하는 id를 받아와서 버튼에 적는다.
            this.setText(name);
        }

        void setDie() {// 해당 버튼의 유저가 죽었을 때 사용한다.
            this.setText(this.getText()+"(Died)");
            isDied = true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {//버튼을 누르면 투표를 서버에 전송한다.
            String form = "";
            if(!isVotable||isDied) {//투표가 불가능한 경우엔 아무것도 하지 않는다.
                return;
            }
            isVotable = false;//투표는 한번만 가능하다.
            switch (flag) {
                case 0: // 게임 시작 전
                case 2: // 밤 - 일반 시민
                case 6: // 유령 or 관전자 
                    return; // 아무것도 하지 않는다.
                case 1: // 낮 - 일반 투표
                    form = "m";// 일반 투표를 보내는 프로토콜의 format
                    break;
                case 3: // 밤 - 마피아 투표
                    form = "o";// 마피아 투표를 보내는 프로토콜의 format
                    break;
                case 4: // 밤 - 경찰 투표
                    form = "p";// 경찰 투표를 보내는 프로토콜의 format
                    break;
                case 5: // 밤 - 의사 투표
                    form = "q";// 의사 투표를 보내는 프로토콜의 format
                    break;
                
                
                default:
                    form = "z";// error sign when form is z
                    break;
            }
            try {
                writer.println(form + "/" + indexOfUser);// format된 string을 서버로 전송한다.
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                text_out.setText("");
                text_out.requestFocus();
            }
        }

    }


    public class TransReciever {// 서버에서 오는 message를 parse하는 클래스
        
        char recieved_form;//받은 message의 format을 저장한다. (참고로 string은 비교할 때 char보다 더 힘들다. 주소가 아니라 값을 비교해야 해서 따로 함수를 써야 한다.)

        String recieved_contents;//받은 message의 contents를 저장한다.

        void trans_parse(String message) {//서버에서 받은 메세지를 parse해서 format과 contents로 나눈다.
            recieved_form = message.charAt(0);
            recieved_contents = message.substring(2);//format정보가 index 0, "/"가 index 1에 해당해서 index 2부터가 contents이다.
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

    public class Names_Panel extends JPanel {

        private static final long serialVersionUID = 1L;// Name Button들을 담고 있는 panel
        
        Names_Panel() {
            super();
            this.setLayout(new GridLayout(2,4));
            for(int i=0 ; i<8 ; i++) {//nameButtons를 초기화한다.
                nameButtons[i] = new NameButton(i);
                this.add(nameButtons[i]);
            }
        }

    }

    public class IncomingReader implements Runnable {// Runnable

        public void run() {
            String message;
            TransReciever trans = new TransReciever();
            try {
                while ((message = reader.readLine()) != null) {
                    trans.trans_parse(message);//translator가 message를 parse해준다.
                    int i;
                    switch (trans.recieved_form) {
                        case 'a'://아이디 받아오기 (index와 아이디 순으로 contents에 저장)
                            i = trans.recieved_contents.charAt(0)-'0';//char을 int로 바꾼다.
                            nameButtons[i].setId(trans.recieved_contents.substring(1));//아이디를 index번째 버튼에 저장한다.
                            break;
                        case 'b'://내 index 받아오기
                            myId = trans.recieved_contents.charAt(0)-'0';//char을 int로 바꾼다.
                            break;
                        case 'c'://일반 채팅
                            chatting_window.append("[ 일반채팅 ] "+ trans.recieved_contents +"\n");
                            break;
                        case 'd'://설명 받아오기
                            chatting_window.append("[ 설명 ] "+ trans.recieved_contents +"\n");
                            break;
                        case 'e'://내 직업 받아오기
                            myJob = trans.recieved_contents.charAt(0)-'0';//char을 int로 바꾼다.
                            break;
                        case 'f'://현재 게임 상태 변경하기
                            flag = trans.recieved_contents.charAt(0)-'0';//char을 int로 바꾼다.
                            break;
                        case 'g'://죽은 사람의 id 받아오기
                            i = trans.recieved_contents.charAt(0)-'0';//char을 int로 바꾼다.
                            nameButtons[i].setDie();
                            break;
                        case 'h'://공지 메세지 받아오기
                            chatting_window.append("[ 공지 ] "+ trans.recieved_contents +"\n");
                            break;
                        case 'i'://마피아 채팅
                            chatting_window.append("[ 마피아 ] "+ trans.recieved_contents +"\n");
                            break;
                        case 'j'://유령 채팅
                            chatting_window.append("[ 유령 ] "+ trans.recieved_contents +"\n");
                            break;
                        default://에러인 상황
                            break;
                    }
                }
            } catch (Exception ex) {
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
        Names_Panel nPanel = new Names_Panel();
        // make_Connection();
        panel.add(scroller);
        panel.add(text_out);
        panel.add(sendButton);
        panel.add(nPanel);

        Thread readerThread = new Thread(new IncomingReader());// 서버에서 새로운 채팅을 계속 읽어들이고 update하는 역할
        readerThread.start();

        frame.setVisible(true);

    }

    public void make_Connection() {
        try {
            sock = new Socket("127.0.0.1", 9999);// 서버 ip, 소켓 번호를 입력해서 서버와 연결한다.
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());// InputStreamReader와
                                                                                          // BufferedReader 사이의 bridge역할
            reader = new BufferedReader(streamReader);// 서버에서 읽어오는 역할
            writer = new PrintWriter(sock.getOutputStream());// 서버에 입력하는역할
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

// 받는 프로토콜 : (a)<맨 처음 아이디 목록 받아오기 + 새로 등록된 사용자의 아이디 받아오기>, (b)<내 숫자 id 받아오기>,
// (c)<일반 채팅>, (d)<설명 받아오기>
// (e)<내 직업 받아오기>, (f)<현재 게임 상태 변경하기>, (g)<죽은 사람 id 받아오기>, (h)<공지 메시지 받아오기>,
// (i)<마피아 채팅>, (j)<유령 채팅>
// 보내는 프로토콜 : (k)<맨 처음 아이디 입력>, (l)<일반 채팅>, (m)<일반 투표>, (n)<마피아 채팅>, (o)<마피아
// 투표>, (p)<경찰 투표>, (q)<의사 투표>, (r)<유령 채팅>
// 서버로 보내는 메시지 형식 : (프로토콜) + "/" + (내용)