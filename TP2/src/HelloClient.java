
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

class HelloClient extends Thread{
    public HelloTable tabela;
    
    private InetAddress group;
    
    public HelloClient(HelloTable tabela){
        this.tabela = tabela;
        try {
            group = InetAddress.getByName("FF02::1");
        } catch (UnknownHostException ex) {
            Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){ 
        while(true){
            try {
                MulticastSocket s = new MulticastSocket(9999);
                s.joinGroup(group);

                HelloPacket pacote = new HelloPacket(tabela.getVizinhos());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(pacote);
                byte[] aEnviar = baos.toByteArray();

                DatagramPacket p = new DatagramPacket(aEnviar, aEnviar.length, group, 9999);
                s.send(p);   
            } catch (UnknownHostException ex) {
                Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
} 