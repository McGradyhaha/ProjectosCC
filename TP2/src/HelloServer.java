import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

class HelloServer {
    public static final int helloInterval = 30;
    
    public static void start() throws Exception { 
        DatagramSocket s = new DatagramSocket(9999); 
        byte[] aReceber = new byte[1024]; 
        while(true) {
            DatagramPacket resposta = new DatagramPacket(aReceber, aReceber.length);
            s.receive(resposta);
            
            
            
            String pedidoString= new String(resposta.getData(), 0, resposta.getLength());

            InetAddress IPAddress= resposta.getAddress();
            int porta= resposta.getPort();
        }
    } 
} 