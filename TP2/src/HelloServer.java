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
    public HelloTable tabela;
    
    private ObjectInputStream in;
    private Socket connectionSocket;
    private ServerSocket serverSocket;
    

    public HelloServer(HelloTable tabela){
        this.tabela = tabela;
    }
    
    @Override
    public void run(){
        while(true) {
            try {
                serverSocket = new ServerSocket(9999);
                connectionSocket = serverSocket.accept();
                this.in = new ObjectInputStream(connectionSocket.getInputStream());
                HelloPacket recebido = (HelloPacket) this.in.readObject();
                System.out.println("Recebi um pacote Hello: " + recebido.toString());
            } catch (    IOException | ClassNotFoundException ex) {
                Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                serverSocket.close();
                connectionSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}