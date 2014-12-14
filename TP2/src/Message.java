
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chalkos
 */
public class Message extends UnknownPacket implements Serializable{
    String origem = null;
    String destino = null;
    String text = null;
    Long timestamp;
    ArrayList<String> nodos;
    
    public Message(String origem, String destino, String texto){
        this.origem = origem;
        this.destino = destino;
        this.nodos = new ArrayList<>();
        this.timestamp = (long)0;
        this.text = texto;
    }
    
    public boolean isForMe(){
        return nodos.size() == 0;
    }
    
    public boolean sendMessage(){
        String destino = this.nodos.get(0);
        this.nodos.remove(0);
        
        try {
            System.out.println("Encaminhando msg para " + Utilities.trimZoneIndice(destino));
            
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
                System.out.println("Encaminhei msg para " + Utilities.trimZoneIndice(destino));
            } catch (IOException ex) {
                System.out.println("Falha ao encaminhar msg para " + Utilities.trimZoneIndice(destino));
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
        return null;
    }

    @Override
    public Message getMessage() {
        return (Message)this;
    }
}
