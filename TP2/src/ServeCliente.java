/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MrFabio
 */
public class ServeCliente extends Thread {

    private Socket cli;

    public ServeCliente(Socket s) {

        this.cli = s;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        int soma = 0, n = 0;
        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(cli.getInputStream()));
                out = new PrintWriter(cli.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServeCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            String str = null;
            try {
                str = in.readLine();

            } catch (IOException ex) {
                Logger.getLogger(ServeCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (str == null) {
                break;
            }
            if (str.equals("sair")) {
                break;
            }

            try {
                n = Integer.parseInt(str);
                soma += n;

            } catch (NumberFormatException e) {
                str = e.toString();
            }

            out.println("soma=" + soma);
            out.flush();

        }

        out.close();
    }

}
