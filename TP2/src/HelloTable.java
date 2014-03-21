
import java.util.ArrayList;
import java.util.HashMap;

public class HelloTable {
    // o vizinho com ip K tem vizinhos V
    public HashMap<String, ArrayList<String>> vizinhos;
    public HashMap<String, Long> tempos;
    
    public HelloTable(){
        vizinhos = new HashMap<>();
        tempos = new HashMap<>();
    }
    
    public synchronized void novaEntrada(String origem, ArrayList<String> novos){
        vizinhos.put(origem, novos);
        tempos.put(origem, System.currentTimeMillis());
    }
    
    public ArrayList<String> getVizinhos(){
        return new ArrayList<>(vizinhos.keySet());
    }
}
