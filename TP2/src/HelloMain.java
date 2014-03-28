
import java.net.InetAddress;
import java.net.MulticastSocket;
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
public class HelloMain {
    public static final int numero_hello_listeners = 10;
    
    public static void main(String[] args) throws Exception {
        boolean onlyListener = false;
        boolean onlyCaster = false;
        
        for(String arg : args){
            if( arg.compareTo("listen") == 0){
                onlyListener = true;
                break;
            }
            
            if( arg.compareTo("cast") == 0){
                onlyCaster = true;
                break;
            }
        }
        
        // hello manutenção da tabela
        HelloTable tabela = new HelloTable();
        HelloMaintenance maint = new HelloMaintenance(tabela);
        maint.start();
        
        // hello multicaster
        HelloMulticaster caster = new HelloMulticaster(tabela);
        
        // hello escutar e responder
        MulticastSocket mSocket = new MulticastSocket(9999);
        InetAddress group = InetAddress.getByName("FF02::1");
        mSocket.joinGroup(group);
        
        ArrayList<HelloListener> listeners = new ArrayList<>();
        
        for(int i=0; i<numero_hello_listeners; i++)
            listeners.add(new HelloListener(tabela, mSocket, i));
        
        // iniciar listener ou caster de acordo com os argumentos
        if( onlyCaster || (onlyListener == false && onlyCaster == false))
            caster.start();
        
        if(onlyListener || (onlyListener == false && onlyCaster == false))
            for(HelloListener listener : listeners)
                listener.start();        
        
        
        
        // fechar sockets e parar threads
        Shutdown shutdownHook = new Shutdown();
        shutdownHook.sockets.add(mSocket);
        shutdownHook.threads.add(maint);
        shutdownHook.threads.add(caster);
        shutdownHook.threads.addAll(listeners);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
}
