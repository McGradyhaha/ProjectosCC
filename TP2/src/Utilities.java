
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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
    
    /**
     * Obtém o próprio IPv6
     */
    public static String getOwnIP(){
        try {
            Enumeration<NetworkInterface> interfaces =  NetworkInterface.getNetworkInterfaces();
            while( interfaces.hasMoreElements()){
                Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
                while( addresses.hasMoreElements()){
                    InetAddress addr = addresses.nextElement();
                    /*System.out.println("addr:" + addr);
                    System.out.println("getA:" + addr.getAddress());
                    System.out.println("CanH:" + addr.getCanonicalHostName());
                    System.out.println("HosA:" + addr.getHostAddress());
                    System.out.println("HosN:" + addr.getHostName());
                    System.out.println("----------");*/
                    String ip = addr.getHostAddress();
                    if( ip.startsWith("fe80") )
                        return ip;
                }
            }
            System.out.println("No IP found. List IPs and quit.");
            interfaces =  NetworkInterface.getNetworkInterfaces();
            while( interfaces.hasMoreElements()){
                Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
                while( addresses.hasMoreElements()){
                    System.out.println("IP: " + addresses.nextElement().getHostAddress());
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(HelloListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
        return null; //nunca chega aqui, foi para nao dar erro
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
