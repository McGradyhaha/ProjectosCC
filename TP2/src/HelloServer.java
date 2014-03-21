import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class HelloServer extends Thread{
    public HelloTable tabela;
    
    private MulticastSocket mSocket;
    private byte[] buf = new byte[1000]; 
    private String id = "";
    

    public HelloServer(HelloTable tabela, MulticastSocket mSocket){
        this.tabela = tabela;
        this.mSocket = mSocket;
    }
    
    public HelloServer(HelloTable tabela, MulticastSocket mSocket, int id){
        this(tabela,mSocket);
        this.id = "#"+id;
    }
    
    @Override
    public void run(){
            while(true) {
                try {
                    //System.out.println("[Server] get datagram packet");
                    DatagramPacket recv = new DatagramPacket(buf, buf.length); 
                    mSocket.receive(recv);

                    //System.out.println("[Server] get stream");
                    ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    HelloPacket pacote = (HelloPacket)ois.readObject();

                    System.out.println("[Server" + id + "] Got this: " + pacote.toString());
                } catch (    IOException | ClassNotFoundException ex) {
                    Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
}