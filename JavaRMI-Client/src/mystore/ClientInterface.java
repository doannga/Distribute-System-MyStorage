/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author DemTran and Nga Đoàn
 * đây là viết java RMI nên ta phải tạo ra 1 interface tại java không thể hỗ trợ đa kế thừa
 */
public interface ClientInterface extends Remote {
    
    
    //phương thức getAddress() trả về một địa chỉ IP 
    public InetAddress getAdress() throws RemoteException;
    
    
    //tạo và thay đổi trạng thái đồng bộ
    public void setSynState(int state) throws RemoteException;
    
    
    // Trả về trạng thái đồng bộ
    public String getSynState() throws RemoteException;
    
}
