
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket ss;

        int port = Integer.parseInt(args[0]);
        //int port = 1337;

        ss = new ServerSocket(port);

        while (true) {
            Socket soc = ss.accept();//fica à espera da ligação
            new ServeCliente(soc).start();
        }

    }

}
