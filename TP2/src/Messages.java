
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chalkos
 */
public class Messages {
    private static final ArrayList<Message> messages = new ArrayList<>();
    
    /**
     * Guarda uma mensagem e inicia a procura de uma rota para a entregar
     * @param m 
     */
    public static void addMessage(Message m){
        messages.add(m);
        new RouteRequestPacket(m.destino).sendRequest();
    }
    
    public static void routeMessage(String origem, String destino, ArrayList<String> nodos){
        for(Message m : messages){
            if(m.origem.equals(origem) && m.destino.equals(destino)){
                m.nodos = nodos;
            }
        }
    }
}
