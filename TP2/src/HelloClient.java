
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

class HelloClient extends Thread{
    HelloTable tabela;
    
    public HelloClient(HelloTable tabela){
        this.tabela = tabela;
    }
    
    @Override
    public void run(){ 
        try {
            InetAddress IPAddress = InetAddress.getByName("FF02::1");
            
            DatagramSocket s = new DatagramSocket();
            HelloPacket pacote = new HelloPacket(tabela.getVizinhos());
            byte[] aEnviar = pacote.
                    DatagramPacket p = new DatagramPacket(aEnviar, aEnviar.length, IPAddress, 9999);
            
                    s.send(p);
                    
                    
        } catch (UnknownHostException ex) {
            Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            String msg = "Hello"; 
            InetAddress group = InetAddress.getByName("FF02::1");
            MulticastSocket s = new MulticastSocket(9999); 
            s.joinGroup(group); 
            DatagramPacket hi = new DatagramPacket(msg.getBytes(), 
            msg.length(), group, 6789); 
            s.send(hi); 
        } catch (SocketException ex) {
            Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
} 