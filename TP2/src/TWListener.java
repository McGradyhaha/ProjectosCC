
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
public class TWListener extends Thread{
    public ServerSocket welcomeSocket;
    private PrintWriter out = null;
    
    public void twWrite(String texto){
        if( out != null && !out.checkError() )
            out.println(texto);
    }
    
    public TWListener(ServerSocket ss){
        welcomeSocket = ss;
    }
    
    private static void handleInput(String input){
        if( !input.startsWith("TW #") )
            return;
        
        input = input.substring(4);
        String destino = input.substring(0, input.indexOf(' '));
        String mensagem = input.substring(input.indexOf(' ')+1);
        
        if(destino.isEmpty() || mensagem.isEmpty())
            return;
        
        // em principio aqui trata-se de uma mensagem válida, enviar
        Messages.addMessage(new Message(Utilities.getName(), destino, mensagem));
    }
    
    @Override
    public void run(){
        try {
            // Socket(), Bind(), Listen()
             
            Socket socketPedido = welcomeSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader( socketPedido.getInputStream()));
            out = new PrintWriter( socketPedido.getOutputStream(), true);
            while(true) {
                // se tiver coisas para ler, lê
                handleInput(in.readLine());
                //out.println(resposta);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(TWListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
