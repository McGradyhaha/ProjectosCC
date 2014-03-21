
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
    public static final int HELLO_INTERVAL = 30;
    
    public HelloTable tabela;
    
    private InetAddress group;
    
    private MulticastSocket s;
    
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
            System.out.println("[Client] Sending multicast...");
            try {
                s = new MulticastSocket(9999);
                s.setTimeToLive(1);
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
            
            try{
                s.close();
            }catch(Exception ex){
                Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                sleep(2000); //depois mete-se o hello interval
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
} 