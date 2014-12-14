
import com.sun.jmx.remote.internal.ServerCommunicatorAdmin;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
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
    
    public static InetAddress group;
    
    public static HelloTable tabela;
    
    public static TWListener tw;

    public static void main(String[] args) throws Exception {
        
        // hello manutenção da tabela
        tabela = new HelloTable();
        HelloMaintenance maint = new HelloMaintenance(tabela);
        maint.start();

        // hello multicaster
        HelloMulticaster caster = new HelloMulticaster(tabela);

        // hello escutar e responder
        MulticastSocket mSocket = new MulticastSocket(9999);
        group = InetAddress.getByName("FF02::1");
        mSocket.joinGroup(group);

        ArrayList<HelloListener> listeners = new ArrayList<>();
        for (int i = 0; i < numero_hello_listeners; i++) {
            listeners.add(new HelloListener(tabela, mSocket, i));
        }
        
        ServerSocket twSocket = new ServerSocket(9999);
        tw = new TWListener(twSocket);
        
        caster.start();

        for (HelloListener listener : listeners) {
            listener.start();
        }
        
        tw.start();

        // fechar sockets e parar threads
        Shutdown shutdownHook = new Shutdown();
        shutdownHook.sockets.add(mSocket);
        shutdownHook.threads.add(maint);
        shutdownHook.threads.add(caster);
        shutdownHook.threads.add(tw);
        shutdownHook.threads.addAll(listeners);
        shutdownHook.serverSockets.add(twSocket);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
}
