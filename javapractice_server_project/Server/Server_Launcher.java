import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server_Launcher {

    public static void main(String[] args) {
        go();
    }

    ArrayList<PrintWriter> clientOutputStreams;//client들의 outputstream들을 모아서 보관하는 ArrayList

    public class Client_Reciever implements Runnable {
        BufferedReader reader;
        Socket sock;
        public Client_Reciever(Socket clientSocket) {
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
            try {
                while ((message = reader.readLine()) != null) {
                    tellEveryone(message);
                }  
            } catch(Exception ex) {
                ex.printStackTrace();
            }      
        }
    }

    public void go() {
        int number_of_connections = 0;
        clientOutputStreams = new ArrayList<PrintWriter>();
        try {
            ServerSocket serverSock = new ServerSocket(9999);//서버의 소켓을 설정한다.

            while(number_of_connections <= 20) {//20번의 연결이 있을 때까지 연결요청을 받는다.
                Socket clientSocket = serverSock.accept();//클라이언트의 연결요청을 받는다.
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());//해당 클라이언트에 쓸 수 있는 writer
                clientOutputStreams.add(writer);//writer를 ArrayList에 넣는다.

                Thread t = new Thread(new Client_Reciever(clientSocket));//해당 클라이언트와 통신하는 thread를 생성한다.
                t.start();
            }
            serverSock.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tellEveryone(String message) {
        try {
            for(int i=0 ; i<clientOutputStreams.size() ; i++) {
                PrintWriter writer = clientOutputStreams.get(i);
                writer.println(message);
                writer.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    

}