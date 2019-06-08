import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server_Launcher {

    /**
	 *
	 */
	
	private static final String 당신의_투표가_활성화되었습니다 = "당신의 투표가 활성화되었습니다.";

	public static void main(String[] args) {
        new Server_Launcher().go();
    }

    /*** inner class에서 접근해야 하는 object들은 여기에서 instance variable로 선언해서 관리한다. ***/

    PrintWriter[] clientOutputStreams = new PrintWriter[8];// client들의 outputstream들을 모아서 보관하는 array
    String[] names = { "waiting", "waiting", "waiting", "waiting", "waiting", "waiting", "waiting", "waiting" };// 8개의
                                                                                                                // id를
                                                                                                                // array에
                                                                                                                // 저장해서
                                                                                                                // 관리한다.
    String[] jobText = {"0번 인덱스", "1번 인덱스", "당신의 직업은 시민입니다. 투표를 통해서 마피아를 모두 제거하면 승리합니다.",
    "당신의 직업은 마피아입니다. 시민에게 걸리지 않게 최대한 많은 시민들을 밤에 처리하세요", "당신의 직업은 경찰입니다. 밤동안 누가 마피아인지 알아내세요",
    "당신의 직업은 의사입니다. 마피아에게 지목당할 것 같은 사람을 살려주세요"};// 직업 설명
    int[] jobs = new int[8];// 8명 각자의 job을 array에 저장해서 관리한다. (2): [시민], (3): [마피아], (4): [경찰], (5): [의사],
                            // (6): [죽은 사람 or 관전]
    int phase = -1;// 현재 게임이 어떤 단계인지 나타내는 flag이다.
    // (-1): [게임 시작 전 + 모든 이름이 준비되진 않은 단계]
    // (0): [게임 시작 전 + 각자에게 직업을 알려주는 단계], (1): (낮) [채팅+투표], (2): [종료조건 확인 + 밤 세팅 단계]
    // (3): (밤, 마피아) [마피아 채팅+마피아 투표]
    // (4): (밤, 경찰) [경찰 투표], (5): (밤, 의사) [의사 투표]
    int numOfReady = 0;// 이름을 내고 ready 상태인 사람의 수(맨 처음 시작하는 조건)
    int alive = 8;// 살아있는 사람 수
    int mafiaNum = 3;// 마피아 수
    int[] polls = new int[8];// 시민투표나 마피아 투표시에 사용하는 array
    int numOfPolls = 0;// 현재까지 투표 갯수
    String[] jobNames = { // 경찰에게 직업을 알려줘야 해서 만들었다.
            "0번 인덱스", "1번 인덱스", "시민", "마피아", "경찰", "의사" };
    int victim_index;// 마피아에게 지목된 사람의 index
    boolean pole_fail;// 마피아 혹은 시민의 투표가 동점자가 나와서 실패했을 때
    int heal_index;// 의사에게 지목된 사람의 index
    boolean not_finished = true;// 페이즈 루프의 조건

    /*** inner class에서 접근해야 하는 object들은 여기에서 instance variable로 선언해서 관리한다. ***/

    public class TransReciever {// 서버에서 오는 message를 parse하는 클래스

        char recieved_form;// 받은 message의 format을 저장한다. (참고로 string은 비교할 때 char보다 더 힘들다. 주소가 아니라 값을 비교해야 해서
                           // 따로 함수를 써야 한다.)

        String recieved_contents;// 받은 message의 contents를 저장한다.

        void trans_parse(String message) {// 클라이언트에서 받은 메세지를 parse해서 format과 contents로 나눈다.
            recieved_form = message.charAt(0);
            recieved_contents = message.substring(2);// format정보가 index 0, "/"가 index 1에 해당해서 index 2부터가 contents이다.
        }
    }

    public class Client_Reciever implements Runnable {
        BufferedReader reader;
        Socket sock;
        int index;// 클라이언트의 index정보를 갖는다.

        public Client_Reciever(Socket clientSocket, int idx) {// 클라이언트에게서 읽어올 수 있게 연결한다.
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
                        names[index] = trans.recieved_contents;// 해당 클라이언트의 name을 names에 저장
                        synchronized (this) {//레디한 사람의 수를 늘린다. (synchronized됨)
                            numOfReady++;
                        }
                        for (int i = 0; i < numOfReady ; i++) {// 공지로 "현재 아이디 입력한 사람 : "+ numOfReady +"/8"를 프린트
                            PrintWriter writer = clientOutputStreams[i];
                            writer.println("h/" + "현재 아이디 입력한 사람 : "+ numOfReady +"/8");// " h / 공지 " 순이다.
                            
                            writer.flush();
                        }
                        break;
                    case 'l':// 클라이언트에서 일반 채팅을 보냄
                            for (int i = 0; i < numOfReady; i++) {// 다 오지 않아도 일반 채팅이 돌아가야 한다.
                                try {
                                PrintWriter writer = clientOutputStreams[i];
                                writer.println("c/" +"\"" +names[index]+"\": "+ trans.recieved_contents);
                                writer.flush();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } 
                            
                        break;
                    case 'm':// 클라이언트에서 일반 투표를 보냄 (synchronized됨)
                        synchronized (this) {
                            polls[Integer.parseInt(trans.recieved_contents)]++;
                            numOfPolls++;
                        }
                        clientOutputStreams[index].println("d/" + "당신의 시민 투표가 제출되었습니다." );// " d / 설명 " 순이다.
                        break;
                    case 'n':// 클라이언트에서 마피아 채팅을 보냄
                        try {
                            for (int i = 0; i < 8; i++) {
                                if (jobs[i] != 3) {// 마피아가 아니면 다음으로
                                    continue;
                                }
                                PrintWriter writer = clientOutputStreams[i];
                                writer.println("i/" +"\"" +names[index]+"\": "+ trans.recieved_contents);
                                writer.flush();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 'o':// 클라이언트에서 마피아 투표를 보냄 (synchronized됨)
                        synchronized (this) {
                            polls[Integer.parseInt(trans.recieved_contents)]++;
                            numOfPolls++;
                        }
                        clientOutputStreams[index].println("d/" + "당신의 마피아 투표가 제출되었습니다." );// " d / 설명 " 순이다.
                        break;
                    case 'p':// 클라이언트에서 경찰 투표를 보냄
                        clientOutputStreams[index].println("d/" + "당신의 경찰 투표가 제출되었습니다." );// " d / 설명 " 순이다.
                        clientOutputStreams[index].println("d/" + names[Integer.parseInt(trans.recieved_contents)]
                                + "의 직업은 " + jobNames[Integer.parseInt(trans.recieved_contents)] + " 입니다.");
                        clientOutputStreams[index].flush();// 해당 경찰에게만 알려준다.
                        finish_phase(4);// 경찰의 phase를 마친다.
                        jump_to_phase(5);// 의사의 phase로 넘어간다.
                        break;
                    case 'q':// 클라이언트에서 의사 투표를 보냄
                        clientOutputStreams[index].println("d/" + "당신의 의사 투표가 제출되었습니다." );// " d / 설명 " 순이다.
                        heal_index = Integer.parseInt(trans.recieved_contents);
                        finish_phase(5);// 의사의 phase를 마친다.
                        break;
                    case 'r':// 클라이언트에서 유령 채팅을 보냄
                        try {
                            for (int i = 0; i < 8; i++) {
                                if (jobs[i] != 6) {// 유령이 아니면 다음으로
                                    continue;
                                }
                                PrintWriter writer = clientOutputStreams[i];
                                writer.println("j/" +"\"" +names[index]+"\": "+ trans.recieved_contents);
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void jump_to_phase(int phase_number) {// 해당 phase로 넘어가는 method이다.
        phase = phase_number;
        switch (phase_number) {
            case 0:// 직업을 랜덤하게 set한다.
                boolean []isJobSet = new boolean[8];//job이 결정됐는지 tracking하는 array
                for(int i=0 ; i <8 ; i++) {//모두 false로 결정.
                    isJobSet[i]= false;
                }
                int numOfMafiaSet=0;//결정된 마피아의 수
                int newJobsIndex=0;//새로운 job을 줄 사람의 index
                Random generator = new Random();
                while(numOfMafiaSet<3) {//마피아는 3명 결정
                    newJobsIndex = generator.nextInt(8);
                    if(!isJobSet[newJobsIndex]) {//아직 새로운 index의 직업이 없다면
                        jobs[newJobsIndex]=3;// 마피아로 직업을 정해준다.
                        isJobSet[newJobsIndex]=true;//직업이 있다고 표시한다.
                        numOfMafiaSet++;//마피아로 정해진 사람 수를 늘린다.
                    }
                }
                int numOfPoliceSet=0;//결정된 경찰의 수
                while(numOfPoliceSet<1) {//경찰은 1명 결정
                    newJobsIndex = generator.nextInt(8);
                    if(!isJobSet[newJobsIndex]) {//아직 새로운 index의 직업이 없다면
                        jobs[newJobsIndex]=4;// 경찰로 직업을 정해준다.
                        isJobSet[newJobsIndex]=true;//직업이 있다고 표시한다.
                        numOfPoliceSet++;//경찰로 정해진 사람 수를 늘린다.
                    }
                }
                int numOfDoctorSet=0;//결정된 의사의 수
                while(numOfDoctorSet<1) {//의사는 1명 결정
                    newJobsIndex = generator.nextInt(8);
                    if(!isJobSet[newJobsIndex]) {//아직 새로운 index의 직업이 없다면
                        jobs[newJobsIndex]=5;// 의사로 직업을 정해준다.
                        isJobSet[newJobsIndex]=true;//직업이 있다고 표시한다.
                        numOfDoctorSet++;//의사로 정해진 사람 수를 늘린다.
                    }
                }
                for(int i=0 ; i<8 ; i++) {//나머지는 시민으로 결정
                    if(!isJobSet[i]) {//아직 직업이 없다면
                        jobs[i]=2;//해당 index의 직업을 시민으로 정해준다.
                    }
                }
                break;
            case 1:// 낮의 투표를 set한다. (투표를 초기화하고 공지로 "낮이 되었습니다. 시민들은 마피아로 의심되는 사람을 투표해주세요." 라는 문구
            // 프린트)
                pole_fail=false;//투표 실패 여부를 초기화한다.
                numOfPolls=0;//투표자 수를 초기화한다.
                for(int i=0 ; i<8 ; i++) {
                    polls[i]=0;//투표를 모두 0표로 초기화한다.
                }
                // 낮을 setting한다. 유령이 아닌 각 클라이언트의 flag를 낮의 flag로 만든다.
                for (int i = 0; i < 8 ; i++) {
                    if(jobs[i]==6) {//유령은 넘어간다.
                        continue;
                    }
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("f/1");// 낮의 flag로 setting한다.
                    writer.flush();
                }
                for (int i = 0; i < 8 ; i++) {// 공지로 "낮이 되었습니다. 시민들은 마피아로 의심되는 사람을 투표해주세요."를 프린트
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "낮이 되었습니다. 시민들은 마피아로 의심되는 사람을 투표해주세요.");// " h / 공지 " 순이다.
                    if(jobs[i]!=6) {// 유령이 아니라면(시민이면)
                        writer.println("d/" + "당신의 시민 투표가_활성화되었습니다" );// " d / 설명 " 순이다.
                        writer.println("s/.");// 클라이언트의 투표를 활성화한다. " s / 아무거나 " 순이다.
                    }
                    writer.flush();
                }
                break;
            case 2:// 밤을 setting한다. flag가 6이 아닌 각 클라이언트의 flag를 자신의 job과 같게 만든다.
                for (int i = 0; i < 8 ; i++) {// 공지로 "밤이 되었습니다."를 프린트
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "밤이 되었습니다.");// " h / 공지 " 순이다.
                    writer.println("f/"+jobs[i]);// 자신의 job과 클라이언트의 flag를 같게 만들어준다. " f / flag " 순이다.
                    writer.flush();
                }
                break;
            case 3:// 마피아 투표를 set한다. (투표를 초기화하고 "마피아는 죽일 사람을 투표해주세요" 라는 문구 프린트)
                pole_fail=false;//투표 실패 여부를 초기화한다.
                numOfPolls=0;//투표자 수를 초기화한다.
                victim_index=-1;//victim index를 초기화한다.
                for(int i=0 ; i<8 ; i++) {
                    polls[i]=0;//투표를 모두 0표로 초기화한다.
                }
                for (int i = 0; i < 8 ; i++) {// 공지로 "마피아는 죽일 사람을 투표해주세요"를 프린트
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "마피아는 죽일 사람을 투표해주세요");// " h / 공지 " 순이다.
                    if(jobs[i] == 3) {// 마피아인 경우 투표를 활성화한다.
                        writer.println("d/" + "당신의 마피아 투표가 활성화되었습니다" );// " d / 설명 " 순이다.
                        writer.println("s/.");// " s / 아무거나 " 순이다.
                    }
                    writer.flush();
                }
                break;
            case 4:// 경찰에게 투표 안내 메세지를 보낸다. 경찰이 투표 결과를 확인하면서 자동으로 의사의 투표로 들어간다. jump_to_phase(5)를
            // call한다.
                int numOfPolAlive=0;//살아있는 경찰의 수
                for (int i = 0; i < 8 ; i++) {// 공지로 "경찰은 직업을 확인할 사람을 투표해주세요." 라고 전송한다.
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "경찰은 직업을 확인할 사람을 투표해주세요." );// " h / 공지 " 순이다.
                    if(jobs[i]==4) {//현재 클라이언트가 경찰이면
                        writer.println("d/" + "당신의 경찰 투표가 활성화되었습니다" );// " d / 설명 " 순이다.
                        writer.println("s/.");// 투표를 활성화한다. " s / 아무거나 " 순이다.
                        numOfPolAlive++;
                    }
                    writer.flush();
                }
                if(numOfPolAlive==0) {// 이미 경찰이 죽었으면
                    finish_phase(4);// 경찰 phase를 마친다.
                    jump_to_phase(5);// 의사 phase로 들어간다.
                }
                break;
            case 5:// 의사의 phase로 넘어간다.
                int numOfDocAlive=0;//살아있는 의사의 수
                heal_index=-1;//살릴 사람을 -1로 초기화한다. 의사가 죽어있다면 -1이 유지된다.
                for (int i = 0; i < 8 ; i++) {// 공지로 "의사는 살릴 사람을 투표해주세요." 라고 전송한다.
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "의사는 살릴 사람을 투표해주세요." );// " h / 공지 " 순이다.
                    if(jobs[i]==5) {//현재 클라이언트가 의사면
                        writer.println("d/" + "당신의 의사 투표가 활성화되었습니다" );// " d / 설명 " 순이다.
                        writer.println("s/.");// 투표를 활성화한다. " s / 아무거나 " 순이다.
                        numOfDocAlive++;
                    }
                    writer.flush();
                }
                if(numOfDocAlive==0) {// 이미 의사가 죽었으면
                    finish_phase(5);// 의사 phase를 마친다.
                }
                break;
        
            default:
                break;
        }
    }

    public void finish_phase(int phase_number) {// 해당 phase를 마무리하는 method이다.
        switch (phase_number) {
            case 0:// 각자의 직업을 모두에게 알려준다.
                for (int i = 0; i < 8 ; i++) {// 클라이언트의 직업 정보를 해당 클라이언트에게 전송한다.
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("e/" + jobs[i]);// " e / 직업번호 " 순이다.
                    writer.println("d/" + jobText[ jobs[i] ]);// "d / 직업 설명" 순이다. 해당 직업에 해당하는 직업 설명을 클라이언트에 전송한다.
                    writer.flush();
                }
                break;
            case 1:// 낮의 투표를 close한다. (투표 결과를 확인하고 pole_fail이 아니면 선택된 사람을 유령으로 만든다.(flag를 6으로) 게임
            // 종료 조건을 확인한다.)
                while(numOfPolls<alive) {//살아있는 사람이 모두 투표를 할 때까지 기다린다.
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int max_Polls=-1;//최대 득표자의 표수
                int max_Pole_index=-1;//최대 득표자의 index
                int max_Pole_number=0;//최대 득표자 수, 2명 이상이면 pole fail을 만든다.
                for(int i=0 ; i<8 ; i++) {//최대 득표자를 찾는다.
                    if(polls[i]>max_Polls) {
                        max_Polls = polls[i];
                        max_Pole_index = i;
                    }
                }
                for(int i=0 ; i<8 ; i++) {//최대 득표자 수를 구한다.
                    if(polls[i]==max_Polls) {
                        max_Pole_number++;
                    }
                }
                if(max_Pole_number>1) {//두명 이상이면 pole fail을 표시한다.
                    pole_fail=true;
                }
                if(pole_fail) {//pole fail이면
                    for (int i = 0; i < 8 ; i++) {// 공지로 "최대 득표자가 두명 이상이 나왔습니다. 투표는 무효로 처리됩니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("h/" + "최대 득표자가 두명 이상이 나왔습니다. 투표는 무효로 처리됩니다.");// " h / 공지 " 순이다.
                        writer.flush();
                    }
                }
                else if(!pole_fail) {
                    alive--;// 살아있는 사람 수를 1명 줄인다.
                    if(jobs[max_Pole_index] == 3) {// 죽은 유저가 마피아면
                        mafiaNum--;// 마피아 수도 1 줄인다.
                    }
                    PrintWriter killer = clientOutputStreams[max_Pole_index];
                    killer.println("f/6");//해당 유저의 상태를 유령으로 만든다.
                    killer.println("e/6");//해당 유저의 직업을 유령으로 만든다.
                    killer.println("d/"+"당신은 죽었습니다. 이제 유령이 되어 유령 채팅만 사용 가능합니다.");//안내 메세지 전달
                    jobs[max_Pole_index]=6;//서버 상에서 직업을 유령으로 세팅한다.
                    for (int i = 0; i < 8 ; i++) {// 공지로 "최대 득표자는 "+user name+" 입니다. 해당 유저는 유령이 됩니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("g/"+max_Pole_index);// 유령 index 보내기
                        writer.println("h/" + "최대 득표자는 " + names[max_Pole_index] + " 입니다. 해당 유저는 유령이 됩니다." );// " h / 공지 " 순이다.
                        writer.flush();
                    }
                    if(alive<=mafiaNum*2) {//살아있는 사람이 죽은 마피아의 2배 이하면 마피아의 승리로 게임이 끝난다.
                        not_finished = false;
                        for (int i = 0; i < 8 ; i++) {// 공지로 "마피아의 수가 시민 수와 같거나 많습니다, 마피아의 승리로 게임이 끝납니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("h/" + "마피아의 수가 시민 수와 같거나 많습니다, 마피아의 승리로 게임이 끝납니다." );// " h / 공지 " 순이다.
                        writer.flush();
                        }
                    }
                    else if(mafiaNum==0) {//마피아가 아무도 남아있지 않다면 시민의 승리로 게임이 끝난다.
                        not_finished = false;
                        for (int i = 0; i < 8 ; i++) {// 공지로 "시민이 마피아를 모두 찾았습니다, 시민의 승리로 게임이 끝납니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("h/" + "시민이 마피아를 모두 찾았습니다, 시민의 승리로 게임이 끝납니다." );// " h / 공지 " 순이다.
                        writer.flush();
                        }
                    }
                }
                break;
            case 2:// (phase_number가 2일때는 함수를 call하지 않는다.)
                
                break;
            case 3:// 마피아 투표를 close한다. (투표 결과를 확인하고 pole_fail이 아니면 선택된 사람을 victim_index에 넣는다.)
                while(numOfPolls<mafiaNum) {//마피아가 모두 투표를 할 때까지 기다린다.
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int mafia_Max_Polls=-1;//마피아 투표 최대 득표자의 표수
                int mafia_Max_Poll_index=-1;//마피아 투표 최대 득표자의 index
                int mafia_Max_Poll_number=0;//마피아 투표 최대 득표자 수, 2명 이상이면 pole fail을 만든다.
                for(int i=0 ; i<8 ; i++) {//최대 득표자를 찾는다.
                    if(polls[i]>mafia_Max_Polls) {
                        mafia_Max_Polls = polls[i];
                        mafia_Max_Poll_index = i;
                    }
                }
                for(int i=0 ; i<8 ; i++) {//최대 득표자 수를 구한다.
                    if(polls[i]==mafia_Max_Polls) {
                        mafia_Max_Poll_number++;
                    }
                }
                if(mafia_Max_Poll_number>1) {//두명 이상이면 pole fail을 표시한다.
                    pole_fail=true;
                }
                else if(mafia_Max_Poll_number==1) {//한명으로 희생자가 결정되면 victim_index에 업데이트한다. 
                    victim_index = mafia_Max_Poll_index;
                }
                for (int i = 0; i < 8 ; i++) {// 공지로 "마피아가 투표를 마쳤습니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("h/" + "마피아가 투표를 마쳤습니다." );// " h / 공지 " 순이다.
                        writer.flush();
                }
                break;
            case 4:// 경찰의 phase를 마친다.
                for (int i = 0; i < 8 ; i++) {// 공지로 "경찰이 투표를 마쳤습니다." 라고 전송한다.
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "경찰이 투표를 마쳤습니다." );// " h / 공지 " 순이다.
                    writer.flush();
                }
                break;
            case 5:// 의사가 투표를 마치면서 finish phase(5)를 call한다. 그 이후 phase가 끝나면 투표정보를 비교해가면서 지난 밤에 어떤
            // 일이 있었는지 출력하고
            // 게임 종료 조건을 확인해서 not_finished를 갱신한다. 맨 마지막으로 phase를 1로 바꿔준다.
                if(pole_fail) {
                    for (int i = 0; i < 8 ; i++) {// 공지로 "마피아들이 의견이 나뉘어 투표에 실패했습니다. 아무도 죽은 사람이 없습니다." 라고 전송한다.
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "마피아들이 의견이 나뉘어 투표에 실패했습니다. 아무도 죽은 사람이 없습니다." );// " h / 공지 " 순이다.
                    writer.flush();
                    }
                    phase=1;//phase를 1로 설정해서 기다리는 main함수를 깨워준다.
                    return;//바로 나와준다.
                }
                else if(heal_index == victim_index) {// 의사가 제대로 살린 경우
                    for (int i = 0; i < 8 ; i++) {// 공지로 "의사가 마피아가 지목한 사람을 살렸습니다. 아무도 죽은 사람이 없습니다." 라고 전송한다.
                    PrintWriter writer = clientOutputStreams[i];
                    writer.println("h/" + "의사가 마피아가 지목한 사람을 살렸습니다. 아무도 죽은 사람이 없습니다." );// " h / 공지 " 순이다.
                    writer.flush();
                    }
                    phase=1;//phase를 1로 설정해서 기다리는 main함수를 깨워준다.
                    return;//바로 나와준다.
                }
                else {//마피아가 지목한 사람이 죽는 경우
                    alive--;// 살아있는 사람 수를 1명 줄인다.
                    if(jobs[victim_index] == 3) {// 죽은 유저가 마피아면
                        mafiaNum--;// 마피아 수도 1 줄인다.
                    }
                    PrintWriter killer = clientOutputStreams[victim_index];
                    killer.println("f/6");//해당 유저의 상태를 유령으로 만든다.
                    killer.println("e/6");//해당 유저의 직업을 유령으로 만든다.
                    killer.println("d/"+"당신은 죽었습니다. 이제 유령이 되어 유령 채팅만 사용 가능합니다.");//안내 메세지 전달
                    jobs[victim_index]=6;//서버 상에서 직업을 유령으로 세팅한다.
                    for (int i = 0; i < 8 ; i++) {// 공지로 "마피아가 고른 사람은 "+user name+" 입니다. 해당 유저는 유령이 됩니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("g/"+victim_index);// 유령 index 보내기
                        writer.println("h/" + "마피아가 고른 사람은 " + names[victim_index] + " 입니다. 해당 유저는 유령이 됩니다." );// " h / 공지 " 순이다.
                        writer.flush();
                    }
                    if(alive<=mafiaNum*2) {//살아있는 사람이 죽은 마피아의 2배 이하면 마피아의 승리로 게임이 끝난다.
                        not_finished = false;
                        for (int i = 0; i < 8 ; i++) {// 공지로 "마피아의 수가 시민 수와 같거나 많습니다, 마피아의 승리로 게임이 끝납니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("h/" + "마피아의 수가 시민 수와 같거나 많습니다, 마피아의 승리로 게임이 끝납니다." );// " h / 공지 " 순이다.
                        writer.flush();
                        }
                    }
                    else if(mafiaNum==0) {//마피아가 아무도 남아있지 않다면 시민의 승리로 게임이 끝난다.
                        not_finished = false;
                        for (int i = 0; i < 8 ; i++) {// 공지로 "시민이 마피아를 모두 찾았습니다, 시민의 승리로 게임이 끝납니다." 라고 전송한다.
                        PrintWriter writer = clientOutputStreams[i];
                        writer.println("h/" + "시민이 마피아를 모두 찾았습니다, 시민의 승리로 게임이 끝납니다." );// " h / 공지 " 순이다.
                        writer.flush();
                        }
                    }
                    phase=1;//phase를 1로 설정해서 기다리는 main함수를 깨워준다.
                }
                break;
        
            default:
                break;
        }
    }

    public void go() {
        int number_of_connections = 0;
        try {
            ServerSocket serverSock = new ServerSocket(9999);// 서버의 소켓을 설정한다.
            while (number_of_connections < 8) {// 8번의 연결이 있을 때까지 연결을 받는다.
                Socket clientSocket = serverSock.accept();// 클라이언트의 연결요청을 받는다.
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());// 해당 클라이언트에 쓸 수 있는 writer
                clientOutputStreams[number_of_connections] = writer;// writer를 해당 인덱스의 자리에 넣는다.
                /** initialize **/
                writer.println("b/" + number_of_connections);// 클라이언트의 index 정보를 전송한다.
                writer.println("d/아이디를 입력해주세요.");// 아이디를 입력해달라고 클라이언트에게 요청
                writer.flush();
                /** initialize **/
                Thread t = new Thread(new Client_Reciever(clientSocket, number_of_connections));// 해당 클라이언트와 통신하는
                                                                                                // thread를 생성한다.
                t.start();
                number_of_connections++;// connection된 수를 늘린다.
            }
            serverSock.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 모든 사람들이 연결되었다.
        while (numOfReady < 8) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } // 모든 사람들이 아이디를 보내기 전까지 sleep
          // 모든 사람들이 아이디를 보냈다.
        try {
            for (int i = 0; i < 8; i++) {// 모든 사람에게 아이디를 전달한다. phase -1이 완료되었다. index와 아이디 순으로 보낸다.
                PrintWriter writer = clientOutputStreams[i];
                for(int j = 0 ; j < 8 ; j++) {
                    writer.println("a/" + j + names[j]);
                }
                writer.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 게임 시작
        jump_to_phase(0);// 직업을 랜덤하게 set한다.
        finish_phase(0);// 각자의 직업을 모두에게 알려준다.
        while (not_finished) {
            jump_to_phase(1);// 낮의 투표를 set한다. (투표를 초기화하고 공지로 "낮이 되었습니다. 시민들은 마피아로 의심되는 사람을 투표해주세요." 라는 문구
                             // 프린트)
            finish_phase(1);// 낮의 투표를 close한다. (투표 결과를 확인하고 pole_fail이 아니면 선택된 사람을 유령으로 만든다.(flag를 6으로) 게임
                            // 종료 조건을 확인한다.)
            if (!not_finished) {
                break;
            } // 종료조건일 때 break한다.
            jump_to_phase(2);// 밤을 setting한다. flag가 6이 아닌 각 클라이언트의 flag를 자신의 job과 같게 만든다.
            jump_to_phase(3);// 마피아 투표를 set한다. (투표를 초기화하고 "마피아는 죽일 사람을 투표해주세요" 라는 문구 프린트)
            finish_phase(3);// 마피아 투표를 close한다. (투표 결과를 확인하고 pole_fail이 아니면 선택된 사람을 victim_index에 넣는다.)
            jump_to_phase(4);// 경찰에게 투표 안내 메세지를 보낸다. 경찰이 투표 결과를 확인하면서 자동으로 의사의 투표로 들어간다. jump_to_phase(5)를
                             // call한다.
            while (phase != 1) {// 의사가 투표를 마치면서 finish phase(5)를 call한다. 그 이후 phase가 끝나면 투표정보를 비교해가면서 지난 밤에 어떤
                                // 일이 있었는지 출력하고
                                // 게임 종료 조건을 확인해서 not_finished를 갱신한다. 맨 마지막으로 phase를 1로 바꿔준다.
                try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }    
}