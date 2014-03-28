
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
        
        
        HelloTable tabela = new HelloTable();
        
        
        
        
        
        if( onlyCaster ){
            HelloMulticaster caster = new HelloMulticaster(tabela);
            caster.start();
        }else if(onlyListener){
            MulticastSocket mSocket = new MulticastSocket(9999);
            InetAddress group = InetAddress.getByName("FF02::1");
            mSocket.joinGroup(group);

            HelloListener servidores[] = new HelloListener[10];
            for(int i=0; i<servidores.length; i++){
                servidores[i] = new HelloListener(tabela, mSocket, i);
                servidores[i].start();
            }
            
            sleep(Integer.MAX_VALUE);
            System.out.println("[Server] closing socket");
            mSocket.leaveGroup(group);
            mSocket.close();
        }else{
            MulticastSocket mSocket = new MulticastSocket(9999);
            InetAddress group = InetAddress.getByName("FF02::1");
            mSocket.joinGroup(group);

            HelloListener servidores[] = new HelloListener[10];
            for(int i=0; i<servidores.length; i++){
                servidores[i] = new HelloListener(tabela, mSocket, i);
                servidores[i].start();
            }

            HelloMulticaster caster = new HelloMulticaster(tabela);
            caster.start();
            
            sleep(Integer.MAX_VALUE);
            System.out.println("[Server] closing socket");
            mSocket.leaveGroup(group);
            mSocket.close();
        }
        
        
        
        HelloMaintenance maint = new HelloMaintenance(tabela);
        maint.start();
    }
}
