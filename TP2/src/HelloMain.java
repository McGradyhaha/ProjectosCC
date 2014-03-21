/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chalkos
 */
public class HelloMain {
    public static void main(String[] args) throws Exception {
        HelloTable tabela = new HelloTable();
        
        HelloServer servidor = new HelloServer(tabela);
        HelloClient cliente = new HelloClient(tabela);
        HelloMaintenance maint = new HelloMaintenance(tabela);
        
        servidor.start();
        
        Thread.sleep(3000);
        
        cliente.start();
        maint.start();
    }
}
