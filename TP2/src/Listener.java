
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Listener/* implements Runnable*/{

    private BufferedReader input;
    private PrintWriter output;


    public Listener(Socket client){

        try {
            this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.output = new PrintWriter(client.getOutputStream());
        }
        catch (IOException ex)
        {
            System.out.println("IO Exception 2 caught");
        }
    }


    public void run() {

        ServerSocket ss;
        int counter=0,valor=0,soma=0;
        boolean flag;
        int port = 1337;
        
        String str = "";

        int total = 0;

        try
        {   ss = new ServerSocket(port);
            counter = 0;soma=0;
            Socket cli = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(cli.getInputStream()));
            PrintWriter out = new PrintWriter(cli.getOutputStream());
            while(true){
                
                try{
                str = in.readLine();
                if(str==null) break;
                if(str.equals("sair"))break;
                valor = Integer.parseInt(str);
                counter++;
                soma+=valor;
                out.println("soma:" + soma);
                out.flush();
                
            }catch (EOFException eofe){
                out.println("EOFException");
                break;
                
                
            }}
        }
        catch (IOException ex) {
            System.out.println("IO Exception 3 caught");
        }
        finally {
            if(soma>0)
                System.out.println("media:" + counter/soma);
            this.output.close();
        }
    }
}
