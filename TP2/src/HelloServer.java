import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class HelloServer extends Thread{
    public HelloTable tabela;
    
    private ObjectInputStream in;
    private Socket connectionSocket;
    private ServerSocket serverSocket;
    

    public HelloServer(HelloTable tabela){
        this.tabela = tabela;
    }
    
    @Override
    public void run(){
        while(true) {
            try {
                System.out.println("[Server] open server socket");
                serverSocket = new ServerSocket(9999);
                
                System.out.println("[Server] open connection socket");
                connectionSocket = serverSocket.accept();
                
                System.out.println("[Server] getting stream");
                //this.in = new ObjectInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
                this.in = new ObjectInputStream(connectionSocket.getInputStream());
                
                System.out.println("[Server] reading into object");
                HelloPacket recebido = (HelloPacket) this.in.readObject();
                
                System.out.println("[Server] Recebi um pacote Hello: " + recebido.toString());
            } catch (    IOException | ClassNotFoundException ex) {
                Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                System.out.println("[Server] closing sockets");
                serverSocket.close();
                connectionSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}