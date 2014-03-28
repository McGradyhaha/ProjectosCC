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

class HelloListener extends Thread{
    public HelloTable tabela;
    
    private MulticastSocket mSocket;
    private byte[] buf = new byte[1000]; 
    private String id = "";
    

    public HelloListener(HelloTable tabela, MulticastSocket mSocket){
        this.tabela = tabela;
        this.mSocket = mSocket;
    }
    
    public HelloListener(HelloTable tabela, MulticastSocket mSocket, int id){
        this(tabela,mSocket);
        this.id = "#"+id;
    }
    
    @Override
    public void run(){
            while(true) {
                try {
                    //System.out.println("[listener] get datagram packet");
                    DatagramPacket recv = new DatagramPacket(buf, buf.length); 
                    mSocket.receive(recv);

                    //System.out.println("[listener] get stream");
                    ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    HelloPacket pacote = (HelloPacket)ois.readObject();

                    System.out.println("[Listener" + id + "] Got this: " + pacote.toString());
                } catch (    IOException | ClassNotFoundException ex) {
                    Logger.getLogger(HelloListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HelloListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
}