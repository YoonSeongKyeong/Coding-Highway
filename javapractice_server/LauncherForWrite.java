package javapractice_server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

class LauncherForWrite {

    public void go(PrintWriter inWriter) {//get inWriter to react on server_writer
        JFrame frame = new JFrame("writing to server : example");//make GUI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        frame.getContentPane().add(pane);
        JTextField line = new JTextField();
        JButton button = new JButton();
        line.addKeyListener(new KeyAdapter() {//enter -> write
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                try {
                    inWriter.println(line.getText());
                    inWriter.flush();
                }
                catch (Exception ex) {
                    System.out.println("printing failed");
                    ex.printStackTrace();
                }
               }
            }
         });

        button.addActionListener(new ActionListener(){//button clicked -> write
        
			@Override
			public void actionPerformed(ActionEvent e) {
                try {
                    inWriter.println(line.getText());
                    inWriter.flush();
                }
                catch (Exception ex) {
                    System.out.println("printing failed");
                    ex.printStackTrace();
                }
				
			}
        });
        pane.add(line,"Center");
        pane.add(button,"East");
        frame.setSize(600, 100);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        LauncherForWrite launcher = new LauncherForWrite();//make launcher to use method - go
        try {
            Socket sock = new Socket("127.0.0.1", 5000);//make connection
            System.out.println(sock);
            PrintWriter writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking estabilished");
            launcher.go(writer);//use go method
            
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}