
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

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
    private static final int MSG_TIMEOUT = 20;
    
    private static final ArrayList<Message> messages = new ArrayList<>();
    
    /**
     * Guarda uma mensagem e inicia a procura de uma rota para a entregar
     * @param m 
     */
    public synchronized static void addMessage(Message m){
        m.timestamp = System.currentTimeMillis();
        messages.add(m);
        new RouteRequestPacket(m.origem, m.destino).sendRequest();
    }
    
    public synchronized static void sendMessage(String origem, String destino, ArrayList<String> nodos){
        Message mensagem = null;
        
        // remover o IP de origem porque não vai ser usado
        nodos.remove(0);
        
        for(Message m : messages){
            if(m.origem.equals(origem) && m.destino.equals(destino)){
                m.nodos = nodos;
                mensagem = m;
            }
        }
        
        if( mensagem == null )
            return;
        
        messages.remove(mensagem);
        mensagem.sendMessage();
    }
    
    public static synchronized void removeTimedOut(){
        Long now = System.currentTimeMillis();
        //ArrayList<Integer> indices = new ArrayList<>();
        Stack<Integer> indices = new Stack<>();
        //ArrayList<String> nomes = new ArrayList<>();
        HashSet<String> nomes = new HashSet<>();
        
        for(int i=0; i<messages.size(); i++){
            Message m = messages.get(i);
            if(m.timestamp + MSG_TIMEOUT*1000 < now){
                //indices.add(i);
                indices.push(i);
                nomes.add(m.destino);
            }
        }
        
        for(int i : indices)
            messages.remove(i);
        
        for(String nome : nomes)
            HelloMain.tw.twWrite("O " + nome + " não está acessível.");
    }
}
