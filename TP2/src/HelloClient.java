
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class HelloClient { 
    public static void main(String args[]) throws Exception { 
        InetAddress IPAddress = InetAddress.getByName("localhost");
        DatagramSocket s = new DatagramSocket(); 
        byte[] aEnviar = new String("Pedido ao servidor..").getBytes();
        DatagramPacket p = new DatagramPacket(aEnviar, aEnviar.length, IPAddress, 9999);
        s.send(p);
        byte[] aReceber= new byte[1024]; 
        DatagramPacket r= new DatagramPacket(aReceber, aReceber.length); 
        s.receive(r); 
        String resposta = new String(r.getData(),0, r.getLength()); 
        System.out.println("Resposta:" + resposta);
        s.close(); 
    } 
} 