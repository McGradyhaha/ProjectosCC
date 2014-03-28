
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.MulticastSocket;

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
        HelloListener servidores[] = new HelloListener[10];
        for(int i=0; i<servidores.length; i++)
            servidores[i] = new HelloListener(tabela, mSocket, i);
        
            
        
        // iniciar listener ou caster de acordo com os argumentos
        if( onlyCaster || (onlyListener == false && onlyCaster == false)){
            caster.start();
        }
        
        if(onlyListener || (onlyListener == false && onlyCaster == false)){
            for(int i=0; i<servidores.length; i++){
                servidores[i] = new HelloListener(tabela, mSocket, i);
                servidores[i].start();
            }
        }
        
        
        
        
        
        
            
            /*
        Runtime.getRuntime().addShutdownHook(
                new Shutdown()
        );*/
    }
    
    
    
}
