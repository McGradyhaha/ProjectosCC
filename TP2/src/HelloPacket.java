
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class HelloPacket implements Serializable {

    private InetAddress proprio;
    private ArrayList<InetAddress> vizinhos;

    public HelloPacket(InetAddress proprio, ArrayList<InetAddress> vizinhos) {
        this.proprio = proprio;
        this.vizinhos = vizinhos;
    }
}
