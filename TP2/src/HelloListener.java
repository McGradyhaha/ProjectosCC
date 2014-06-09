
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class HelloListener extends Thread {

    SimpleDateFormat ssd = new SimpleDateFormat();
    GregorianCalendar sdd = new GregorianCalendar();

    public HelloTable tabela;

    private MulticastSocket mSocket;
    private byte[] buf = new byte[1000];
    private String id = "";

    public HelloListener(HelloTable tabela, MulticastSocket mSocket) {
        this.tabela = tabela;
        this.mSocket = mSocket;
        StringBuilder sddd = new StringBuilder();

        sdd.add(Calendar.YEAR, MIN_PRIORITY);
    }

    public HelloListener(HelloTable tabela, MulticastSocket mSocket, int id) {
        this(tabela, mSocket);
        this.id = "#" + id;
    }
    
    private void handleHelloPacket(DatagramPacket recv, HelloPacket pacote){
        try {
            //System.out.println("[Listener" + id + "] Got package!");
            //System.out.println("Recebi um HelloPacket");
            
            this.tabela.novaEntrada(recv.getAddress().getHostAddress(), pacote.getVizinhos());
            /* não é preciso resposta porque todos os nodos vão enviar a sua lista em multicast
            if( pacote.responder ){
                // 0 - qualquer porta livre (isto é a porta local, não é relevante e pode ser qualquer)
                DatagramSocket s = new DatagramSocket(0);
                
                HelloPacket resposta = new HelloPacket(tabela.getVizinhos());
                resposta.responder = false;
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(resposta);
                
                System.out.println("Respondi ao HelloPacket");
                byte[] aEnviar = baos.toByteArray();
                DatagramPacket p = new DatagramPacket(aEnviar, aEnviar.length, recv.getSocketAddress());
                
                s.send(p);
                s.close();
            }*/
        } catch (IOException ex) {
            Logger.getLogger(HelloListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleRouteRequest(DatagramPacket recv, RouteRequestPacket routereq){
        //System.out.println("[Listener" + id + "] Got package!");
        System.out.println("Recebi um RouteRequestPacket");

        
            System.out.println(routereq.getRota().toString());
            //System.exit(0);
        
        // se o destino não for eu
        if( routereq.isForMe() )
            System.out.println("Sou o destino do routeRequest!!!");
        else
            RouteRequestPacket.sendRequest(routereq, tabela);
    }

    @Override
    public void run(){
        boolean skip;
        while(true) {
            try {
                //System.out.println("[listener] get datagram packet");
                DatagramPacket recv = new DatagramPacket(buf, buf.length); 
                mSocket.receive(recv);
                
                // se o datagrama recebido tiver origem na própria máquina, ignorar
                if( Utilities.getIPv6().equals(recv.getAddress().getHostAddress()) ) continue;

                //System.out.println("[listener] get stream");
                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                ObjectInputStream ois = new ObjectInputStream(bais);
                UnknownPacket packet = (UnknownPacket)ois.readObject();

                HelloPacket pacote = packet.getHelloPacket();
                RouteReplyPacket routerep = packet.getRouteReplyPacket();
                RouteRequestPacket routereq = packet.getRouteRequestPacket();
                
                if (pacote != null)
                    handleHelloPacket(recv, pacote);
                else if (routereq != null)
                    handleRouteRequest(recv, routereq);
                else if (routerep != null) {

                    //System.out.println("[Listener" + id + "] Got package!");
                    System.out.println("Recebi um RouteReplyPacket");

                    String meuinet = InetAddress.getLocalHost().toString();
                    int salto = 0;

                    if (routerep.getNsaltos() != routerep.getRota().size())//Se não for o último
                    {
                        String str = "";
                        //Verificar se já veio por aqui
                        for (String st : routerep.getRota()) {

                            //está na direção certa ? Ou será percorrer inversamente ?
                            if (salto == routerep.getNsaltos()) {//Encontrar o próximo nodo
                                str = st;
                                break;//Sai do ciclo e em 's' tem o endereço para enviar
                            }
                            salto++;
                        }
                        int tenho_na_lista = 0;
                        for (String viz : tabela.getVizinhos()) {
                            if (viz.compareTo(str) == 0)//Se está na lista de vizinhos
                            {
                                tenho_na_lista = 1;
                            }
                        }

                        if (tenho_na_lista == 1) {
                            break;// Se não está na lista de vizinhos descarta
                        }

                        InetAddress dest = InetAddress.getByName(recv.getAddress().getHostName());

                        DatagramSocket s = new DatagramSocket(0);

                        //Cria um Route Reply
                        RouteReplyPacket resposta = new RouteReplyPacket(routerep);

                        resposta.incNsaltos();//Incrementar o número de saltos

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(resposta);
                        byte[] aEnviar = baos.toByteArray();

                        //System.out.println("[Listener" + id + "] Respondeu.");
                        System.out.println("Enviei um RouteReplyPacket");

                        DatagramPacket p2 = new DatagramPacket(aEnviar, aEnviar.length, InetAddress.getByName(str), 999);

                        s.send(p2);
                        s.close();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(HelloListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
