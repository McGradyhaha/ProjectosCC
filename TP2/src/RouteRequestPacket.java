
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RouteRequestPacket extends UnknownPacket implements Serializable {

    private final ArrayList<String> rota;
    private int maxsaltos;
    private String destino;
    private String origem;

    public RouteRequestPacket(ArrayList<String> rota, int nsaltos, int maxsaltos, String destino) {
        this.rota = rota;
        this.destino = destino;
        this.maxsaltos = maxsaltos;
    }

    public RouteRequestPacket() {
        this.rota = new ArrayList<>();
        this.maxsaltos = 30;
    }

    public RouteRequestPacket(String origem, String destino) {
        this.rota = new ArrayList<>();
        this.maxsaltos = 30;
        this.destino = destino;
        this.origem = origem;
    }

    public String getOrigem() {
        return origem;
    }

    public RouteRequestPacket(RouteRequestPacket pacote) {
        this.rota = pacote.getRota();
        this.maxsaltos = pacote.getMaxSaltos();
        this.destino = pacote.getDestino();
    }
    
    public boolean isForMe(){
        return Utilities.getName().equals(destino);
    }
    
    public void addMeToRoute(){
        rota.add(Utilities.getIPv6());
    }
    
    /**
     * Incrementar o numero de saltos
     * Não fazer nada se ultrapassar o numero máximo de saltos
     * Não fazer nada se o request já tiver passado por aqui (loop na rede)
     * Adicionar-se à lista de sitios por onde o request passou
     * Enviar o RouteRequestPacket para todos os vizinhos
     * @param req O request original
     * @param tabela A tabela de vizinhos
     */
    public boolean sendRequest(){
        // Não fazer nada se o request já tiver passado por aqui (loop na rede)
        String myAddr = Utilities.getIPv6();
        for(String passou : rota){
            if(passou.equals(myAddr))
                return false;
        }
        
        // Incrementar o numero de saltos
        // Adicionar-se à lista de sitios por onde o request passou
        rota.add(myAddr);
        
        // Não fazer nada se ultrapassar o numero máximo de saltos
        if( getNsaltos() >= maxsaltos )
            return false;
        
        // Enviar o RouteRequestPacket para todos os vizinhos
        try {
            System.out.println("Enviando RouteRequestPacket..");
            
            MulticastSocket s = new MulticastSocket();
            s.setInterface(Utilities.getInetAddress());
            s.setTimeToLive(1);
            s.joinGroup(HelloMain.group);
            
            ByteArrayOutputStream baost = new ByteArrayOutputStream();
            ObjectOutputStream oost = new ObjectOutputStream(baost);
            oost.writeObject(this);
            
            byte[] aEnviar = baost.toByteArray();
            
            //enviar para todos os vizinhos
            for( InetAddress addr : HelloMain.tabela.getVizinhosAddr() )
                try {
                    s.send(new DatagramPacket(aEnviar, aEnviar.length, InetAddress.getByName(addr.getHostAddress()), 9999));
                    System.out.println("Enviei um RouteRequestPacket para " + addr.getHostAddress());
                } catch (IOException ex) {
                    System.out.println("Falha ao enviar RouteRequestPacket para " + addr.getHostAddress());
                    Logger.getLogger(RouteRequestPacket.class.getName()).log(Level.SEVERE, null, ex);
                }
            s.close();
            return true;
            
        } catch (SocketException ex) {
            Logger.getLogger(RouteRequestPacket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RouteRequestPacket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public ArrayList<String> getRota() {
        return new ArrayList<>(rota);
    }

    public int getMaxSaltos() {
        return maxsaltos;
    }

    public int getNsaltos() {
        return rota.size();
    }

    @Override
    public String toString() {
        return "Conheço o percurso (#" + rota.size() + "): " + rota.toString();
    }
    
    @Override
    public HelloPacket getHelloPacket() {
        return null;
    }

    @Override
    public RouteReplyPacket getRouteReplyPacket() {
        return null;
    }

    @Override
    public RouteRequestPacket getRouteRequestPacket() {
        return (RouteRequestPacket)this;
    }

    @Override
    public Message getMessage() {
        return null;
    }
}
