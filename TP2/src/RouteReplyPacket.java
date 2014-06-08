
import java.io.Serializable;
import java.util.ArrayList;

public class RouteReplyPacket extends UnknownPacket implements Serializable {

    private final ArrayList<String> rota;
    private int nsaltos;

    public RouteReplyPacket(ArrayList<String> rota) {
        this.rota = rota;
    }

    public RouteReplyPacket() {
        this.rota = new ArrayList<>();
        this.nsaltos = 0;
    }

    public RouteReplyPacket(RouteReplyPacket pacote) {
        this.rota = pacote.getRota();
        this.nsaltos = pacote.getNsaltos();
    }

    public ArrayList<String> getRota() {
        return new ArrayList<>(rota);
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
        String proximo = "";

        proximo = rota.get(nsaltos);
        incNsaltos();

        return proximo;
    }

    @Override
    public String toString() {
        return "Conheço o percurso (#" + rota.size() + "): " + rota.toString();
    }
    
    @Override
    public HelloPacket getHelloPacket() {
        return null;
    }

    @Override
    public RouteReplyPacket getRouteReplyPacket() {
        return (RouteReplyPacket)this;
    }

    @Override
    public RouteRequestPacket getRouteRequestPacket() {
        return null;
    }
}
