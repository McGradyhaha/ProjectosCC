import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class HelloServer extends Thread{
    public static final int helloInterval = 30;
    
    public HelloTable tabela;
    
    private ObjectInputStream in;
    private Socket connectionSocket;
    private ServerSocket serverSocket;
    

    public HelloServer(HelloTable tabela){
        this.tabela = tabela;
        try {
            serverSocket = new ServerSocket(9999);
            this.in = new ObjectInputStream(connectionSocket.getInputStream());
            
        } catch (SocketException ex) {
            Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        while(true) {
            try {
                connectionSocket = serverSocket.accept();
                HelloPacket recebido = (HelloPacket) this.in.readObject();
                System.out.println("Recebi um pacote Hello: " + recebido.toString());
            } catch (IOException ex) {
                Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}