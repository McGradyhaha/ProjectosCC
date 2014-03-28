
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

class HelloMulticaster extends Thread{
    public static final int HELLO_INTERVAL = 30;
    
    public HelloTable tabela;
    
    private InetAddress group;
    
    private MulticastSocket s;
    
    public HelloMulticaster(HelloTable tabela){
        this.tabela = tabela;
        try {
            group = InetAddress.getByName("FF02::1");
        } catch (UnknownHostException ex) {
            Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){ 
        while(true){
            //System.out.println("[Caster] Sending multicast...");
            try {
                Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
                
                // enviar por todas as interfaces de rede
                while( interfaces.hasMoreElements() ){
                    s = new MulticastSocket(9999);
                    
                    NetworkInterface ni = (NetworkInterface)interfaces.nextElement();
                    
                    if(ni.isLoopback() || !ni.isUp()) continue;
                    
                    
                    //System.out.println("NetInterface: " + ni.getName() + ", " + ni.getDisplayName());
                    
                    s.setNetworkInterface( ni );
                    s.setTimeToLive(1);
                    s.joinGroup(group);

                    HelloPacket pacote = new HelloPacket(tabela.getVizinhos());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(pacote);

                    System.out.println("[Caster] Enviando (por " + ni.getDisplayName() + "): " + pacote.getVizinhos().get(0));

                    byte[] aEnviar = baos.toByteArray();

                    DatagramPacket p = new DatagramPacket(aEnviar, aEnviar.length, group, 9999);
                    s.send(p);   
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try{
                s.close();
            }catch(Exception ex){
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                sleep(2500); //depois mete-se o hello interval
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
} 