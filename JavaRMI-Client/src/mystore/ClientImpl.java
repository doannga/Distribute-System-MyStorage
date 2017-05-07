/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author DemTran
 * 
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

    private final InetAddress clientAddress;
    private int state;// trạng thái của đồng bộ hóa
    
    public ClientImpl(InetAddress clientAddress) throws RemoteException{
        super();
        this.clientAddress = clientAddress;
    }
    
    @Override
    public InetAddress getAdress() throws RemoteException {
        return clientAddress;
    }

    @Override
    public void setSynState(int state) throws RemoteException {
        this.state = state;
    }

    @Override
    public String getSynState() throws RemoteException {
        return this.state + "";
    }

}
