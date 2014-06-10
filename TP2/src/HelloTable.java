
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloTable {
    public static final int HELLO_TIMEOUT = 3; //segundos
    
    // o vizinho com ip K tem vizinhos V
    public HashMap<String, ArrayList<String>> vizinhos;
    public HashMap<String, Long> tempos;
    
    public HelloTable(){
        vizinhos = new HashMap<>();
        tempos = new HashMap<>();
        
        if( HELLO_TIMEOUT <= HelloMulticaster.HELLO_INTERVAL){
            System.err.println("[CRITICAL] timeout(" + HELLO_TIMEOUT + ") <= interval(" + HelloMulticaster.HELLO_INTERVAL + "). Stop.");
            System.exit(1);
        }
    }
    
    public synchronized void novaEntrada(String origem, ArrayList<String> novos) throws IOException{
        vizinhos.put(origem, novos);
        tempos.put(origem, System.currentTimeMillis());
    }
    
    public synchronized void print(){
        //System.out.print("\033[H\033[2J"); //clear console
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("-----[ clear ]-----");
        System.out.flush();
        System.out.println("Hi, my name is " + Utilities.getName() + " and my ip is " + Utilities.getIPv6());
        System.out.println("[tabela] recebido:");
        for( String chave : vizinhos.keySet()){
            System.out.println("   " + chave + " (age: " + (System.currentTimeMillis()-tempos.get(chave))/1000 + " s)");
            for( String nodo : vizinhos.get(chave)){
                System.out.println("      " + nodo);
            }
        }
    }
    
    public synchronized void removerPerdidos(){
        Long now = System.currentTimeMillis();
        
        ArrayList<String> paraRemover = new ArrayList<>();
        
        for( String chave : tempos.keySet())
            if( tempos.get(chave) + HELLO_TIMEOUT * 1000 < now )
                // missing, deve remover
                paraRemover.add(chave);
        
        for( String chave : paraRemover){
            vizinhos.remove(chave);
            tempos.remove(chave);
        }
    }
    
    public ArrayList<String> getVizinhos(){
        return new ArrayList<>(vizinhos.keySet());
    }
    
    public ArrayList<String> getVizinhosNaoVisitados(ArrayList<String> visitados){
        ArrayList<String> res = new ArrayList<>();
        
        boolean visto;
        for( String vizinho : vizinhos.keySet() ){
            visto = false;
            for( String visitado : visitados )
                if( Utilities.trimZoneIndice(vizinho).equals(Utilities.trimZoneIndice(visitado)) )
                    visto = true;
            
            if(!visto)
                res.add(vizinho);
        }
        
        return res;
    }
    
    public ArrayList<InetAddress> getVizinhosAddr(){
        ArrayList<InetAddress> res = new ArrayList<>();
        for(String s : getVizinhos()){
            try {
                res.add(InetAddress.getByName(s));
            } catch (UnknownHostException ex) {
                System.out.println("Não conseguiu converter: " + s);
                Logger.getLogger(HelloTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }
    
    public ArrayList<String> getVizinhosSemZona(){
        ArrayList<String> res = new ArrayList<>();
        for(String s : getVizinhos()){
            res.add(Utilities.trimZoneIndice(s));
        }
        return res;
    }
}
