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
import javax.swing.JOptionPane;

import com.healthmarketscience.rmiio.SerializableInputStream;
import com.healthmarketscience.rmiio.SerializableOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author NgaPC
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    private File serverFile;
    private boolean isStart;
    private int syncState;
    private Registry rmiRegistry;
    private ArrayBlockingQueue listConnected;
    private final int MAX_CLIENT = 10;

    public ServerImpl(File serverFile) throws RemoteException {
        super();
        this.syncState = 0;
        this.isStart = false;
        this.serverFile = serverFile;
        this.listConnected = null;
    }

    @Override
    public boolean connect(ClientInterface clientInt) throws RemoteException {
        if (this.listConnected.size() < MAX_CLIENT) {
            // push client to queue
            this.listConnected.add(clientInt);
            System.out.println("push to queue: " + clientInt.hashCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canWork(ClientInterface clientInt) throws RemoteException {

        if (this.listConnected.peek().equals(clientInt)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void start() throws Exception {
        // registry port 3000
        rmiRegistry = LocateRegistry.createRegistry(3000);
        //Thay thế các ràng buộc, rebind() để tránh lỗi trong trường hợp "server" đã tồn tại trong RMI Registry
        rmiRegistry.rebind("server", this);
        JOptionPane.showMessageDialog(null, "Server Started");
        isStart = true;
        this.listConnected = new ArrayBlockingQueue(MAX_CLIENT);
    }

    @Override
    public void stop() throws Exception {
        int choice = JOptionPane.showConfirmDialog(null, " You are sure to stop application?");
        if (choice == JOptionPane.YES_OPTION) {
            isStart = false;
            syncState = 0;
            // Loại bỏ ràng buộc trong this registry
            rmiRegistry.unbind("server");
            unexportObject(this, true);
            // Removes the remote object, obj, from the RMI runtime.
            unexportObject(rmiRegistry, true);

            this.listConnected.clear();
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
    public OutputStream getOutputStreamFile(File file) throws Exception {
        return new SerializableOutputStream(new FileOutputStream(file));
    }

    @Override
    public InputStream getInputStreamFile(File file) throws Exception {
        return new SerializableInputStream(new FileInputStream(file));
    }

    @Override
    public void setSyncState(int state) throws RemoteException {
        this.syncState = state;
    }

    @Override
    public void disConnect(ClientInterface clientInt) throws RemoteException {
        System.out.println("A client disconnected: " +  clientInt.hashCode());
        this.listConnected.remove(clientInt);
    }
}
