/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public boolean connect (ClientInterface clientInt) throws RemoteException;
    public boolean canWork(ClientInterface clientInt) throws RemoteException;
    public void disConnect(ClientInterface clientInt) throws RemoteException;
    public void setSyncState(int state) throws RemoteException;
    public void start() throws Exception;
    public void stop() throws Exception;
    public boolean isStart() throws RemoteException;
    public File getServerFile() throws RemoteException;
    public OutputStream getOutputStreamFile(File file) throws Exception;
    public InputStream getInputStreamFile(File file) throws Exception;
    
}
