
import java.io.Serializable;
import java.util.ArrayList;

public class HelloPacket extends UnknownPacket implements Serializable {

    private final ArrayList<String> vizinhos;
    public boolean responder = true;

    public HelloPacket(ArrayList<String> vizinhos) {
        this.vizinhos = new ArrayList<>(vizinhos);
    }

    public ArrayList<String> getVizinhos() {
        return new ArrayList<>(vizinhos);
    }

    @Override
    public String toString() {
        return "Conheço os (#" + vizinhos.size() + "): " + vizinhos.toString();
    }

    @Override
    public HelloPacket getHelloPacket() {
        return (HelloPacket)this;
    }

    @Override
    public RouteReplyPacket getRouteReplyPacket() {
        return null;
    }

    @Override
    public RouteRequestPacket getRouteRequestPacket() {
        return null;
    }
}
