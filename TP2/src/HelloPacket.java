
import java.io.Serializable;
import java.util.ArrayList;

public class HelloPacket implements Serializable {
    private ArrayList<String> vizinhos;

    public HelloPacket(ArrayList<String> vizinhos) {
        this.vizinhos = vizinhos;
    }

    @Override
    public String toString() {
        return "Conheço os: " + vizinhos;
    }
}
