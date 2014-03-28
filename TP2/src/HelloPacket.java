
import java.io.Serializable;
import java.util.ArrayList;

public class HelloPacket implements Serializable {
    private final ArrayList<String> vizinhos;
    public boolean responder = true;
    
    public HelloPacket(ArrayList<String> vizinhos) {
        this.vizinhos = vizinhos;
    }

    public ArrayList<String> getVizinhos() {
        return vizinhos;
    }
    
    @Override
    public String toString() {
        return "Conheço os (#" + vizinhos.size() + "): " + vizinhos.toString();
    }
}
