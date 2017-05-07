/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.joda.time.DateTimeUtils;

public class Synchronization implements Runnable {

    private final int DEFAULT_SIZE = 16 * 1024 * 1024;
    private boolean isDone;
    public int state;
    private File clientFile, serverFile;
    private ClientInterface client;
    private ServerInterface server;

    public Synchronization(boolean isDone, File clientFile, File serverFile, ClientInterface client, ServerInterface server) {
        this.state = 0;
        this.isDone = isDone;
        this.clientFile = clientFile;
        this.serverFile = serverFile;
        this.client = client;
        this.server = server;
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

    public void stopSync() {
        toLog("Stopping synchronization...");
        DateTimeUtils.setCurrentMillisSystem();
        toLog("Current time" + DateTimeUtils.currentTimeMillis());
        this.isDone = true;
    }

    public void copyFile(File srcFile, File destFile) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        toLog("Copying : " + srcFile.getAbsolutePath() + " -> " + destFile.getAbsolutePath());
        try {
            switch (this.state) {
                case 1:
                    // thực hiện coppy file từ client to server
                    is = new FileInputStream(srcFile);
                    os = this.server.getOutputStreamFile(destFile);// gửi file này tới server
                    break;
                case 2:
                    // coppy file từ server to client
                    is = this.server.getInputStreamFile(srcFile);
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

    // function synchronize.
    public void synchronize(File src, File des) throws Exception {
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
                synchronize(srcFile, desFile);
                des.setLastModified(src.lastModified());
            }
        } else {
            if (des.exists() && des.isDirectory()) {
                delete(des);
            }
            copyFile(src, des);
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
        long clientLM = getLastModified(this.clientFile);
        long serverLM = getLastModified(this.serverFile);
        long clientCount = getFileCount(this.clientFile);
        long serverCount = getFileCount(this.serverFile);
        toLog("Info: \n"
                + "length: " + clientCount + " - " + serverCount + "\n"
                + "lastModified: " + clientLM + " - " + serverLM);
        if (clientLM > serverLM) {
            // sync from client to server
            this.state = 1;
            toLog("xxx");
        } else if (serverLM > clientLM) {
            // sync from server to client
            this.state = 2;
        } else // same last modified
        // check for count files
        {
            if (clientCount > serverCount) {
                // client more files than server
                // sync: client -> server
                this.state = 1;
            } else if (serverCount > clientCount) {
                // sync: server -> client
                this.state = 2;
            } else {
                this.state = 0;
                // Nothing change
                // do whaterver you want
            }
        }
    }

    private static void toLog(String text) {
        TheMainScreenClient.tAre_show.append(text + "\n");
    }

    @Override
    public void run() {
        try {
            while (!isDone) {
                // change logic check for synch
                check();
                switch (this.state) {
                    case 1:
                        synchronize(this.clientFile, this.serverFile);
                        break;
                    case 2:
                        synchronize(this.serverFile, this.clientFile);
                        break;
                    default:
                        toLog("Nothing change");
                        break;
                }
                this.client.setSynState(this.state);
                this.server.setSyncState(this.state);
                Thread.sleep(10000);
                if (!this.server.isStart()) {
                    toLog("server đã ngừng hoạt động");
                    this.server.disConnect(this.client);
                    break;
                }
            }

        } catch (Exception ex) {
            toLog("Something wrong in thread");
            toLog("error: " + ex.getMessage());
            this.isDone = true;
        }
        toLog("Thread is Stoped");
    }

    public boolean isDone() {
        return isDone;
    }

    public int getState() {
        return state;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setState(int state) {
        this.state = state;
    }
}
