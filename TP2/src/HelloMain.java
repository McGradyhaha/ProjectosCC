
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
        boolean onlyServer = false;
        boolean onlyClient = false;
        
        for(String arg : args){
            if( arg.compareTo("server") == 0){
                onlyServer = true;
                break;
            }
            
            if( arg.compareTo("client") == 0){
                onlyClient = true;
                break;
            }
        }
        
        
        HelloTable tabela = new HelloTable();
        
        
        
        
        
        if( onlyClient ){
            HelloClient cliente = new HelloClient(tabela);
            cliente.start();
        }else if(onlyServer){
            MulticastSocket mSocket = new MulticastSocket(9999);
            InetAddress group = InetAddress.getByName("FF02::1");
            mSocket.joinGroup(group);

            HelloServer servidores[] = new HelloServer[10];
            for(int i=0; i<servidores.length; i++){
                servidores[i] = new HelloServer(tabela, mSocket, i);
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

            HelloServer servidores[] = new HelloServer[10];
            for(int i=0; i<servidores.length; i++){
                servidores[i] = new HelloServer(tabela, mSocket, i);
                servidores[i].start();
            }

            HelloClient cliente = new HelloClient(tabela);
            cliente.start();
            
            sleep(Integer.MAX_VALUE);
            System.out.println("[Server] closing socket");
            mSocket.leaveGroup(group);
            mSocket.close();
        }
        
        
        
        HelloMaintenance maint = new HelloMaintenance(tabela);
        maint.start();
    }
}
