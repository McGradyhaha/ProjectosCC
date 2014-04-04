
import java.io.Serializable;
import java.util.ArrayList;

public class RouteRequestPacket implements Serializable {

    private final ArrayList<String> rota;
    private int nsaltos;

    public RouteRequestPacket(ArrayList<String> rota) {
        this.rota = rota;
    }

    public ArrayList<String> getVizinhos() {
        return rota;
    }

    public int getNsaltos() {
        return nsaltos;
    }

    public void incNsaltos() {
        this.nsaltos++;
    }

    public String getNext(String meuip) {
        String proximo = "";

        proximo = rota.get(nsaltos);
        incNsaltos();

        return proximo;
    }

    @Override
    public String toString() {
        return "Conheço o percurso (#" + rota.size() + "): " + rota.toString();
    }
}
