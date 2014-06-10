
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
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
public class Shutdown extends Thread{
    public ArrayList<Thread> threads = new ArrayList<>();
    public ArrayList<DatagramSocket> sockets = new ArrayList<>();
    public ArrayList<ServerSocket> serverSockets = new ArrayList<>();
    
    @Override
    public void run() {
        for(Thread t : threads)
            if( t.isAlive() && !t.isInterrupted())
                t.interrupt();
        
        for(DatagramSocket s : sockets)
            if( !s.isClosed() )
                s.close();
        
        for(ServerSocket s : serverSockets)
            if( !s.isClosed() )
                try {
                    s.close();
                } catch (IOException ex) {}
        
        System.out.println("\nNap time..");
    }
    
}
