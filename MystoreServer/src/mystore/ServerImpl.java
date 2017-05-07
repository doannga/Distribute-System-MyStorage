/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import com.healthmarketscience.rmiio.SerializableInputStream;
import com.healthmarketscience.rmiio.SerializableOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author NgaPC
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInterface{
    
    private File serverFile;
    private boolean isStart = false;
    private Registry rmiRegistry;// cái này có tác dụng gì hả nga ơi. >>> nhớ giải thích còn viết báo cáo nhé
    private ArrayList listConnected;
    private TheScreenServer tarIPClient;

    public ServerImpl(File serverFile) throws RemoteException{
        super();
        this.serverFile = serverFile;
    }
    
    @Override
    public boolean connect(ClientInterface clientInt) throws RemoteException {
        return true;
    }


    @Override
    public void synState(ClientInterface clientInt) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start() throws Exception {
        // registry port 300
        rmiRegistry = LocateRegistry.createRegistry(3000);
        //Thay thế các ràng buộc, rebind() để tránh lỗi trong trường hợp "server" đã tồn tại trong RMI Registry
        rmiRegistry.rebind("server", this);
        JOptionPane.showMessageDialog(null, "Server Started");
        isStart = true;
    }

    @Override
    public void stop() throws Exception {  
        int choice = JOptionPane.showConfirmDialog(null," You are sure to stop application?");
        if( choice == JOptionPane.YES_OPTION){
            isStart = false;
            // Loại bỏ ràng buộc trong this registry
            rmiRegistry.unbind("server");
            unexportObject(this, true);
            // Removes the remote object, obj, from the RMI runtime.
            unexportObject(rmiRegistry, true);
        }
    }

    @Override
    public boolean isStart() throws RemoteException {
        return isStart;
    }

    @Override
    public File getServerFile() throws RemoteException {
        return serverFile;
    }

    @Override
    public void setServerFile(File serverFile) throws RemoteException {
        this.serverFile = serverFile;
    }

    @Override
    public OutputStream getOutputStreamFile(File file) throws Exception {
        return new SerializableOutputStream(new FileOutputStream(file));
    }

    @Override
    public InputStream getInputStreamFile(File file) throws Exception {
        return new SerializableInputStream(new FileInputStream(file));
    }
  
}
