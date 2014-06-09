
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
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
public class Utilities {
    private static NetworkInterface networkInterface = null;
    private static InetAddress mainInet = null;
    private static String myIPv6 = null;
    private static String myName = null;
    
    public static NetworkInterface getNetworkInterface(){
        if( networkInterface == null )
            startup();
        return networkInterface;
    }
    
    public static InetAddress getInetAddress(){
        if( mainInet == null)
            startup();
        return mainInet;
    }
    
    public static String getIPv6(){
        if( myIPv6 == null )
            startup();
        return myIPv6;
    }
    
    public static String getName(){
        if( myName == null)
            startup();
        return myName;
    }
    
    private static synchronized void startup(){
        try {
            Enumeration<NetworkInterface> interfaces =  NetworkInterface.getNetworkInterfaces();
            while( interfaces.hasMoreElements()){
                NetworkInterface nInterface = interfaces.nextElement();
                
                if( !nInterface.getName().equals("eth0") )
                    continue;
                
                Enumeration<InetAddress> addresses = nInterface.getInetAddresses();
                while( addresses.hasMoreElements()){
                    InetAddress addr = addresses.nextElement();
                    
                    String ip = addr.getHostAddress();
                    if( ip.startsWith("fe80") && addr.getAddress() != null && addr.getAddress().length > 0 ){
                        // guardar as coisas, visto que este é o ip certo
                        networkInterface = nInterface;
                        mainInet = addr;
                        myIPv6 = ip;
                        myName = "A" + ip.substring(ip.lastIndexOf(':')+1, ip.indexOf('%'));
                        
                        return;
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(HelloListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // debug
        try {
            Enumeration<NetworkInterface> interfaces =  NetworkInterface.getNetworkInterfaces();
            while( interfaces.hasMoreElements()){
                NetworkInterface nInterface = interfaces.nextElement();
                
                Enumeration<InetAddress> addresses = nInterface.getInetAddresses();
                while( addresses.hasMoreElements()){
                    InetAddress addr = addresses.nextElement();
                    
                    System.out.println("name:" + nInterface.getName());
                    System.out.println("namD:" + nInterface.getDisplayName());
                    System.out.println("loop:" + nInterface.isLoopback());
                    System.out.println("isup:" + nInterface.isUp());
                    System.out.println("virt:" + nInterface.isVirtual());
                    System.out.println("addr:" + addr);
                    System.out.println("getA:" + addr.getAddress());
                    System.out.println("CanH:" + addr.getCanonicalHostName());
                    System.out.println("HosA:" + addr.getHostAddress());
                    System.out.println("HosN:" + addr.getHostName());
                    System.out.println("----- next Addr -----");
                }
                System.out.println("----- next interface -----");
            }
        } catch (SocketException ex) {
            Logger.getLogger(HelloListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("Não conseguiu o utilities startup.");
        System.exit(0);
    }
    
    /**
     * Remove o indice da zona do IPv6 (o %xx no fim do endereço)
     * @param ip
     * @return 
     */
    public static String trimZoneIndice(String ip){
        int percent = ip.indexOf('%');
        if( percent >= 0 )
            return ip.substring(0, percent);
        return ip;
    }
}
