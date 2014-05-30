
import java.io.Serializable;
import java.util.ArrayList;

public class RouteRequestPacket implements Serializable {

    private final ArrayList<String> rota;
    private int nsaltos, maxsaltos;
    private String destino;

    public RouteRequestPacket(ArrayList<String> rota, int nsaltos, int maxsaltos, String destino) {
        this.rota = rota;
        this.destino = destino;
        this.nsaltos = nsaltos;
        this.maxsaltos = maxsaltos;
    }

    public RouteRequestPacket() {
        this.rota = new ArrayList<>();
        this.nsaltos = 0;
        this.nsaltos = 0;
    }

    public RouteRequestPacket(RouteRequestPacket pacote) {
        this.rota = pacote.getRota();
        this.nsaltos = pacote.getNsaltos();
        this.maxsaltos = pacote.getMaxSaltos();
        this.destino = pacote.getDestino();
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public ArrayList<String> getRota() {
        return new ArrayList<>(rota);
    }

    public int getMaxSaltos() {
        return maxsaltos;
    }

    public int getNsaltos() {
        return nsaltos;
    }

    public void addNodo(String nodo) {
        this.rota.add(nodo);
    }

    public int getRotaSize() {
        return rota.size();
    }

    public void incNsaltos() {
        this.nsaltos++;
    }

    public String getNext(String meuip) {
        String proximo;

        proximo = rota.get(nsaltos);
        incNsaltos();

        return proximo;
    }

    @Override
    public String toString() {
        return "Conheço o percurso (#" + rota.size() + "): " + rota.toString();
    }
}
