import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

class HelloServer {
    public static final int helloInterval = 30;
    public static final int deadInterval = 30;
    
    private InetAddress proprio;
    private ArrayList<HelloPacket> vizinhos;
    
    public static void main(String args[]) throws Exception { 
        DatagramSocket s = new DatagramSocket(9999); 
        byte[] aReceber = new byte[1024]; 
        while(true) {
            DatagramPacket pedido = new DatagramPacket(aReceber, aReceber.length); 
            s.receive(pedido); 

            String pedidoString= new String(pedido.getData(), 0, pedido.getLength()); 

            InetAddress IPAddress= pedido.getAddress();
            int porta= pedido.getPort(); 

            byte[] aEnviar= pedidoString.toUpperCase().getBytes(); 

            DatagramPacket resposta = new DatagramPacket(aEnviar, aEnviar.length, 
                IPAddress, porta
            ); 
            s.send(resposta); 
        }
    } 
} 