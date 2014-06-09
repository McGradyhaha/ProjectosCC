
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
public class Message {
    String origem = null;
    String destino = null;
    String text = null;
    ArrayList<String> nodos;
    
    public Message(String origem, String destino, String texto){
        this.origem = origem;
        this.destino = destino;
        this.nodos = new ArrayList<>();
    }
}
