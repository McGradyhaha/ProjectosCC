
import java.net.DatagramSocket;
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
public class Shutdown extends Thread{
    public ArrayList<Thread> threads = new ArrayList<>();
    public ArrayList<DatagramSocket> sockets = new ArrayList<>();
    
    @Override
    public void run() {
        for(Thread t : threads)
            if( t.isAlive() && !t.isInterrupted())
                t.interrupt();
        
        for(DatagramSocket s : sockets)
            if( !s.isClosed() )
                s.close();
        System.out.println("\nNap time..");
        
        
    }
    
}
