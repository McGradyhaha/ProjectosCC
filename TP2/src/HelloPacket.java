
import java.io.Serializable;
import java.util.ArrayList;

public class HelloPacket implements Serializable {
    private ArrayList<String> vizinhos;
    public boolean responder = true;
    
    public HelloPacket(ArrayList<String> vizinhos) {
        this.vizinhos = vizinhos;
        
        //Double num = Math.random()*1000;
        //int distinguir = num.intValue();
        //vizinhos.add( distinguir + " - " + System.currentTimeMillis());
    }

    public ArrayList<String> getVizinhos() {
        return vizinhos;
    }
    
    @Override
    public String toString() {
        return "Conheço os (#" + vizinhos.size() + "): " + vizinhos.toString();
    }
}
