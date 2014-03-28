
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
public class Shutdown extends Thread{
    
    //cenas que o Thread precisa para poder terminar o programa de forma decente
    private HelloTable hello_tabela;
    private HelloMaintenance hello_maintenance;
    private HelloMulticaster hello_multicaster;
    private MulticastSocket hello_udp_socket;
    private InetAddress hello_multicast_group;
    private HelloListener hello_servidores[];

    public Shutdown(HelloTable hello_tabela, HelloMaintenance hello_maintenance, HelloMulticaster hello_multicaster, MulticastSocket hello_udp_socket, InetAddress hello_multicast_group, HelloListener[] hello_servidores) {
        super();
        this.hello_tabela = hello_tabela;
        this.hello_maintenance = hello_maintenance;
        this.hello_multicaster = hello_multicaster;
        this.hello_udp_socket = hello_udp_socket;
        this.hello_multicast_group = hello_multicast_group;
        this.hello_servidores = hello_servidores;
    }
    
    @Override
    public void run() {
        
        
        
        System.out.println("Shutting down");
        
    }
    
}
