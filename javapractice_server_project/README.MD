# 이 프로그램은 채팅을 이용한 마피아 게임입니다. (2019/06/07 에 시작)
## 이 프로그램의 코드는 [head first java](https://www.amazon.com/Head-First-Java-Kathy-Sierra/dp/0596009208) 의 일부 코드를 사용했습니다.  

## 1번 목표 : 채팅 구현하기 - 각 플레이어의 id와 함께 구현 (2019/06/07에 완료 + GUI 미완성)

# **[서버 소켓은 9999번을 사용해서 연결한다.]**

### **클라이언트**
- main thread : GUI initialize + writing 구현
- reader thread : 서버에서 받아온 line을 채팅창에 print해준다.

#### 클라이언트 - object 설명
- Client_Launcher : 클라이언트 역할의 전체를 수행하는 런쳐. 나머지 클래스를 innerclass로 가지며 main method에서 해당 innerclass들을 instantiate 한다.
- Chatting_Window : 채팅창에 해당하는 오브젝트
- Chatting_Scroller : 채팅창의 스크롤 기능 구현
- Chatting_Out : 채팅방의 댓글창에 해당하는 오브젝트, 엔터키를 누르면 서버로 채팅을 전송한다.
- SendButton : 댓글창의 전송 버튼에 해당하는 오브젝트, 누르면 서버로 채팅을 전송한다.
- Main_Frame : GUI에서 프레임에 해당한다. Main_Panel을 담고 있다.
- Main_Panel : GUI에서 패널에 해당한다. 나머지 GUI요소를 모두 담고 있다.
- IncomingReader : Runnable 오브젝트로, 새로운 스레드에서 돌아가고, 서버에서 오는 input을 받아서 채팅창으로 전달하는 역할을 한다.


### **서버**
- main thread : 클라이언트에서 보내는 서버로의 연결 요청을 받아서 새로운 포트를 열어서 연결하고, 해당 연결을 관리하는 thread 실행
- connection thread : 클라이언트에서 채팅을 보낼때마다 모든 나머지 클라이언트에게 해당 내용을 보내준다.

#### 서버 - object 설명
- Server_Launcher : 클라이언트 역할의 전체를 수행하는 런쳐. 나머지 클래스를 innerclass로 가지며 main method에서 부른 go method로 최대 20개까지 client의 연결을 받아서, 전용 스레드로 연결을 생성하는 역할을 하고 있다. 클라이언트의 outputStream을 clientOutputStreams에 모아준다.
- Client_Reciever : Runnable 오브젝트로, 새로운 스레드에서 돌아가고, 클라이언트의 inputStream을 BufferedReader로 받아온 이후에, 클라이언트가 chatting을 보내면 tellEveryone method를 콜해서 모든 클라이언트에게 해당 message를 보내준다.





## 2번 목표 : 마피아 게임 구현하기.