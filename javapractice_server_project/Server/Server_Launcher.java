import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server_Launcher {

    public static void main(String[] args) {
        new Server_Launcher().go();
    }


    /*** inner class에서 접근해야 하는 object들은 여기에서 instance variable로 선언해서 관리한다. ***/

    PrintWriter []clientOutputStreams = new PrintWriter[8];//client들의 outputstream들을 모아서 보관하는 array
    String []names = {"waiting", "waiting", "waiting", "waiting", "waiting", "waiting", "waiting", "waiting"};// 8개의 id를 array에 저장해서 관리한다.
    int []jobs = new int[8];//8명 각자의 job을 array에 저장해서 관리한다. (2): [시민], (3): [마피아], (4): [경찰], (5): [의사], (6): [죽은 사람 or 관전]
    int phase = -1;// 현재 게임이 어떤 단계인지 나타내는 flag이다.
    // (-1): [게임 시작 전 + 모든 이름이 준비되진 않은 단계]
    // (0): [게임 시작 전 + 각자에게 직업을 알려주는 단계], (1): (낮) [채팅+투표], (2): [종료조건 확인 + 밤 세팅 단계] (3): (밤, 마피아) [마피아 채팅+마피아 투표]
    // (4): (밤, 경찰) [경찰 투표], (5): (밤, 의사) [의사 투표], (6): [종료조건 확인 + 낮 세팅 단계]
    int numOfReady=0;//이름을 내고 ready 상태인 사람의 수(맨 처음 시작하는 조건)
    int alive=8;// 살아있는 사람 수
    int mafiaNum=3;//마피아 수
    int[] poles = new int[8];//시민투표나 마피아 투표시에 사용하는 array
    int numOfPoles = 0;//현재까지 투표 갯수
    String []jobNames = {//경찰에게 직업을 알려줘야 해서 만들었다.
        "0번 인덱스",
        "1번 인덱스",
        "시민",
        "마피아",
        "경찰",
        "의사"
    };
    int victim_index;//마피아에게 지목된 사람의 index
    boolean pole_fail;//마피아 혹은 시민의 투표가 동점자가 나와서 실패했을 때
    int heal_index;//의사에게 지목된 사람의 index
    boolean not_finished = true;//페이즈 루프의 조건
    
    /*** inner class에서 접근해야 하는 object들은 여기에서 instance variable로 선언해서 관리한다. ***/

    public class TransReciever {// 서버에서 오는 message를 parse하는 클래스
        
        char recieved_form;//받은 message의 format을 저장한다. (참고로 string은 비교할 때 char보다 더 힘들다. 주소가 아니라 값을 비교해야 해서 따로 함수를 써야 한다.)

        String recieved_contents;//받은 message의 contents를 저장한다.

        void trans_parse(String message) {//클라이언트에서 받은 메세지를 parse해서 format과 contents로 나눈다.
            recieved_form = message.charAt(0);
            recieved_contents = message.substring(2);//format정보가 index 0, "/"가 index 1에 해당해서 index 2부터가 contents이다.
        }
    }

    public class Client_Reciever implements Runnable {
        BufferedReader reader;
        Socket sock;
        int index;//클라이언트의 index정보를 갖는다.
        public Client_Reciever(Socket clientSocket, int idx) {//클라이언트에게서 읽어올 수 있게 연결한다.
            index = idx;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        public void run() {
            String message;
            TransReciever trans = new TransReciever();
            try {
                while ((message = reader.readLine()) != null) {
                    trans.trans_parse(message);
                    switch (trans.recieved_form) {
                        case 'k':// 클라이언트에서 새로 지은 아이디를 보냄
                            names[index] = trans.recieved_contents;//해당 클라이언트의 name을 names에 저장
                            break;
                        case 'l':// 클라이언트에서 일반 채팅을 보냄
                            try {
                                for(int i=0 ; i<8 ; i++) {
                                    PrintWriter writer = clientOutputStreams[i];
                                    writer.println("l/"+message);
                                    writer.flush();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case 'm':// 클라이언트에서 일반 투표를 보냄
                            synchronized(this) {
                                poles[Integer.parseInt(trans.recieved_contents)]++;
                                numOfPoles++;
                            }
                            break;
                        case 'n':// 클라이언트에서 마피아 채팅을 보냄
                            try {
                                for(int i=0 ; i<8 ; i++) {
                                    if(jobs[i]!= 3) {//마피아가 아니면 다음으로
                                        continue;
                                    }
                                    PrintWriter writer = clientOutputStreams[i];
                                    writer.println("i/"+message);
                                    writer.flush();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case 'o':// 클라이언트에서 마피아 투표를 보냄
                            synchronized(this) {
                                poles[Integer.parseInt(trans.recieved_contents)]++;
                                numOfPoles++;
                            }
                            break;
                        case 'p':// 클라이언트에서 경찰 투표를 보냄
                            clientOutputStreams[index].println("d/"+names[trans.recieved_contents]+"의 직업은 "+jobNames[trans.recieved_contents]+" 입니다.");
                            clientOutputStreams[index].flush();//해당 경찰에게만 알려준다.
                            finish_phase(4);//경찰의 phase를 마친다.
                            jump_to_phase(5);//의사의 phase로 넘어간다.
                            break;
                        case 'q':// 클라이언트에서 의사 투표를 보냄
                            heal_index = Integer.parseInt(trans.recieved_contents);
                            finish_phase(5);//의사의 phase를 마친다.
                            break;
                        case 'r':// 클라이언트에서 유령 채팅을 보냄
                            try {
                                for(int i=0 ; i<8 ; i++) {
                                    if(jobs[i]!= 6) {//유령이 아니면 다음으로
                                        continue;
                                    }
                                    PrintWriter writer = clientOutputStreams[i];
                                    writer.println("j/"+message);
                                    writer.flush();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                    
                        default:
                            break;
                    }
                }  
            } catch(Exception ex) {
                ex.printStackTrace();
            }      
        }
    }

    public void jump_to_phase( int phase_number ) {//해당 phase로 넘어가는 method이다.
        phase = phase_number;
    }

    public void finish_phase( int phase_number ) {//해당 phase를 마무리하는 method이다.

    }

    public void go() {
        int number_of_connections = 0;
        try {
            ServerSocket serverSock = new ServerSocket(9999);//서버의 소켓을 설정한다.
            while(number_of_connections < 8) {//8번의 연결이 있을 때까지 연결을 받는다.
                Socket clientSocket = serverSock.accept();//클라이언트의 연결요청을 받는다.
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());//해당 클라이언트에 쓸 수 있는 writer
                clientOutputStreams[number_of_connections] = writer;//writer를 해당 인덱스의 자리에 넣는다.
                /** initialize **/
                for(int i=0 ; i<number_of_connections ; i++) {//지금까지의 아이디 정보를 클라이언트에게 전송한다.
                    writer.println("a/"+i+names[i]);//" a / 인덱스 아이디 " 순이다.
                }
                writer.println("b/"+number_of_connections);//클라이언트의 index 정보를 전송한다.
                writer.println("d/아이디를 입력해주세요.");//아이디를 입력해달라고 클라이언트에게 요청
                writer.flush();
                /** initialize **/
                Thread t = new Thread(new Client_Reciever(clientSocket, number_of_connections));//해당 클라이언트와 통신하는 thread를 생성한다.
                t.start();
                number_of_connections++;//connection된 수를 늘린다.
            }
            serverSock.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 모든 사람들이 연결되었다.
        while(numOfReady<8) {Thread.sleep(3000);}//모든 사람들이 아이디를 보내기 전까지 sleep
        // 모든 사람들이 아이디를 보냈다.
        try {
            for(int i=0 ; i<8 ; i++) {//모든 사람에게 아이디를 전달한다. phase -1이 완료되었다.
                PrintWriter writer = clientOutputStreams[i];
                writer.println("a/"+message);
                writer.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //게임 시작
        jump_to_phase(0);// 직업을 랜덤하게 set한다.
        finish_phase(0);// 각자의 직업을 모두에게 알려준다.
        while(not_finished) {
            jump_to_phase(1);// 낮의 투표를 set한다. (투표를 초기화하고 공지로 "낮이 되었습니다. 시민들은 마피아로 의심되는 사람을 투표해주세요." 라는 문구 프린트)
            finish_phase(1);// 낮의 투표를 close한다. (투표 결과를 확인하고 pole_fail이 아니면 선택된 사람을 유령으로 만든다.(flag를 6으로) 게임 종료 조건을 확인한다.)
            if(!not_finished) {break;}// 종료조건일 때 break한다.
            jump_to_phase(2);// 밤을 setting한다. flag가 6이 아닌 각 클라이언트의 flag를 자신의 job과 같게 만든다. 
            jump_to_phase(3);//마피아 투표를 set한다. (투표를 초기화하고 "마피아는 죽일 사람을 투표해주세요" 라는 문구 프린트)
            finish_phase(3);//마피아 투표를 close한다. (투표 결과를 확인하고 pole_fail이 아니면 선택된 사람을 victim_index에 넣는다.)
            jump_to_phase(4);//경찰에게 투표 안내 메세지를 보낸다. 경찰이 투표 결과를 확인하면서 자동으로 의사의 투표로 들어간다. jump_to_phase(5)를 call한다.
            while(phase!=1) {//의사가 투표를 마치면서 finish phase(5)를 call한다. 그 이후 phase가 끝나면 투표정보를 비교해가면서 지난 밤에 어떤 일이 있었는지 출력하고 
                             // 게임 종료 조건을 확인해서 not_finished를 갱신한다. 맨 마지막으로 phase를 1로 바꿔준다.
                Thread.sleep(3000);
            }
        }
        jump_to_phase(1);


    // (0): [게임 시작 전 + 각자에게 직업을 알려주는 단계], (1): (낮) [채팅+투표], (2): [종료조건 확인 + 밤 세팅 단계] (3): (밤, 마피아) [마피아 채팅+마피아 투표]
    // (4): (밤, 경찰) [경찰 투표], (5): (밤, 의사) [의사 투표], (6): [종료조건 확인 + 낮 세팅 단계]


    }

    public void tellEveryone(String message) {
        try {
            for(int i=0 ; i<8 ; i++) {
                PrintWriter writer = clientOutputStreams[i];
                writer.println(message);
                writer.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    

}