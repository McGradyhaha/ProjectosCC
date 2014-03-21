
import java.util.ArrayList;
import java.util.HashMap;

public class HelloTable {
    // o vizinho com ip K tem vizinhos V
    public HashMap<String, ArrayList<String>> vizinhos;
    
    public synchronized void novaEntrada(String origem, ArrayList<String> novos){
        vizinhos.put(origem, novos);
    }
    
    public ArrayList<String> getVizinhos(){
        return new ArrayList<>(vizinhos.keySet());
    }
}
