package javapractice_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Launcher {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 4242);//client ip + server port number
            InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String advice = reader.readLine();
            System.out.println("Today you should: " + advice );
            reader.close();
            s.close();
        } catch (IOException ex) {

        }
    }
}