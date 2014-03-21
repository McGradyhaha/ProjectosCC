
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloMaintenance extends Thread{
    public static final int deadInterval = 30;
    
    public HelloTable tabela;
    
    public HelloMaintenance(HelloTable tabela) {
        this.tabela = tabela;
    }
    
    private void cleanUp(){
        Long tempo = System.currentTimeMillis();
        
        int limpos = 0;
        int total = 0;
        
        for(Entry<String, Long> entry : tabela.tempos.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            
            if( tempo - deadInterval*1000 > value){
                //expirou
                tabela.vizinhos.remove(key);
                tabela.tempos.remove(key);
                limpos++;
            }
            total++;
        }
        //System.out.println("[Debug] Limpou " + limpos + " de " + total);
    }

    @Override
    public void run() {
        while(true){
            cleanUp();
            
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloMaintenance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
