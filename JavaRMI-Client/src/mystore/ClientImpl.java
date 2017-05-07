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
    private String SynState;// trạng thái của đồng bộ hóa
    
    public ClientImpl(InetAddress clientAddress) throws RemoteException{
        super();
        this.clientAddress = clientAddress;
    }
    
    @Override
    public InetAddress getAdress() throws RemoteException {
        return clientAddress;
    }
// dưới đây để thay đổi hàm trạng thái đồng bộ hóa
    @Override
    public void setSynState(int state) throws RemoteException {
        if(state == 1){
            SynState = "Upload from "+ getAdress()+" to Server";
        }
        if(state == 2){
            SynState = "Download from Server to "+getAdress();
        }
        else{
            SynState = " Server and "+getAdress()+" is synchronized.";
        }
    }
// lấy ra trạng thái đồng bộ
    @Override
    public String getSynState() throws RemoteException {
        return SynState;
    }
    
}
