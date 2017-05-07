/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    public InetAddress getAdress() throws RemoteException;

    public void setSynState(int state) throws RemoteException;

    public String getSynState() throws RemoteException;
    
}
