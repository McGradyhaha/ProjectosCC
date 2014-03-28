
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloMaintenance extends Thread{
    private HelloTable tabela;
    
    public HelloMaintenance(HelloTable tabela) {
        this.tabela = tabela;
    }

    @Override
    public void run() {
        while(true){
            tabela.removerPerdidos();
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloMaintenance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
