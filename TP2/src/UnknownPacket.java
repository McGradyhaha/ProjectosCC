/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chalkos
 */
public abstract class UnknownPacket {
    public abstract HelloPacket getHelloPacket();
    public abstract RouteReplyPacket getRouteReplyPacket();
    public abstract RouteRequestPacket getRouteRequestPacket();
}
