/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DemTran chứa các phương thức và cách thức dồng bộ hóa
 *
 */
public class Synchronization implements Runnable {

    static final int DEFAULT_SIZE = 16 * 1024 * 1024;
    private static boolean isDone;
    private static int state;
    private static File clientFile;
    private static File serverFile;
    private static ClientInterface client;
    private static ServerInterface server;

    // khởi tạo hàm thôi 
    public Synchronization(boolean isDone, File clientFile, File serverFile, ClientInterface client, ServerInterface server) {
        state = 0;
        this.isDone = isDone;
        this.clientFile = clientFile;
        this.serverFile = serverFile;
        this.client = client;
        this.server = server;
    }

    // hàm này dùng để xóa file có thể là không dùng vì khi đồng bộ nó sẽ xóa hết mất flie trước đó
    // coppy code này trên mạng thật là vui 
    public static void delete(File file) {
        if (file.isDirectory()) {
            for (File subfile : file.listFiles()) {
                delete(subfile);
            }
        }
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("không thể xóa file    " + file);
            }
        }
    }

    // vâng vẫn tham khảo code trên mạng tiếp mình thật bá đạo. 
    // không cần kiểm tra file vì hàm này không sử dụng trực tiếp mà nó để hàm khác dùng
    public static void copyFile(File srcFile, File destFile, long chunksize) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        System.out.println("Copying : " + srcFile.getAbsolutePath() + " -> " + destFile.getAbsolutePath());
        try {
            if (state == 1) {// thực hiện coppy file từ client to server 
                is = new FileInputStream(srcFile);
                os = server.getOutputStreamFile(destFile);// gửi file này tới server
            } else if (state == 2) { // coppy file từ server to client
                is = server.getInputStreamFile(srcFile);
                os = new FileOutputStream(destFile);
            } else {
                // không làm gì vì 2 file ở client và server đã giống nhau
            }
//            is = new FileInputStream(srcFile);
//            os = new FileOutputStream(destFile);
            //sử dụng mảng byte để đọc ghi dữ liệu. Chỗ này là tham khảo
            byte[] byteBuff = new byte[DEFAULT_SIZE];
            int len = 0;
            while ((len = is.read(byteBuff)) >= 0) {
//                os.write(byteBuff);
//                os.flush();
                os.write(byteBuff, 0, len);

            }
            // test using FileChannel to coppy Files

//        FileChannel inFC = null, outFC = null;
//        try {
//            inFC = new FileInputStream(srcFile).getChannel();
//            outFC = new FileOutputStream(destFile).getChannel();
//            outFC.transferFrom(inFC, 0, inFC.size());
//        } finally {
//            inFC.close();
//            outFC.close();
//        }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        System.out.println("Copy Done");
        boolean successTimestamp = destFile.setLastModified(srcFile.lastModified());
        if (!successTimestamp) {
            System.out.println(" không thể thay đổi dấu thời gian do đồng bộ hóa có vấn đề" + destFile);
        }

    }

    public static void synchronize(File src, File des, boolean smart) throws Exception {
        synchronize(src, des, smart, DEFAULT_SIZE);
        System.out.println(DEFAULT_SIZE);
    }

    // thật ra ở đây dùng đệ quy
    public static void synchronize(File src, File des, boolean smart, long chunkSize) throws Exception {
        if (chunkSize <= 0) {
            System.out.println(" sử dụng size mặc định");
            chunkSize = DEFAULT_SIZE;
            System.out.println(DEFAULT_SIZE);
        }
        if (src.isDirectory()) {
            if (!des.exists()) {
                if (!des.mkdirs()) {
                    throw new IOException("Không thể tạo đường dẫn" + des);
                } else if (!des.isDirectory()) {
                    throw new IOException("nguồn và đích không cùng loại" + src.getCanonicalFile() + des.getCanonicalPath());
                }
            }

            String[] sources = src.list();// lưu các tên file của src
            String[] dest = des.list();// lưu các tên file của des
            Set<String> srcNames = new HashSet<String>(Arrays.asList(sources));// lệnh này chưa hiểu nhưng nó dùng để delete
            // xóa các file không phải là của nguồn
            for (String filename : dest) {
                if (!srcNames.contains(filename)) {
                    delete(new File(des, filename));
                }
            }
            // coppy dữ liệu thôi
            for (String filename : sources) {
                File srcFile = new File(src, filename);
                File desFile = new File(des, filename);
                synchronize(srcFile, desFile, smart, chunkSize);
            }
        } else {
            if (des.exists() && des.isDirectory()) {
                delete(des);
            }
            copyFile(src, des, chunkSize);
        }
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

    private void checkxxx() {
        long clientLM = clientFile.lastModified();
        long serverLM = serverFile.lastModified();
        long clientCount = clientFile.list().length;
        long serverCount = serverFile.list().length;
        System.out.println("Info: \n"
                + "length: " + clientCount + " - " + serverCount + "\n"
                + "lastModified: " + clientLM + " - " + serverLM);
        if (clientLM > serverLM) {
            // client modified after server -> sync from client to server
            //...
            state = 1;
        } else if (serverLM > clientLM) {
            // sync from server to client
            state = 2;//từ client đến server
        } else // same last modified
        // check for list files
        if (clientCount > serverCount) {
            // client more files than server
            // sync: client -> server
            state = 1;
        } else if (serverCount > clientCount) {
            // sync: server -> client
            state = 2;//từ client đến server
        } else {
            state = 0;
            // Nothing change
            // do whaterver you want
        }
    }

    @Override
    public void run() {
        boolean smart = true;
        try {
            while (!isDone) {

//               if(clLength>svLength||clientLM>serverLM ) {
//                   System.out.println("Upload : Client -> Server");
//                   state=1;// từ server lên client
//                   synchronize(clientFile,serverFile,smart);
//               }else if(clLength<svLength||clientLM<serverLM){
//                   System.out.println("Download: Server -> Client");
//                   state=2;//từ client đến server
//                   synchronize(serverFile,clientFile,smart);
//               }else{
//                   state=0;
//                   // không làm gì cả
//               }
                // change logic check for synch
                checkxxx();
                if (state == 1) {
                    synchronize(clientFile, serverFile, smart);
                } else if (state == 2) {
                    synchronize(serverFile, clientFile, smart);
                } else {
                    System.out.println("Nothing change");
                }
                client.setSynState(state);
                //server.synState(client); // chưa implement
                Thread.sleep(10000);
                if (server.isStart()) {
                    continue;
                } else {
                    System.out.println("server đã ngừng hoạt động");
                    server.disConnect(client);
                    break;
                }
            }

        } catch (Exception ex) {
            System.out.println("Something wrong in thread");
            ex.printStackTrace();
            isDone = true;
        }
        System.out.println("Thread Stoped");
    }

    public static void stopsync() {
//        System.out.println("Stop sync");
//		DateTimeUtils.setCurrentMillisSystem();
//		System.out.println("Current time" + DateTimeUtils.currentTimeMillis());
//		isDone = true;
        System.out.println("dừng đồng bộ hóa");
        isDone = true;
    }

}
