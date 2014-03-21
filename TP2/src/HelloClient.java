
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class HelloClient {
    public static void multicast() throws Exception { 
        InetAddress IPAddress = InetAddress.getByName("FF02::1");
        DatagramSocket s = new DatagramSocket();
        byte[] aEnviar = new String("Feito multicast").getBytes();
        DatagramPacket p = new DatagramPacket(aEnviar, aEnviar.length, IPAddress, 9999);
        s.send(p);
    } 
} 