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
    
    private ObjectInputStream in;
    private Socket connectionSocket;
    private ServerSocket serverSocket;
    private MulticastSocket mSocket;
    private byte[] buf = new byte[1000]; 
    

    public HelloServer(HelloTable tabela){
        this.tabela = tabela;
    }
    
    @Override
    public void run(){
        while(true) {
            try {
                
                //System.out.println("[Server] open server socket");
                mSocket = new MulticastSocket(9999);
                mSocket.joinGroup(InetAddress.getByName("FF02::1"));

                //System.out.println("[Server] get datagram packet");
                DatagramPacket recv = new DatagramPacket(buf, buf.length); 
                mSocket.receive(recv);
                
                //System.out.println("[Server] get stream");
                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                ObjectInputStream ois = new ObjectInputStream(bais);
                HelloPacket pacote = (HelloPacket)ois.readObject();
                
                System.out.println("[Server] Got this: " + pacote.toString());
            } catch (    IOException | ClassNotFoundException ex) {
                Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("[Server] closing socket");
            mSocket.close();
        }
    }
}