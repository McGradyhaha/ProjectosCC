
/**
 *
 * @author Win7
 */
public class UDPPacket {

    private RouteReplyPacket reply;
    private RouteRequestPacket request;
    private HelloPacket hello;

    public UDPPacket(RouteReplyPacket reply, RouteRequestPacket request, HelloPacket hello) {
        this.reply = reply;
        this.request = request;
        this.hello = hello;
    }

    public UDPPacket() {
        this.reply = null;
        this.request = null;
        this.hello = null;
    }

    public RouteReplyPacket getReply() {
        return reply;
    }

    public void setReply(RouteReplyPacket reply) {
        this.reply = reply;
    }

    public RouteRequestPacket getRequest() {
        return request;
    }

    public void setRequest(RouteRequestPacket request) {
        this.request = request;
    }

    public HelloPacket getHello() {
        return hello;
    }

    public void setHello(HelloPacket hello) {
        this.hello = hello;
    }

}
