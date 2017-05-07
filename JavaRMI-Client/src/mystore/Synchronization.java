/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTextArea;
import org.joda.time.DateTimeUtils;

public class Synchronization implements Runnable {

    static final int DEFAULT_SIZE = 16 * 1024 * 1024;
    private static boolean isDone;
    public static int state;
    private static File clientFile;
    private static File serverFile;
    private static ClientInterface client;
    private static ServerInterface server;
    private static String rootFilePath;
    static File scrFile;
    static File destFile;

    public Synchronization(boolean isDone, File clientFile, File serverFile, ClientInterface client, ServerInterface server) {
        Synchronization.state = 0;
        Synchronization.isDone = isDone;
        Synchronization.clientFile = clientFile;
        Synchronization.serverFile = serverFile;
        Synchronization.client = client;
        Synchronization.server = server;
    }
 
    public static void delete(File file) {
        if (file.isDirectory()) {
            for (File subfile : file.listFiles()) {
                delete(subfile);
            }
        }
        if (file.exists()) {
            if (!file.delete()) {
                toLog("không thể xóa file    " + file);
            }
        }
    }
    public static void stopsync() {
        DateTimeUtils.setCurrentMillisSystem();
        toLog("Current time" + DateTimeUtils.currentTimeMillis());
        toLog("Stop synchronizing...");
        isDone = true;
    }
    
    public static void copyFile(File srcFile, File destFile, long chunksize) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        toLog("Copying : " + srcFile.getAbsolutePath() + " -> " + destFile.getAbsolutePath());
        try {
            switch (state) {
                case 1:
                    // thực hiện coppy file từ client to server
                    is = new FileInputStream(srcFile);
                    os = server.getOutputStreamFile(destFile);// gửi file này tới server
                    break;
                case 2:
                    // coppy file từ server to client
                    is = server.getInputStreamFile(srcFile);
                    os = new FileOutputStream(destFile);
                    break;
                    // không làm gì vì 2 file ở client và server đã giống nhau
                default:

                    break;
            }
            //sử dụng mảng byte để đọc ghi dữ liệu. Chỗ này là tham khảo
            byte[] byteBuff = new byte[DEFAULT_SIZE];
            int len;
            while ((len = is.read(byteBuff)) >= 0) {
                os.flush();
                os.write(byteBuff, 0, len);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        toLog("Copy Done");
        boolean successTimestamp = destFile.setLastModified(srcFile.lastModified());
        if (!successTimestamp) {
            toLog(" không thể thay đổi dấu thời gian do đồng bộ hóa có vấn đề" + destFile);
        }
    }

    public static void synchronize(File src, File des, boolean smart) throws Exception {
        synchronize(src, des, smart, DEFAULT_SIZE);
    }

    // function synchronize.
    public static void synchronize(File src, File des, boolean smart, long chunkSize) throws Exception {
        if (chunkSize <= 0) {
            toLog(" sử dụng size mặc định");
            chunkSize = DEFAULT_SIZE;
        }
        if (src.isDirectory()) {
            if (!des.exists()) {
                if (!des.mkdirs()) {
                    throw new IOException("Không thể tạo đường dẫn" + des);
                } else if (!des.isDirectory()) {
                    throw new IOException("nguồn và đích không cùng loại" + src.getCanonicalFile() + des.getCanonicalPath());
                }
            }
            // Save name file of source
            String[] sources = src.list();
            // save name file of des
            String[] dest = des.list();
            // get list (name, key) of source.
            Set<String> srcNames = new HashSet<>(Arrays.asList(sources));
            // No resource is deleted.
            for (String filename : dest) {
                if (!srcNames.contains(filename)) {
                    delete(new File(des, filename));
                }
            }
            // coppy file/ folder.
            for (String filename : sources) {
                File srcFile = new File(src, filename);
                File desFile = new File(des, filename);
                synchronize(srcFile, desFile, smart, chunkSize);
                des.setLastModified(src.lastModified());
            }
        } else {
            if (des.exists() && des.isDirectory()) {
                delete(des);
            }
            copyFile(src, des, chunkSize);
        }
    }

    // the last modified file
    private long getLastModified(File file) {
        if (file.isDirectory()) {
            long fileLM = file.lastModified();
            File[] list = file.listFiles();

            for (File subFile : list) {
                long subFileLM = getLastModified(subFile);
                if (subFileLM > fileLM) {
                    fileLM = subFileLM;
                }
            }
            return fileLM;
        } else {
            return file.lastModified();
        }
    }

    private long getFileCount(File file) {
        long sum = 0;
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (File subFile : list) {
                long subFileCount = getFileCount(subFile);
                sum += subFileCount;
            }
            return sum;
        } else {
            return 1;
        }
    }

    // function check condition to synchronize
    private void check() {
        long clientLM = getLastModified(clientFile);
        long serverLM = getLastModified(serverFile);
        long clientCount = getFileCount(clientFile);
        long serverCount = getFileCount(serverFile);
        toLog("Info: \n"
                + "length: " + clientCount + " - " + serverCount + "\n"
                + "lastModified: " + clientLM + " - " + serverLM);
        if (clientLM > serverLM && clientCount > serverCount) {
            // sync from client to server
            state = 1;
            toLog("xxx");
        } else if (serverLM > clientLM && serverCount > clientCount) {
            // sync from server to client
            state = 2;
        } else // same last modified
        // check for count files
         if (clientCount > serverCount) {
                // client more files than server
                // sync: client -> server
                state = 1;
            } else if (serverCount > clientCount) {
                // sync: server -> client
                state = 2;
            } else {
                state = 0;
                // Nothing change
                // do whaterver you want
            }
    }

    private static void toLog(String text) {
        TheMainScreenClient.tAre_show.append(text + "\n");
    }

    @Override
    public void run() {
        boolean smart = true;
        try {
            while (!isDone) {
                // change logic check for synch
                check();
                switch (state) {
                    case 1:
                        synchronize(clientFile, serverFile, smart);
                        break;
                    case 2:
                        synchronize(serverFile, clientFile, smart);
                        break;
                    default:
                        toLog("Nothing change");
                        break;
                }
                client.setSynState(state);
                Thread.sleep(10000);
                if (!server.isStart()) {
                    toLog("server đã ngừng hoạt động");
                    server.disConnect(client);
                    break;
                }
            }

        } catch (Exception ex) {
            toLog("Something wrong in thread");
            toLog("error: " + ex.getMessage());
            isDone = true;
        }
        toLog("Thread is Stoped");
    }

    public static int getDefault_size() {
        return DEFAULT_SIZE;
    }

    public static boolean isIsDone() {
        return isDone;
    }

    public static int getState() {
        return state;
    }

    public static File getClientFile() {
        return clientFile;
    }

    public static File getServerFile() {
        return serverFile;
    }

    public static ClientInterface getClient() {
        return client;
    }

    public static ServerInterface getServer() {
        return server;
    }

    public static void setIsDone(boolean isDone) {
        Synchronization.isDone = isDone;
    }

    public static void setState(int state) {
        Synchronization.state = state;
    }

    public static void setClientFile(File clientFile) {
        Synchronization.clientFile = clientFile;
    }

    public static void setServerFile(File serverFile) {
        Synchronization.serverFile = serverFile;
    }

    public static void setClient(ClientInterface client) {
        Synchronization.client = client;
    }

    public static void setServer(ServerInterface server) {
        Synchronization.server = server;
    }
}
