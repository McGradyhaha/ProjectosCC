
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RouteReplyPacket extends UnknownPacket implements Serializable {

    private ArrayList<String> rota;
    private int nodoAtual;

    public RouteReplyPacket(ArrayList<String> nodos) {
        this.rota = nodos;
        this.nodoAtual = nodos.size()-1;
    }

    public ArrayList<String> getRota() {
        return new ArrayList<>(rota);
    }
    
    /**
     * Avança na lista de nodos para o proximo nodo
     * @return O próximo nodo
     */
    public String getAtual() {
        return rota.get(nodoAtual);
    }
    
    
    public boolean sendReply(){
        nodoAtual--;
        String destino = rota.get(nodoAtual);
        
        try {
            System.out.println("Enviando RouteReplyPacket para " + Utilities.trimZoneIndice(destino));
            
            MulticastSocket s = new MulticastSocket();
            s.setInterface(Utilities.getInetAddress());
            s.setTimeToLive(1);
            s.joinGroup(HelloMain.group);
            
            ByteArrayOutputStream baost = new ByteArrayOutputStream();
            ObjectOutputStream oost = new ObjectOutputStream(baost);
            oost.writeObject(this);
            
            byte[] aEnviar = baost.toByteArray();
            
            //enviar para o proximo nodo
            try {
                s.send(new DatagramPacket(aEnviar, aEnviar.length, InetAddress.getByName(Utilities.trimZoneIndice(destino)), 9999));
                System.out.println("Enviei um RouteReplyPacket para " + Utilities.trimZoneIndice(destino));
            } catch (IOException ex) {
                System.out.println("Falha ao enviar RouteReplyPacket para " + Utilities.trimZoneIndice(destino));
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

    public int getNodoAtual() {
        return nodoAtual;
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
        return (RouteReplyPacket)this;
    }

    @Override
    public RouteRequestPacket getRouteRequestPacket() {
        return null;
    }
}
