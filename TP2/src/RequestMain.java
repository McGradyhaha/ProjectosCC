
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

class RequestMain extends Thread {

    public static final int HELLO_INTERVAL = 1; //segundos

    public HelloTable tabela;

    private InetAddress group;

    private MulticastSocket s;

    public RequestMain(HelloTable tabela) {
        this.tabela = tabela;
        try {
            group = InetAddress.getByName("FF02::1");
        } catch (UnknownHostException ex) {
            Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            //System.out.println("[Caster] Sending multicast...");
            try {
                Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

                // enviar por todas as interfaces de rede
                while (interfaces.hasMoreElements()) {
                    s = new MulticastSocket(9999);

                    NetworkInterface ni = (NetworkInterface) interfaces.nextElement();

                    if (ni.isLoopback() || !ni.isUp()) {
                        continue;
                    }

                    //System.out.println("NetInterface: " + ni.getName() + ", " + ni.getDisplayName());
                    s.setNetworkInterface(ni);
                    s.setTimeToLive(1);
                    s.joinGroup(group);

                    HelloPacket pacote = new HelloPacket(tabela.getVizinhos());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(pacote);

                    //System.out.println("[Caster] Enviando vizinhos..");
                    System.out.println("*");

                    byte[] aEnviar = baos.toByteArray();

                    DatagramPacket p = new DatagramPacket(aEnviar, aEnviar.length, group, 9999);
                    s.send(p);

                    // Request
                    DatagramSocket s = new DatagramSocket(0);

                    RouteRequestPacket resposta = new RouteRequestPacket();

                    ByteArrayOutputStream baost = new ByteArrayOutputStream();
                    ObjectOutputStream oost = new ObjectOutputStream(baost);
                    oost.writeObject(resposta);

                    //System.out.println("[Listener" + id + "] Respondeu.");
                    System.out.println("Enviei um RouteRequestPacket");

                    byte[] aEnviar2 = baost.toByteArray();

                    for (String vizinho : tabela.getVizinhos()) {

                        DatagramPacket p2 = new DatagramPacket(aEnviar2, aEnviar2.length, InetAddress.getByName(vizinho), 9999);

                        s.send(p2);
                        s.close();
                    }
                    s.close();

                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                s.close();
            } catch (Exception ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                sleep(HELLO_INTERVAL * 1000); //depois mete-se o hello interval
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloMulticaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
